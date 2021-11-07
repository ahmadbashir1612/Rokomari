package com.project.rokomari.ui.done;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rokomari.R;
import com.project.rokomari.adapter.TaskListAdapter;
import com.project.rokomari.model.Task;
import com.project.rokomari.ui.InProgress.InProgressViewModel;
import com.project.rokomari.ui.test.TestViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoneFragment extends Fragment implements TaskListAdapter.RefreshTaskListener {

    @BindView(R.id.done_task_list_view)
    RecyclerView rvDoneTaskList;

    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter taskListAdapter;

    DoneViewModel doneViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        doneViewModel =
                new ViewModelProvider(this).get(DoneViewModel.class);

        doneViewModel.init(getActivity());
        View root = inflater.inflate(R.layout.fragment_done, container, false);
        ButterKnife.bind(this, root);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDoneTaskList.setLayoutManager(linearLayoutManager);

        taskListAdapter = new TaskListAdapter(getActivity(),this);
        rvDoneTaskList.setAdapter(taskListAdapter);

        doneViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskListAdapter.setItem(tasks);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        doneViewModel.refreshItems();
    }

    @Override
    public void didRefreshTask() {
        doneViewModel.refreshItems();
    }
}
