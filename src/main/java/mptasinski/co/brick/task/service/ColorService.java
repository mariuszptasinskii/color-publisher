package mptasinski.co.brick.task.service;

import mptasinski.co.brick.task.api.model.Color;
import mptasinski.co.brick.task.config.ColorsConfiguration;
import mptasinski.co.brick.task.mapping.ColorMessageMapper;
import mptasinski.co.brick.task.messaging.ColorRabbitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Singleton
public class ColorService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ColorService.class);

    @Inject
    private ColorsConfiguration colorsConfiguration;

    @Inject
    private ColorMessageMapper colorMessageMapper;

    @Inject
    private ColorRabbitClient colorRabbitClient;

    public void processColors(List<Color> colors) {
        colors.stream()
                .filter(Color::isPublish)
                .map(colorsConfiguration::getColorName)
                .filter(isNameCorrect(Objects::nonNull))
                .map(colorMessageMapper::mapToColorMessageDto)
                .forEach(colorRabbitClient::send);
    }

    private Predicate<String> isNameCorrect(Predicate<String> predicate) {
        return value -> {
            if (!predicate.test(value)) {
                LOGGER.warn("Color value is incorrect and won't be processed further");
                return false;
            }

            return true;
        };
    }
}
