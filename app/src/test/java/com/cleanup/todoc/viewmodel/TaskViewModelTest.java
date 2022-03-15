package com.cleanup.todoc.viewmodel;

import static org.junit.Assert.assertSame;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.utils.TaskComparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskViewModelTest {

    private final ProjectRepository mProjectRepository = Mockito.mock(ProjectRepository.class);
    private final TaskRepository mTaskRepository = Mockito.mock(TaskRepository.class);
    private TaskViewModel mTaskViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        mTaskViewModel = new TaskViewModel(mProjectRepository, mTaskRepository);
    }

    @Test
    public void getProject() {
        Project testProject = Mockito.mock(Project.class);

        mTaskViewModel.getProject(testProject.getId());

        Mockito.verify(mProjectRepository, Mockito.atLeastOnce()).getProject(testProject.getId());
    }

    @Test
    public void getAllProject() {
        LiveData<List<Project>> projectLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(projectLiveData).when(mProjectRepository).getAllProject();

        LiveData<List<Project>> result = mTaskViewModel.getAllProject();

        Assert.assertEquals(projectLiveData, result);
        Mockito.verify(mProjectRepository, Mockito.atLeastOnce()).getAllProject();
    }

    //To test sorting function
    private List<Task> getTaskList() {
        return Arrays.asList(new Task(1, "aaa", 123), new Task(2, "zzz", 124), new Task(3, "hhh", 125));
    }

    @Test
    public void sortTask() throws InterruptedException {
        List<Task> expectedList = getTaskList();
        Collections.sort(expectedList, new TaskComparator.TaskAZComparator());

        List<Task> sortedList = mTaskViewModel.sortTask(MainActivity.SortMethod.ALPHABETICAL, expectedList);
        Assert.assertEquals(expectedList, sortedList);
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }

    @Test
    public void insertTask() {
        Task testTask = Mockito.mock(Task.class);

        mTaskViewModel.insertTask(testTask);

        Mockito.verify(mTaskRepository).insertTask(testTask);
    }

    @Test
    public void updateTask() {
        Task testTask = Mockito.mock(Task.class);

        mTaskViewModel.updateTask(testTask);

        Mockito.verify(mTaskRepository).updateTask(testTask);
    }

    @Test
    public void deleteTaskById() {
        Task testTask = Mockito.mock(Task.class);

        mTaskViewModel.deleteTaskById(testTask.getId());

        Mockito.verify(mTaskRepository).deleteTaskById(testTask.getId());
    }

    @Test
    public void deleteAllTasks() {
        LiveData<List<Task>> taskLiveData = mTaskViewModel.getAllTasks();

        mTaskViewModel.deleteAllTasks();
        Mockito.verify(mTaskRepository).deleteAllTasks();
        Assert.assertNull(taskLiveData);
    }

    @Test
    public void getAllTasks() {
        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(taskLiveData).when(mTaskRepository).getAllTasks();

        LiveData<List<Task>> result = mTaskViewModel.getAllTasks();

        Assert.assertEquals(taskLiveData, result);
        Mockito.verify(mTaskRepository, Mockito.atLeastOnce()).getAllTasks();
    }

}