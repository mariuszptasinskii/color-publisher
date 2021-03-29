package pl.mptasinski.colorpublisher.api.service

import mu.KLogging
import org.springframework.stereotype.Service
import pl.mptasinski.colorpublisher.api.config.ColorsConfig
import pl.mptasinski.colorpublisher.api.model.Color
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ColorService(val colorsConfig: ColorsConfig) {

    companion object : KLogging()

    fun publishColors(colors: List<Color>): Mono<Void> {

        return Flux.fromIterable(colors)
            .filter { it.publish }
            .mapNotNull { colorsConfig.getColorName(it.rgbColor) }
            .doOnNext{ logger.info { "Your color is: $it "}}
            .doOnError{ logger.error { it }}
            .then()
    }
}