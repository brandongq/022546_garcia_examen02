package com.example.brandon.mcpatito.Models;

import java.util.ArrayList;

/**
 * Created by Brandon on 27-Oct-17.
 */

public class Board {
    //Properties
    private String id;
    private String name;
    private String author;
    private ArrayList<Ladder> ladders;
    private ArrayList<Snake> snakes;

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Ladder> getLadders() {
        return ladders;
    }

    public void setLadders(ArrayList<Ladder> ladders) {
        this.ladders = ladders;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public void setSnakes(ArrayList<Snake> snakes) {
        this.snakes = snakes;
    }
}
