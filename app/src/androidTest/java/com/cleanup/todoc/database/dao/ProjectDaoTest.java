package com.cleanup.todoc.database.dao;

import static com.cleanup.todoc.LiveDataTestUtil.getOrAwaitValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private TodocDatabase mTodocDatabase;
    private ProjectDao mProjectDao;

    private static final Project TEST_PROJECT = new Project("Test Project A", 0xFFB4CDBB);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        mProjectDao = mTodocDatabase.mProjectDao();
    }

    @After
    public void closeDb() throws IOException {
        mTodocDatabase.close();
    }

    @Test
    public void insertProject() throws InterruptedException {
        List<Project> projectList = getOrAwaitValue(mProjectDao.getAllProject());
        assertFalse(projectList.contains(TEST_PROJECT));

        mProjectDao.insertProject(TEST_PROJECT);

        Project projectFromTheList = getOrAwaitValue(mProjectDao.getAllProject()).get(0);
        assertThat(projectFromTheList.getName(), equalTo(TEST_PROJECT.getName()));
    }

}
