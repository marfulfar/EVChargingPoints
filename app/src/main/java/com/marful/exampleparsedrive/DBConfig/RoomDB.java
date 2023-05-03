package com.marful.exampleparsedrive.DBConfig;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.marful.exampleparsedrive.Entities.ChargingPoint;
import com.marful.exampleparsedrive.daos.ChargingPointDao;

@Database(
        entities = {ChargingPoint.class},
        version = 1
)

public abstract class RoomDB extends RoomDatabase {

    public abstract ChargingPointDao ChargingPointsDAO();
}
