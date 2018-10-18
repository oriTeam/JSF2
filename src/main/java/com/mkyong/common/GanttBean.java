/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import java.lang.Number;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.mkyong.pojo.ExcelWorkbook;
import com.mkyong.pojo.ExcelWorksheet;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


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
        // get fileCdontent
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
            //      String fileData = new String(Files.readAllBytes(Paths.get(UPLOADFOLDER + "/" + filename)));
            String fileData =  excelToJson(uploadedFile);

            GanttEntity ganttEntity = new GanttEntity(fileData);
            updateData(ganttEntity);

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
        context.addMessage(null, new FacesMessage("Save Successfully !!",  "Save Successfully !!") );
    }

    public String excelToJson(File excelFile) {
        String jsonResult = "";
        GanttEntity ganttEntity = new GanttEntity();
        ArrayList<GanttRow> ganttRows = new ArrayList<GanttRow>();

        try {
            FileInputStream excelInput = new FileInputStream(excelFile);
            Workbook workbook = new XSSFWorkbook(excelInput);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if(row.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();

                GanttRow ganttRow = new GanttRow();

                //Name for ganttRow
                ganttRow.setName(row.getCell(1).getStringCellValue());

                //Create a new ArrayList of Tasks
                ArrayList<Task> taskArrayList = new ArrayList<Task>();

                //Create a new task
                String id = getStringValue(row.getCell(0), workbook);
                String name = getStringValue(row.getCell(0), workbook);
                String color = "#f5f5f5";
                String from = getStringValue(row.getCell(2), workbook);
                String to = getStringValue(row.getCell(3), workbook);
                String data = getStringValue(row.getCell(0), workbook);
                String process_string = getStringValue(row.getCell(4), workbook);
                if(process_string.contains("%")) {
                    process_string = process_string.replace("%", "");
                }
                int progress_percent = (int) Double.parseDouble(process_string);
                //int progress_percent = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(4)));

                Task task = new Task(id, name, color, from, to, data, progress_percent);

                taskArrayList.add(task);
                ganttRow.setTasks(taskArrayList);
                //add to Rowlist
                ganttRows.add(ganttRow);
            }
        }
        catch (FileNotFoundException fnfe) {
            fnfe.fillInStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ganttEntity.setGanttRows(ganttRows);
            jsonResult = ganttEntity.toJson();
        }
        return jsonResult;
    }

    public String getStringValue(Cell cell, Workbook workbook) {
        CellType cellType = cell.getCellTypeEnum();
        String stringResult = "";
        switch (cellType){
            case _NONE:
                stringResult = "No Data";
                break;
            case BOOLEAN:
                stringResult = new String(new Boolean(cell.getBooleanCellValue()).toString());
//                stringResult = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                stringResult = "No Data";
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ");
                    stringResult = dateFormat.format(cell.getDateCellValue());
                }
                else if (cell.getCellStyle().getDataFormatString().contains("%")) {
                    // Detect Percent Values
                    Double value = cell.getNumericCellValue() * 100;
                    stringResult = new String(value.toString());
                }
                else {
                    Double value = cell.getNumericCellValue();
                    stringResult = new String(value.toString());
                };

                break;
            case STRING:
                stringResult = cell.getStringCellValue();
                break;
            case FORMULA:
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                stringResult = String.valueOf(evaluator.evaluate(cell).getNumberValue());
                break;
            case ERROR:
                stringResult = "Error";
                break;

                default:
                    stringResult = cell.getStringCellValue();
                    break;
        }
        return stringResult;
    }
}
