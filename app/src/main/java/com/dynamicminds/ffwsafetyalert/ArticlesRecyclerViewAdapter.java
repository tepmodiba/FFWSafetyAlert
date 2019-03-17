package com.dynamicminds.ffwsafetyalert;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<ArticlesRecyclerViewAdapter.RecyclerViewHolder> {


    private Context context;
    private List<Article> articles;
    private RequestOptions options;

    public ArticlesRecyclerViewAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.article_row_item, parent, false);
        view.setId(viewType);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.articleTitle.setText(articles.get(i).getTitle());
        recyclerViewHolder.articleBody.setText(articles.get(i).getBody());

       final int id = articles.get(i).getId();

        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked" + id , Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, ArticleDetails.class).putExtra("id", id));
            }
        });
        //Glide.with(context).load(articles.get(i).getImage()).apply(options).into(recyclerViewHolder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView articleTitle;
        TextView articleBody;
        ImageView articleImage;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            articleTitle = itemView.findViewById(R.id.article_title);
            articleBody = itemView.findViewById(R.id.article_excerpt);
            articleImage = itemView.findViewById(R.id.article_image);
        }


    }
}
