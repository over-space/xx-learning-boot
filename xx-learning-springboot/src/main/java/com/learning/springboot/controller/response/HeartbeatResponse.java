package com.learning.springboot.controller.response;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author over.li
 * @since 2023/4/10
 */
public class HeartbeatResponse implements Serializable {

    private String applicationName;

    private Integer serverPort;

    private LocalDateTime startupDateTime;


    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public LocalDateTime getStartupDateTime() {
        return startupDateTime;
    }

    public void setStartupDateTime(LocalDateTime startupDateTime) {
        this.startupDateTime = startupDateTime;
    }
}
