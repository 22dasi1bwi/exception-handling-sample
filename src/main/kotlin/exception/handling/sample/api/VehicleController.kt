package exception.handling.sample.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/vehicle")
class VehicleController {

    @Post
    fun create (@Body requestBody: VehicleModel) = CountryCode("DE")
}