package windowBlindsIoTApp.temperature.service;

import java.time.OffsetDateTime;

public record TemperaturePutRequestDto(long id, double temperature, OffsetDateTime dateTime) {

}
