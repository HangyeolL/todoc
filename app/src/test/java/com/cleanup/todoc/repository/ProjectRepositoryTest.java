package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class ProjectRepositoryTest {

    private ProjectDao mProjectDao = Mockito.mock(ProjectDao.class);
    private ProjectRepository mProjectRepository;

    private static final Project TEST_PROJECT = new Project("Test Project A", 0xFFB4CDBB);


    @Before
    public void setUp() {
        mProjectRepository = new ProjectRepository(mProjectDao);
    }

    @Test
    public void getAllProjects() {
        LiveData<List<Project>> projectLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(projectLiveData).when(mProjectDao).getAllProject();

        LiveData<List<Project>> result = mProjectRepository.getAllProject();

        Assert.assertEquals(projectLiveData, result);
        Mockito.verify(mProjectDao, Mockito.atLeastOnce()).getAllProject();
    }

    @Test
    public void getProject() {
        Mockito.doReturn(TEST_PROJECT).when(mProjectDao).getProject(TEST_PROJECT.getId());

        Project expectedProject = mProjectRepository.getProject(TEST_PROJECT.getId());

        Assert.assertEquals(TEST_PROJECT, expectedProject);
        Mockito.verify(mProjectDao, Mockito.atLeastOnce()).getProject(TEST_PROJECT.getId());
    }

    @Test
    public void getProjectInDifferentWay() {
        Project testProject = Mockito.mock(Project.class);

        mProjectRepository.getProject(testProject.getId());

        Mockito.verify(mProjectDao, Mockito.atLeastOnce()).getProject(testProject.getId());
    }
}