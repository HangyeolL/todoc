package com.cleanup.todoc.viewmodel;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.addTask.AddTaskDialogFragmentViewModel;
import com.cleanup.todoc.ui.addTask.AddTaskDialogFragmentViewState;
import com.cleanup.todoc.ui.addTask.AddTaskProjectSpinnerItemViewState;
import com.cleanup.todoc.ui.task.TasksViewStates;
import com.cleanup.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTaskDialogFragmentViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;
    private TestExecutor executor;
    private AddTaskDialogFragmentViewModel mViewModel;

    private MutableLiveData<List<Project>> projectListMutableLiveData = new MutableLiveData<>();

    private static final Project PROJECT0 = new Project(0, "project0", 0);
    private static final Project PROJECT1 = new Project(1, "project1", 1);
    private static final Project PROJECT2 = new Project(2, "project2", 2);

    @Before
    public void setUp() {
        mProjectRepository = Mockito.mock(ProjectRepository.class);
        mTaskRepository = Mockito.mock(TaskRepository.class);
        executor = new TestExecutor();

        projectListMutableLiveData.setValue(getProjectList());

        Mockito.doReturn(projectListMutableLiveData).when(mProjectRepository).getAllProjectLiveData();

        mViewModel = new AddTaskDialogFragmentViewModel(mTaskRepository, mProjectRepository, executor);
    }

    @Test
    public void nominal_case() {
        //Given
        LiveData<AddTaskDialogFragmentViewState> liveData = mViewModel.getViewStateMediatorLiveData();
        liveData.observeForever(addTaskDialogFragmentViewState -> {
        });
        AddTaskDialogFragmentViewState result = liveData.getValue();

        //Then
        List<AddTaskProjectSpinnerItemViewState> list = new ArrayList<>();
        list.add(new AddTaskProjectSpinnerItemViewState(0, 0, "project0"));
        list.add(new AddTaskProjectSpinnerItemViewState(1, 1, "project1"));
        list.add(new AddTaskProjectSpinnerItemViewState(2, 2, "project2"));

        AddTaskDialogFragmentViewState expected = new AddTaskDialogFragmentViewState(list, false);

        assertEquals(
                result,
                expected
        );
    }

    @Test
    public void on_add_task_button_click_with_success() {
        //Given
        long selectedProjectId = 1;
        String taskDescription = "taskDescription";

        //When
        mViewModel.onProjectSelected(selectedProjectId);
        mViewModel.onTaskDescriptionChanged(taskDescription);
        mViewModel.onAddTaskButtonClick();

        //Then
        Mockito.verify(mTaskRepository).insertTask(new Task(selectedProjectId, taskDescription));
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
}
