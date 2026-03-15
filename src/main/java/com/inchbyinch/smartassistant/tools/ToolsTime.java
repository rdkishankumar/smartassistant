package com.inchbyinch.smartassistant.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ToolsTime {
    private static final Logger logger = LoggerFactory.getLogger(ToolsTime.class);

    @Tool(name = "getCurrentLocalTime", description = "Get the user's local time ")
    String getCurrentLocalTime() {
        logger.debug("getCurrentLocalTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    @Tool(name = "getCurrentTimeByZone",
            description = "Get the current time for a specific timezone")
    public String getCurrentLocalTimeZone(
            @ToolParam(description = "Timezone like Asia/Kolkata, UTC, Europe/London")
            String timeZone) {

        logger.debug("getCurrentLocalTimeZone called for {}", timeZone);

        return LocalDateTime.now(ZoneId.of(timeZone)).toString();
    }

}
