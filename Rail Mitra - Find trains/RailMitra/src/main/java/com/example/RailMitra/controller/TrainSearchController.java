package com.example.RailMitra.controller;

import com.example.RailMitra.entity.TrainSchedule;
import com.example.RailMitra.service.TrainSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class TrainSearchController {

   private TrainSearchService trainSearchService;

    public TrainSearchController(TrainSearchService trainSearchService) {
        this.trainSearchService = trainSearchService;
    }


    @GetMapping("/by-code")
    public List<TrainSchedule> FindTrainByStationCode(@RequestParam String sourceCode, @RequestParam String destinationCode){
        return trainSearchService.FindTrainByStationCode(sourceCode.toUpperCase(),destinationCode.toUpperCase());
    }

    @GetMapping("/by-name")
    public List<TrainSchedule> TrainByStationName(@RequestParam String sourceName, @RequestParam String destinationName){
        return trainSearchService.FindTrainByStationName(sourceName.toUpperCase(),destinationName.toUpperCase());
    }
}
