package com.pajakku.tupaimobile.dao;

import android.app.Activity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.Transactionlog;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.util.AppConstant;

/**
 * Created by dul on 17/01/19.
 */

@Database(entities = {Sspunpaid.class, Sspdone.class, Kjs.class, Taxtype.class, Wajibpajak.class, Transactionlog.class}, version = AppConstant.DB_VER, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract SspunpaidDao sspunpaidDao();
    public abstract SspdoneDao sspdoneDao();
    public abstract KjsDao kjsDao();
    public abstract TaxtypeDao taxTypeDao();
    public abstract WajibpajakDao wajibpajakDao();
    public abstract TransactionlogDao transactionlogDao();

    public static AppDatabase get(Activity act){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(act.getApplicationContext(),
                    AppDatabase.class, AppConstant.DB_NAME)
//                    .addMigrations(MIGRATION_1_2)
//                    .addMigrations(MIGRATION_2_3)
//                    .addMigrations(MIGRATION_3_4)
//                    .addMigrations(MIGRATION_4_5)
                    .build();
        }
        return appDatabase;
    }
}
