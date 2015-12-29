package com.yaacovmushnik.ex10;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btGetJoke;
    private TextView textJoke;
    private RequestQueue queue;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialization();






    }


    private void Initialization()
    {
        btGetJoke=(Button)findViewById(R.id.button);
        textJoke=(TextView)findViewById(R.id.textView);
        dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        textJoke.setText("");

        queue = Volley.newRequestQueue(this);

        btGetJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.GET,
                                "http://api.icndb.com/jokes/random/1",
                                new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            dialog.cancel();
                                            Log.i("aviramLog", "response:" + response);
                                            JSONArray array = response.getJSONArray("value");
                                            Log.i("aviramLog", "array:" + array);
                                            String joke  = array.getJSONObject(0).getString("joke");
                                            Log.i("aviramLog", "joke:" + joke);
                                            textJoke.setText(joke);

                                        }catch (JSONException e){}
                                    }
                                },new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){

                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "ERROR: can't load", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(request);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
