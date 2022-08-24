package com.cleanup.todoc.ui.task;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Objects;

public class TasksViewStates {

    private final long taskId;
    private final String taskDescription;
    @ColorInt
    private final int projectColor;

    public TasksViewStates(long taskId, String taskDescription, int projectColor) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.projectColor = projectColor;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    @ColorInt
    public int getProjectColor() {
        return projectColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksViewStates that = (TasksViewStates) o;
        return taskId == that.taskId && projectColor == that.projectColor && Objects.equals(taskDescription, that.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskDescription, projectColor);
    }

    @NonNull
    @Override
    public String toString() {
        return "TasksViewStates{" +
            "taskId=" + taskId +
            ", taskDescription='" + taskDescription + '\'' +
            ", projectColor=" + projectColor +
            '}';
    }
}
