package com.primedesign.taskschedulerwithalarm.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class, Category.class},
        version = 1, exportSchema = false)

public abstract class TaskDatabase extends RoomDatabase {

    // Create a database instance that acts as a unique singleton throughout the app
    private static TaskDatabase instance;

    //Room generates the code for this method
    public abstract TaskDao taskDao();
    public abstract CategoryDao categoryDao();
    //public abstract TaskCategoryJoinDao taskCategoryJoinDao();

    // get Database instance. Synchronized (one thread at a time can access this method)
    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class,
                    "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    // Populate the database on Create
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private CategoryDao categoryDao;
        //private TaskCategoryJoinDao taskCategoryJoinDao;

        private PopulateDbAsyncTask(TaskDatabase db) {
            taskDao = db.taskDao();
            categoryDao = db.categoryDao();
            //taskCategoryJoinDao = db.taskCategoryJoinDao();
        }

  /*      @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insert(new Task("Buy some milk", "2 liters", "Low","pending",1568804400000L, "Home"));
            taskDao.insert(new Task("Plan your birthday party", "Shopping List", "High","pending", 1569661200000L, "Home"));
            categoryDao.insert(new Category("Home"));
            categoryDao.insert(new Category("Work"));
            return null;
        }*/

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert the existing tasks and categories
            taskDao.insert(new Task("Buy some milk", "2 liters", "Low", "pending", 1568804400000L, "Home"));
            taskDao.insert(new Task("Plan your birthday party", "Shopping List", "High", "pending", 1569661200000L, "Home"));
            categoryDao.insert(new Category("Home"));
            categoryDao.insert(new Category("Work"));

            // Insert 6 more random tasks
            taskDao.insert(new Task("Go for a run", "5 miles", "Medium", "pending", 1570000000000L, "School"));
            taskDao.insert(new Task("Read a book", "Science fiction", "Low", "pending", 1571000000000L, "School"));
            taskDao.insert(new Task("Complete project", "Deadline approaching", "High", "pending", 1572000000000L, "Work"));
            taskDao.insert(new Task("Grocery shopping", "Essentials", "Medium", "pending", 1573000000000L, "Home"));
            taskDao.insert(new Task("Call a friend", "Catch up", "Medium", "pending", 1574000000000L, "School"));
            taskDao.insert(new Task("Prepare dinner", "Italian cuisine", "Low", "pending", 1575000000000L, "Home"));

            // Insert the associated categories
            categoryDao.insert(new Category("School"));
            categoryDao.insert(new Category("Work"));

            return null;
        }

    }
}
