package com.cleanup.todoc.ui.task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.FragmentTaskListBinding;
import com.cleanup.todoc.ui.NavigationListener;
import com.cleanup.todoc.ui.ViewModelFactory;

import java.util.List;

public class TaskListFragment extends Fragment implements TasksAdapter.DeleteTaskListener{

    private TasksViewModel mTaskViewModel;
    private FragmentTaskListBinding binding;
    private NavigationListener navigationListener;

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        navigationListener = (NavigationListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        mTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(getActivity())).get(TasksViewModel.class);

        TasksAdapter adapter = new TasksAdapter(this);
        binding.listTasks.setAdapter(adapter);

        mTaskViewModel.getTasksViewStateLiveData().observe(getViewLifecycleOwner(), new Observer<List<TasksViewStates>>() {
            @Override
            public void onChanged(List<TasksViewStates> tasksViewStates) {
                updateView(tasksViewStates);
                adapter.submitList(tasksViewStates);
            }
        });

        binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationListener.displayAddTaskDialog();
            }
        });

        return binding.getRoot();
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
    public void onDeleteTaskViewStates(TasksViewStates tasksViewStates) {
        mTaskViewModel.onDeleteTask(tasksViewStates.getTaskId());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean handled = false;
        int id = item.getItemId();
        // Will move this to ViewModel
        TasksViewModel.SortMethod sortMethod = TasksViewModel.SortMethod.NONE;

        if (id == R.id.filter_alphabetical) {
            sortMethod = TasksViewModel.SortMethod.ALPHABETICAL;
            handled = true;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = TasksViewModel.SortMethod.ALPHABETICAL_INVERTED;
            handled = true;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = TasksViewModel.SortMethod.OLD_FIRST;
            handled = true;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = TasksViewModel.SortMethod.RECENT_FIRST;
            handled = true;
        }

        mTaskViewModel.onSortTaskMenuClick(sortMethod);

        if (handled) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
