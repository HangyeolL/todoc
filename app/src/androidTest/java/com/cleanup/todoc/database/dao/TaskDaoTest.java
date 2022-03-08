package com.cleanup.todoc.database.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase mTodocDatabase;
    private TaskDao mTaskDao;

    private final Project projectToTest = new Project()
    private final Task taskToTest = new Task();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        mTaskDao = mTodocDatabase.mTaskDao();
    }

    @After
    public void closeDb() throws IOException {
        mTodocDatabase.close();
    }

    public void insertTask() {

    }
}
