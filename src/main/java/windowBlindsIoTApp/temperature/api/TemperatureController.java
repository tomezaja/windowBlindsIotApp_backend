package windowBlindsIoTApp.temperature.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import windowBlindsIoTApp.temperature.service.*;

import java.time.OffsetDateTime;
import java.util.List;


@RestController(value = "/v1/temperatures")
@RequestMapping("/api/temperatures")
public class TemperatureController {
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    private final TemperatureService  temperatureService;

    @PostMapping
    public ResponseEntity<TemperatureResponse> createTemperatureRequest(@RequestBody TemperatureRequest temperatureRequest) {

        var temperatureRequestDto = new TemperatureRequestDto(
                temperatureRequest.temperature()
        );

        var temperatureResponseDto = temperatureService.process(temperatureRequestDto);

        return new ResponseEntity<TemperatureResponse>(
                new TemperatureResponse(
                        temperatureResponseDto.temperature(),
                        temperatureResponseDto.dateTime()
                ), HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemperatureResponseDto> GetTemperatureById(@PathVariable("id") Long id) {

        var temperature = temperatureService.getTemperatureById(id);

        return ResponseEntity.ok(temperature);
    }

//    @GetMapping
//    public ResponseEntity<List<TemperatureResponseDto>> GetAllTemperaturesRequest() {
//
//        List<TemperatureResponseDto> temperatureResponse = temperatureService.getAll();
//
//        return ResponseEntity.ok(temperatureResponse);
//    }

//    @GetMapping
//     public ResponseEntity<Page<TemperatureResponseDto>> GetAllTemperaturesPaginatedRequest (@RequestParam(name = "page", required = false) int page,@RequestParam(name = "page-size", required = false) int pageSize) {
//
//        Page<TemperatureResponseDto> temperatureResponse = temperatureService.getAllPaginated(page-1, pageSize);
//
//        return ResponseEntity.ok(temperatureResponse);
//    }

    @GetMapping
    public ResponseEntity<?> getAllTemperaturesRequest (
            @RequestParam(name="page", required = false) Integer page,
            @RequestParam(name="page-size", required = false) Integer pageSize
    ) {
        if (page != null && pageSize != null) {
            Page<TemperatureResponseDto> temperatureResponse = temperatureService.getAllPaginated(page-1, pageSize);

            return ResponseEntity.ok(temperatureResponse);
        } else {
            List<TemperatureResponseDto> temperatureResponse = temperatureService.getAll();

            return ResponseEntity.ok(temperatureResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemperatureResponse> updateTemperatureAndTime (
            @RequestBody TemperatureRequest temperatureRequest,
            @PathVariable(name="id") Long id
    ) {
        var temperatureRequestDto = new TemperaturePutRequestDto(
                id,
                temperatureRequest.temperature(),
                OffsetDateTime.now()
        );

        var temperatureResponseDto = temperatureService.updateTemperatureAndTime(temperatureRequestDto);

        return new ResponseEntity<>(
                new TemperatureResponse(
                        temperatureResponseDto.temperature(),
                        temperatureResponseDto.dateTime()
                ), HttpStatus.CREATED
        );
    }

    @PatchMapping(path = "/{id}" )
    public ResponseEntity<TemperatureResponse> updateTemperature (
            @RequestBody TemperatureRequest temperatureRequest,
            @PathVariable(name="id") Long id
    ) {
        var temperatureRequestDto = new TemperaturePatchRequestDto(
                id,
                temperatureRequest.temperature()
        );

        var temperatureResponseDto = temperatureService.updateTemperature(temperatureRequestDto);

        return ResponseEntity.ok(
                new TemperatureResponse(
                        temperatureResponseDto.temperature(),
                        temperatureResponseDto.dateTime()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemperature(@PathVariable(name="id") Long id) {
        if (temperatureService.deleteTemperatureById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
