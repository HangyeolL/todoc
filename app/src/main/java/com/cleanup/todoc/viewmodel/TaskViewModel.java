package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class TaskViewModel extends ViewModel {

    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;

    /**
     * Constructor
     */
    public TaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
    }

    /**
     * Project Methods
     */
    public Project getProject(long id) {
        return mProjectRepository.getProject(id);
    }

    public List<Project> getAllProject() {
        return mProjectRepository.getAllProject();
    }

    /**
     * Task methods
     */

    public void insertTask(Task task) {
        Executors.newSingleThreadExecutor().execute(()-> mTaskRepository.insertTask(task));
    }

    public void updateTask(Task task) {
        Executors.newSingleThreadExecutor().execute(()-> mTaskRepository.updateTask(task));
    }

    public void deleteTaskById(long taskId) {
        Executors.newSingleThreadExecutor().execute(() -> mTaskRepository.deleteTaskById(taskId));
    }

    public void deleteAllTasks() {
        Executors.newSingleThreadExecutor().execute(()-> mTaskRepository.deleteAllTasks());
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }
}
