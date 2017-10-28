package com.example.brandon.mcpatito.Models;

/**
 * Created by Brandon on 27-Oct-17.
 */

public class Snake {
    //Properties
    private int id;
    private String boardId;
    private int begin;
    private int destination;

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
