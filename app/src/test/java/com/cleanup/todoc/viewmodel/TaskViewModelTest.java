package com.cleanup.todoc.viewmodel;

import static org.junit.Assert.assertSame;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.ui.task.TaskListFragmentViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModelTest {

    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;
    private TaskListFragmentViewModel mTaskViewModel;

    private static final Task TASK1 = new Task(1, "aaa", 123);
    private static final Task TASK2 = new Task(2, "bbb", 124);
    private static final Task TASK3 = new Task(3, "ccc", 125);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        mProjectRepository = Mockito.mock(ProjectRepository.class);
        mTaskRepository = Mockito.mock(TaskRepository.class);
        mTaskViewModel = new TaskListFragmentViewModel(mProjectRepository, mTaskRepository);
    }

    @Test
    public void getProject() {
        Project testProject = Mockito.mock(Project.class);

        mTaskViewModel.getProject(testProject.getId());

        Mockito.verify(mProjectRepository, Mockito.atLeastOnce()).getProject(testProject.getId());
    }

    @Test
    public void getAllProject() {
        mTaskViewModel.getAllProject();

        Mockito.verify(mProjectRepository, Mockito.atLeastOnce()).getAllProjectLiveData();
    }

    //Method for sort function testing purpose
    public List<Task> getTaskList() {
        final List<Task> taskList = new ArrayList<>();
        taskList.add(TASK1);
        taskList.add(TASK2);
        taskList.add(TASK3);
        return taskList;
    }

    @Test
    public void sortTaskByAtoZ() {
        List<Task> taskList = getTaskList();
        ArrayList<Task> sortedTaskList = (ArrayList<Task>) mTaskViewModel.sortTask(MainActivity.SortMethod.ALPHABETICAL, taskList);

        assertSame(sortedTaskList.get(0), TASK1);
        assertSame(sortedTaskList.get(1), TASK2);
        assertSame(sortedTaskList.get(2), TASK3);
    }

    @Test
    public void sortTaskByZtoA() {
        List<Task> taskList = getTaskList();
        ArrayList<Task> sortedTaskList = (ArrayList<Task>) mTaskViewModel.sortTask(MainActivity.SortMethod.ALPHABETICAL_INVERTED, taskList);

        assertSame(sortedTaskList.get(0), TASK3);
        assertSame(sortedTaskList.get(1), TASK2);
        assertSame(sortedTaskList.get(2), TASK1);
    }

    @Test
    public void sortTaskByMostRecentFirst() {
        List<Task> taskList = getTaskList();
        ArrayList<Task> sortedTaskList = (ArrayList<Task>) mTaskViewModel.sortTask(MainActivity.SortMethod.RECENT_FIRST, taskList);

        assertSame(sortedTaskList.get(0), TASK3);
        assertSame(sortedTaskList.get(1), TASK2);
        assertSame(sortedTaskList.get(2), TASK1);
    }

    @Test
    public void sortTaskByOldestFirst() {
        List<Task> taskList = getTaskList();
        ArrayList<Task> sortedTaskList = (ArrayList<Task>) mTaskViewModel.sortTask(MainActivity.SortMethod.OLD_FIRST, taskList);

        assertSame(sortedTaskList.get(0), TASK1);
        assertSame(sortedTaskList.get(1), TASK2);
        assertSame(sortedTaskList.get(2), TASK3);
    }

    @Test
    public void insertTask() throws InterruptedException {
        mTaskViewModel.insertTask(TASK1);

        Thread.sleep(10);

        Mockito.verify(mTaskRepository).insertTask(TASK1);
    }

    @Test
    public void deleteTaskById() throws InterruptedException {
        mTaskViewModel.deleteTaskById(TASK1.getId());

        Thread.sleep(10);

        Mockito.verify(mTaskRepository).deleteTaskById(TASK1.getId());
    }

    @Test
    public void deleteAllTasks() throws InterruptedException {
        mTaskViewModel.deleteAllTasks();

        Thread.sleep(10);

        Mockito.verify(mTaskRepository).deleteAllTasks();
    }

    @Test
    public void getAllTasks() {
        mTaskViewModel.getAllTasks();

        Mockito.verify(mTaskRepository, Mockito.atLeastOnce()).getAllTasksLiveData();
    }

}