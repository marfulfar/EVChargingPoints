package com.marful.exampleparsedrive.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.marful.exampleparsedrive.Entities.ChargingPoint;

import java.util.List;

@Dao
public interface ChargingPointDao {

    @Query("SELECT * FROM ChargingPoint")
    public List<ChargingPoint> getAllChargingPoints();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<ChargingPoint> chargingPoints);
}
