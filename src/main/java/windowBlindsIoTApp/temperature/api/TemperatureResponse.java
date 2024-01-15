package windowBlindsIoTApp.temperature.api;

import java.time.OffsetDateTime;

public record TemperatureResponse(double temperature, OffsetDateTime dateTime) {
}
