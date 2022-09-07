package com.cleanup.todoc.ui.addTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
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

    private final TaskRepository taskRepository;
    private final Executor executor;

    private final MediatorLiveData<AddTaskDialogFragmentViewState> viewStateMediatorLiveData = new MediatorLiveData<>();

    @Nullable
    private Long projectId;
    @Nullable
    private String taskName;

    public AddTaskDialogFragmentViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;

        LiveData<List<Project>> projectListLiveData = projectRepository.getAllProjectLiveData();

        viewStateMediatorLiveData.setValue(
            new AddTaskDialogFragmentViewState(
                new ArrayList<>(),
                true
            )
        );

        viewStateMediatorLiveData.addSource(projectListLiveData, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                combine(projects);
            }
        });
    }

    private void combine(@Nullable List<Project> projectList) {
        if (projectList == null) {
            return;
        }

        List<AddTaskProjectSpinnerItemViewState> addTaskProjectSpinnerItemViewStateList = new ArrayList<>();

        for (Project project : projectList) {
            addTaskProjectSpinnerItemViewStateList.add(
                new AddTaskProjectSpinnerItemViewState(
                    project.getId(),
                    project.getColor(),
                    project.getName()
                )
            );
        }

        viewStateMediatorLiveData.setValue(
            new AddTaskDialogFragmentViewState(
                addTaskProjectSpinnerItemViewStateList,
                false
            )
        );
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
            executor.execute(() -> taskRepository.insertTask(
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
