package com.example.RailMitra.service;

import com.example.RailMitra.entity.Train;
import com.example.RailMitra.entity.TrainSchedule;
import com.example.RailMitra.repo.TrainRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TrainService {

    private TrainRepo trainRepo;

    public TrainService(TrainRepo trainRepo) {
        this.trainRepo = trainRepo;
    }

    public Train addTrain(Train train) {
        return trainRepo.save(train);
    }


    public List<Train> getAlltrain() {
        return trainRepo.findAll();
    }

}
