package com.example.brandon.mcpatito.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.brandon.mcpatito.Models.Board;
import com.example.brandon.mcpatito.Models.Ladder;
import com.example.brandon.mcpatito.Models.Snake;
import com.example.brandon.mcpatito.R;
import com.example.brandon.mcpatito.Utils.BoardHelper;
import com.example.brandon.mcpatito.Utils.LadderHelper;
import com.example.brandon.mcpatito.Utils.SnakeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddBoard extends AppCompatActivity {

    ArrayList<Ladder> ladders = new ArrayList<>();
    ArrayList<Snake> snakes = new ArrayList<>();
    BoardHelper oBoardHelper = new BoardHelper(this);
    LadderHelper oLadderHelper = new LadderHelper(this);
    SnakeHelper oSnakeHelper = new SnakeHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        Button btnAddLadder = (Button) findViewById(R.id.btnAddLadder);
        Button btnAddSnake = (Button) findViewById(R.id.btnAddSnake);
        Button btnAddBoard = (Button) findViewById(R.id.btnAddBoard);
        final RequestQueue queue = Volley.newRequestQueue(this);

        btnAddLadder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tbBeginLadder = (EditText) findViewById(R.id.tbBeginLadder);
                EditText tbDestinationLadder = (EditText) findViewById(R.id.tbDestinationLadder);

                String begin = tbBeginLadder.getText().toString();
                String destination = tbDestinationLadder.getText().toString();

                if (tryParseInt(begin) == true && tryParseInt(destination) == true) {
                    int beginInt = Integer.parseInt(begin);
                    int destinationInt = Integer.parseInt(destination);
                    if (beginInt > destinationInt) {
                        Toast.makeText(getApplicationContext(), "The begin must be lower than the destination", Toast.LENGTH_SHORT);
                    } else if (beginInt == 1 || beginInt == 100) {
                        Toast.makeText(getApplicationContext(), "The begin cannot be that value", Toast.LENGTH_SHORT);
                    } else if (beginInt < 1 || beginInt > 100) {
                        Toast.makeText(getApplicationContext(), "The begin is out of bounds", Toast.LENGTH_SHORT);
                    } else if (destinationInt == 1 || destinationInt == 100) {
                        Toast.makeText(getApplicationContext(), "The destination cannot be that value", Toast.LENGTH_SHORT);
                    } else if (destinationInt < 1 || destinationInt > 100) {
                        Toast.makeText(getApplicationContext(), "The destination is out of bounds", Toast.LENGTH_SHORT);
                    } else {
                        Ladder oLadder = new Ladder();
                        oLadder.setBegin(beginInt);
                        oLadder.setDestination(destinationInt);
                        ladders.add(oLadder);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "The values are not numeric", Toast.LENGTH_SHORT);
                }
            }
        });


        btnAddSnake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tbBeginSnake = (EditText) findViewById(R.id.tbBeginSnake);
                EditText tbDestinationSnake = (EditText) findViewById(R.id.tbDestinationSnake);

                String begin = tbBeginSnake.getText().toString();
                String destination = tbDestinationSnake.getText().toString();

                if (tryParseInt(begin) == true && tryParseInt(destination) == true) {
                    int beginInt = Integer.parseInt(begin);
                    int destinationInt = Integer.parseInt(destination);
                    if (beginInt < destinationInt) {
                        Toast.makeText(getApplicationContext(), "The begin must be greater than the destination", Toast.LENGTH_SHORT);
                    } else if (beginInt == 1 || beginInt == 100) {
                        Toast.makeText(getApplicationContext(), "The begin cannot be that value", Toast.LENGTH_SHORT);
                    } else if (beginInt < 1 || beginInt > 100) {
                        Toast.makeText(getApplicationContext(), "The begin is out of bounds", Toast.LENGTH_SHORT);
                    } else if (destinationInt == 1 || destinationInt == 100) {
                        Toast.makeText(getApplicationContext(), "The destination cannot be that value", Toast.LENGTH_SHORT);
                    } else if (destinationInt < 1 || destinationInt > 100) {
                        Toast.makeText(getApplicationContext(), "The destination is out of bounds", Toast.LENGTH_SHORT);
                    } else {
                        Ladder oLadder = new Ladder();
                        oLadder.setBegin(beginInt);
                        oLadder.setDestination(destinationInt);
                        ladders.add(oLadder);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "The values are not numeric", Toast.LENGTH_SHORT);
                }
            }
        });


        btnAddBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ladders.size() == 0 || snakes.size() == 0) {
                    Toast.makeText(getApplicationContext(), "You must have at least one ladder and one snake", Toast.LENGTH_SHORT);
                } else {
                    EditText tbName = (EditText) findViewById(R.id.tbName);
                    EditText tbAuthor = (EditText) findViewById(R.id.tbAuthor);
                    String name = tbName.getText().toString();
                    String author = tbAuthor.getText().toString();

                    Board oBoard = new Board();
                    oBoard.setName(name);
                    oBoard.setAuthor(author);

                    JSONObject jObject = parseBoardToJSONObject(oBoard);

                    JsonObjectRequest jsonPostRequest = new JsonObjectRequest
                            (Request.Method.POST, "http://107.170.247.123:2403/snakes-ladders", jObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                                    } catch (Exception e) {
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    queue.add(jsonPostRequest);
                }
            }
        });
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private JSONObject parseBoardToJSONObject (Board oBoard) {
        JSONObject jObject = new JSONObject();
        JSONArray jLadders = new JSONArray();
        JSONArray jSnakes = new JSONArray();
        for (int i = 0; i < ladders.size(); i++) {
            JSONObject ladder = new JSONObject();
            try {
                Ladder l = ladders.get(i);
                ladder.put("begin", l.getBegin());
                ladder.put("destination", l.getDestination());
                jLadders.put(l);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < snakes.size(); i++) {
            JSONObject snake = new JSONObject();
            try {
                Snake s = snakes.get(i);
                snake.put("begin", s.getBegin());
                snake.put("destination", s.getDestination());
                jSnakes.put(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            jObject.put("name", oBoard.getName());
            jObject.put("author", oBoard.getAuthor());
            jObject.put("ladders", jLadders);
            jObject.put("snakes", jSnakes);

        }catch (JSONException e){

        }

        return jObject;
    }
}
