package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executors;

public class TaskRepository {

    private TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public void insertTask(Task task) {
        Executors.newSingleThreadExecutor().execute(()-> mTaskDao.insertTask(task));
    }

    public void updateTask(Task task) {
        Executors.newSingleThreadExecutor().execute(()-> mTaskDao.updateTask(task));
    }

    public void deleteTaskById(long taskId) {
        Executors.newSingleThreadExecutor().execute(() -> mTaskDao.deleteTaskById(taskId));
    }

    public void deleteAllTasks() {
        Executors.newSingleThreadExecutor().execute(()-> mTaskDao.deleteAllTasks());
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }
}
