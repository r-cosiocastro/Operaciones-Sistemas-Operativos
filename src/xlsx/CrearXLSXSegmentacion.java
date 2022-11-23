package xlsx;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.BloqueSegmentacion;
import objetos.MemoriaSegmentacion;
import objetos.Registro;
import objetos.Segmento;
import objetos.TablaPaginacion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author krawz
 */
public class CrearXLSXSegmentacion {

    Workbook workbook;
    Sheet sheet;
    Cell cell;

    public CrearXLSXSegmentacion(MemoriaSegmentacion memoria) {
        workbook = new XSSFWorkbook();

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

        Font fuenteError = workbook.createFont();
        fuenteError.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        CellStyle nombreAlgoritmoStyle = workbook.createCellStyle();
        nombreAlgoritmoStyle.setFont(fuenteAlgoritmo);

        CellStyle errorStyle = workbook.createCellStyle();
        errorStyle.setFont(fuenteError);

        CellStyle tablaStyle = workbook.createCellStyle();
        tablaStyle.setAlignment(HorizontalAlignment.CENTER);
        tablaStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tablaStyle.setBorderLeft(BorderStyle.MEDIUM);
        tablaStyle.setBorderRight(BorderStyle.MEDIUM);
        tablaStyle.setBorderTop(BorderStyle.MEDIUM);
        tablaStyle.setBorderBottom(BorderStyle.MEDIUM);
        tablaStyle.setWrapText(true);

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

        CellStyle vacioStyle = workbook.createCellStyle();
        vacioStyle.setAlignment(HorizontalAlignment.CENTER);
        //ganttEsperaStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        vacioStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        vacioStyle.setFillPattern(FillPatternType.THICK_FORWARD_DIAG);
        vacioStyle.setBorderLeft(BorderStyle.MEDIUM);
        vacioStyle.setBorderRight(BorderStyle.MEDIUM);
        vacioStyle.setBorderTop(BorderStyle.MEDIUM);
        vacioStyle.setBorderBottom(BorderStyle.MEDIUM);

        int rowNum = 0;

        sheet.createRow(rowNum++);
        Row row = createOrGetRow(rowNum++);

        cell = row.createCell(1);
        cell.setCellValue("Segmento");
        cell.setCellStyle(tablaStyleHeader);

        cell = row.createCell(2);
        cell.setCellValue("Base");
        cell.setCellStyle(tablaStyleHeader);

        cell = row.createCell(3);
        cell.setCellValue("Límite");
        cell.setCellStyle(tablaStyleHeader);

        row = createOrGetRow(rowNum++);

        for (Segmento seg : memoria.segmentos) {
            cell = row.createCell(1);
            cell.setCellValue(seg.getId());
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(2);
            cell.setCellValue(seg.getBase());
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(3);
            cell.setCellValue(seg.getLimite());
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(4);
            cell.setCellValue(seg.getMensajeEstado());
            if (seg.getEstado() == 1) {
                cell.setCellStyle(errorStyle);
            }

            row = createOrGetRow(rowNum++);
        }
        rowNum = 1;
        row = createOrGetRow(rowNum++);

        cell = row.createCell(6);
        cell.setCellValue("Registro");
        cell.setCellStyle(tablaStyleHeader);

        cell = row.createCell(7);
        cell.setCellValue("Segmento");
        cell.setCellStyle(tablaStyleHeader);

        cell = row.createCell(8);
        cell.setCellValue("Desplazamiento");
        cell.setCellStyle(tablaStyleHeader);

        row = createOrGetRow(rowNum++);

        for (Registro reg : memoria.registros) {
            cell = row.createCell(6);
            cell.setCellValue(reg.getRegistro());
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(7);
            cell.setCellValue(reg.getSegmento().getId());
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(8);
            cell.setCellValue(reg.getDesplazamiento());
            cell.setCellStyle(tablaStyle);

            cell = row.createCell(9);
            cell.setCellValue(reg.getUbicacion());
            if (reg.getEstado() == 1) {
                cell.setCellStyle(errorStyle);
            }

            row = createOrGetRow(rowNum++);
        }

        rowNum = memoria.segmentos.size() + 4;
        row = createOrGetRow(rowNum++);

        cell = row.createCell(2);
        cell.setCellValue("0");
        cell.setCellStyle(crearEstiloEtiquetas(memoria.bloques.get(0).tamaño, 1));

        for (BloqueSegmentacion bs : memoria.bloques) {
            if (bs.disponible) {
                cell = row.createCell(1);
                cell.setCellValue("");
                cell.setCellStyle(vacioStyle);
                row.setHeightInPoints(bs.tamaño);
            } else {
                if (bs.direccion == 1) {
                    cell = row.createCell(1);
                    cell.setCellValue(bs.titulo);
                    cell.setCellStyle(crearEstiloTablas(bs.tamaño));

                    cell = row.createCell(2);
                    cell.setCellValue(bs.valor);
                    cell.setCellStyle(crearEstiloEtiquetas(bs.tamaño, bs.direccion));
                    row.setHeightInPoints(bs.tamaño);
                } else {
                    cell = row.createCell(1);
                    cell.setCellValue(bs.titulo);
                    cell.setCellStyle(crearEstiloTablas(bs.tamaño));

                    cell = row.createCell(2);
                    cell.setCellValue(bs.valor);
                    cell.setCellStyle(crearEstiloEtiquetas(bs.tamaño, bs.direccion));
                    row.setHeightInPoints(bs.tamaño);
                    combinarCeldas(rowNum - 2, rowNum - 1, 1, 1);
                }
            }
            row = createOrGetRow(rowNum++);
        }

        row = createOrGetRow(rowNum - 2);
        cell = row.createCell(2);
        cell.setCellValue(memoria.getTamano());
        cell.setCellStyle(crearEstiloEtiquetas(memoria.bloques.get(memoria.bloques.size() - 1).tamaño, 2));

        // Resize all columns to fit the content size
        //sheet.autoSizeColumn(4);
        //sheet.autoSizeColumn(2);
        for (int i = 1; i <= 8; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = null;
        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            String fecha = dtf.format(now);

            fileOut = new FileOutputStream("logs\\Log-Segmentacion " + fecha + ".xlsx");
            workbook.write(fileOut);
            // Closing the workbook
            fileOut.close();
            workbook.close();

            Desktop.getDesktop().open(new File("logs\\Log-Segmentacion " + fecha + ".xlsx"));
        } catch (IOException ex) {
            Logger.getLogger(CrearXLSXProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    CellStyle crearEstiloEtiquetas(int tamañoFuente, int direccion) {
        Font fuente = workbook.createFont();
        int size = tamañoFuente / 2;
        while (size > 200) {
            size /= 2;
        }
        fuente.setFontHeightInPoints((short) size);
        CellStyle newStyle = workbook.createCellStyle();
        newStyle.setAlignment(HorizontalAlignment.LEFT);
        switch (direccion) {
            case 1:
                newStyle.setVerticalAlignment(VerticalAlignment.TOP);
                break;
            case 2:
                newStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
                break;
        }
        newStyle.setFont(fuente);
        return newStyle;
    }

    CellStyle crearEstiloTablas(int tamañoFuente) {
        Font fuente = workbook.createFont();
        int size = tamañoFuente / 2;
        while (size > 200) {
            size /= 2;
        }
        fuente.setFontHeightInPoints((short) size);
        CellStyle newStyle = workbook.createCellStyle();
        newStyle.setAlignment(HorizontalAlignment.CENTER);

        newStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        newStyle.setBorderLeft(BorderStyle.MEDIUM);
        newStyle.setBorderRight(BorderStyle.MEDIUM);
        newStyle.setBorderTop(BorderStyle.MEDIUM);
        newStyle.setBorderBottom(BorderStyle.MEDIUM);
        newStyle.setFont(fuente);
        return newStyle;
    }

    void combinarCeldas(int desdeFila, int hastaFila, int desdeColumna, int hastaColumna) {

        sheet.addMergedRegion(new CellRangeAddress(desdeFila, hastaFila, desdeColumna, hastaColumna));
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
