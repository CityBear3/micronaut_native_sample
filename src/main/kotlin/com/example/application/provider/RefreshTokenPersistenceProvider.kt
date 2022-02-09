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
import java.util.*

@Singleton
class RefreshTokenPersistenceProvider(private val userRepositoryExecute: UserRepositoryExecute) :
    RefreshTokenPersistence {
    override fun persistToken(event: RefreshTokenGeneratedEvent?) {
        if (event != null) {
            val payload = event.refreshToken
            userRepositoryExecute.updateById(UUID.fromString(event.authentication.name), payload, false)
        }
    }

    override fun getAuthentication(refreshToken: String?): Publisher<Authentication> {
        return Flux.create({ emitter: FluxSink<Authentication> ->
            val tokenOpt = userRepositoryExecute.findByToken(refreshToken!!)
            if (tokenOpt != null) {
                if (tokenOpt.userRevoked) {
                    emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null))
                } else {
                    emitter.next(Authentication.build(tokenOpt.userId.toString(), listOf(tokenOpt.userRole.toString())))
                    emitter.complete()
                }
            } else {
                emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
