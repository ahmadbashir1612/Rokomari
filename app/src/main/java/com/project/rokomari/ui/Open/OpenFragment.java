package com.project.rokomari.ui.Open;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenFragment extends Fragment implements TaskListAdapter.RefreshTaskListener {

    @BindView(R.id.open_task_list_view)
    RecyclerView rvOpenTaskList;

    private OpenViewModel openViewModel;
    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter taskListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        openViewModel = new ViewModelProvider(this).get(OpenViewModel.class);
        openViewModel.init(getActivity());
        View root = inflater.inflate(R.layout.fragment_open, container, false);
        ButterKnife.bind(this, root);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOpenTaskList.setLayoutManager(linearLayoutManager);

        taskListAdapter = new TaskListAdapter(getActivity(),this);
        rvOpenTaskList.setAdapter(taskListAdapter);

        openViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
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
        openViewModel.refreshItems();
    }

    @Override
    public void didRefreshTask() {
        openViewModel.refreshItems();
    }
}