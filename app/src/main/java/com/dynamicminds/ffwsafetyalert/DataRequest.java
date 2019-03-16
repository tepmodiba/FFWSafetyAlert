package com.dynamicminds.ffwsafetyalert;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataRequest {
    public static String objectResults;
    public static JSONArray arrayResults;



    public JSONArray getJsonArray(Context context, String URI) {
        String url = "https://jsonplaceholder.typicode.com/todos/1";


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URI, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        arrayResults = response;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        arrayResults = null;
                    }
                });


        VolleyInstance.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return arrayResults;
    }
}
