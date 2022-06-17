package com.midd.core.administracion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;


import com.midd.core.modelo.Equipo;
import com.midd.core.repositorio.EquipoRepo;

@Service
public class ServicioReportes{

    private final EquipoRepo equipoRepo;

    public ServicioReportes(EquipoRepo equipoRepo) {
        this.equipoRepo = equipoRepo;
    }


    public ByteArrayInputStream exportAllTeam(){
        String[] columns = 
        {"Tipo", "Nombre Equipo", "Líder Banco", "Líder Técnico", "Descripción", "Estado", "Fecha de Registro"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Reporte de Equipos");
        Row row =  sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<Equipo> equipos = equipoRepo.findAll();
        int initRow = 1;
        for (Equipo equipo : equipos) {
            row =  sheet.createRow(initRow);
            row.createCell(0).setCellValue(equipo.getTipo_equipo_asi());
            row.createCell(1).setCellValue(equipo.getNombre_equipo_asi());
            row.createCell(2).setCellValue(equipo.getNombre_lider());
            row.createCell(3).setCellValue(equipo.getNombre_tecnico());
            row.createCell(4).setCellValue(equipo.getDescripcion_asi());
            row.createCell(5).setCellValue(equipo.isEstado_asi());
            row.createCell(6).setCellValue(equipo.getFecha_registro());

            initRow += 1;
        }

        try {
            workbook.write(stream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ByteArrayInputStream(stream.toByteArray());
    }


    private ByteArrayInputStream ByteArrayInputStream(byte[] byteArray) {
        return exportAllTeam();
    }

}
