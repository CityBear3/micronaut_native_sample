package com.example.api.errorHandler

import com.example.api.response.ErrorResponse
import com.example.exception.UsedEmailException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [UsedEmailException::class, ExceptionHandler::class])
class UsedEmailExceptionHandler : ExceptionHandler<UsedEmailException, HttpResponse<ErrorResponse>> {
    override fun handle(request: HttpRequest<*>?, exception: UsedEmailException): HttpResponse<ErrorResponse> {
        return HttpResponse.status<ErrorResponse?>(HttpStatus.CONFLICT)
            .body(ErrorResponse(exception.code, exception.message))
    }
}
