package com.example.application.provider

import com.example.domain.user.UserRepositoryExecute
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

@Singleton
class RefreshTokenPersistenceProvider(private val userRepositoryExecute: UserRepositoryExecute) :
    RefreshTokenPersistence {
    override fun persistToken(event: RefreshTokenGeneratedEvent?) {
        if (event != null) {
            val payload = event.refreshToken
            val expireOn = OffsetDateTime.of(LocalDateTime.now().plusMonths(1), ZoneOffset.UTC)
            userRepositoryExecute.updateById(
                UUID.fromString(event.authentication.name),
                payload,
                false,
                expireOn.toInstant()
            )
        }
    }

    override fun getAuthentication(refreshToken: String?): Publisher<Authentication> {
        return Flux.create({ emitter: FluxSink<Authentication> ->
            val tokenOpt = userRepositoryExecute.findByToken(refreshToken!!)
            if (tokenOpt != null) {
                if (tokenOpt.userRevoked) {
                    emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null))
                }

                if (tokenOpt.expireOn != null) {
                    val expireOn = OffsetDateTime.ofInstant(tokenOpt.expireOn, ZoneOffset.UTC)
                    val nowTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC)
                    if (!expireOn.isAfter(nowTime)) {
                        emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token expired", null))
                    }
                }

                emitter.next(Authentication.build(tokenOpt.userId.toString(), listOf(tokenOpt.userRole.toString())))
                emitter.complete()

            } else {
                emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
