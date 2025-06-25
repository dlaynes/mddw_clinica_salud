package com.grupo2.clinicasalud.service.utils;

import java.net.InetAddress;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EnvironmentService {
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    public String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch(Exception e){
            System.err.println("Could not determine hostname: " + e.getMessage());
            return "localhost";
        }
    }

    public String getUrl() {
        return getScheme() +
                "://" +
                getHostname() +
                ":" +
                getActivePort() +
                "/";
    }

    // TODO
    public String getScheme() {
        return "http";
    }

    public int getActivePort() {
        return webServerAppCtxt.getWebServer().getPort();
    }

}
