package com.ojwang.edkins.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ojwang.edkins.Home.HomeSubCategory.Dao.PaybillDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.WorkerDao;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;

@Database(entities = {PaybillModel.class, WorkerModel.class},version = 1)
public abstract class EdkinsDb extends RoomDatabase {
    public static EdkinsDb instance;

    public abstract PaybillDao paybillDao();
    public abstract WorkerDao workersDao();

    public static synchronized EdkinsDb getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,EdkinsDb.class,"edkins_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private PaybillDao paybillDao;
        private WorkerDao workersDao;

        private PopulateDbAsyncTask(EdkinsDb db){
            workersDao = db.workersDao();
            paybillDao = db.paybillDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
