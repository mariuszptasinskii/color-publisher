package pl.mptasinski.colorpublisher.service

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.mptasinski.colorpublisher.api.model.ColorRequest
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono

@Component
class ColorHandler(private val colorService: ColorService) {

    fun handleMapToRgbColor(request: ServerRequest): Mono<ServerResponse> {
        return Mono.justOrEmpty(request.pathVariable("color"))
            .flatMap { colorService.mapColorToRGBColor(it) }
            .flatMap { ServerResponse.ok().bodyValue("Your RGB color is '$it'") }
            .onErrorResume { ServerResponse.badRequest().body("We couldn't match your color", String::class.java) }
    }

    fun handlePublishColors(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(ColorRequest::class.java)
            .flatMapMany { colorService.publishColors(it.colors) }
            .then( ServerResponse.ok().bodyValue("Your request has been successfully processed!"))
    }
}