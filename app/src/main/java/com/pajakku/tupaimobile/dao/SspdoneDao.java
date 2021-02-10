package com.pajakku.tupaimobile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pajakku.tupaimobile.model.Sspdone;

import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Dao
public interface SspdoneDao {

    @Insert
    void insertAll(List<Sspdone> data);

    @Query("SELECT * FROM "+ Sspdone.TABLE_NAME+" order by "+Sspdone.COLUMN_ID+" desc")
    List<Sspdone> getAll();

    @Query("SELECT * FROM "+ Sspdone.TABLE_NAME+" where "+Sspdone.COLUMN_ID+" = :id")
    Sspdone getById(long id);

    @Query("DELETE FROM "+ Sspdone.TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM "+ Sspdone.TABLE_NAME+" where "+Sspdone.COLUMN_ID+" = :id")
    void deleteById(long id);
}
