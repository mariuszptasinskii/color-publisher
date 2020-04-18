package mptasinski.co.brick.task.api.model;

import java.util.ArrayList;
import java.util.List;

public class ColorRequest {

    private List<Color> colors = new ArrayList<>();

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }


}
