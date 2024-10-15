package com.example.m03_bounce;

import java.util.List;

public interface DB_Interface {
    public int count();
    public long save(BallModel dataModel);
    public int update(BallModel dataModel);
    public int deleteById(Long id);
    public List<Ball> findAll();
    public String getNameById(Long id);
}
