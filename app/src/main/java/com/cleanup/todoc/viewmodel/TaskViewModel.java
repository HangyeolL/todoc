package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class TaskViewModel extends ViewModel {

    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;

    private LiveData<List<Task>> mTaskList;

    /**
     * Constructor
     */
    public TaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
    }
//  I dont think I need to do this
//    public void init() {
//        if (mTaskList != null) {
//            return;
//        }
//        mTaskList = mTaskRepository.getAllTasks();
//    }

    /**
     * Project Methods
     */
    public Project getProject(long id) {
        return mProjectRepository.getProject(id);
    }

    public List<Project> getAllProject() {
        List<Project> mProjectList;
        Executors.newSingleThreadExecutor().execute(() -> {
            mProjectList = mProjectRepository.getAllProject();
        });
        return mProjectList;
    }

    /**
     * Task methods
     */

    public void insertTask(Task task) {
//        Executors.newSingleThreadExecutor().execute(() -> mTaskRepository.insertTask(task));
        mTaskRepository.insertTask(task);
    }

    public void updateTask(Task task) {
//        Executors.newSingleThreadExecutor().execute(() -> mTaskRepository.updateTask(task));
        mTaskRepository.updateTask(task);
    }

    public void deleteTaskById(long taskId) {
//        Executors.newSingleThreadExecutor().execute(() -> mTaskRepository.deleteTaskById(taskId));
        mTaskRepository.deleteTaskById(taskId);
    }

    public void deleteAllTasks() {
//        Executors.newSingleThreadExecutor().execute(() -> mTaskRepository.deleteAllTasks());
        mTaskRepository.deleteAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
//        return mTaskList;
        return mTaskRepository.getAllTasks();
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.getName().compareTo(right.getName());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.getName().compareTo(left.getName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.getCreationTimestamp() - left.getCreationTimestamp());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.getCreationTimestamp() - right.getCreationTimestamp());
        }
    }
}
