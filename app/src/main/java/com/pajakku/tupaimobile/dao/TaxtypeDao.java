package com.pajakku.tupaimobile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pajakku.tupaimobile.model.Taxtype;

import java.util.List;
import java.util.Set;

/**
 * Created by dul on 17/01/19.
 */

@Dao
public interface TaxtypeDao {
    String TABLE_NAME = "tax_type";

    @Insert
    void insertAll(List<Taxtype> data);

    @Query("SELECT * FROM "+ TABLE_NAME)
    List<Taxtype> getAll();

    @Query("SELECT * FROM "+ TABLE_NAME +" where "+Taxtype.COLUMN_ID+" IN (:codes)")
    List<Taxtype> getAll(Set<String> codes);

    @Query("DELETE FROM "+ TABLE_NAME)
    void deleteAll();
}
