package com.project.rokomari.ui.InProgress;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.rokomari.Database.DatabaseHelper;
import com.project.rokomari.model.Task;

import java.util.List;

public class InProgressViewModel extends ViewModel {

    private MutableLiveData<List<Task>> tasks;
    DatabaseHelper db;

    void init(Context context){
        if(tasks==null){
            tasks=new MutableLiveData<>();
            db=new DatabaseHelper(context);
            tasks.setValue(db.getTasks(1));
        }
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void refreshItems() {
        tasks.setValue(db.getTasks(1));

    }
}