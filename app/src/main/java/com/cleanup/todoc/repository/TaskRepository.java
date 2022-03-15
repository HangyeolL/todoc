package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public void insertTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void updateTask(Task task) {
        mTaskDao.updateTask(task);
    }

    public void deleteTaskById(long taskId) {
        mTaskDao.deleteTaskById(taskId);
    }

    public void deleteAllTasks() {
        mTaskDao.deleteAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }
}
