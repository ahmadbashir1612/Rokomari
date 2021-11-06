package com.project.rokomari.Database;

public class TaskDataModel {

    public static final String TABLE_NAME = "Task";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK_NAME= "task_name";
    public static final String COLUMN_CREATED_DATE= "created_date";
    public static final String COLUMN_DEADLINE= "deadline";
    public static final String COLUMN_DESCRIPTION= "description";
    public static final String COLUMN_STATUS= "status";
    public static final String COLUMN_EMAIL= "email";
    public static final String COLUMN_PHONE= "phone";
    public static final String COLUMN_URL= "url";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TASK_NAME + " TEXT,"
                    + COLUMN_CREATED_DATE + " TEXT,"
                    + COLUMN_DEADLINE + " TEXT,"
                    + COLUMN_DESCRIPTION+ " TEXT,"
                    + COLUMN_STATUS+ " INTEGER,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"
                    + COLUMN_URL + " TEXT"
                    + ")";
}
