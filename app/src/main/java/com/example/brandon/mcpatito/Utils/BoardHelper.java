package com.example.brandon.mcpatito.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.mcpatito.Models.Board;
import com.example.brandon.mcpatito.Models.Ladder;

import java.util.ArrayList;

/**
 * Created by Brandon on 27-Oct-17.
 */

public class BoardHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] BOARD_TABLE_COLUMNS = {
            DBUtils.BOARD_ID,
            DBUtils.BOARD_NAME,
            DBUtils.BOARD_AUTHOR
    };

    public BoardHelper(Context context) {
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Board> getAllBoards(LadderHelper oLadderHelper, SnakeHelper oSnakeHelper) {
        ArrayList<Board> boardsList = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.BOARD_TABLE_NAME, BOARD_TABLE_COLUMNS, null, null, null, null, null);

        //oLadderHelper.open();
        //oSnakeHelper.open();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Board oBoard = parseBoard(cursor);
            //oBoard.setLadders(oLadderHelper.getLaddersByBoardId(oBoard.getId()));
            //oBoard.setSnakes(oSnakeHelper.getSnakesByBoardId(oBoard.getId()));
            boardsList.add(oBoard);
            cursor.moveToNext();
        }
        //oLadderHelper.close();
        //oSnakeHelper.close();
        cursor.close();
        return boardsList;
    }

    private Board parseBoard(Cursor cursor) {
        Board oBoard = new Board();
        oBoard.setId(cursor.getString(cursor.getColumnIndex(DBUtils.BOARD_ID)));
        oBoard.setName(cursor.getString(cursor.getColumnIndex(DBUtils.BOARD_NAME)));
        oBoard.setAuthor(cursor.getString(cursor.getColumnIndex(DBUtils.BOARD_AUTHOR)));
        return oBoard;
    }

    public Board addBoard(String id, String name, String author) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.BOARD_ID, id);
        values.put(DBUtils.BOARD_NAME, name);
        values.put(DBUtils.BOARD_AUTHOR, author);

        database.insert(DBUtils.BOARD_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.BOARD_TABLE_NAME,
                BOARD_TABLE_COLUMNS,
                DBUtils.BOARD_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Board board = parseBoard(cursor);
        cursor.close();

        return board;
    }

}
