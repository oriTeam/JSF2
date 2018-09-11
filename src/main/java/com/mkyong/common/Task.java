package com.mkyong.common;

import java.util.Date;

public class Task {
    private String id;
    private String name;
    private String color;
    private String from;
    private String to;
    private String data;
    private int progress_percent = 100;
    private String progress_color = "#3C8CF8";

    public Task(String id, String name, String color, String from, String to, String data, int progress_percent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.from = from;
        this.to = to;
        this.data = data;
        this.progress_percent = progress_percent;
    }

    public String toJson() {
        String jsonTask = "{";
        jsonTask += "\"id\":\"" + this.id.toString() + "\",";
        jsonTask += "\"name\":\"" + this.name.toString() + "\",";
        jsonTask += "\"color\":\"" + this.color.toString() + "\",";
        jsonTask += "\"from\":\"" + this.from.toString() + "\",";
        jsonTask += "\"to\":\"" + this.to.toString() + "\",";
        jsonTask += "\"data\":\"" + this.data.toString() + "\",";
        jsonTask += "\"progress\":{" + "\"percent\":\"" + String.valueOf(this.progress_percent) + "\"," + "\"color\":\"" + this.progress_color.toString() + "\"" + "}";
        jsonTask += "}";
        return jsonTask;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgress_color() {
        return progress_color;
    }

    public void setProgress_color(String progress_color) {
        this.progress_color = progress_color;
    }

    public int getProgress_percent() {
        return progress_percent;
    }

    public void setProgress_percent(int progress_percent) {
        this.progress_percent = progress_percent;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}