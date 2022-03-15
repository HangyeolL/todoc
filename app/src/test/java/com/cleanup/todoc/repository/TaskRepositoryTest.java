package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class TaskRepositoryTest {

    private TaskDao mTaskDao = Mockito.mock(TaskDao.class);
    private TaskRepository mTaskRepository;

    @Before
    public void setUp() throws Exception {
        mTaskRepository = new TaskRepository(mTaskDao);
    }

    @Test
    public void insertTask() {
        Task testTask = Mockito.mock(Task.class);

        mTaskRepository.insertTask(testTask);

        Mockito.verify(mTaskDao).insertTask(testTask);
    }

    @Test
    public void updateTask() {
        Task testTask = Mockito.mock(Task.class);

        mTaskRepository.updateTask(testTask);

        Mockito.verify(mTaskDao).updateTask(testTask);
    }

    @Test
    public void deleteTaskById() {
        Task testTask = Mockito.mock(Task.class);

        mTaskRepository.deleteTaskById(testTask.getId());

        Mockito.verify(mTaskDao).deleteTaskById(testTask.getId());
    }

    @Test
    public void deleteAllTasks() {
        LiveData<List<Task>> taskLiveData = mTaskRepository.getAllTasks();

        mTaskRepository.deleteAllTasks();
        Mockito.verify(mTaskDao).deleteAllTasks();
        Assert.assertEquals(null, taskLiveData);
    }

    @Test
    public void getAllTasks() {
        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(taskLiveData).when(mTaskDao).getAllTasks();

        LiveData<List<Task>> result = mTaskRepository.getAllTasks();

        Assert.assertEquals(taskLiveData, result);
        Mockito.verify(mTaskDao, Mockito.atLeastOnce()).getAllTasks();
    }
}