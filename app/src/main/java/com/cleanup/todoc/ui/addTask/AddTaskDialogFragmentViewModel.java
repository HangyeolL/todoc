package com.cleanup.todoc.ui.addTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskDialogFragmentViewModel extends ViewModel {

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    private MediatorLiveData<AddTaskDialogFragmentViewState> viewStateMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<Boolean> isProgressBarVisibleMutableLiveData = new MutableLiveData<>(true);

    @Nullable
    private Long projectId;
    @Nullable
    private String taskName;

    public AddTaskDialogFragmentViewModel(TaskRepository mTaskRepository, ProjectRepository mProjectRepository, Executor mExecutor) {
        this.mTaskRepository = mTaskRepository;
        this.mProjectRepository = mProjectRepository;
        this.mExecutor = mExecutor;

        LiveData<List<Project>> projectList = mProjectRepository.getAllProjectLiveData();

        viewStateMediatorLiveData.addSource(projectList, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                combine(projects, isProgressBarVisibleMutableLiveData.getValue());
            }
        });

        viewStateMediatorLiveData.addSource(isProgressBarVisibleMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isProgressBarVisible) {
                combine(projectList.getValue(), isProgressBarVisible);
            }
        });
    }

    private void combine(@Nullable List<Project> projectList, Boolean isProgressBarVisible) {
        if (projectList == null ) {
            return;
        }

        List<AddTaskProjectSpinnerItemViewState> addTaskProjectSpinnerItemViewStateList = new ArrayList<>();

        for (Project project : projectList) {
            AddTaskProjectSpinnerItemViewState addTaskProjectSpinnerItemViewState = new AddTaskProjectSpinnerItemViewState(project.getId(), project.getColor(), project.getName());
            addTaskProjectSpinnerItemViewStateList.add(addTaskProjectSpinnerItemViewState);
        }

        if (!addTaskProjectSpinnerItemViewStateList.isEmpty()) {
            isProgressBarVisibleMutableLiveData.setValue(false);
        } else {
            isProgressBarVisibleMutableLiveData.setValue(true);
        }

        AddTaskDialogFragmentViewState addTaskDialogFragmentViewState =
                new AddTaskDialogFragmentViewState(addTaskProjectSpinnerItemViewStateList, isProgressBarVisibleMutableLiveData.getValue());

        viewStateMediatorLiveData.setValue(addTaskDialogFragmentViewState);
    }

    public MediatorLiveData<AddTaskDialogFragmentViewState> getViewStateMediatorLiveData() {
        return viewStateMediatorLiveData;
    }

    public void onProjectSelected(long projectId) {
        this.projectId = projectId;
    }

    public void onTaskDescriptionChanged(String taskName) {
        this.taskName = taskName;
    }

    public void onAddTaskButtonClick() {
        if (projectId != null && taskName != null && !taskName.isEmpty()) {
            mExecutor.execute(() -> mTaskRepository.insertTask(
                    new Task(projectId,
                            taskName,
                            new Date().getTime()
                    )
            ));
        } else {
            //TODO : toast message to inform that user cant create to do
        }
    }

}
