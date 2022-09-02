package com.cleanup.todoc.ui.task;


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
import com.cleanup.todoc.utils.TaskComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskListFragmentViewModel extends ViewModel {

    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    private final MutableLiveData<SortMethod> currentSortingMutableLiveData = new MutableLiveData<>(SortMethod.NONE);

    private final MediatorLiveData<List<TasksViewStates>> viewStateMediatorLiveData = new MediatorLiveData<>();

    /**
     * Constructor
     */
    public TaskListFragmentViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mExecutor = executor;

        LiveData<List<Project>> projectsLiveData = projectRepository.getAllProjectLiveData();
        LiveData<List<Task>> tasksLiveData = taskRepository.getAllTasksLiveData();

        viewStateMediatorLiveData.addSource(projectsLiveData, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                combine(projects, tasksLiveData.getValue(), currentSortingMutableLiveData.getValue());
            }
        });
        viewStateMediatorLiveData.addSource(tasksLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                combine(projectsLiveData.getValue(), tasks, currentSortingMutableLiveData.getValue());
            }
        });
        viewStateMediatorLiveData.addSource(currentSortingMutableLiveData, new Observer<SortMethod>() {
            @Override
            public void onChanged(SortMethod sortMethod) {
                combine(projectsLiveData.getValue(), tasksLiveData.getValue(), sortMethod);
            }
        });
    }

    private void combine(@Nullable List<Project> projects, @Nullable List<Task> tasks, @Nullable SortMethod sortMethod) {
        if (projects == null || tasks == null || sortMethod == null) {
            return;
        }

        List<TasksViewStates> tasksViewStatesList = new ArrayList<>();

        Comparator<Task> comparator = sortMethod.getComparator();

        if (comparator != null) {
            Collections.sort(tasks, comparator);
        }

        for (Task task : tasks) {
            for (Project project : projects) {
                if (project.getId() == task.getProjectId()) {
                    tasksViewStatesList.add(
                        new TasksViewStates(
                            task.getId(),
                            task.getName(),
                            project.getColor(),
                            project.getName()
                        )
                    );
                    break;
                }
            }
        }

        viewStateMediatorLiveData.setValue(tasksViewStatesList);
    }

    public LiveData<List<TasksViewStates>> getTasksViewStateLiveData() {
        return viewStateMediatorLiveData;
    }

    public void onSortTaskMenuClick(SortMethod sortMethod) {
        currentSortingMutableLiveData.setValue(sortMethod);
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

    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL(new TaskComparator.TaskAZComparator()),
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED(new TaskComparator.TaskZAComparator()),
        /**
         * Lastly created first
         */
        RECENT_FIRST(new TaskComparator.TaskRecentComparator()),
        /**
         * First created first
         */
        OLD_FIRST(new TaskComparator.TaskOldComparator()),
        /**
         * No sort
         */
        NONE(null);

        @Nullable
        private final Comparator<Task> comparator;

        SortMethod(@Nullable Comparator<Task> comparator) {
            this.comparator = comparator;
        }

        @Nullable
        public Comparator<Task> getComparator() {
            return comparator;
        }
    }
}
