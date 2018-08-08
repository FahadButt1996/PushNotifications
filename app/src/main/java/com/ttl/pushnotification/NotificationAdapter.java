package com.ttl.pushnotification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ttl.pushnotification.DAO.DataBase;
import com.ttl.pushnotification.model.NotificationModel;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public Context context;
    public List<NotificationModel> list;

    public NotificationAdapter(Context cn, List<NotificationModel> li) {
        this.context = cn;
        this.list = li;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_child, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).title);
        holder.message.setText(list.get(position).textBody);
        holder.date.setText(list.get(position).date);

        holder.circle_text.setText(list.get(position).title.substring(0,3));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.getInstance(context).getDao().DeleteSpecificNotification(list.get(position).id);
                Toast.makeText(context, "clicked on delete button", Toast.LENGTH_SHORT).show();
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked on share button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView message;
        private TextView date;
        private Button delete;
        private Button share;
        private TextView circle_text;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            delete = itemView.findViewById(R.id.delete);
            share = itemView.findViewById(R.id.share);
            circle_text = itemView.findViewById(R.id.circle_text);
        }
    }
}
