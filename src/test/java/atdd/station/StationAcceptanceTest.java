package atdd.station;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

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
    public void test() {
        String stationName = "강남역";
        String inputJson = "{\"name\":\""+stationName+"\"}";

        System.out.println("111111");

        webTestClient.post().uri("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(inputJson), String.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Scenario: 지하철역 목록 조회
     * Given "강남역" 지하철역이 등록되어 있다.
     * When 사용자는 지하철역 목록조회를 요청한다.
     * Then 사용자는 "강남역" 지하철역의 정보를 응답받는다.
     */
    @Test
    public void 지하철역_목록_조회() {
        //Given
        test();

        System.out.println("@222222");

        //When
        webTestClient.get().uri("/stations/강남역")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.name").isEqualTo("강남역");
    }


}
