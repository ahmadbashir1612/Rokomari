package com.project.rokomari.ui.InProgress;

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
;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InProgressFragment extends Fragment implements TaskListAdapter.RefreshTaskListener {
    @BindView(R.id.in_progress_task_list_view)
    RecyclerView rvInProgressTaskList;

    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter taskListAdapter;

    private InProgressViewModel inProgressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inProgressViewModel =
                new ViewModelProvider(this).get(InProgressViewModel.class);

        inProgressViewModel.init(getActivity());
        View root = inflater.inflate(R.layout.fragment_in_progress, container, false);
        ButterKnife.bind(this, root);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvInProgressTaskList.setLayoutManager(linearLayoutManager);

        taskListAdapter = new TaskListAdapter(getActivity(),this);
        rvInProgressTaskList.setAdapter(taskListAdapter);

        inProgressViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
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
        inProgressViewModel.refreshItems();
    }

    @Override
    public void didRefreshTask() {
        inProgressViewModel.refreshItems();
    }

}