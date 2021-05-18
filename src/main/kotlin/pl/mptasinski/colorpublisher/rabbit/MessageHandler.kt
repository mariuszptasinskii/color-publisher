package pl.mptasinski.colorpublisher.rabbit

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.function.Consumer
import java.util.function.Supplier

@Configuration
class MessageHandler {

    companion object {
        val processor: Sinks.Many<Message<*>> = Sinks.many().multicast().onBackpressureBuffer()
        var logger = KotlinLogging.logger {}
    }

    @Bean
    fun sendColor(): Supplier<Flux<Message<*>>> {
        return Supplier {
            processor.asFlux()
        }
    }

    @Bean
    fun receive(): Consumer<Flux<Message<*>>> {
        return Consumer {
            it.subscribe {
                logger.info { "Your mapped color is: ${it.payload}" }
            }
        }
    }
}