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
class UsedEmailExceptionHandler(private val errorResponseFactory: ErrorResponseFactory) :
    ExceptionHandler<UsedEmailException, HttpResponse<ErrorResponse>> {
    override fun handle(request: HttpRequest<*>?, exception: UsedEmailException?): HttpResponse<ErrorResponse> {
        if (exception != null) {
            return HttpResponse.badRequest(
                errorResponseFactory.build(
                    HttpStatus.BAD_REQUEST,
                    exception.code,
                    exception.message
                )
            )
        }
        return HttpResponse.serverError(errorResponseFactory.buildUnknownError())
    }
}
