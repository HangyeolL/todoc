package com.cleanup.todoc.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.cleanup.todoc.ui.addTask.AddTaskDialogFragmentViewState;

import java.util.Objects;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "project_id", onDelete = CASCADE))
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    private final long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "project_id",index = true)
    private final long projectId;

    /**
     * The name of the task
     */
    private final String name;

    /**
     * Instantiates a new Task.
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     */

    @Ignore
    public Task(long projectId, @NonNull String name) {
        this(0, projectId, name);
    }

    @VisibleForTesting
    public Task(long id, long projectId, @NonNull String name) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
    }

    /**
     * Returns the unique identifier of the task.
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    /**
     * Returns the name of the task.
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && projectId == task.projectId && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name);
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", projectId=" + projectId +
            ", name='" + name + '\'' +
            '}';
    }
}
