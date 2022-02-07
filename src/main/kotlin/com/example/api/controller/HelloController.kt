package com.example.api.controller

import com.example.api.response.SampleResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api")
class HelloController {
    @Secured("ROLE_USER")
    @Get("/hello")
    fun greet(): Publisher<HttpResponse<SampleResponse>> {
        return Flux.create {
            it.next(HttpResponse.ok(SampleResponse(code = 200, message = "Hello, World!")))
            it.complete()
        }
    }
}
