package com.f1insider.storage;

import java.util.List;

public interface SeasonDao {
    void addSeason(int year, String wdc, String wcc) throws EntityNotFoundException;

    boolean isSeason(int year);

    List<String> getAllSeason();
    int getChampionshipsOfDriver(int idDriver);
    String getWcc(int year);
    String getWdc(int year);
    void setWcc(int year, String wcc);
    void setWdc(int year, String wdc);
}
