package com.cleanup.todoc.repository;

import com.cleanup.todoc.database.dao.ProjectDao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProjectRepositoryTest {

    private ProjectDao mProjectDao;
    private ProjectRepository mProjectRepository;

    @Before
    public void setUp() {
        mProjectDao = Mockito.mock(ProjectDao.class);
        mProjectRepository = new ProjectRepository(mProjectDao);
    }

    @Test
    public void getAllProjects() {
        mProjectRepository.getAllProjectLiveData();

        Mockito.verify(mProjectDao, Mockito.atLeastOnce()).getAllProject();
    }

    @Test
    public void getProject() {
        mProjectRepository.getProject(1);

        Mockito.verify(mProjectDao, Mockito.atLeastOnce()).getProject(1);
    }
}