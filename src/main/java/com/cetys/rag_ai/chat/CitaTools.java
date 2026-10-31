package com.cetys.rag_ai.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CitaTools {

    //TODO CitasService
    final Logger logger = LoggerFactory.getLogger(CitaTools.class);

    Set<String> TIPOS_SANGRE = Set.of("O+", "O-", "AB-", "AB+", "A+", "A-");
    @Tool
    String agendarCita(
            @ToolParam(description= "N./ombre completo del donante")
            String nombreDonante,
            @ToolParam(description= "Sede: Centro, Norte o Sur")
            String sede,
            @ToolParam(description= "Fecha siguiendo ISO-8601" )
            String fecha,
            @ToolParam(description= "Tipo de sangre, eg. 0+, A-")
            String tipoSangre){
        logger.info("Primer tool call:: agendarCita");
        return "Cita completa: " + nombreDonante+ " " + sede+fecha+ " " + tipoSangre;
    }
}
