package com.example.brandon.mcpatito.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.mcpatito.Models.Snake;

import java.util.ArrayList;

/**
 * Created by Brandon on 27-Oct-17.
 */

public class SnakeHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] SNAKE_TABLE_COLUMNS = {
            DBUtils.SNAKE_ID,
            DBUtils.SNAKE_BOARD_ID,
            DBUtils.SNAKE_BEGIN,
            DBUtils.SNAKE_DESTINATION
    };

    public SnakeHelper(Context context) {
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Snake> getSnakesByBoardId(String boardId) {
        ArrayList<Snake> snakes = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.SNAKE_TABLE_NAME,
                SNAKE_TABLE_COLUMNS,
                DBUtils.SNAKE_BOARD_ID + " = " + boardId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            snakes.add(parseSnake(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return snakes;
    }

    private Snake parseSnake(Cursor cursor) {
        Snake oSnake = new Snake();
        oSnake.setId(cursor.getInt(cursor.getColumnIndex(DBUtils.SNAKE_ID)));
        oSnake.setBoardId(cursor.getString(cursor.getColumnIndex(DBUtils.SNAKE_BOARD_ID)));
        oSnake.setBegin(cursor.getInt(cursor.getColumnIndex(DBUtils.SNAKE_BEGIN)));
        oSnake.setDestination(cursor.getInt(cursor.getColumnIndex(DBUtils.SNAKE_DESTINATION)));
        return oSnake;
    }

    public Snake addSnake(int id, String boardId, int begin, int destination) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.SNAKE_ID, id);
        values.put(DBUtils.SNAKE_BOARD_ID, boardId);
        values.put(DBUtils.SNAKE_BEGIN, begin);
        values.put(DBUtils.SNAKE_DESTINATION, destination);

        database.insert(DBUtils.SNAKE_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.SNAKE_TABLE_NAME,
                SNAKE_TABLE_COLUMNS,
                DBUtils.SNAKE_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Snake snake = parseSnake(cursor);
        cursor.close();

        return snake;
    }
}
