package com.cleanup.todoc.ui.task;

import static com.cleanup.todoc.ui.task.TasksActivity.SortMethod;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.utils.TaskComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class TasksViewModel extends ViewModel {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    private final MediatorLiveData<List<TasksViewStates>> viewStateMediatorLiveData = new MediatorLiveData<>();

    /**
     * Constructor
     */
    public TasksViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;

        LiveData<List<Project>> projectsLiveData = projectRepository.getAllProjectLiveData();
        LiveData<List<Task>> tasksLiveData = taskRepository.getAllTasksLiveData();

        viewStateMediatorLiveData.addSource(projectsLiveData, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                combine(projects, tasksLiveData.getValue());
            }
        });
        viewStateMediatorLiveData.addSource(tasksLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                combine(projectsLiveData.getValue(), tasks);
            }
        });

    }

    private void combine(@Nullable List<Project> projects, @Nullable List<Task> tasks) {
        if (projects == null || tasks == null) {
            return;
        }

        List<TasksViewStates> tasksViewStatesList = new ArrayList<>();
        for (Task task : tasks) {
            for (Project project : projects) {
                if (project.getId() == task.getProjectId()) {
                    tasksViewStatesList.add(new TasksViewStates(
                            task.getId(), task.getName(), project.getColor(), project.getName()));
                    break;
                }
            }
        }
        viewStateMediatorLiveData.setValue(tasksViewStatesList);
    }

    public LiveData<List<TasksViewStates>> getTasksViewStateLiveData() {
        return viewStateMediatorLiveData;
    }

    public List<TasksViewStates> onSortTaskMenuClick(SortMethod sortOption, List<TasksViewStates> tasksViewStatesList) {
        switch (sortOption) {
            case ALPHABETICAL:
                Collections.sort(tasksViewStatesList, new TaskComparator.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasksViewStatesList, new TaskComparator.TaskZAComparator());
                break;
//            case RECENT_FIRST:
//                Collections.sort(tasksViewStatesList, new TaskComparator.TaskRecentComparator());
//                break;
//            case OLD_FIRST:
//                Collections.sort(tasksViewStatesList, new TaskComparator.TaskOldComparator());
//                break;
        }
        return tasksViewStatesList;
    }

    /**
     * Project Methods
     */
    public Project getProject(long id) {
        return mProjectRepository.getProject(id);
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProjectLiveData();
    }

    public void deleteAllTasks() {
        mExecutor.execute(() -> mTaskRepository.deleteAllTasks());
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasksLiveData();
    }


    public void onAddTaskButtonClick(@NonNull Project project, @NonNull String taskName) {
        mExecutor.execute(() -> mTaskRepository.insertTask(
                new Task(
                        project.getId(),
                        taskName,
                        new Date().getTime()
                )
        ));
    }

    public void onDeleteTask(long taskId) {
        mExecutor.execute(() -> mTaskRepository.deleteTaskById(taskId));
    }
}
