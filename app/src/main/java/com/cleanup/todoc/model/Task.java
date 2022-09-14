package com.cleanup.todoc.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
     * The timestamp when the task has been created
     */
    private final long creationTimestamp;

    /**
     * Instantiates a new Task.
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    @Ignore
    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this(0, projectId, name, creationTimestamp);
    }

    @VisibleForTesting
    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
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

    public long getCreationTimestamp() {
        return creationTimestamp;
    }
}
