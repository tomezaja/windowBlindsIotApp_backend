package windowBlindsIoTApp.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TemperatureController {

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureController(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @PostMapping("/api/temperatures")
    public Temperature create (@RequestBody Temperature temperature) {
        temperature.setDate(LocalDateTime.now());

        this.temperatureRepository.save(temperature);

        return temperature;
    }
}
