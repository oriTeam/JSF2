/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import javax.servlet.http.Part;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author nkt-pvt
 */

public class GanttEntity {
//    String tasks;
//    String resources;
    private String content;
    private ArrayList<GanttRow> ganttRows;

    public String toJson() {
        String ganttEntity = "[";
        for(GanttRow ganttRow: this.ganttRows) {
            ganttEntity += ganttRow.toJson() + ",";
        }
        ganttEntity = ganttEntity.substring(0, ganttEntity.length() - 1);
        ganttEntity += "]";
        return ganttEntity;
    }

    public GanttEntity(){};

    public GanttEntity(String content) {
        this.content = content;
    }

    public static GanttEntity parseToJson(String data){
        return new GanttEntity(data);
    }
    
    public void save(){
        //save to backend
    }
    
    public String toString(){
        return this.content;
    }

    public ArrayList<GanttRow> getGanttRows() {
        return ganttRows;
    }

    public void setGanttRows(ArrayList<GanttRow> ganttRows) {
        this.ganttRows = ganttRows;
    }
}
