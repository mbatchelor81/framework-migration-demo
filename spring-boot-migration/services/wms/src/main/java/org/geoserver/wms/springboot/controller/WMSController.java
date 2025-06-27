package org.geoserver.wms.springboot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wms")
public class WMSController {

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getCapabilities(
            @RequestParam(value = "service", defaultValue = "WMS") String service,
            @RequestParam(value = "version", defaultValue = "1.3.0") String version,
            @RequestParam(value = "request", defaultValue = "GetCapabilities") String request) {
        
        // This is a simplified example - actual implementation would use WMS service
        String capabilities = """
                <?xml version="1.0" encoding="UTF-8"?>
                <WMS_Capabilities version="1.3.0">
                    <Service>
                        <Name>WMS</Name>
                        <Title>GeoServer Web Map Service</Title>
                        <Abstract>Spring Boot Migration WMS</Abstract>
                    </Service>
                </WMS_Capabilities>
                """;
        
        return ResponseEntity.ok(capabilities);
    }
}
