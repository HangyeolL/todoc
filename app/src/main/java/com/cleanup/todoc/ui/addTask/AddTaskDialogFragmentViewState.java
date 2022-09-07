package com.cleanup.todoc.ui.addTask;

import java.util.List;
import java.util.Objects;

public class AddTaskDialogFragmentViewState {

    private final List<AddTaskProjectSpinnerItemViewState> addTaskProjectSpinnerItemViewStateList;
    private final boolean isProgressBarVisible;

    public AddTaskDialogFragmentViewState(
        List<AddTaskProjectSpinnerItemViewState> addTaskProjectSpinnerItemViewStateList,
        boolean isProgressBarVisible
    ) {
        this.addTaskProjectSpinnerItemViewStateList = addTaskProjectSpinnerItemViewStateList;
        this.isProgressBarVisible = isProgressBarVisible;
    }

    public List<AddTaskProjectSpinnerItemViewState> getAddTaskProjectSpinnerItemViewStateList() {
        return addTaskProjectSpinnerItemViewStateList;
    }

    public boolean getProgressBarVisible() {
        return isProgressBarVisible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskDialogFragmentViewState that = (AddTaskDialogFragmentViewState) o;
        return isProgressBarVisible == that.isProgressBarVisible && Objects.equals(addTaskProjectSpinnerItemViewStateList, that.addTaskProjectSpinnerItemViewStateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addTaskProjectSpinnerItemViewStateList, isProgressBarVisible);
    }

    @Override
    public String toString() {
        return "AddTaskDialogFragmentViewState{" +
            "addTaskProjectSpinnerItemViewStateList=" + addTaskProjectSpinnerItemViewStateList +
            ", isProgressBarVisible=" + isProgressBarVisible +
            '}';
    }
}
