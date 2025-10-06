package techstuff.watchdog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import techstuff.watchdog.dto.TrackerResponseDTO;
import techstuff.watchdog.model.EmployeeTapTracker;
import techstuff.watchdog.repository.EmployeeTapTrackerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackerService {

    @Autowired
    private EmployeeTapTrackerRepository repository;

    public List<TrackerResponseDTO> getTrackerService(final LocalDate queryDate) {
        List<EmployeeTapTracker> trackerList = repository.findAllByDateOrderByNameAsc(queryDate);
        return trackerList.stream().map(model ->
        {
            TrackerResponseDTO responseDTO = TrackerResponseDTO.builder().build();
            BeanUtils.copyProperties(model, responseDTO);
            return responseDTO;
        }
        ).collect(Collectors.toList());
    }
}
