package mptasinski.co.brick.task.api.model;

public class Color {

    private String color;
    private boolean publish;

    public Color(String color, boolean publish) {
        this.color = color;
        this.publish = publish;
    }

    public Color() {

    }

    public Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
