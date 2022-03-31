package com.cleanup.todoc.repository;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskRepositoryTest {

    private TaskDao mTaskDao;
    private TaskRepository mTaskRepository;

    @Before
    public void setUp() {
        mTaskDao = Mockito.mock(TaskDao.class);
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
        mTaskRepository.deleteAllTasks();

        Mockito.verify(mTaskDao).deleteAllTasks();
    }

    @Test
    public void getAllTasks() {
        mTaskRepository.getAllTasks();

        Mockito.verify(mTaskDao, Mockito.atLeastOnce()).getAllTasks();
    }
}