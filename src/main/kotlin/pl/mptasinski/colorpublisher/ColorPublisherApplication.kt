package pl.mptasinski.colorpublisher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.mptasinski.colorpublisher.api.config.ColorsConfig

@SpringBootApplication
@EnableConfigurationProperties(ColorsConfig::class)
class ColorPublisherApplication

fun main(args: Array<String>) {
	runApplication<ColorPublisherApplication>(*args)
}
