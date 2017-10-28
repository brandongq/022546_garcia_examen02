package com.example.brandon.mcpatito.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brandon on 27-Oct-17.
 */

public class DBUtils extends SQLiteOpenHelper {
    public static final String DB_NAME = "McPatitoDB.db";
    public static final int DB_VERSION = 1;

    public static final String BOARD_TABLE_NAME = "Board";
    public static final String BOARD_ID = "Id";
    public static final String BOARD_NAME = "Name";
    public static final String BOARD_AUTHOR = "Author";

    public static final String LADDER_TABLE_NAME = "Ladder";
    public static final String LADDER_ID = "Id";
    public static final String LADDER_BOARD_ID = "BoardId";
    public static final String LADDER_BEGIN = "Begin";
    public static final String LADDER_DESTINATION = "Destination";

    public static final String SNAKE_TABLE_NAME = "Snake";
    public static final String SNAKE_ID = "Id";
    public static final String SNAKE_BOARD_ID = "BoardId";
    public static final String SNAKE_BEGIN = "Begin";
    public static final String SNAKE_DESTINATION = "Destination";

    public static final String DB_CREATE_TABLE_BOARD = "CREATE TABLE " + BOARD_TABLE_NAME + " (" +
            BOARD_ID + " TEXT NOT NULL, " +
            BOARD_NAME + " TEXT NOT NULL, " +
            BOARD_AUTHOR + " TEXT NOT NULL);";

    public static final String DB_CREATE_TABLE_LADDER = "CREATE TABLE " + LADDER_TABLE_NAME+ " (" +
            LADDER_BOARD_ID + " TEXT NOT NULL, " +
            LADDER_BEGIN + " INTEGER NOT NULL, " +
            LADDER_DESTINATION + " INTEGER NOT NULL);";

    public static final String DB_CREATE_TABLE_SNAKE = "CREATE TABLE " + SNAKE_TABLE_NAME+ " (" +
            SNAKE_BOARD_ID + " TEXT NOT NULL, " +
            SNAKE_BEGIN + " INTEGER NOT NULL, " +
            SNAKE_DESTINATION + " INTEGER NOT NULL);";

    public DBUtils (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_BOARD);
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_LADDER);
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_SNAKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Board");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Ladder");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Snake");
        onCreate(sqLiteDatabase);
    }
}
