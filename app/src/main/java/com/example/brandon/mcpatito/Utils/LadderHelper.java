package com.example.brandon.mcpatito.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.mcpatito.Models.Ladder;

import java.util.ArrayList;

/**
 * Created by Brandon on 27-Oct-17.
 */

public class LadderHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] LADDER_TABLE_COLUMNS = {
            DBUtils.LADDER_ID,
            DBUtils.LADDER_BOARD_ID,
            DBUtils.LADDER_BEGIN,
            DBUtils.LADDER_DESTINATION
    };

    public LadderHelper(Context context) {
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Ladder> getLaddersByBoardId(String boardId) {
        ArrayList<Ladder> ladders = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.LADDER_TABLE_NAME,
                LADDER_TABLE_COLUMNS,
                DBUtils.LADDER_BOARD_ID + " = " + boardId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ladders.add(parseLadder(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return ladders;
    }

    private Ladder parseLadder(Cursor cursor) {
        Ladder oLadder = new Ladder();
        oLadder.setId(cursor.getInt(cursor.getColumnIndex(DBUtils.LADDER_ID)));
        oLadder.setBoardId(cursor.getString(cursor.getColumnIndex(DBUtils.LADDER_BOARD_ID)));
        oLadder.setBegin(cursor.getInt(cursor.getColumnIndex(DBUtils.LADDER_BEGIN)));
        oLadder.setDestination(cursor.getInt(cursor.getColumnIndex(DBUtils.LADDER_DESTINATION)));
        return oLadder;
    }

    public Ladder addLadder(int id, String boardId, int begin, int destination) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.LADDER_ID, id);
        values.put(DBUtils.LADDER_BOARD_ID, boardId);
        values.put(DBUtils.LADDER_BEGIN, begin);
        values.put(DBUtils.LADDER_DESTINATION, destination);

        database.insert(DBUtils.LADDER_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.LADDER_TABLE_NAME,
                LADDER_TABLE_COLUMNS,
                DBUtils.LADDER_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Ladder ladder = parseLadder(cursor);
        cursor.close();

        return ladder;
    }
}
