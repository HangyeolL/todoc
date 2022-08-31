package com.cleanup.todoc.database.dao;

import static com.cleanup.todoc.LiveDataTestUtil.getOrAwaitValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
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

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private TodocDatabase mTodocDatabase;
    private ProjectDao mProjectDao;

    private static final Project TEST_PROJECT = new Project("Test Project A", 0xFFB4CDBB);
    private static final Project TEST_PROJECT2 = new Project("Test Project B", 0xFFB4CDBB);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        mProjectDao = mTodocDatabase.getProjectDao();
    }

    @After
    public void closeDb() {
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

   @Test
   public void getProjectById() throws InterruptedException {
       mProjectDao.insertProject(TEST_PROJECT);

       Project expectedProject = mProjectDao.getProject(getOrAwaitValue(mProjectDao.getAllProject()).get(0).getId());

       assertThat(TEST_PROJECT.getName(), equalTo(expectedProject.getName()));
    }

   @Test
   public void getAllProject() throws InterruptedException {
       mProjectDao.insertProject(TEST_PROJECT);
       mProjectDao.insertProject(TEST_PROJECT2);

       List<Project> projectList = getOrAwaitValue(mProjectDao.getAllProject());
       assertFalse(projectList.isEmpty());
       assertEquals(2, projectList.size());
       assertThat(TEST_PROJECT.getName(), equalTo(projectList.get(0).getName()));
       assertThat(TEST_PROJECT2.getName(), equalTo(projectList.get(1).getName()));
   }

}
