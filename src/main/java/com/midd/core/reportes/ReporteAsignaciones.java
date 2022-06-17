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

import com.midd.core.administracion.*;
import com.midd.core.modelo.*;

@Service
public class ReporteAsignaciones {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private final ServiciosPerfil servicio_perfil;
    private final ServiciosAsociados servicio_asociados;
    private final ServicioEquipo servicio_equipo;

    List<AsignacionProyecto> asignaciones;

    public ReporteAsignaciones(ServiciosPerfil servicio_perfil, ServiciosAsociados servicio_asociados,
            ServicioEquipo servicio_equipo, List<AsignacionProyecto> asignaciones) {
        this.servicio_perfil = servicio_perfil;
        this.servicio_asociados = servicio_asociados;
        this.servicio_equipo = servicio_equipo;
        this.asignaciones = asignaciones;

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Reporte Asignaciones");
    }

    private void cabeceraTabla() {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell;
        String[] headers = {
                "Ultimatix",
                "Nombres",
                "Estado",
                "Nombre del equipo",
                "Tipo de equipo",
                "Líder Banco",
                "Líder técnico",
                "Fecha de inicio",
                "Fecha fin",
                "Asignación",
                "Usuario de red",
                "Correo electrónico",
                "Teléfono",

        };

        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }

    }

    private void escribirDatosTabla() {
        int initRow = 1;
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String mensaje = "";

        for (AsignacionProyecto asignacion : asignaciones) {
            Row row = sheet.createRow(initRow++);

            if (asignacion.getEstado()) {
                mensaje = "Vigente";
            } else {
                mensaje = "No vigente";
            }

            Perfil perfil = servicio_perfil.buscarPerfilMio(asignacion.getUltimatix_asi());
            Asociado asociado = servicio_asociados.buscarAsociadoPorId(asignacion.getUltimatix_asi());
            Equipo equipo = servicio_equipo.buscarEquipoId(asignacion.getId_equipo_asi());

            String[] rows = {
                perfil.getId_ultimatix().toString(),
                perfil.getNombres_completos(),
                mensaje,
                equipo.getNombre_equipo_asi(),
                equipo.getTipo_equipo_asi(),
                equipo.getNombre_lider(),
                equipo.getNombre_tecnico(),
                dateFormat.format(asignacion.getFecha_inicio()),
                dateFormat.format(asignacion.getFecha_fin()),
                String.valueOf(asignacion.getAsignacion()),
                perfil.getUsuario_red(),
                asociado.getCorreo(),
                asociado.getTelefono()
            };
            
            
            Cell cell;
            
            for(int i=0; i < rows.length; i++){
                cell = row.createCell(i);
                cell.setCellValue(rows[i]);
                cell.setCellStyle(cellStyle);
            }
              

        }

    }

    public void export(HttpServletResponse response) throws IOException {
        cabeceraTabla();
        escribirDatosTabla();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
