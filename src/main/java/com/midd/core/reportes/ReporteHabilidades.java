package com.midd.core.reportes;

import java.io.IOException;
import java.util.ArrayList;
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

import com.midd.core.administracion.ServiciosAsociados;
import com.midd.core.modelo.*;

@Service
public class ReporteHabilidades {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private ServiciosAsociados serviciosAsociados;

    List<Perfil> habilidades;

    public ReporteHabilidades(List<Perfil> habilidades, ServiciosAsociados serviciosAsociados) {
        this.habilidades = habilidades;
        this.serviciosAsociados = serviciosAsociados;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Reporte Habilidades");
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
                "Habilidades técnicas",
                "Nivel habilidad técnica",
                "Habilidades funcionales",
                "Nivel habilidad funcional",
                "Aplicaciones",
                "Nivel aplicaciones",
                "Usuario de red",
                "Teléfono",
                "Correo"
        };

        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    public String[] celdas(String ultimatix, String nombres, String habilidadesT, String nivelesT, String habilidaesF,
            String nivelesF, String aplicaciones, String nivelesA, String usuarioRed, String telefono, String correo) {

        String[] rows = {
                ultimatix,
                nombres,
                habilidadesT,
                nivelesT,
                habilidaesF,
                nivelesF,
                aplicaciones,
                nivelesA,
                usuarioRed,
                telefono,
                correo
        };
        return rows;
    }

    private void escribirDatosTabla() {
        int initRow = 1;
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        List<String[]> habilidades_row = new ArrayList<>();

        for (Perfil perfiles : habilidades) {

            Asociado asociado = serviciosAsociados.buscarAsociadoPorId(perfiles.getId_ultimatix());
            String habilidadesT = "", habilidadesF = "", aplicaciones = "";
            String nivelesT = "", nivelesF = "", nivelesA = "";
            if (perfiles.getHabilidades().length >= perfiles.getHabilidades_funcionales().length &&
                    perfiles.getHabilidades().length >= perfiles.getAplicaciones().length) {
                for (int i = 0; i < perfiles.getHabilidades().length; i++) {
                    habilidadesT = perfiles.getHabilidades()[i];
                    nivelesT = perfiles.getNivel_habilidad()[i];

                    if (i >= perfiles.getHabilidades_funcionales().length) {
                        habilidadesF = "";
                        nivelesF = "";
                    } else {
                        habilidadesF = perfiles.getHabilidades_funcionales()[i];
                        nivelesF = perfiles.getNivel_habilidad_funcional()[i];
                    }

                    if (i >= perfiles.getAplicaciones().length) {
                        aplicaciones = "";
                        nivelesA = "";

                    } else {
                        aplicaciones = perfiles.getAplicaciones()[i];
                        nivelesA = perfiles.getNivel_aplicaciones()[i];
                    }

                    String[] rows = celdas(perfiles.getId_ultimatix().toString(), perfiles.getNombres_completos(),
                            habilidadesT, nivelesT, habilidadesF, nivelesF, aplicaciones, nivelesA,
                            perfiles.getUsuario_red(), asociado.getTelefono(), asociado.getCorreo());
                    habilidades_row.add(rows);

                }
            }
            if (perfiles.getHabilidades_funcionales().length >= perfiles.getHabilidades().length &&
                    perfiles.getHabilidades_funcionales().length >= perfiles.getAplicaciones().length) {
                for (int i = 0; i < perfiles.getHabilidades_funcionales().length; i++) {
                    habilidadesF = perfiles.getHabilidades_funcionales()[i];
                    nivelesF = perfiles.getNivel_habilidad_funcional()[i];

                    if (i >= perfiles.getHabilidades().length) {
                        habilidadesT = "";
                        nivelesT="";
                    } else {
                        habilidadesT = perfiles.getHabilidades()[i];
                        nivelesT = perfiles.getNivel_habilidad()[i];
                    }

                    if (i >= perfiles.getAplicaciones().length) {
                        aplicaciones = "";
                        nivelesA = "";
                    } else {
                        aplicaciones = perfiles.getAplicaciones()[i];
                        nivelesA = perfiles.getNivel_aplicaciones()[i];
                    }

                    String[] rows = celdas(perfiles.getId_ultimatix().toString(), perfiles.getNombres_completos(),
                            habilidadesT, nivelesT, habilidadesF, nivelesF, aplicaciones, nivelesA,
                            perfiles.getUsuario_red(), asociado.getTelefono(), asociado.getCorreo());
                    habilidades_row.add(rows);
                }

            } else {
                for (int i = 0; i < perfiles.getAplicaciones().length; i++) {
                    aplicaciones = perfiles.getAplicaciones()[i];
                    nivelesA = perfiles.getAplicaciones()[i];

                    if (i >= perfiles.getHabilidades().length) {
                        habilidadesT = "";
                        nivelesT = "";

                    } else {
                        habilidadesT = perfiles.getHabilidades()[i];
                        nivelesT = perfiles.getNivel_habilidad()[i];
                    }

                    if (i >= perfiles.getHabilidades_funcionales().length) {
                        habilidadesF = "";
                        nivelesF = "";
                    } else {
                        habilidadesF = perfiles.getHabilidades_funcionales()[i];
                        nivelesF = perfiles.getNivel_habilidad_funcional()[i];

                    }

                    String[] rows = celdas(perfiles.getId_ultimatix().toString(), perfiles.getNombres_completos(),
                            habilidadesT, nivelesT, habilidadesF, nivelesF, aplicaciones, nivelesA,
                            perfiles.getUsuario_red(), asociado.getTelefono(), asociado.getCorreo());
                    habilidades_row.add(rows);
                }
            }
        }
        Cell cell;
        for (int i = 0; i < habilidades_row.size(); i++) {
            Row row = sheet.createRow(initRow++);
            for (int j = 0; j < habilidades_row.get(i).length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(habilidades_row.get(i)[j]);
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
