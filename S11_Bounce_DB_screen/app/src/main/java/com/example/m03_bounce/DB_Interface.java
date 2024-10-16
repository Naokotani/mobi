package com.example.m03_bounce;

import java.util.List;

public interface DB_Interface {
     int count();
     long save(BallModel dataModel);
     int update(BallModel dataModel);
     int deleteById(Long id);
     List<Ball> findAll();
     String getNameById(Long id);
}
