package com.cleanup.todoc.database.dao;

import static com.cleanup.todoc.LiveDataTestUtil.getOrAwaitValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase mTodocDatabase;
    private TaskDao mTaskDao;

    @Mock
    private ProjectDao mProjectDao;

    private static final Project TEST_PROJECT = new Project("Test Project A", 0xFFB4CDBF);
    private static final Project TEST_PROJECT2 = new Project("Test Project B", 0xFFB4CDAA);

    private static final Task TEST_TASK = new Task(1, "Test Task A", new Date().getTime());
    private static final Task TEST_TASK2 = new Task(2, "Test Task B", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).allowMainThreadQueries().build();
        mTaskDao = mTodocDatabase.mTaskDao();

        mTodocDatabase.mProjectDao().insertProject(TEST_PROJECT);
        mTodocDatabase.mProjectDao().insertProject(TEST_PROJECT2);
    }

    @After
    public void closeDb() throws IOException {
        mTodocDatabase.close();
    }

    // verify by name and size not containing boolean
    @Test
    public void insertTask() throws InterruptedException {
        List<Task> taskListBefore = getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(0, taskListBefore.size());

        mTaskDao.insertTask(TEST_TASK);
        List<Task> taskListAfter = getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(1, taskListAfter.size());

        Task taskFromTheList = getOrAwaitValue(mTaskDao.getAllTasks()).get(0);
        assertThat(taskFromTheList.getName(), equalTo(TEST_TASK.getName()));
    }

    @Test
    public void deleteTaskById() throws InterruptedException {
        mTaskDao.insertTask(TEST_TASK);
        List<Task> taskList = getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(1, taskList.size());

        mTaskDao.deleteTaskById(taskList.get(0).getId());
        List<Task> taskListAfter = getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(0, taskListAfter.size());
    }

    @Test
    public void deleteAllTasks() throws InterruptedException {
        mTaskDao.insertTask(TEST_TASK);
        mTaskDao.insertTask(TEST_TASK2);
        List<Task> taskList = getOrAwaitValue(mTaskDao.getAllTasks());

        assertThat(TEST_TASK.getName(), equalTo(taskList.get(0).getName()));
        assertThat(TEST_TASK2.getName(), equalTo(taskList.get(1).getName()));
        assertEquals(2, taskList.size());

        mTaskDao.deleteAllTasks();
        List<Task> taskListAfter = getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(0, taskListAfter.size());
    }
}
