package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public Project getProject(long id) {
       return mProjectDao.getProject(id);
    }

    public LiveData<List<Project>> getAllProjectLiveData() {
        return mProjectDao.getAllProject();
    }

}
