package com.example.RailMitra.controller;

import com.example.RailMitra.entity.Train;
import com.example.RailMitra.service.TrainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {


    private TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<Train> getAlltrain(){
        return trainService.getAlltrain();
    }

    @PostMapping
    public Train addTrain(@RequestBody Train train){
        return trainService.addTrain(train);
    }
}
