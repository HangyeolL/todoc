package com.cleanup.todoc.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.addTask.AddTaskDialogFragmentViewModel;
import com.cleanup.todoc.ui.task.TaskListFragmentViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    private static ViewModelFactory mViewModelFactory;

    /**
     * Singleton
     * instantiate ViewModelFactory with things inside constructors
     */
    public static ViewModelFactory getInstance(Context context) {

        if (mViewModelFactory == null) {
            synchronized (ViewModelFactory.class) {
                mViewModelFactory = new ViewModelFactory(context);
            }
        }
        return mViewModelFactory;
    }

    /**
     * Constructor
     * Instantiate 2 repository
     */
    private ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        mProjectRepository = new ProjectRepository(database.getProjectDao());
        mTaskRepository = new TaskRepository(database.getTaskDao());
        mExecutor = Executors.newFixedThreadPool(2);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(TaskListFragmentViewModel.class)) {
            return (T) new TaskListFragmentViewModel(mProjectRepository, mTaskRepository, mExecutor);
        }
        if (modelClass.isAssignableFrom(AddTaskDialogFragmentViewModel.class)) {
            return (T) new AddTaskDialogFragmentViewModel(mProjectRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
