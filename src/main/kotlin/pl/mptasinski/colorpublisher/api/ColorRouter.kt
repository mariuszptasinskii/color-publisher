package pl.mptasinski.colorpublisher.api

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import pl.mptasinski.colorpublisher.api.model.Color
import pl.mptasinski.colorpublisher.api.model.ColorRequest
import pl.mptasinski.colorpublisher.service.ColorService
import reactor.core.publisher.Mono

@Configuration
class ColorRouter(private val colorService: ColorService) {

    companion object : KLogging()

    @Bean
    fun router(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/colors".nest {
                GET() {
                    //todo add pathVariable and map color
                    ServerResponse.ok().bodyValue("Your color")
                }
                POST("/publish") { it ->
                    it.bodyToMono(ColorRequest::class.java)
                        .flatMap { colorService.publishColors(it.colors) }
                        .flatMap { ServerResponse.accepted().bodyValue(true) }
                }
            }
        }
    }
}
