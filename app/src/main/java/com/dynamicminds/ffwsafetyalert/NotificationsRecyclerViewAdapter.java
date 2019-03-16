package com.dynamicminds.ffwsafetyalert;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.RecyclerViewHolder> {
    private Context context;
    private List<Notification> notificatins;

    public NotificationsRecyclerViewAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notificatins = notifications;
    }

    @NonNull
    @Override
    public NotificationsRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.notification_row_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.notificationTitle.setText(notificatins.get(i).getTitle());
        recyclerViewHolder.notificationBody.setText(notificatins.get(i).getBody());
    }

    @Override
    public int getItemCount() {
        return notificatins.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView notificationTitle;
        TextView notificationBody;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationBody = itemView.findViewById(R.id.notification_body);
        }

    }

}
