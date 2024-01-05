package windowBlindsIoTApp.temperature;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "temperatures")
@Data
public class Temperature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private double temperature;
    private LocalDateTime date;

}
