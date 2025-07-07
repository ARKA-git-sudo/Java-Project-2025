package com.example.RailMitra.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainName;

    private String trainNo;


    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TrainSchedule> trainScheduleList;



    public Train(){

    }

    public Train(Long id, String trainName, String trainNo, List<TrainSchedule> trainScheduleList) {
        this.id = id;
        this.trainName = trainName;
        this.trainNo = trainNo;
        this.trainScheduleList = trainScheduleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<TrainSchedule> getTrainScheduleList() {
        return trainScheduleList;
    }

    public void setTrainScheduleList(List<TrainSchedule> trainScheduleList) {
        this.trainScheduleList = trainScheduleList;
    }
}
