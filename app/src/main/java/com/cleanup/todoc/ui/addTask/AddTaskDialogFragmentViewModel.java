package com.cleanup.todoc.ui.addTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskDialogFragmentViewModel extends ViewModel {

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    @Nullable
    private Long projectId;
    @Nullable
    private String taskName;

    public AddTaskDialogFragmentViewModel(TaskRepository mTaskRepository, ProjectRepository mProjectRepository, Executor mExecutor) {
        this.mTaskRepository = mTaskRepository;
        this.mProjectRepository = mProjectRepository;
        this.mExecutor = mExecutor;
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProjectLiveData();
    }

    public void onProjectSelected(long projectId) {
        this.projectId = projectId;
    }

    public void onTaskDescriptionChanged(String taskName) {
        this.taskName = taskName;
    }

    public void onAddTaskButtonClick() {
        if(projectId != null && taskName != null) {
            mExecutor.execute(() -> mTaskRepository.insertTask(
                    new Task(projectId,
                            taskName,
                            new Date().getTime()
                    )
            ));
        }
    }

}
