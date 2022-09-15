package com.cleanup.todoc.viewmodel;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.task.TaskListFragmentViewModel;
import com.cleanup.todoc.ui.task.TasksViewStates;
import com.cleanup.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragmentViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;
    private TestExecutor executor;
    private TaskListFragmentViewModel mTaskViewModel;

    private static final Project PROJECT0 = new Project(0, "project0", 0);
    private static final Project PROJECT1 = new Project(1, "project1", 1);
    private static final Project PROJECT2 = new Project(2, "project2", 2);

    private static final Task TASK0 = new Task(0, 2, "task0", 122);
    private static final Task TASK1 = new Task(1, 1, "task1", 123);
    private static final Task TASK2 = new Task(2, 0, "task2", 124);

    private final MutableLiveData<List<Project>> projectsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Task>> tasksMutableLiveData = new MutableLiveData<>();

    @Before
    public void setUp() {
        mProjectRepository = Mockito.mock(ProjectRepository.class);
        mTaskRepository = Mockito.mock(TaskRepository.class);
        executor = new TestExecutor();

        projectsMutableLiveData.setValue(getProjectList());
        tasksMutableLiveData.setValue(getTaskList());

        Mockito.doReturn(projectsMutableLiveData).when(mProjectRepository).getAllProjectLiveData();
        Mockito.doReturn(tasksMutableLiveData).when(mTaskRepository).getAllTasksLiveData();

        mTaskViewModel = new TaskListFragmentViewModel(mProjectRepository, mTaskRepository, executor);
    }

    @Test
    public void nominal_case() {
        // When
        LiveData<List<TasksViewStates>> liveData = mTaskViewModel.getTasksViewStateLiveData();
        liveData.observeForever(tasksViewStates -> {
        });
        List<TasksViewStates> result = liveData.getValue();

        // Then
        List<TasksViewStates> expected = new ArrayList<>();
        expected.add(new TasksViewStates(0, "task0", 2, "project2"));
        expected.add(new TasksViewStates(1, "task1", 1, "project1"));
        expected.add(new TasksViewStates(2, "task2", 0, "project0"));

        assertEquals(
            expected,
            result
        );
    }

    @Test
    public void sorting_ALPHABETICAL() {
        // Given
        mTaskViewModel.onSortTaskMenuClick(TaskListFragmentViewModel.SortMethod.ALPHABETICAL);

        // When
        LiveData<List<TasksViewStates>> liveData = mTaskViewModel.getTasksViewStateLiveData();
        liveData.observeForever(tasksViewStates -> {
        });
        List<TasksViewStates> result = liveData.getValue();

        // Then
        List<TasksViewStates> expected = new ArrayList<>();
        expected.add(new TasksViewStates(0, "task0", 2, "project2"));
        expected.add(new TasksViewStates(1, "task1", 1, "project1"));
        expected.add(new TasksViewStates(2, "task2", 0, "project0"));

        assertEquals(
            expected,
            result
        );
    }

    @Test
    public void sorting_ALPHABETICAL_INVERTED() {
        // Given
        mTaskViewModel.onSortTaskMenuClick(TaskListFragmentViewModel.SortMethod.ALPHABETICAL_INVERTED);

        // When
        LiveData<List<TasksViewStates>> liveData = mTaskViewModel.getTasksViewStateLiveData();
        liveData.observeForever(tasksViewStates -> {
        });
        List<TasksViewStates> result = liveData.getValue();

        // Then
        List<TasksViewStates> expected = new ArrayList<>();
        expected.add(new TasksViewStates(2, "task2", 0, "project0"));
        expected.add(new TasksViewStates(1, "task1", 1, "project1"));
        expected.add(new TasksViewStates(0, "task0", 2, "project2"));

        assertEquals(
            expected,
            result
        );
    }

    @Test
    public void sorting_RECENT_FIRST() {
        // Given
        mTaskViewModel.onSortTaskMenuClick(TaskListFragmentViewModel.SortMethod.RECENT_FIRST);

        // When
        LiveData<List<TasksViewStates>> liveData = mTaskViewModel.getTasksViewStateLiveData();
        liveData.observeForever(tasksViewStates -> {
        });
        List<TasksViewStates> result = liveData.getValue();

        // Then
        List<TasksViewStates> expected = new ArrayList<>();
        expected.add(new TasksViewStates(2, "task2", 0, "project0"));
        expected.add(new TasksViewStates(1, "task1", 1, "project1"));
        expected.add(new TasksViewStates(0, "task0", 2, "project2"));

        assertEquals(
            expected,
            result
        );
    }

    @Test
    public void sorting_OLD_FIRST() {
        // Given
        mTaskViewModel.onSortTaskMenuClick(TaskListFragmentViewModel.SortMethod.OLD_FIRST);

        // When
        LiveData<List<TasksViewStates>> liveData = mTaskViewModel.getTasksViewStateLiveData();
        liveData.observeForever(tasksViewStates -> {
        });
        List<TasksViewStates> result = liveData.getValue();

        // Then
        List<TasksViewStates> expected = new ArrayList<>();
        expected.add(new TasksViewStates(0, "task0", 2, "project2"));
        expected.add(new TasksViewStates(1, "task1", 1, "project1"));
        expected.add(new TasksViewStates(2, "task2", 0, "project0"));

        assertEquals(
            expected,
            result
        );
    }

    @Test
    public void onDeleteTask() {
        //Given
        long taskId = 1;

        //When
        mTaskViewModel.onDeleteTask(taskId);

        //Then
        Mockito.verify(mTaskRepository).deleteTaskById(taskId);
    }

    /**
     * Inputs
     */

    public List<Project> getProjectList() {
        final List<Project> taskList = new ArrayList<>();
        taskList.add(PROJECT0);
        taskList.add(PROJECT1);
        taskList.add(PROJECT2);
        return taskList;
    }

    public List<Task> getTaskList() {
        final List<Task> taskList = new ArrayList<>();
        taskList.add(TASK0);
        taskList.add(TASK1);
        taskList.add(TASK2);
        return taskList;
    }
}