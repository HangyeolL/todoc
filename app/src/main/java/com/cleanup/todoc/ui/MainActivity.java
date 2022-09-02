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

//    private void showAddTaskDialog() {
//        FragmentDialogAddTaskBinding dialogAddTaskBinding = FragmentDialogAddTaskBinding.inflate(LayoutInflater.from(this));
//        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);
//
//        alertBuilder.setTitle(R.string.add_task);
//        alertBuilder.setView(dialogAddTaskBinding.getRoot());
//
//        alertBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mTaskViewModel.onAddTaskButtonClick((Project) dialogAddTaskBinding.projectSpinner.getSelectedItem(), dialogAddTaskBinding.txtTaskName.getText().toString());
//                dialog.dismiss();
//            }
//        });
//        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        alertBuilder.create().show();
//
//        mTaskViewModel.getAllProject().observe(this, new Observer<List<Project>>() {
//            @Override
//            public void onChanged(List<Project> projectList) {
//                final ArrayAdapter<Project> spinnerAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, projectList);
//                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dialogAddTaskBinding.projectSpinner.setAdapter(spinnerAdapter);
//
//            }
//        });
//    }

}
