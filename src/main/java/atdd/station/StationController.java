package atdd.station;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class StationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationController.class);

    @PostMapping(value = "/stations", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> createStation(@RequestBody Map<String, String> param) {

        String stationName = param.get("name");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Location", stationName);

        return ResponseEntity.ok().headers(httpHeaders).body("{\"name\":\""+stationName+"\"}");
    }

}
