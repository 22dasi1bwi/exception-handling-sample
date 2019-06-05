package exception.handling.sample

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("exception.handling.sample")
                .mainClass(Application.javaClass)
                .start()
    }
}