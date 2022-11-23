package xlsx;


import java.awt.Desktop;
import java.io.File;
import objetos.ListaProcesos;
import objetos.Proceso;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author krawz
 */
public class CrearXLSXProcesos {

    private String[] columnasProcesos = {"Nombre", "Prioridad", "T. llegada", "R. de CPU", "T. espera", "T. finalización"};
    Sheet sheet;

    // Initializing employees data to insert into the excel file
    public CrearXLSXProcesos(List<ListaProcesos> listaProcesos) {

        /**
         * Example
         */
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
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

        CellStyle ganttStyle = workbook.createCellStyle();
        ganttStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle ganttOcupadoStyle = workbook.createCellStyle();
        ganttOcupadoStyle.setAlignment(HorizontalAlignment.CENTER);
        //ganttOcupadoStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        ganttOcupadoStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        ganttOcupadoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        ganttOcupadoStyle.setFont(fuenteOcupado);

        CellStyle ganttEsperaStyle = workbook.createCellStyle();
        ganttEsperaStyle.setAlignment(HorizontalAlignment.CENTER);
        //ganttEsperaStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        ganttEsperaStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        ganttEsperaStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        ganttEsperaStyle.setFont(fuenteEspera);

        CellStyle ganttStyleHeader = workbook.createCellStyle();
        ganttStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        ganttStyleHeader.setFont(headerFont);

        int rowNum = 0;

        for (ListaProcesos listaProceso : listaProcesos) {

            sheet.createRow(rowNum++);
            Row row = createOrGetRow(rowNum++);
            //row.createCell(0).setCellValue(listaProceso.getAlgoritmo());
            Cell celdaNombre = row.createCell(0);
            celdaNombre.setCellValue(listaProceso.getAlgoritmo());
            celdaNombre.setCellStyle(nombreAlgoritmoStyle);
            sheet.createRow(rowNum++);

            // Create a Row
            Row headerRow = createOrGetRow(rowNum++);

            // Create cells
            for (int i = 0; i < columnasProcesos.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnasProcesos[i]);
                cell.setCellStyle(headerCellStyle);
            }

            for (Proceso proceso : listaProceso.getProcesos()) {
                row = createOrGetRow(rowNum++);

                row.createCell(0)
                        .setCellValue(proceso.getNombre());

                row.createCell(1)
                        .setCellValue(proceso.getPrioridad());

                row.createCell(2)
                        .setCellValue(proceso.getTiempoLlegada());

                row.createCell(3)
                        .setCellValue(proceso.getRafagaCPU());

                row.createCell(4)
                        .setCellValue(proceso.getTiempoEspera());

                row.createCell(5)
                        .setCellValue(proceso.getTiempoFinalizacion());
            }

            sheet.createRow(rowNum++);
            row = createOrGetRow(rowNum++);
            row.createCell(0).setCellValue("Promedio tiempo finalización: " + listaProceso.getPromedioTiempoFinalizacion());
            row = createOrGetRow(rowNum++);
            row.createCell(0).setCellValue("Promedio tiempo espera: " + listaProceso.getPromedioTiempoEspera());

            sheet.createRow(rowNum++);
            row = createOrGetRow(rowNum++);

            Cell headerCell = row.createCell(0);
            headerCell.setCellValue("Diagrama de Gantt");
            headerCell.setCellStyle(headerCellStyle);

            row = createOrGetRow(rowNum++);

            if (listaProceso.isApropiativos()) {
                //  Numeros
                row = createOrGetRow(rowNum++);
                row.createCell(0).setCellValue("");
                for (int j = 1; j < listaProceso.getTiempoFinalizacionMasAlto() + 2; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(j - 1);
                    cell.setCellStyle(ganttStyleHeader);
                    //row.createCell(j).setCellValue(j - 1);
                }

                row = createOrGetRow(rowNum++);

                for (Proceso ps : listaProceso.getProcesos()) {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(ps.getNombre());
                    cell.setCellStyle(ganttStyleHeader);
                    //row.createCell(0).setCellValue(ps.getNombre());
                    int ganttCell = 1;
                    for (int x = 0; x < ps.getGantt().length; x++) {
                        if (x > ps.getTiempoFinalizado()) {
                            break;
                        } else if (x < ps.getTiempoLlegada()) {
                            row.createCell(ganttCell++).setCellValue("");
                        } else {
                            cell = row.createCell(ganttCell++);
                            cell.setCellValue(String.valueOf(ps.getGantt()[x]));
                            if (ps.getGantt()[x] == 'O') {
                                cell.setCellStyle(ganttOcupadoStyle);
                            } else {
                                cell.setCellStyle(ganttEsperaStyle);
                            }
                            //row.createCell(ganttCell++).setCellValue(String.valueOf(ps.getGantt()[x]));
                        }
                    }
                    row = createOrGetRow(rowNum++);
                }

            } else {
                //  Numeros
                row = createOrGetRow(rowNum++);
                row.createCell(0).setCellValue("");
                for (int j = 1; j < listaProceso.getTiempoFinalizacionMasAlto() + 1; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(j);
                    cell.setCellStyle(ganttStyleHeader);
                    //row.createCell(j).setCellValue(j);
                }

                row = createOrGetRow(rowNum++);

                for (Proceso ps : listaProceso.getProcesos()) {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(ps.getNombre());
                    cell.setCellStyle(ganttStyleHeader);
                    //row.createCell(0).setCellValue(ps.getNombre());
                    int ganttCell = 1;
                    for (int x = 0; x < ps.getGantt().length; x++) {
                        if (x > ps.getTiempoFinalizacion() - 1) {
                            break;
                        }

                        cell = row.createCell(ganttCell++);
                        cell.setCellValue(String.valueOf(ps.getGantt()[x]));
                        if (ps.getGantt()[x] == 'O') {
                            cell.setCellStyle(ganttOcupadoStyle);
                        } else {
                            cell.setCellStyle(ganttEsperaStyle);
                        }

                        //row.createCell(ganttCell++).setCellValue(String.valueOf(ps.getGantt()[x]));
                    }
                    row = createOrGetRow(rowNum++);
                }

            }

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

            fileOut = new FileOutputStream("logs\\Log-Procesos " + fecha + ".xlsx");
            workbook.write(fileOut);
            // Closing the workbook
            fileOut.close();
            workbook.close();
            
            Desktop.getDesktop().open(new File("logs\\Log-Procesos " + fecha + ".xlsx"));
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
