package mptasinski.co.brick.task.service;

import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import mptasinski.co.brick.task.api.model.Color;
import mptasinski.co.brick.task.dto.ColorMessageDto;
import mptasinski.co.brick.task.mapping.ColorMessageMapper;
import mptasinski.co.brick.task.messaging.ColorRabbitClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@MicronautTest
class ColorServiceTest {

    @Inject
    ColorService colorService;

    @Inject
    ColorRabbitClient colorRabbitClient;

    @Inject
    ColorMessageMapper colorMessageMapper;

    @Test
    void testProcessColorsAllGood() {
        //for
        when(colorMessageMapper.mapToColorMessageDto(anyString())).thenReturn(new ColorMessageDto());
        //when
        colorService.processColors(getOnlyGoodColors());

        //then
        verify(colorMessageMapper, times(2)).mapToColorMessageDto(any());
        verify(colorRabbitClient, times(2)).send(any(ColorMessageDto.class));

    }

    @Test
    void testProcessColorsAllBad() {
        //for
        when(colorMessageMapper.mapToColorMessageDto(anyString())).thenReturn(new ColorMessageDto());

        //when
        colorService.processColors(getOnlyBadColors());

        //then
        verify(colorMessageMapper, times(0)).mapToColorMessageDto(anyString());
        verify(colorRabbitClient, times(0)).send(any(ColorMessageDto.class));
    }

    @Test
    void testProcessBadAndGoodColors() {
        when(colorMessageMapper.mapToColorMessageDto(anyString())).thenReturn(new ColorMessageDto());

        //when
        colorService.processColors(getGoodAndBadColors());

        //then
        verify(colorMessageMapper, times(2)).mapToColorMessageDto(anyString());
        verify(colorRabbitClient, times(2)).send(any(ColorMessageDto.class));
    }

    private List<Color> getGoodAndBadColors() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color("255,0,0", true));
        colors.add(new Color("0,255,0", true));
        colors.add(new Color("0,0,255", false));
        colors.add(new Color("0,0", true));
        colors.add(new Color("", true));

        return colors;
    }

    private List<Color> getOnlyGoodColors() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color("255,0,0", true));
        colors.add(new Color("0,255,0", true));

        return colors;
    }

    private List<Color> getOnlyBadColors() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color("0,0", true));
        colors.add(new Color("", true));

        return colors;
    }


    @MockBean(ColorRabbitClient.class)
    ColorRabbitClient colorRabbitClient() {
        return mock(ColorRabbitClient.class);
    }


    @MockBean(ColorMessageMapper.class)
    ColorMessageMapper colorMessageMapper() {
        return mock(ColorMessageMapper.class);
    }
}