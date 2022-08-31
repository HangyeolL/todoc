package com.cleanup.todoc.ui.addTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.ProjectRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskDialogFragmentViewModel extends ViewModel {

    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    public AddTaskDialogFragmentViewModel(ProjectRepository mProjectRepository, Executor mExecutor) {
        this.mProjectRepository = mProjectRepository;
        this.mExecutor = mExecutor;
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProjectLiveData();
    }

}
