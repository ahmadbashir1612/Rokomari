package com.project.rokomari.Database;

import static com.project.rokomari.Database.TaskDataModel.COLUMN_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.project.rokomari.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Database helper.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "issue_db";


    /**
     * Instantiates a new Database helper.
     *
     * @param context the context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table
        db.execSQL(TaskDataModel.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TaskDataModel.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    /**
     * Insert field status long.
     *
     * @param data the data
     * @return the long
     */
    public long insertTask(Task data) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(TaskDataModel.COLUMN_TASK_NAME, data.getTaskName());
        values.put(TaskDataModel.COLUMN_CREATED_DATE, data.getCreatedDate());
        values.put(TaskDataModel.COLUMN_DEADLINE, data.getDeadline());
        values.put(TaskDataModel.COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_STATUS, data.getStatus());
        values.put(TaskDataModel.COLUMN_EMAIL, data.getEmail());
        values.put(TaskDataModel.COLUMN_PHONE, data.getPhone());
        values.put(TaskDataModel.COLUMN_URL, data.getUrl());

        // insert row
        long id = db.insert(TaskDataModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Task getTask(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TaskDataModel.TABLE_NAME,
                new String[]{TaskDataModel.COLUMN_ID, TaskDataModel.COLUMN_TASK_NAME, TaskDataModel.COLUMN_CREATED_DATE, TaskDataModel.COLUMN_DEADLINE,
                        TaskDataModel.COLUMN_DESCRIPTION,
                        TaskDataModel.COLUMN_STATUS, TaskDataModel.COLUMN_EMAIL
                        , TaskDataModel.COLUMN_EMAIL, TaskDataModel.COLUMN_PHONE, TaskDataModel.COLUMN_URL},
                TaskDataModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Task task = new Task(
                cursor.getInt(cursor.getColumnIndex(TaskDataModel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_CREATED_DATE)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_DEADLINE)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TaskDataModel.COLUMN_STATUS)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_URL)));

        // close the db connection
        cursor.close();
        return task;
    }

    /**
     * Gets all field status.
     *
     * @return the all status
     */
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TaskDataModel.TABLE_NAME + " ORDER BY " + TaskDataModel.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {

                    Task task = new Task();
                    task.setId(cursor.getInt(cursor.getColumnIndex(TaskDataModel.COLUMN_ID)));
                    task.setTaskName(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_TASK_NAME)));
                    task.setCreatedDate(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_CREATED_DATE)));
                    task.setDeadline(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_DEADLINE)));
                    task.setDescription(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_DESCRIPTION)));
                    task.setStatus(cursor.getInt(cursor.getColumnIndex(TaskDataModel.COLUMN_STATUS)));
                    task.setEmail(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_EMAIL)));
                    task.setPhone(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_PHONE)));
                    task.setUrl(cursor.getString(cursor.getColumnIndex(TaskDataModel.COLUMN_URL)));

                    tasks.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {

        } finally {
            cursor.close();
        }

        // close db connection
        db.close();
        return tasks;
    }


    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskDataModel.COLUMN_TASK_NAME, task.getTaskName());
        values.put(TaskDataModel.COLUMN_CREATED_DATE, task.getCreatedDate());
        values.put(TaskDataModel.COLUMN_DEADLINE, task.getDeadline());
        values.put(TaskDataModel.COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_STATUS, task.getStatus());
        values.put(TaskDataModel.COLUMN_EMAIL, task.getEmail());
        values.put(TaskDataModel.COLUMN_PHONE, task.getPhone());
        values.put(TaskDataModel.COLUMN_URL, task.getUrl());

        // updating row
        return db.update(TaskDataModel.TABLE_NAME, values, TaskDataModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }


    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskDataModel.TABLE_NAME, TaskDataModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }


}
