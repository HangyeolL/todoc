package com.cleanup.todoc.viewmodel;


import static com.cleanup.todoc.ui.MainActivity.SortMethod;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.utils.TaskComparator;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskViewModel extends ViewModel {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    private List<Project> mProjects;
    private List<Task> mTasks;

    /**
     * Constructor
     */
    public TaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = Executors.newFixedThreadPool(2);

    }

    /**
     * Sorting List of Tasks according to Enum
     */
    public List<Task> sortTask(SortMethod sortOption, List<Task> tasks) {
        switch (sortOption) {
            case ALPHABETICAL:
                Collections.sort(tasks, new TaskComparator.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new TaskComparator.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new TaskComparator.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new TaskComparator.TaskOldComparator());
                break;
        }
        return tasks;
    }

    /**
     * Project Methods
     */
//    public void updateProjects(List<Project> projects) {
//        mProjects = projects;
//    }

    public Project getProject(long id) {
        return mProjectRepository.getProject(id);
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProject();
    }

    /**
     * Task methods
     */

//    public void updateTaskList(List<Task> tasks) {
//        mTasks = tasks;
//    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.insertTask(task));
    }

    public void updateTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.updateTask(task));
    }

    public void deleteTaskById(long taskId) {
        mExecutor.execute(() -> mTaskRepository.deleteTaskById(taskId));
    }

    public void deleteAllTasks() {
        mExecutor.execute(() -> mTaskRepository.deleteAllTasks());
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }
}
