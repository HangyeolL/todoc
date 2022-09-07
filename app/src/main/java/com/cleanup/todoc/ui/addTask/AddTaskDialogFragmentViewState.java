package com.cleanup.todoc.ui.addTask;

import java.util.List;
import java.util.Objects;

public class AddTaskDialogFragmentViewState {

    private final List<AddTaskProjectSpinnerItemViewState> mAddTaskProjectSpinnerItemViewStateList;
    private final Boolean isProgressBarVisible;

    public AddTaskDialogFragmentViewState(List<AddTaskProjectSpinnerItemViewState> mAddTaskProjectSpinnerItemViewStateList, Boolean isProgressBarVisible) {
        this.mAddTaskProjectSpinnerItemViewStateList = mAddTaskProjectSpinnerItemViewStateList;
        this.isProgressBarVisible = isProgressBarVisible;
    }

    public List<AddTaskProjectSpinnerItemViewState> getAddTaskProjectSpinnerItemViewStateList() {
        return mAddTaskProjectSpinnerItemViewStateList;
    }

    public Boolean getProgressBarVisible() {
        return isProgressBarVisible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskDialogFragmentViewState that = (AddTaskDialogFragmentViewState) o;
        return Objects.equals(mAddTaskProjectSpinnerItemViewStateList, that.mAddTaskProjectSpinnerItemViewStateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAddTaskProjectSpinnerItemViewStateList);
    }

    @Override
    public String toString() {
        return "AddTaskDialogFragmentViewState{" +
                "mAddTaskProjectSpinnerItemViewStateList=" + mAddTaskProjectSpinnerItemViewStateList +
                '}';
    }

}
