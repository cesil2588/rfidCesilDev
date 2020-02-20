package com.systemk.spyder.Service.CustomBean;

public class ForceBoxGroup {

    private String style;

    private String color;

    private String size;

    private Long count;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ForceBoxGroup() {
    }

    public ForceBoxGroup(String style, String color, String size, Long count) {
        this.style = style;
        this.color = color;
        this.size = size;
        this.count = count;
    }
}
