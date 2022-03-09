package com.cleanup.todoc.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase mTodocDatabase;
    private TaskDao mTaskDao;

    private final Project testProject = new Project("Test Project A", 0xFFB4CDBB);
    private final Task testTask = new Task(testProject.getId(), "Test Task A", new Date().getTime());
    private final Task testTask2 = new Task(testProject.getId(), "Test Task B", new Date().getTime());

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
        List<Task> taskList = mTaskDao.getAllTasks().getValue();
        assertEquals(0, taskList.size());

        mTaskDao.insertTask(testTask);
        assertTrue(taskList.contains(testTask));
        assertEquals(1, taskList.size());
        assertEquals(taskList.get(0).getName(), testTask.getName());
    }

    public void deleteTaskById() {
        List<Task> taskList = mTaskDao.getAllTasks().getValue();

        mTaskDao.insertTask(testTask);
        assertTrue(taskList.contains(testTask));
        assertEquals(1, taskList.size());

        mTaskDao.deleteTaskById(testTask.getId());
        assertFalse(taskList.contains(testTask));
        assertEquals(0, taskList.size());
    }

    public void deleteAllTasks() {
        List<Task> taskList = mTaskDao.getAllTasks().getValue();
        mTaskDao.insertTask(testTask);
        mTaskDao.insertTask(testTask2);

        assertTrue(taskList.contains(testTask));
        assertTrue(taskList.contains(testTask2));
        assertEquals(2, taskList.size());

        mTaskDao.deleteAllTasks();
        assertFalse(taskList.contains(testTask));
        assertFalse(taskList.contains(testTask2));
        assertEquals(0, taskList.size());
    }
}
