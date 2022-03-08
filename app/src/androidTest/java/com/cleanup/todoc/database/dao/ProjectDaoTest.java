package com.cleanup.todoc.database.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private TodocDatabase mTodocDatabase;
    private ProjectDao mProjectDao;

    private final Project testProject = new Project("Test Project A", 0xFFB4CDBB);
    private final List<Project> predefinedProjectList = Arrays.asList(
            new Project("Projet Lucidia", 0xFFB4CDBA),
            new Project("Projet Tartampion", 0xFFEADAD1),
            new Project("Projet Circus", 0xFFA3CED2));

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        mProjectDao = mTodocDatabase.mProjectDao();
    }

    @After
    public void closeDb() throws IOException {
        mTodocDatabase.close();
    }

    public void insertProjectAndGetProject() {
        mProjectDao.insertProject(testProject);
        Project foundProject = mProjectDao.getProject(testProject.getId());
        assertThat(testProject, equalTo(foundProject));
    }

    public void getAllProject() {
        List<Project> prepopulatedProject = mProjectDao.getAllProject();
        assertThat(prepopulatedProject, equalTo(predefinedProjectList));
    }
}
