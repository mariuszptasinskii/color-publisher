package pl.mptasinski.colorpublisher.api

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import pl.mptasinski.colorpublisher.service.ColorHandler

@Configuration
class ColorRouter(private val colorHandler: ColorHandler) {

    @Bean
    fun router(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/colors".nest {
                GET("/{color}", colorHandler::handleMapToRgbColor)
                POST("/publish", colorHandler::handlePublishColors)
            }
        }
    }
}
