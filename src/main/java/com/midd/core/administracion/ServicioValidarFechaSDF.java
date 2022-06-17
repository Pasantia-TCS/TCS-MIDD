package com.midd.core.administracion;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class ServicioValidarFechaSDF {
    
    public boolean validarFecha(java.sql.Date fechaPorVerificar){
        List<Date> feriados = new ArrayList<>();
        //Feriado 2022
        	feriados.add(Date.valueOf("2022-03-01"));
        	feriados.add(Date.valueOf("2022-04-15"));
        	feriados.add(Date.valueOf("2022-05-02"));
        	feriados.add(Date.valueOf("2022-05-23"));
        	feriados.add(Date.valueOf("2022-08-12"));
        	feriados.add(Date.valueOf("2022-10-10"));
        	feriados.add(Date.valueOf("2022-11-03"));
        	feriados.add(Date.valueOf("2022-11-04"));
        	feriados.add(Date.valueOf("2022-12-05"));
        	feriados.add(Date.valueOf("2022-12-26"));
            //Feriado 2023
        	feriados.add(Date.valueOf("2023-01-02"));
        	feriados.add(Date.valueOf("2023-02-20"));
        	feriados.add(Date.valueOf("2023-02-21"));
        	feriados.add(Date.valueOf("2023-04-07"));
        	feriados.add(Date.valueOf("2023-05-01"));
        	feriados.add(Date.valueOf("2023-05-26"));
        	feriados.add(Date.valueOf("2023-08-11"));
        	feriados.add(Date.valueOf("2023-10-09"));
        	feriados.add(Date.valueOf("2023-11-02"));
        	feriados.add(Date.valueOf("2023-11-03"));
        	feriados.add(Date.valueOf("2023-12-05"));
        	feriados.add(Date.valueOf("2023-12-25"));
            //
            LocalDate uno = fechaPorVerificar.toLocalDate();
            int cont = 0;
            for (Date iterante : feriados) {
                if(fechaPorVerificar.toLocalDate().toString().equals(iterante.toLocalDate().toString())){

                	cont = cont+1;
                }
            }
            
            if((uno.getDayOfWeek().toString().equals("SATURDAY"))||(uno.getDayOfWeek().toString().equals("SUNDAY"))||(cont>0)){
                return true;
            }  
        return false;
    }
}
