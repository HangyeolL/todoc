package com.cleanup.todoc.database.dao;

import static com.cleanup.todoc.LiveDataTestUtil.getOrAwaitValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
                .build();
        mProjectDao = mTodocDatabase.getProjectDao();
    }

    @After
    public void closeDb() {
        mTodocDatabase.close();
    }

    @Test
    public void insert_one_project() throws InterruptedException {
        //Scenario : User requests to insert one project in the list

        //Given : I have empty project list
        List<Project> projectListBefore = getOrAwaitValue(mProjectDao.getAllProject());
        assertTrue(projectListBefore.isEmpty());
        assertFalse(projectListBefore.contains(TEST_PROJECT));

        //When
        mProjectDao.insertProject(TEST_PROJECT);

        //Then
        Project projectFromTheList = getOrAwaitValue(mProjectDao.getAllProject()).get(0);
        assertThat(projectFromTheList.getName(), equalTo(TEST_PROJECT.getName()));
        List<Project> projectListAfter = getOrAwaitValue(mProjectDao.getAllProject());
        assertTrue(projectListAfter.size() == 1);
    }

    @Test
    public void get_the_right_project() throws InterruptedException {
        // Scenario : User requests to find the project

        //Given : I have one project already in the list
        mProjectDao.insertProject(TEST_PROJECT);
        List<Project> projectListBefore = getOrAwaitValue(mProjectDao.getAllProject());
        assertTrue(projectListBefore.size() == 1);

        //When
        Project expectedProject = mProjectDao.getProject(getOrAwaitValue(mProjectDao.getAllProject()).get(0).getId());

        //Then
        assertThat(TEST_PROJECT.getName(), equalTo(expectedProject.getName()));
    }

    @Test
    public void getAllProject() throws InterruptedException {
        //Scenario : User request to get the list of all the project

        //Given : I have a project list with 2 projects inside
        mProjectDao.insertProject(TEST_PROJECT);
        mProjectDao.insertProject(TEST_PROJECT2);

        //When
        List<Project> projectList = getOrAwaitValue(mProjectDao.getAllProject());
        assertFalse(projectList.isEmpty());
        assertEquals(2, projectList.size());

        //Then
        assertThat(TEST_PROJECT.getName(), equalTo(projectList.get(0).getName()));
        assertThat(TEST_PROJECT2.getName(), equalTo(projectList.get(1).getName()));
    }

}
