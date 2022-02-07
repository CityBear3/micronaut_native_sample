package com.example.application.provider

import com.example.application.service.PasswordEncodeService
import com.example.domain.user.UserEmail
import com.example.domain.user.UserRepositoryExecute
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class UserAuthenticationProvider(
    private val passwordEncoderService: PasswordEncodeService,
    private val userRepositoryExecute: UserRepositoryExecute
) : AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            if (authenticationRequest != null) {
                val user = userRepositoryExecute.findByUserEmail(UserEmail(authenticationRequest.identity as String))
                if (passwordEncoderService.matches(
                        authenticationRequest.secret as String,
                        user.userPassword.getValue()
                    )
                ) {
                    emitter.next(
                        AuthenticationResponse.success(
                            user.userId.toString(),
                            listOf(user.userRole.toString())
                        )
                    )
                    emitter.complete()
                } else {
                    emitter.error(AuthenticationResponse.exception())
                }
            } else {
                emitter.error(AuthenticationResponse.exception())
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
