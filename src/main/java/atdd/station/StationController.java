package atdd.station;

import atdd.dto.Line;
import atdd.dto.Station;
import atdd.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/stations", produces = "application/json")
public class StationController {

    private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    @Resource
    private StationService stationService;

    @PostMapping("")
    public ResponseEntity<Station> createStation(@RequestBody Station station) {

        stationService.createStation(station);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Location", station.getName());

        return ResponseEntity.created(URI.create("/stations/" + station.getName())).headers(httpHeaders).body(station);
    }

    @GetMapping("")
    public ResponseEntity<List<Station>> getStation() {

        return ResponseEntity.ok(stationService.getStation());
    }

    @GetMapping("/details/{stationName}")
    public ResponseEntity<Station> detailStation(@PathVariable String stationName) {

        return ResponseEntity.ok(stationService.detailStation(stationName));

    }

    @DeleteMapping("/{stationName}")
    public ResponseEntity<Long> deleteStation(@PathVariable String stationName) {

        return ResponseEntity.ok(stationService.deleteStation(stationName));

    }

}
