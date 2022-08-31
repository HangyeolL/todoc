package com.cleanup.todoc.ui.addTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.FragmentDialogAddTaskBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.ui.ViewModelFactory;

import java.util.List;

public class AddTaskDialogFragment extends DialogFragment {

    private AddTaskDialogFragmentViewModel mViewModel;

    public static AddTaskDialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDialogAddTaskBinding binding = FragmentDialogAddTaskBinding.inflate(LayoutInflater.from(requireContext()));

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(getActivity())).get(AddTaskDialogFragmentViewModel.class);

        mViewModel.getAllProject().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projectList) {
                // Instead of Spinner in XML we can use AutoComplete text view
                ArrayAdapter<Project> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, projectList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.projectSpinner.setAdapter(spinnerAdapter);

            }
        });

        return binding.getRoot();
    }
}
