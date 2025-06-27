package org.geoserver.wms.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "org.geoserver.wms.springboot",
    "org.geoserver.wms.springboot.config",
    "org.geoserver.wms.springboot.service",
    "org.geoserver.wms.springboot.controller"
})
public class WmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }
}
