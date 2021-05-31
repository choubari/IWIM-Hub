package com.choubapp.iwim_hub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choubapp.iwim_hub.Model.User;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UserListAdapter  extends FirestoreRecyclerAdapter<User,UserListAdapter.UserListViewHolder> {
    //private ArrayList<User> postList;
    private OnItemClickListener listener;


    public class UserListViewHolder extends RecyclerView.ViewHolder {
        public TextView fullname, email;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname= itemView.findViewById(R.id.fullname);
            email= itemView.findViewById(R.id.email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserListAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @NonNull
    @Override
    public UserListAdapter.UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usercard, parent, false);
        return new UserListViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull UserListViewHolder holder, int position, @NonNull User model) {
        holder.fullname.setText(model.getNom()+" "+model.getPrenom());
        System.out.println("nnnn" + model.getNom()+" "+model.getPrenom());
        holder.email.setText(model.getEmail());
    }

}