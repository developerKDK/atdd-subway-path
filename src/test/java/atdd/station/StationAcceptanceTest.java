package atdd.station;

import atdd.dto.Line;
import atdd.dto.Station;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class StationAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(StationAcceptanceTest.class);

    @Autowired
    private WebTestClient webTestClient;

    /**
     * 지하철 정보
     * {
     *     "id":1,
     *     "name":"강남역"
     * }
     */

    /**
     * Scenario: 지하철역 등록
     * When 관리자는 "강남역" 지하철역 등록을 요청한다.
     * Then "강남역" 지하철역이 등록된다.
     */
    @Test
    public void testCreateStation() {

        Station station = Station.builder()
                .name("강남역")
                .build();
        createStation(station);

    }

    /**
     * Scenario: 지하철역 목록 조회
     * Given "강남역" 지하철역이 등록되어 있다.
     * When 사용자는 지하철역 목록조회를 요청한다.
     * Then 사용자는 "강남역" 지하철역의 정보를 응답받는다.
     */
    @Test
    public void testGetStation() {

        Station station = Station.builder()
                .name("강남역")
                .build();
        createStation(station);

        List<Station> result = webTestClient.get().uri("/stations")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Station.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(result.get(0).getName()).isEqualTo(station.getName());
    }

    /**
     * Scenario: 지하철역 정보 조회
     * Given "강남역" 지하철역이 등록되어 있다.
     * When 사용자는 "강남역" 지하철역의 정보 조회를 요청한다.
     * Then 사용자는 "강남역" 지하철역의 정보를 응답받는다.
     */

    @Test
    public void testGetStationInfo() {

        Station station = Station.builder()
                .name("강남역")
                .build();
        createStation(station);

        webTestClient.get().uri("/stations/details/강남역")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(station.getName());
    }

    /**
     * Scenario: 지하철역 삭제
     * Given "강남역" 지하철역이 등록되어 있다.
     * When 관리자는 "강남역" 지하철역 삭제를 요청한다.
     * Then "강남역" 지하철역이 삭제되었다.
     */
    @Test
    public void testDeleteStaion() {

        Station station = Station.builder()
                .name("강남역")
                .build();
        createStation(station);

        webTestClient.delete().uri("/stations/강남역")
                .exchange()
                .expectStatus().isOk();
    }

    /**
     * 공통) 지하철역 등록
     */
    public void createStation(Station station) {

        webTestClient.post().uri("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(station), Station.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectHeader().exists("Location")
                .expectBody()
                .jsonPath("$.name").isEqualTo(station.getName());
    }


    /**
     * Feature: 지하철 노선 관리
     *
     * Scenario: 지하철 노선 등록
     * When 관리자는 "2호선" 지하철 노선 등록을 요청한다.
     * Then "2호선" 지하철 노선이 등록 되었다.
     */
    @Test
    public void testCreateSubway() {

        String startTime = "05:00";
        String endTime = "23:50";

        Station station = Station.builder()
                .name("삼성역")
                .build();

        List<Station> stations = new ArrayList<>();
        stations.add(station);

        Line line = Line.builder()
                .name("2호선")
                .startTime(startTime)
                .endTime(endTime)
                .intervalTime(10)
                .stations(stations)
                .build();

    }

    /**
     * Scenario: 지하철 노선 목록 조회
     * Given "2호선" 지하철 노선이 등록되어 있다.
     * When 사용자는 지하철 노선의 목록 조회를 요청한다.
     * Then 지하철 노선의 목록을 응답받는다.
     */
    @Test
    public void testGetSubway() {

    }

    /**
     * Scenario: 지하철 노선 정보 조회
     * Given "2호선" 지하철 노선이 등록되어 있다.
     * When 사용자는 "2호선" 지하철 노선의 정보 조회를 요청한다.
     * Then "2호선" 지하철 노선의 정보를 응답받는다.
     */
    @Test
    public void testGetSubwayInfo() {

    }

    /**
     * Scenario: 지하철 노선 삭제
     * Given "2호선" 지하철 노선이 등록되어 있다.
     * When 관리자는 "2호선" 지하철 노선 삭제를 요청한다.
     * Then "2호선" 지하철 노선이 삭제되었다.
     */
    @Test
    public void testDeleteSubway() {

    }
}

