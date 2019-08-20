package com.example.speechapp.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.speechapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder>
{

    Context context;
    List<Message> messages;
    DatabaseReference messageDb;


    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messageDb){
        this.context = context;
        this.messageDb =messageDb;
        this.messages = messages;
    }



    @Override
    public MessageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapterViewHolder holder, int position) {

        Message message = messages.get(position);
        if (message.getName().equals(AllMethods.name)){
            holder.tvTitle.setText("You:"+ message.getMessage());
            holder.tvTitle.setGravity(Gravity.START);

            holder.Li.setBackgroundColor(Color.parseColor("#EF9E73"));

        }
        else {
            holder.tvTitle.setText(message.getName() + ":" +message.getMessage());
            holder.IbDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tvTitle;
        ImageButton IbDelete;
        LinearLayout Li;


        public MessageAdapterViewHolder( View itemView) {
            super(itemView);

            Li =(LinearLayout)itemView.findViewById(R.id.L1Message);
            tvTitle =(TextView)itemView.findViewById(R.id.tvTitle);
            IbDelete=(ImageButton)itemView.findViewById(R.id.ibDelete);

            IbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageDb.child(messages.get(getAdapterPosition()).getKey()).removeValue();
                }
            });
        }
    }
}
