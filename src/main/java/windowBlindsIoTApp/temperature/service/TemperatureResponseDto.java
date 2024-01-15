package windowBlindsIoTApp.temperature.service;

import java.time.OffsetDateTime;

public record TemperatureResponseDto(double temperature, OffsetDateTime dateTime) {
}
