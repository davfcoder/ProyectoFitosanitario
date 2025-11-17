package Clases.libreria;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtil {

    public static void generarReporteInspeccionExcel(List<Object[]> filas, String ruta) {
        Workbook wb = null;
        FileOutputStream fos = null;

        try {
            wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Inspección");

            // Encabezados
            Row header = sheet.createRow(0);
            String[] columnas = {
                    "ID Inspección",
                    "Fecha",
                    "Productor",
                    "Predio",
                    "Estado Fenológico",
                    "Cantidad Plantas",
                    "Observaciones"
            };

            for (int i = 0; i < columnas.length; i++) {
                header.createCell(i).setCellValue(columnas[i]);
            }

            // Formato fecha
            CreationHelper ch = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(ch.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));

            // Contenido
            int filaNum = 1;
            for (Object[] fila : filas) {
                Row row = sheet.createRow(filaNum++);
                for (int c = 0; c < fila.length; c++) {

                    Cell cell = row.createCell(c);
                    Object val = fila[c];

                    if (val == null) {
                        cell.setCellValue("");
                    } else if (val instanceof java.sql.Timestamp) {
                        cell.setCellValue((java.util.Date) val);
                        cell.setCellStyle(dateStyle);
                    } else if (val instanceof Number) {
                        cell.setCellValue(((Number) val).doubleValue());
                    } else {
                        cell.setCellValue(val.toString());
                    }
                }
            }

            // Ajustar columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar archivo
            fos = new FileOutputStream(ruta);
            wb.write(fos);

            JOptionPane.showMessageDialog(null,
                    "Reporte Excel generado correctamente en:\n" + ruta);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar el archivo Excel:\n" + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
