package com.project.rokomari.ui.test;

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

public class TestFragment extends Fragment implements TaskListAdapter.RefreshTaskListener {
    @BindView(R.id.test_task_list_view)
    RecyclerView rvTestTaskList;

    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter taskListAdapter;

    private TestViewModel testViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViewModel =
                new ViewModelProvider(this).get(TestViewModel.class);

        testViewModel.init(getActivity());
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, root);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvTestTaskList.setLayoutManager(linearLayoutManager);

        taskListAdapter = new TaskListAdapter(getActivity(),this);
        rvTestTaskList.setAdapter(taskListAdapter);

        testViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
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
        testViewModel.refreshItems();
    }

    @Override
    public void didRefreshTask() {
        testViewModel.refreshItems();
    }
}