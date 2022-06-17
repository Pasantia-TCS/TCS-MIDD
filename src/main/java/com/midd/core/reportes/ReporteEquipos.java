package com.midd.core.reportes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.midd.core.modelo.Equipo;

@Service
public class ReporteEquipos {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    List<Equipo> equipos;

    public ReporteEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Reporte Equipos");
    }

    private void cabeceraTabla(){
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        Cell cell;
        String[] headers = {
                "Tipo",
                "Nombre de equipo",
                "Líder Banco",
                "Líder Técnico",
                "Descripción",
                "Estado",
                "Fecha de registro"

        };
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }

    }

    private void escribirDatosTabla(){
        int initRow = 1;
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String mensaje = "";

        for (Equipo equipo : equipos) {
            Row row = sheet.createRow(initRow++);

            if (equipo.isEstado_asi()) {
                mensaje = "Vigente";
            } else {
                mensaje = "No vigente";
            }

            String[] rows = {
                equipo.getTipo_equipo_asi(),
                equipo.getNombre_equipo_asi(),
                equipo.getNombre_lider(),
                equipo.getNombre_tecnico(),
                equipo.getDescripcion_asi(),
                mensaje,
                dateFormat.format(equipo.getFecha_registro())
            };

            Cell cell;
            
            for(int i=0; i < rows.length; i++){
                cell = row.createCell(i);
                cell.setCellValue(rows[i]);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException{
        cabeceraTabla();
        escribirDatosTabla();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    
}
