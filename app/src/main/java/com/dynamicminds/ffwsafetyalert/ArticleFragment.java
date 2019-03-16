package com.dynamicminds.ffwsafetyalert;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {

    private String URL = "http://effregapp.org/api/article/";
    private JsonArrayRequest jsonArrayRequest;
    private List<Article> articles;
    private RecyclerView recyclerView;
    private Context context;

    public ArticleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container, false);
        context = getContext();
        articles = new ArrayList<>();
        recyclerView = view.findViewById(R.id.articles_list);

        jsonRequest();

        return view;
    }

    private void jsonRequest() {
        jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                //txt.setText(response.toString());
                Log.d("Data: ", response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Article article = new Article();
                        article.setId(jsonObject.getInt("article_id"));
                        article.setTitle(jsonObject.getString("title"));
                        article.setBody(jsonObject.getString("body"));
                        article.setImage(jsonObject.getString("image"));

                        articles.add(article);

                        Log.d("Articles: ", articles.toString());

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

                setUpRecyclerView(articles);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyInstance.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    private void setUpRecyclerView(List<Article> articles) {
        ArticlesRecyclerViewAdapter articlesRecyclerViewAdapter = new ArticlesRecyclerViewAdapter(context, articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(articlesRecyclerViewAdapter);
    }
}
