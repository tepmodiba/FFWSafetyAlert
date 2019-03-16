package com.dynamicminds.ffwsafetyalert;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dynamicminds.ffwsafetyalert.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private String URL = "https://jsonplaceholder.typicode.com/todos/";
    private JsonArrayRequest jsonArrayRequest;
    private List<Notification> notifications;
    private RecyclerView recyclerView;
    private Context context;
    private TextView mTxt;

    public NotificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        context = getContext();
        notifications = new ArrayList<>();
        recyclerView = view.findViewById(R.id.notification_list);
        mTxt = view.findViewById(R.id.mTxt);
        jsonRequest();
        return view;
    }

    private void jsonRequest() {
        jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                 //mTxt.setText(response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Notification notification = new Notification();
                        notification.setId(jsonObject.getInt("id"));
                        notification.setTitle(jsonObject.getString("title"));
                        notification.setBody(jsonObject.getString("body"));
                        notifications.add(notification);

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

                setUpRecyclerView(notifications);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyInstance.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    private void setUpRecyclerView(List<Notification> notifications) {
        NotificationsRecyclerViewAdapter notificationsRecyclerViewAdapter = new NotificationsRecyclerViewAdapter(this.context, notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(notificationsRecyclerViewAdapter);
    }
}
