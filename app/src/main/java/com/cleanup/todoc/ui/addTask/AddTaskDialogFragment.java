package com.cleanup.todoc.ui.addTask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        final AddTaskProjectSpinnerAdapter adapter = new AddTaskProjectSpinnerAdapter(requireContext());
        binding.projectSpinner.setAdapter(adapter);

        binding.projectSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.onProjectSelected(adapter.getItem(position).getId());
            }
        });

        binding.txtTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onTaskDescriptionChanged(s.toString());
            }
        });

        mViewModel.getAllProject().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projectList) {
                adapter.addAll(projectList);
            }
        });

        binding.buttonOk.setOnClickListener(listener -> {
            mViewModel.onAddTaskButtonClick();
        });

        binding.buttonCancel.setOnClickListener(listener -> {
            dismiss();
        });

        return binding.getRoot();
    }
}
