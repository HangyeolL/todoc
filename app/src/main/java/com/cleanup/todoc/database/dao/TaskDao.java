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

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTaskById(long taskId);

    @Query("DELETE FROM Task")
    void deleteAllTasks();

    // Mentor took out order by creationTimestamp
    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getAllTasks();
}
