package mptasinski.co.brick.task.mapping;

import mptasinski.co.brick.task.dto.ColorMessageDto;

@javax.inject.Singleton
public class ColorMessageMapper {

    public ColorMessageDto mapToColorMessageDto(String color) {
        ColorMessageDto colorMessageDto = new ColorMessageDto();
        colorMessageDto.setColor(color.toUpperCase());

        return colorMessageDto;
    }
}
