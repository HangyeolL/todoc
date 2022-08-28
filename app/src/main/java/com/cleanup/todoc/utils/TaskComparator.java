package com.cleanup.todoc.utils;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.task.TasksViewStates;

import java.util.Comparator;

public class TaskComparator {
    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<TasksViewStates> {
        @Override
        public int compare(TasksViewStates left, TasksViewStates right) {
            return left.getTaskDescription().compareTo(right.getTaskDescription());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<TasksViewStates> {
        @Override
        public int compare(TasksViewStates left, TasksViewStates right) {
            return right.getTaskDescription().compareTo(left.getTaskDescription());
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
