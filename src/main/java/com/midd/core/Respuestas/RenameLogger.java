package com.midd.core.Respuestas;

import org.slf4j.*;
import org.springframework.stereotype.Service;

@Service
public class RenameLogger {
    Logger logger = LoggerFactory.getLogger(RenameLogger.class);

    public void setLoggerWarm(String mensaje){
        this.logger.warn(mensaje);
    }
      public void setLoggerInfo(String mensaje){
        this.logger.info(mensaje);
      }
}
