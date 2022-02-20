package com.cleanup.todoc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insertProject(Project project);

    @Query("SELECT * FROM Project WHERE id = :id")
    Project getProject(long id);

    @Query("SELECT * FROM Project")
    List<Project> getAllProject();

}
