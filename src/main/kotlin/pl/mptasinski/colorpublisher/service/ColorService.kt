package pl.mptasinski.colorpublisher.service

import mu.KLogging
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import pl.mptasinski.colorpublisher.api.model.Color
import pl.mptasinski.colorpublisher.config.ColorsConfig
import pl.mptasinski.colorpublisher.rabbit.MessageHandler
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class ColorService(val colorsConfig: ColorsConfig) {

    companion object : KLogging()

    fun publishColors(colors: List<Color>): Flux<String?> {
        return Flux.fromIterable(colors)
            .filter { it.publish }
            .mapNotNull { colorsConfig.getColorName(it.rgbColor) }
            .doOnNext { processMessage(it) }
            .doOnError { logger.error { it } }

    }

    fun mapColorToRGBColor(color: String): Mono<String?> {
        return Mono.just(color)
            .map { colorsConfig.getRGBColor(it) }
            .doOnError { logger.error { } }
    }

    private fun processMessage(color: String?) {
        color?.let {
            logger.info { "Your color is: $it " }
            MessageHandler.processor.tryEmitNext(MessageBuilder.withPayload(it).build())
        }
    }
}