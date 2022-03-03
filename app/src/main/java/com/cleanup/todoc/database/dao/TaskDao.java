package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("DELETE FROM task WHERE id = :taskId")
    void deleteTaskById(long taskId);

    @Query("DELETE FROM task")
    void deleteAllTasks();

    @Query("SELECT * FROM task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getAllTasks();
}
