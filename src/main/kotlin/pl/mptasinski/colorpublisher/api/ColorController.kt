package pl.mptasinski.colorpublisher.api

import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.mptasinski.colorpublisher.api.model.ColorRequest
import pl.mptasinski.colorpublisher.service.ColorService
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/colors")
class ColorController(private val colorService: ColorService) {

    companion object : KLogging()

    @GetMapping()
    fun getColors(): ResponseEntity<String> =
        ResponseEntity.ok("Your color")

    @PostMapping("/publish")
    fun publishColors(@RequestBody @Valid colorRequest: Mono<ColorRequest>): Mono<ResponseEntity<Boolean>> {
        logger.info { "Received a request to publish some rgb colors" }

        return colorRequest.flatMap { colorService.publishColors(it.colors) }
            .thenReturn(ResponseEntity.accepted().body( true))
    }


}