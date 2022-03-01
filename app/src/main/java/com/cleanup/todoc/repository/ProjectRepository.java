package com.cleanup.todoc.repository;

import android.app.Application;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;
import java.util.concurrent.Executors;

public class ProjectRepository {

    private ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public Project getProject(long id) {
       return mProjectDao.getProject(id);
    }

    public List<Project> getAllProject() {
        return mProjectDao.getAllProject();
    }

}
