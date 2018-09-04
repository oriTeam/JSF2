/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author nkt-pvt
 */

public abstract class AbstractBean implements Serializable{
    String data;

    void pushData(String dat) {}

    void updateData(GanttEntity ganttEntity) {
        String ganttString = ganttEntity.toString();
        this.data = ganttString;
    }

    public final String getData(){
        return this.data;
    }
    
//    public final void setData(String data){
//        GanttEntity gantt = GanttEntity.parseJson(data);
//        processData(gantt);
//    }
    
    abstract void processData(GanttEntity gantt);
    
    abstract GanttEntity retrieveData();
}
