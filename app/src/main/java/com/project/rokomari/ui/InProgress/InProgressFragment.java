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

import com.project.rokomari.R;;

import butterknife.ButterKnife;

public class InProgressFragment extends Fragment {

    private InProgressViewModel inProgressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inProgressViewModel =
                new ViewModelProvider(this).get(InProgressViewModel.class);

        View root = inflater.inflate(R.layout.fragment_in_progress, container, false);
        ButterKnife.bind(this, root);

        inProgressViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

}