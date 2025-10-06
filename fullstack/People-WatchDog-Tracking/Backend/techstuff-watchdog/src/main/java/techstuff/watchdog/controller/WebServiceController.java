package techstuff.watchdog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import techstuff.watchdog.dto.TrackerResponseDTO;
import techstuff.watchdog.service.TrackerService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trackerService")
@CrossOrigin( originPatterns = {"http://localhost*"})
public class WebServiceController {

    @Autowired
    private TrackerService trackerService;

    @GetMapping("/getTrackerData")
    public List<TrackerResponseDTO> getTracker(@RequestParam("date")LocalDate queryDate) {
        return trackerService.getTrackerService(queryDate);
    }

}
