package windowBlindsIoTApp.temperature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<Temperature, Long> {

    Temperature findByTemperature(double temperature);

}
