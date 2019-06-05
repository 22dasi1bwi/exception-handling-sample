package exception.handling.sample.api

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import java.util.*
import javax.inject.Singleton

data class VehicleModel (val countryCode: CountryCode)

data class CountryCode(@JsonValue val value: String) {
    override fun toString() = value

    init {
        if(!Locale.getISOCountries().contains(value)){
            throw InvalidInputException(
                    ErrorMessage(ErrorCode.SERVICE_UNAVAILABLE,
                            "$value is not allowed as country code. Only ISO 3166-1 alpha-2 country codes are allowed."))
        }

    }

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromValue(value: String) = CountryCode(value)
    }
}

class InvalidInputException (val errorMessage: ErrorMessage) : Exception()

data class ErrorMessage(@JsonProperty("error-code") val errorCode: ErrorCode?, @JsonProperty("error-message") val message: String?)

enum class ErrorCode {
    SERVICE_UNAVAILABLE
}

@Produces
@Singleton
@Requires(classes = [InvalidInputException::class, ExceptionHandler::class])
class InvalidInputExceptionHandler : ExceptionHandler<InvalidInputException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: InvalidInputException?): HttpResponse<Any> =
            HttpResponse.badRequest(exception?.errorMessage)
}