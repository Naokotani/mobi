package com.codelab.basics;

import android.content.Context;

import java.util.List;

public interface Repository<T> {
    int count();
    void save(T entry);
    int update(T entry);  // Not implemented
    int deleteById(Long id);  // Not implemented
    List<T> findAll();
    T findEntryByID(int id);
    void buildDB();
    String getNameById(Long id);  // Not implemented
    void deleteRepository();
}
