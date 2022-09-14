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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksViewStates that = (TasksViewStates) o;
        return taskId == that.taskId && projectColor == that.projectColor && Objects.equals(taskDescription, that.taskDescription) && Objects.equals(projectName, that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskDescription, projectColor, projectName);
    }

    @NonNull
    @Override
    public String toString() {
        return "TasksViewStates{" +
            "taskId=" + taskId +
            ", taskDescription='" + taskDescription + '\'' +
            ", projectColor=" + projectColor +
            ", projectName='" + projectName + '\'' +
            '}';
    }
}
