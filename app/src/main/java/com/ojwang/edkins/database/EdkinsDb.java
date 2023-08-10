package com.ojwang.edkins.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ojwang.edkins.home.homeSubCategory.dao.CreditorDao;
import com.ojwang.edkins.home.homeSubCategory.dao.DebtorDao;
import com.ojwang.edkins.home.homeSubCategory.dao.PaybillDao;
import com.ojwang.edkins.home.homeSubCategory.dao.StockDao;
import com.ojwang.edkins.home.homeSubCategory.dao.StoreDao;
import com.ojwang.edkins.home.homeSubCategory.dao.ToOrderDao;
import com.ojwang.edkins.home.homeSubCategory.dao.WorkerDao;
import com.ojwang.edkins.home.homeSubCategory.model.CreditorModel;
import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.home.homeSubCategory.model.PaybillModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockInModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockOutModel;
import com.ojwang.edkins.home.homeSubCategory.model.StoreModel;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderListModel;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderModel;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerDebtModel;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerModel;

import java.util.concurrent.FutureTask;

@Database(entities = {PaybillModel.class, WorkerModel.class, CreditorModel.class, DebtorModel.class,
        WorkerDebtModel.class, ToOrderModel.class, ToOrderListModel.class, StockModel.class,
        StoreModel.class, StockInModel.class, StockOutModel.class},version = 2,exportSchema = false)
public abstract class EdkinsDb extends RoomDatabase {
    public static EdkinsDb instance;

    public abstract PaybillDao paybillDao();
    public abstract WorkerDao workersDao();
    public abstract CreditorDao creditorDao();
    public abstract DebtorDao debtorDao();
    public abstract ToOrderDao toOrderDao();
    public abstract StockDao stockDao();
    public abstract StoreDao storeDao();

    public static synchronized EdkinsDb getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,EdkinsDb.class,"edkins_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends FutureTask<Void> {

        private EdkinsDb mDb;

        public PopulateDbAsyncTask(EdkinsDb db) {
            super(() -> {
                WorkerDao workersDao = db.workersDao();
                PaybillDao paybillDao = db.paybillDao();
                CreditorDao creditorDao = db.creditorDao();
                DebtorDao debtorDao = db.debtorDao();
                ToOrderDao toOrderDao = db.toOrderDao();
                StockDao stockDao = db.stockDao();
                StoreDao storeDao = db.storeDao();
                // Perform database operations here
                return null;
            });
            mDb = db;
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            mDb = null;
            return mayInterruptIfRunning;
        }
    }
}
