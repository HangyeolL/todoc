package com.cleanup.todoc.ui.task;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.Objects;

public class TasksViewStates {

    private final long taskId;
    private final String taskDescription;
    @ColorInt
    private final int projectColor;
    private final String projectName;

    public TasksViewStates(long taskId, String taskDescription, int projectColor, String projectName) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.projectColor = projectColor;
        this.projectName = projectName;

    }

    public long getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getProjectColor() {
        return projectColor;
    }

    public String getProjectName() {
        return projectName;
    }

}
