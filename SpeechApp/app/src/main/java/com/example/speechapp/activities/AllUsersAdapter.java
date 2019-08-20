package com.example.speechapp.activities;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.speechapp.R;

import java.util.ArrayList;

class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.AllUsersViewHolder> {

    Activity context;
    ArrayList<UserModel> userModelArrayList;

    public AllUsersAdapter(Activity context, ArrayList<UserModel>  userModelArrayList){
        this.context= context;
        this.userModelArrayList= userModelArrayList;
    }



    @Override
    public AllUsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users,parent,false);
        AllUsersViewHolder allUsersAdapter = new AllUsersViewHolder(view);

        return allUsersAdapter;
    }

    @Override
    public void onBindViewHolder( AllUsersViewHolder holder, int position) {

        UserModel user =userModelArrayList.get(position);
        holder.textViewName.setText(user.getName());


    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public class AllUsersViewHolder  extends RecyclerView.ViewHolder{

        TextView textViewName;
        Button button;

        public AllUsersViewHolder( View itemView) {
            super(itemView);

            textViewName =(TextView)itemView .findViewById(R.id.Item_Name);
            button =itemView.findViewById(R.id.call_btn);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UserModel userModel = userModelArrayList.get(getAdapterPosition());
                    ((CallsActivity)context).callUser(userModel);

                }
            });
        }
    }
}

