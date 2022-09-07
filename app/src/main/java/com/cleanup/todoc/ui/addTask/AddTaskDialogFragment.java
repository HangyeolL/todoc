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

    ArrayAdapter<Project> projectArrayAdapter;

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
                mViewModel.onProjectSelected(projectArrayAdapter.getItem(position).getId());
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

        mViewModel.getViewStateMediatorLiveData().observe(getViewLifecycleOwner(), new Observer<AddTaskDialogFragmentViewState>() {
            @Override
            public void onChanged(AddTaskDialogFragmentViewState addTaskDialogFragmentViewState) {
                adapter.addAll(addTaskDialogFragmentViewState.getAddTaskProjectSpinnerItemViewStateList());

                if (addTaskDialogFragmentViewState.getProgressBarVisible()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });

//                projectArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.add_task_project_spinner_item, R.id.textView, projectList);
//                projectArrayAdapter.setDropDownViewResource(R.layout.add_task_project_spinner_item);
//                binding.projectSpinner.setAdapter(projectArrayAdapter);

        binding.buttonOk.setOnClickListener(listener -> {
            mViewModel.onAddTaskButtonClick();
            dismiss();
        });

        binding.buttonCancel.setOnClickListener(listener -> {
            dismiss();
        });

        return binding.getRoot();
    }
}
