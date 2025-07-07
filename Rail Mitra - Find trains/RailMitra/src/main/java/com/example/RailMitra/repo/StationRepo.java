package com.example.RailMitra.repo;

import com.example.RailMitra.entity.Station;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepo extends JpaRepository<Station,Long> {
}
