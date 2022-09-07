package com.cleanup.todoc.ui.addTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.AddTaskProjectSpinnerItemBinding;
import com.cleanup.todoc.model.Project;

public class AddTaskProjectSpinnerAdapter extends ArrayAdapter<AddTaskProjectSpinnerItemViewState> {

    public AddTaskProjectSpinnerAdapter(@NonNull Context context) {
        super(context, R.layout.add_task_project_spinner_item);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    public View getCustomView(int position, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddTaskProjectSpinnerItemBinding binding = AddTaskProjectSpinnerItemBinding.inflate(inflater, parent, false);

        AddTaskProjectSpinnerItemViewState item = getItem(position);

        assert item != null;

        binding.imageView.setColorFilter(item.getProjectColor());
        binding.textView.setText(item.getProjectName());

        return binding.getRoot();
    }
}
