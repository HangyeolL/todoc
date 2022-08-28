package com.cleanup.todoc.ui.task;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.databinding.DialogAddTaskBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.ui.ViewModelFactory;

import java.util.List;

public class TasksActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    private ActivityMainBinding binding;

    private TasksAdapter mAdapter;

    private TasksViewModel mTaskViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(TasksViewModel.class);

        mAdapter = new TasksAdapter(this);
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mTaskViewModel.getTasksViewStateLiveData().observe(this, new Observer<List<TasksViewStates>>() {
            @Override
            public void onChanged(List<TasksViewStates> tasksViewStates) {
                updateView(tasksViewStates);
                mAdapter.submitList(tasksViewStates);
                binding.listTasks.setAdapter(mAdapter);
            }
        });

        binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

    }

    private void updateView(List<TasksViewStates> tasksViewStates) {
        if (tasksViewStates.size() == 0) {
            binding.lblNoTask.setVisibility(View.VISIBLE);
            binding.listTasks.setVisibility(View.GONE);
        } else {
            binding.lblNoTask.setVisibility(View.GONE);
            binding.listTasks.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SortMethod sortMethod = SortMethod.NONE;

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }

        mAdapter.submitList(mTaskViewModel.onSortTaskMenuClick(sortMethod, mAdapter.getList()));
        binding.listTasks.setAdapter(mAdapter);

        return super.onOptionsItemSelected(item);
    }

    public void onDeleteTaskViewStates(TasksViewStates tasksViewStates) {
        mTaskViewModel.onDeleteTask(tasksViewStates.getTaskId());
    }

    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

    private void showAddTaskDialog() {
        DialogAddTaskBinding dialogAddTaskBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(this));
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(dialogAddTaskBinding.getRoot());

        alertBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTaskViewModel.onAddTaskButtonClick((Project) dialogAddTaskBinding.projectSpinner.getSelectedItem(), dialogAddTaskBinding.txtTaskName.getText().toString());
                dialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBuilder.create().show();

        mTaskViewModel.getAllProject().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projectList) {
                final ArrayAdapter<Project> spinnerAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, projectList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialogAddTaskBinding.projectSpinner.setAdapter(spinnerAdapter);

            }
        });
    }

}
