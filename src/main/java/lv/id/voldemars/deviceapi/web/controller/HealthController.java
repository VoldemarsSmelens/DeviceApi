package lv.id.voldemars.deviceapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;


@RestController
@Tag(name = "Health service")
@Log4j2
class HealthController {

    @GetMapping("/status")
    @Operation(summary = "Health controller")
    public String status() {
        log.debug("{\"application\": \"device-api\",\"status\":\"UP\"}");
        return "{\"application\": \"device-api\",\"status\":\"UP\"}";
    }
}
