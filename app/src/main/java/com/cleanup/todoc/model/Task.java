package com.cleanup.todoc.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
    private long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "project_id",index = true)
    private long projectId;

    /**
     * The name of the task
     */
    private String name;

    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    /**
     * Instantiates a new Task.
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Returns the unique identifier of the task.
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     * @param id the unique idenifier of the task to set
     */
    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     * @param projectId the unique identifier of the project associated to the task to set
     */
    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the name of the task.
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     * @param name the name of the task to set
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Deleted method but I created again to make app work
     * @return
     */

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Sets the timestamp when the task has been created.
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
