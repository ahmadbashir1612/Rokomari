package com.project.rokomari.ui.done;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.rokomari.R;
import com.project.rokomari.ui.InProgress.InProgressViewModel;

import butterknife.ButterKnife;

public class DoneFragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_done, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

}
