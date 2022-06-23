package com.midd.core.Respuestas;

import org.slf4j.*;
import org.springframework.stereotype.Service;

@Service
public class RenameLogger {
    Logger logger = LoggerFactory.getLogger(RenameLogger.class);


    // FUNCION LOGGER PARA ADVERTENCIAS
    public void setLoggerWarm(String mensaje){
        this.logger.warn(mensaje);
    }

    // FUNCION LOGGER PARA CASOS DE EXITO
    public void setLoggerInfo(String mensaje){
        this.logger.info(mensaje);
      }
}
