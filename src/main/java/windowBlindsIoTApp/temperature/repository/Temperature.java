package windowBlindsIoTApp.temperature.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Clock;
import java.time.OffsetDateTime;


@Getter
@Entity
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Temperature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double temperature;

    @Column(nullable = false)
    private OffsetDateTime dateTime;

    public Temperature(double temperature) {
        this.temperature = temperature;
        this.dateTime = OffsetDateTime.now(Clock.systemUTC());
    }

    public Temperature(long id, double temperature, OffsetDateTime dateTime) {
        this.id = id;
        this.temperature = temperature;
        this.dateTime = dateTime;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
