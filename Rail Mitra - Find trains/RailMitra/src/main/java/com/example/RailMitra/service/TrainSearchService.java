package com.example.RailMitra.service;

import com.example.RailMitra.entity.TrainSchedule;
import com.example.RailMitra.repo.TrainScheduleRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TrainSearchService {

    private TrainScheduleRepo trainScheduleRepo;

    public TrainSearchService(TrainScheduleRepo trainScheduleRepo) {
        this.trainScheduleRepo = trainScheduleRepo;
    }

    public List<TrainSchedule> FindTrainByStationCode(String SourceCode, String DestinationCode) {
        return trainScheduleRepo.findBySource_StationCodeAndDestination_StationCode(SourceCode,DestinationCode);
    }

    public List<TrainSchedule> FindTrainByStationName(String SourceName, String DestinationName) {
        return trainScheduleRepo.findBySource_StationNameAndDestination_StationName(SourceName,DestinationName);
    }
}
