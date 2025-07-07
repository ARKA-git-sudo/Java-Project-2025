package com.example.RailMitra.repo;

import com.example.RailMitra.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepo extends JpaRepository<Train,Long> {
}
