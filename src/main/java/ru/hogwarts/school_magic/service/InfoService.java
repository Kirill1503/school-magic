package ru.hogwarts.school_magic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Value("${server.port}")
    private Integer port;

    Logger logger = LoggerFactory.getLogger(InfoService.class);

    public Integer getPort() {
        logger.info("Was invoke method getPort");
        return port;
    }
}
