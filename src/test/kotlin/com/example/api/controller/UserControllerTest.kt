package com.example.api.controller

import com.example.api.request.SignupRequest
import com.example.api.response.SignupResponse
import com.example.exception.ErrorCode
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class UserControllerTest {
    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun testSignupSuccessful() {
        val body = SignupRequest("test", "test@example.com", "test-pass-1234")
        val signupRequest = HttpRequest.POST("/api/signup", body)
        val signupResponse = client.toBlocking().retrieve(signupRequest, SignupResponse::class.java)
        Assertions.assertNotNull(signupResponse.userID)
        Assertions.assertEquals(HttpStatus.OK.code, signupResponse.status)
    }

    @Test
    fun testSignupFailedByMissingAllElements() {
        val emptySignupRequest = null
        val signupRequest = HttpRequest.POST("/api/signup", emptySignupRequest)
        val exception = Assertions.assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking().exchange(
                signupRequest,
                Argument.of(SignupResponse::class.java),
                Argument.of(Map::class.java)
            )
        }
        Assertions.assertNotNull(exception)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status)

        val body = exception.response.getBody(Map::class.java)
        Assertions.assertTrue(body.isPresent)

        val response = body.get()
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.code, response["status"])
        Assertions.assertEquals(ErrorCode.LACK_OF_PARAMETER_ERROR.code, response["code"])
        Assertions.assertEquals("missing required elements error", response["message"])
    }
}
