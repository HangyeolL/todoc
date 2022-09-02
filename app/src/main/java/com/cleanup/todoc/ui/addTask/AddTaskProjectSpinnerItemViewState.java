package com.cleanup.todoc.ui.addTask;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Objects;

public class AddTaskProjectSpinnerItemViewState {

    private final long projectId;

    @ColorInt
    private final int projectColor;

    @NonNull
    private final String projectName;

    public AddTaskProjectSpinnerItemViewState(long projectId, int projectColor, @NonNull String projectName) {
        this.projectId = projectId;
        this.projectColor = projectColor;
        this.projectName = projectName;
    }

    public long getProjectId() {
        return projectId;
    }

    public int getProjectColor() {
        return projectColor;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskProjectSpinnerItemViewState that = (AddTaskProjectSpinnerItemViewState) o;
        return projectId == that.projectId && projectColor == that.projectColor && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectColor, projectName);
    }

    @Override
    public String toString() {
        return "AddTaskProjectSpinnerItemViewState{" +
                "projectId=" + projectId +
                ", projectColor=" + projectColor +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
