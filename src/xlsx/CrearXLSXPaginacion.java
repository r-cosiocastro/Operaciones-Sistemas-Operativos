package xlsx;

import java.awt.Desktop;
import java.io.File;
import objetos.TablaPaginacion;
import objetos.MemoriaPaginacion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krawz
 */
public class CrearXLSXPaginacion {
    
    Sheet sheet;
    Cell cell;

    public CrearXLSXPaginacion(MemoriaPaginacion memoria) {
        Workbook workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Log");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        Font fuenteAlgoritmo = workbook.createFont();
        fuenteAlgoritmo.setBold(true);
        fuenteAlgoritmo.setFontHeightInPoints((short) 14);
        fuenteAlgoritmo.setColor(IndexedColors.BLUE.getIndex());

        Font fuenteOcupado = workbook.createFont();
        fuenteOcupado.setBold(true);
        fuenteOcupado.setColor(IndexedColors.WHITE.getIndex());

        Font fuenteEspera = workbook.createFont();
        fuenteEspera.setBold(true);
        fuenteEspera.setColor(IndexedColors.WHITE.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        CellStyle nombreAlgoritmoStyle = workbook.createCellStyle();
        nombreAlgoritmoStyle.setFont(fuenteAlgoritmo);

        CellStyle tablaStyle = workbook.createCellStyle();
        tablaStyle.setAlignment(HorizontalAlignment.CENTER);
        tablaStyle.setBorderLeft(BorderStyle.MEDIUM);
        tablaStyle.setBorderRight(BorderStyle.MEDIUM);
        tablaStyle.setBorderTop(BorderStyle.MEDIUM);
        tablaStyle.setBorderBottom(BorderStyle.MEDIUM);

        CellStyle alignLeftStyle = workbook.createCellStyle();
        alignLeftStyle.setAlignment(HorizontalAlignment.LEFT);

        CellStyle alignRightStyle = workbook.createCellStyle();
        alignRightStyle.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle tablaStyleHeader = workbook.createCellStyle();
        tablaStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        tablaStyleHeader.setBorderLeft(BorderStyle.MEDIUM);
        tablaStyleHeader.setBorderRight(BorderStyle.MEDIUM);
        tablaStyleHeader.setBorderTop(BorderStyle.MEDIUM);
        tablaStyleHeader.setBorderBottom(BorderStyle.MEDIUM);
        tablaStyleHeader.setFont(headerFont);

        int rowNum = 0;

        sheet.createRow(rowNum++);
        Row row = createOrGetRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("Tamaño de pagina: " + memoria.getTamanoPagina() + " bytes");
        cell.setCellStyle(headerCellStyle);

        row = createOrGetRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("Tamaño de memoria física: " + memoria.getMemoriaFisica() + " bytes");
        cell.setCellStyle(headerCellStyle);

        row = createOrGetRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("Tamaño de memoria lógica: " + memoria.getMemoriaLogica() + " bytes");
        cell.setCellStyle(headerCellStyle);

        row = createOrGetRow(rowNum++);
        row = createOrGetRow(rowNum++);

        cell = row.createCell(1);
        cell.setCellValue("Memoria lógica");
        cell.setCellStyle(headerCellStyle);

        row = createOrGetRow(rowNum++);
        row = createOrGetRow(rowNum++);

        for (int x = 0; x < memoria.paginasMV.length; x++) {
            cell = row.createCell(1);
            if (memoria.paginasMV[x] == null) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(memoria.paginasMV[x].getProceso().getNombre());
            }
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(0);
            cell.setCellValue(x);
            cell.setCellStyle(alignRightStyle);

            row = createOrGetRow(rowNum++);
        }

        rowNum = 5;
        row = createOrGetRow(rowNum++);

        cell = row.createCell(9);
        cell.setCellValue("Memoria física");
        cell.setCellStyle(headerCellStyle);

        row = createOrGetRow(rowNum++);
        row = createOrGetRow(rowNum++);

        for (int x = 0; x < memoria.paginasMF.length; x++) {
            cell = row.createCell(9);
            if (memoria.paginasMF[x] == null) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(memoria.paginasMF[x].getProceso().getNombre());
            }
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(10);
            cell.setCellValue(x);
            cell.setCellStyle(alignLeftStyle);

            row = createOrGetRow(rowNum++);
        }

        rowNum = 5;
        row = createOrGetRow(rowNum++);

        cell = row.createCell(4);
        cell.setCellValue("Tablas de paginación");
        cell.setCellStyle(headerCellStyle);

        row = createOrGetRow(rowNum++);

        int izq_der = 0;
        int izq = 0;
        int der = 0;
        int actualRow;
        for (TablaPaginacion tp : memoria.tablasPaginacion) {
            actualRow = rowNum;
            row = createOrGetRow(actualRow++);
            if (izq_der % 2 == 0) {
                cell = row.createCell(3);
                cell.setCellValue("Proceso: " + tp.proceso.getNombre());
                cell.setCellStyle(headerCellStyle);
                row = createOrGetRow(actualRow++);
                for (int x = 0; x < tp.indexMV.length; x++) {
                    cell = row.createCell(3);
                    cell.setCellValue(tp.indexMV[x]);
                    cell.setCellStyle(tablaStyle);
                    row = createOrGetRow(actualRow++);
                }

                actualRow = rowNum + 1;
                row = createOrGetRow(actualRow++);

                for (int x = 0; x < tp.indexMF.length; x++) {
                    cell = row.createCell(4);
                    cell.setCellValue(tp.indexMF[x]);
                    cell.setCellStyle(tablaStyle);
                    row = createOrGetRow(actualRow++);
                }
                izq = tp.indexMF.length;
            } else {
                cell = row.createCell(6);
                cell.setCellValue("Proceso: " + tp.proceso.getNombre());
                cell.setCellStyle(headerCellStyle);
                row = createOrGetRow(actualRow++);
                for (int x = 0; x < tp.indexMV.length; x++) {
                    cell = row.createCell(6);
                    cell.setCellValue(tp.indexMV[x]);
                    cell.setCellStyle(tablaStyle);
                    row = createOrGetRow(actualRow++);
                }
                actualRow = rowNum + 1;
                row = createOrGetRow(actualRow++);
                for (int x = 0; x < tp.indexMF.length; x++) {
                    cell = row.createCell(7);
                    cell.setCellValue(tp.indexMF[x]);
                    cell.setCellStyle(tablaStyle);
                    row = createOrGetRow(actualRow++);
                }
                der = tp.indexMF.length;

                if (izq >= der) {
                    rowNum += izq + 2;
                } else {
                    rowNum += der + 2;
                }
            }
            izq_der++;
        }

        /*
        // Resize all columns to fit the content size
        for (int i = 0; i < columnasProcesos.length; i++) {
            sheet.autoSizeColumn(i);
        }
         */
        // Write the output to a file
        FileOutputStream fileOut = null;

        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            String fecha = dtf.format(now);

            fileOut = new FileOutputStream("logs\\Log-Paginacion " + fecha + ".xlsx");
            workbook.write(fileOut);
            // Closing the workbook
            fileOut.close();
            workbook.close();
            
            Desktop.getDesktop().open(new File("logs\\Log-Paginacion " + fecha + ".xlsx"));
        } catch (IOException ex) {
            Logger.getLogger(CrearXLSXProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Row createOrGetRow(int row) {
        if (sheet.getRow(row) == null) {
            return sheet.createRow(row);
        } else {
            return sheet.getRow(row);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }
}
