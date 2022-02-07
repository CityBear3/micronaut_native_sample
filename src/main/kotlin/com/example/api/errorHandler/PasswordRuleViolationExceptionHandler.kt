package com.example.api.errorHandler

import com.example.api.response.ErrorResponse
import com.example.exception.PasswordRuleViolationException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [PasswordRuleViolationException::class, ExceptionHandler::class])
class PasswordRuleViolationExceptionHandler :
    ExceptionHandler<PasswordRuleViolationException, HttpResponse<ErrorResponse>> {
    override fun handle(
        request: HttpRequest<*>?,
        exception: PasswordRuleViolationException
    ): HttpResponse<ErrorResponse> {
        return HttpResponse.badRequest(ErrorResponse(exception.code, exception.message))
    }
}
