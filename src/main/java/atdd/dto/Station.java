package atdd.dto;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="station")
@Getter
@ToString
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Builder
    public Station(String name) {
        this.name = name;
    }

    public Station toEntity() {
        return Station.builder()
                .name(this.name)
                .build();
    }

}
