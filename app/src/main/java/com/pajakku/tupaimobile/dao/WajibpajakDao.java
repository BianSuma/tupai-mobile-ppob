package com.pajakku.tupaimobile.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pajakku.tupaimobile.model.Wajibpajak;

import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Dao
public interface WajibpajakDao {

    @Query("SELECT * FROM "+ Wajibpajak.TABLE_NAME + " order by "+ Wajibpajak.COLUMN_NAME)
    List<Wajibpajak> getAll();

    @Insert
    void insertAll(List<Wajibpajak> data);

    @Update
    void update(Wajibpajak org);

    @Query("DELETE FROM "+ Wajibpajak.TABLE_NAME)
    void deleteAll();

    @Delete
    void deleteOrg(Wajibpajak... orgs);
}
