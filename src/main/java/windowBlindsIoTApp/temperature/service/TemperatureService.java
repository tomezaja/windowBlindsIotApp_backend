package windowBlindsIoTApp.temperature.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import windowBlindsIoTApp.temperature.repository.Temperature;
import windowBlindsIoTApp.temperature.repository.TemperatureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;

    public TemperatureResponseDto process(@NonNull TemperatureRequestDto temperatureRequestDto) {
        var temperature = new Temperature(
                temperatureRequestDto.temperature()
        );

        var savedTemperature = temperatureRepository.save(temperature);

        return new TemperatureResponseDto(
                savedTemperature.getTemperature(),
                savedTemperature.getDateTime()
        );
    }

    public List<TemperatureResponseDto> getAll() {
        var temperatures = temperatureRepository.findAll();

        return temperatures.stream()
                .map(temperature -> {
                    return new TemperatureResponseDto(temperature.getTemperature(), temperature.getDateTime());
                })
                .collect(Collectors.toList());
    }

    public TemperatureResponseDto getTemperatureById(@NonNull Long id) {
        var temperature = temperatureRepository.findById(id);

        return new TemperatureResponseDto(
                    temperature.get().getTemperature(),
                    temperature.get().getDateTime()
        );
    }

    public Page<TemperatureResponseDto> getAllPaginated(int page, int pageSize) {
        var pagination = PageRequest.of(page, pageSize);
        var temperaturesPage = temperatureRepository.findAll(pagination);

        var temperaturesDtoList = temperaturesPage.getContent().stream()
                .map( temperature -> {
                    return new TemperatureResponseDto(
                            temperature.getTemperature(),
                            temperature.getDateTime()
                    );
                }
                ).collect(Collectors.toList());

        return new PageImpl<>(temperaturesDtoList, temperaturesPage.getPageable(),temperaturesPage.getTotalElements() );
    }

    public TemperatureResponseDto updateTemperatureAndTime(@NonNull TemperaturePutRequestDto temperatureRequestDto) {

        var newTemperature = new Temperature(
                temperatureRequestDto.id(),
                temperatureRequestDto.temperature(),
                temperatureRequestDto.dateTime()
        );

        temperatureRepository.save(newTemperature);

        return new TemperatureResponseDto(
                newTemperature.getTemperature(),
                newTemperature.getDateTime()
        );
    }

    public TemperatureResponseDto updateTemperature(@NonNull TemperaturePatchRequestDto temperatureRequestDto) {

        var oldTemperature = temperatureRepository.getById(temperatureRequestDto.id());

        oldTemperature.setTemperature(temperatureRequestDto.temperature());

        temperatureRepository.save(oldTemperature);


        return new TemperatureResponseDto(
                oldTemperature.getTemperature(),
                oldTemperature.getDateTime()
        );
    }

    public boolean deleteTemperatureById(@NonNull Long id) {
        if (temperatureRepository.existsById(id)) {
            temperatureRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
