package pl.mptasinski.colorpublisher.api.config

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.annotation.PostConstruct

@ConstructorBinding
@ConfigurationProperties("colors")
data class ColorsConfig(
    val mapping: Map<String, String>,
    var colorsBiMapping: BiMap<String, String>?
) {

    @PostConstruct
    fun setup() {
        colorsBiMapping = HashBiMap.create(mapping).inverse()
    }

    fun getColorName(color: String?): String? {
        return color?.let {
            colorsBiMapping?.get(it)
        }

    }

}