package mptasinski.co.brick.task.config;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.micronaut.context.annotation.ConfigurationProperties;
import mptasinski.co.brick.task.api.model.Color;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties("colors")
public class ColorsConfiguration {

    private Map<String, String> mapping = new HashMap<>();
    private BiMap<String, String> colorsBiMap;

    @PostConstruct
    public void setUp() {
        colorsBiMap = HashBiMap.create(mapping);
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public String getColorName(Color color) {
        return colorsBiMap.inverse().get(color.getColor());
    }
}
