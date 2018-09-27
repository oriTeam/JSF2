package com.mkyong.common;


import java.util.ArrayList;

public class GanttRow {
    private String name;
    private int height = 3;
    private String color = "#fff";
    private ArrayList<Task> tasks;

    public String toJson(){
        String ganttRowJson = "{";
        ganttRowJson += "\"name\":\"" + this.name.toString() + "\",";
        ganttRowJson += "\"height\":\"" + Integer.toString(this.height) + "em\",";
        ganttRowJson += "\"color\":\"" + this.color.toString() + "\",";
        ganttRowJson += "\"tasks\":[";
        for(Task task : this.tasks) {
            ganttRowJson += task.toJson() + ",";
        }
        ganttRowJson = ganttRowJson.substring(0, ganttRowJson.length() - 1);
        ganttRowJson += "]";
        ganttRowJson += "}";
        return ganttRowJson;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}