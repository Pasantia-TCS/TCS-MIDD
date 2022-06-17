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
public class ReporteActivo {
    
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private final ServiciosPerfil servicio_perfil;
    private final ServiciosAsociados servicios_asociados;

    List<Activos> activos;

    public ReporteActivo(ServiciosPerfil servicio_perfil, ServiciosAsociados servicios_asociados, List<Activos> activos) {
        this.servicio_perfil = servicio_perfil;
        this.servicios_asociados = servicios_asociados;
        this.activos = activos;

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Reporte Activos");
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
            "Ultimatix",
            "Nombres",
            "Usuario de red",
            "Tipo",
            "Marca",
            "Serie",
            "Código de activo",
            "Edificio",
            "Piso",
            "Área",
            "Estado",
            "Nombre de máquina",
            "MAC Address",
            "IP",
            "IP Reservada",
            "Fecha de asignación",
            "Fecha de registro",
            "Fecha de devolución",
            "Correo electrónico",
            "Teléfono"
        };
        for(int i =0; i < headers.length; i++){
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
        String fechaActivo;
        String mensaje,hostname,mac,ip,tipo = "";
        for (Activos activo : activos) {
            Row row = sheet.createRow(initRow++);

            if (activo.isEstado()) 
                mensaje = "Devuelto";
            else {
                if (activo.isBorrado_logico()) {
                    mensaje = "Eliminado";
                } else {
                    mensaje = "En uso";
                }
            }
        
            

            Perfil perfil = servicio_perfil.buscarPerfilMio(activo.getId_ultimatix());
            Asociado asociado = servicios_asociados.buscarAsociadoPorId(activo.getId_ultimatix());
            if(activo.getHostname().equals("") || activo.getHostname() ==  null )
                hostname = "N/A";
            else           
                hostname = activo.getHostname();

            if(activo.getDireccion_mac().equals("") || activo.getDireccion_mac() ==  null )   
                mac = ("N/A");
            else 
                mac = activo.getDireccion_mac();

            if(activo.getDireccion_ip().equals("") || activo.getDireccion_ip() ==  null )
                ip = "N/A";
            else 
                ip = activo.getDireccion_ip();
            

             if(activo.getTipo().equals("CPU/Portatil")){
                if (!activo.isReservada_ip())
                    tipo = "No";
                else
                    tipo = "Sí";
            }else
                tipo = "N/A";

            if(activo.getFecha_devolucion() ==  null )
                fechaActivo = "N/A";
            else 
                fechaActivo= dateFormat.format(activo.getFecha_devolucion());

            String[] rows = {
                perfil.getId_ultimatix().toString(),
                perfil.getNombres_completos(),
                perfil.getUsuario_red(),
                activo.getTipo(),
                activo.getMarca(),
                activo.getSerie(),
                activo.getCodigo_barras(),
                activo.getEdificio(),
                activo.getPiso(),
                activo.getArea(),
                mensaje,
                hostname,
                mac,
                ip,
                tipo,
                dateFormat.format(activo.getFecha_entrega()),
                dateFormat.format(activo.getFecha_registro()),
                fechaActivo,
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

    public void export(HttpServletResponse response) throws IOException{
        cabeceraTabla();
        escribirDatosTabla();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
