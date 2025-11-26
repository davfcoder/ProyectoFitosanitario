/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.libreria;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    /**
     * Método 1: Exportar una tabla completa (Para la Tabla de Inspecciones).
     */
    public boolean exportarTablaGeneral(JTable table, File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte General");
            TableModel model = table.getModel();

            // 1. Crear Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
                
                // Estilo negrita para encabezado (Opcional)
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // 2. Llenar Datos
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    Row.MissingCellPolicy policy = Row.MissingCellPolicy.CREATE_NULL_AS_BLANK;
                    Cell cell = row.createCell(j);
                    
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // 3. Autoajustar columnas
            for (int i = 0; i < model.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            // 4. Guardar archivo
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método 2: Exportar Detalle (Fila Seleccionada + Tabla Plagas).
     */
    public boolean exportarReporteIndividual(JTable tblMaestra, int filaSeleccionada, JTable tblDetalle, File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte Individual");
            
            // --- PARTE 1: DATOS DE LA INSPECCIÓN (Vertical) ---
            TableModel modelM = tblMaestra.getModel();
            int rowIdx = 0; // Índice de fila en Excel

            // Título
            Row titleRow = sheet.createRow(rowIdx++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE DE INSPECCIÓN FITOSANITARIA");
            rowIdx++; // Espacio

            // Escribir datos de la inspección seleccionada (Campo : Valor)
            for (int col = 0; col < modelM.getColumnCount(); col++) {
                // Saltamos la columna 0 si es el ID oculto, o la imprimimos si quieres
                Row row = sheet.createRow(rowIdx++);
                
                // Nombre de la columna (Label)
                Cell cellLabel = row.createCell(0);
                cellLabel.setCellValue(modelM.getColumnName(col));
                
                // Estilo negrita para label
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cellLabel.setCellStyle(style);

                // Valor
                Object value = modelM.getValueAt(filaSeleccionada, col);
                Cell cellValue = row.createCell(1);
                if (value != null) cellValue.setCellValue(value.toString());
            }

            rowIdx++; // Espacio en blanco separador
            rowIdx++; 

            // --- PARTE 2: TABLA DE PLAGAS ---
            Row subTitleRow = sheet.createRow(rowIdx++);
            subTitleRow.createCell(0).setCellValue("PLAGAS EVIDENCIADAS:");
            
            TableModel modelD = tblDetalle.getModel();

            // Encabezados de la tabla detalle
            Row headerRow = sheet.createRow(rowIdx++);
            for (int i = 0; i < modelD.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(modelD.getColumnName(i));
                // Estilo negrita
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Datos de la tabla detalle
            for (int i = 0; i < modelD.getRowCount(); i++) {
                Row row = sheet.createRow(rowIdx++);
                for (int j = 0; j < modelD.getColumnCount(); j++) {
                    Object value = modelD.getValueAt(i, j);
                    Cell cell = row.createCell(j);
                    if (value != null) cell.setCellValue(value.toString());
                }
            }

            // Autoajustar columnas (solo las primeras N columnas usadas)
            for (int i = 0; i < Math.max(modelM.getColumnCount(), modelD.getColumnCount()); i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
