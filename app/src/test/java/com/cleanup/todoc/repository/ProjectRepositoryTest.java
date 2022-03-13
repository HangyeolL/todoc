package com.cleanup.todoc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class ProjectRepositoryTest {

    private final ProjectDao mProjectDao = Mockito.mock(ProjectDao.class);

    private ProjectRepository mProjectRepository;

    @Before
    public void setUp() {
        mProjectRepository = new ProjectRepository(mProjectDao);
    }

    @Test
    public void getAllProjects() {
        LiveData<List<Project>> projectList = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(projectList).when(mProjectDao).getAllProject();

        // When
        LiveData<List<Project>> result = mProjectRepository.getAllProject();

        // Then
        assertEquals(projectList, result);
        Mockito.verify(mProjectDao).getAllProject();

    }



}
