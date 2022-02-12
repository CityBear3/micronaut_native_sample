package com.example.api.controller

import com.example.api.request.SignupRequest
import com.example.api.response.SignupResponse
import com.example.application.service.UserApplicationService
import com.example.domain.user.*
import com.example.exception.MissingRequiredParameterException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Controller("/api")
class UserController(
    private val userApplicationService: UserApplicationService
) {
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Consumes(MediaType.APPLICATION_JSON)
    @Post("/signup")
    fun signup(@Body signupRequest: SignupRequest?): Publisher<HttpResponse<SignupResponse>> {
        return Flux.create({emitter: FluxSink<HttpResponse<SignupResponse>> ->
            if (signupRequest != null) {
                val user = User(
                    UserName(signupRequest.name),
                    UserEmail(signupRequest.email),
                    UserPassword(signupRequest.password),
                    UserRole.ROLE_USER
                )
                val createdUser = userApplicationService.signup(user)
                emitter.next(HttpResponse.created(SignupResponse(HttpStatus.OK.code, createdUser.userId!!)))
                emitter.complete()
            } else {
                emitter.error(MissingRequiredParameterException())
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
