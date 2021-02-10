package com.pajakku.tupaimobile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pajakku.tupaimobile.model.Transactionlog;

import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Dao
public interface TransactionlogDao {

    @Insert
    void insertAll(List<Transactionlog> data);

    @Query("SELECT * FROM "+ Transactionlog.TABLE_NAME+" order by "+Transactionlog.COLUMN_UPDATEDAT+" desc")
    List<Transactionlog> getAll();

    @Query("DELETE FROM "+ Transactionlog.TABLE_NAME)
    void deleteAll();
}
