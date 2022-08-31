package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {
    /**
     * Singleton
     */
    private static volatile TodocDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDao mProjectDao();

    public abstract TaskDao mTaskDao();

    // --- INSTANCE ---
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class, "TodocDatabase.db")
                    .allowMainThreadQueries()
                    .addCallback(prepopulateDatabase())
                    .build();
            }
        }

        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {

        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Executors.newSingleThreadExecutor().execute(() -> {
                    INSTANCE.mProjectDao().insertProject(new Project("Projet Lucidia", 0xFFB4CDBA));
                    INSTANCE.mProjectDao().insertProject(new Project("Projet Tartampion", 0xFFEADAD1));
                    INSTANCE.mProjectDao().insertProject(new Project("Projet Circus", 0xFFA3CED2));

                });
            }
        };
    }
}
