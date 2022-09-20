package com.cleanup.todoc.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cleanup.todoc.R;
import com.cleanup.todoc.ui.addTask.AddTaskDialogFragment;
import com.cleanup.todoc.ui.task.TaskListFragment;
import com.cleanup.todoc.ui.task.TaskListAdapter;
import com.cleanup.todoc.ui.task.TaskListFragmentViewModel;
import com.cleanup.todoc.ui.task.TasksViewStates;

public class MainActivity extends AppCompatActivity implements TaskListAdapter.DeleteTaskListener, NavigationListener {

    private TaskListFragmentViewModel mTaskViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_frame_layout, TaskListFragment.newInstance())
            .commitNow();
    }

    public void onDeleteTaskViewStates(TasksViewStates tasksViewStates) {
        mTaskViewModel.onDeleteTask(tasksViewStates.getTaskId());
    }

    @Override
    public void displayAddTaskDialog() {
        AddTaskDialogFragment.newInstance().show(getSupportFragmentManager(), null);
    }
}
