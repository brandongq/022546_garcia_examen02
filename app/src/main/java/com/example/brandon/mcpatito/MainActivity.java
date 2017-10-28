package com.example.brandon.mcpatito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.brandon.mcpatito.Models.Board;
import com.example.brandon.mcpatito.Models.Ladder;
import com.example.brandon.mcpatito.Models.Snake;
import com.example.brandon.mcpatito.Utils.BoardHelper;
import com.example.brandon.mcpatito.Utils.LadderHelper;
import com.example.brandon.mcpatito.Utils.SnakeHelper;
import com.example.brandon.mcpatito.View.AddBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons
        Button btnGetBoards = (Button) findViewById(R.id.btnGetBoards);
        Button btnAddBoard = (Button) findViewById(R.id.btnAddBoard);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final BoardHelper oBoardHelper = new BoardHelper(this);
        final LadderHelper oLadderHelper = new LadderHelper(this);
        final SnakeHelper oSnakeHelper = new SnakeHelper(this);

        btnGetBoards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, "http://107.170.247.123:2403/snakes-ladders", null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                oBoardHelper.open();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject board = response.getJSONObject(i);
                                        String id = board.getString("id");
                                        //if (oPostHelper.exists(id) == false) {
                                            String name = board.getString("name");
                                            String author = board.getString("author");
                                            //JSONArray ladders = board.getJSONArray("ladders");
                                            //JSONArray snakes = board.getJSONArray("snakes");
                                            oBoardHelper.addBoard(id, name, author);
                                        //}
                                    }
                                oBoardHelper.close();
                                } catch (Exception e) {
                                    oBoardHelper.close();
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "Boards retrieved successfully", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(jsonArrayRequest);
            }
        });

        btnAddBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBoard.class);
                startActivity(intent);
            }
        });
    }




}