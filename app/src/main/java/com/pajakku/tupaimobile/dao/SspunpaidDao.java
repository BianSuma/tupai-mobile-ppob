package com.pajakku.tupaimobile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pajakku.tupaimobile.model.Sspunpaid;

import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Dao
public interface SspunpaidDao {

    @Insert
    void insertAll(List<Sspunpaid> data);

    @Update
    void update(Sspunpaid ssp);

    @Query("SELECT * FROM "+ Sspunpaid.TABLE_NAME+" order by "+Sspunpaid.COLUMN_ID+" desc")
    List<Sspunpaid> getAll();

    @Query("SELECT * FROM "+ Sspunpaid.TABLE_NAME+" where "+Sspunpaid.COLUMN_ID+" = :id")
    Sspunpaid getById(long id);

    @Query("DELETE FROM "+ Sspunpaid.TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM "+ Sspunpaid.TABLE_NAME+" where "+Sspunpaid.COLUMN_ID+" = :id")
    void deleteById(long id);
}
