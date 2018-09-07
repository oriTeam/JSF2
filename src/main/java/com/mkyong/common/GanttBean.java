/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


/**
 * @author nkt-pvt
 */

@ManagedBean(name = "ganttBean")
@SessionScoped
public class GanttBean extends AbstractBean {

    private UploadedFile excelFile;
    private static final String FILENAME = "/home/vantrong291/Documents/updatedData.txt";
    private String UPLOADFOLDER = "/home/vantrong291/workspaces/java/JavaServerFaces/uploads";

    public void upload(FileUploadEvent e) {
        this.excelFile = e.getFile();
//        File newFile = (File) this.file;
//        System.out.println(newFile.toString());
        System.out.println("Uploaded File Name Is :: " +
                this.excelFile.getFileName() +
                " :: Uploaded File Size :: " + this.excelFile.getSize());
        if (this.excelFile != null) {
            FacesMessage message = new FacesMessage("Succesful", this.excelFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }



    @Override
    void processData(GanttEntity gantt) {
        // save data to FILENAME
        String dat = gantt.toString();
        try {
            FileUtils.writeStringToFile(new File(FILENAME), dat);
//            System.out.println("Save");
//            System.out.println(this.data);

        } catch (IOException ex) {
            Logger.getLogger(GanttEntity.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.out.println(ex);
        }
    }

    @Override
    GanttEntity retrieveData() {
        //      do something to retieve data
        //      Ex: load data from db

        GanttEntity gantt = new GanttEntity();
        return gantt;
    }

    public String uploadExcel(FileUploadEvent e) {
        // get fileContent
        try {
            this.excelFile = e.getFile();

            String filename = FilenameUtils.getName(excelFile.getFileName());
            InputStream input = excelFile.getInputstream();
            File uploadedFile = new File(UPLOADFOLDER, filename);
            OutputStream output = new FileOutputStream(uploadedFile);
            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
            String fileData = new String(Files.readAllBytes(Paths.get(UPLOADFOLDER + "/" + filename)));


//            if(this.excelFile != null) {
//                FacesMessage message = new FacesMessage("Succesful", this.excelFile.getFileName() + " is uploaded.");
//                FacesContext.getCurrentInstance().addMessage(null, message);
//            }


            GanttEntity ganttEntity = new GanttEntity(fileData);
            updateData(ganttEntity);
            System.out.print(fileData);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String contextPath = origRequest.getContextPath();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "test.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return "success";
        } catch (IOException ex) {
            Logger.getLogger(GanttEntity.class.getName()).log(Level.SEVERE, null, ex);
            return "failed";
        } catch (NullPointerException ex) {
            Logger.getLogger(GanttEntity.class.getName()).log(Level.SEVERE, null, ex);
            return "failed";
        }

    }

    @Override
    public void pushData(String dat) {
        GanttEntity ganttEntity = GanttEntity.parseToJson(dat);
        processData(ganttEntity);
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Successful",  "Save") );

    }


}
