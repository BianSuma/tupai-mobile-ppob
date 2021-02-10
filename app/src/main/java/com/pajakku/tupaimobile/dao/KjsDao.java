package com.pajakku.tupaimobile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pajakku.tupaimobile.model.Kjs;

import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Dao
public interface KjsDao {

    @Query("SELECT * FROM "+ Kjs.TABLE_NAME+" ORDER BY "+Kjs.COLUMN_NAME+" COLLATE NOCASE ASC")
    List<Kjs> getAll();

    @Query("SELECT * FROM "+ Kjs.TABLE_NAME+" where "+Kjs.COLUMN_CODE+" = :code")
    Kjs getByCode(String code);

    @Insert
    void insertAll(List<Kjs> data);

    @Query("DELETE FROM "+Kjs.TABLE_NAME)
    void deleteAll();
}
