package com.choubapp.iwim_hub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choubapp.iwim_hub.Model.Matiere;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MatiereAdapter extends FirestoreRecyclerAdapter<Matiere,MatiereAdapter.MatiereListViewHolder> {
    //private ArrayList<Matiere> postList;
    private MatiereAdapter.OnItemClickListener listener;

    public abstract void onItemClick(DocumentSnapshot documentSnapshot, int position);


    public class MatiereListViewHolder extends RecyclerView.ViewHolder {
        public TextView matiere, module, responsable, horaire;

        public MatiereListViewHolder(@NonNull View itemView) {
            super(itemView);
            matiere = itemView.findViewById(R.id.matiere);
            module = itemView.findViewById(R.id.module);
            responsable = itemView.findViewById(R.id.responsable);
            horaire = itemView.findViewById(R.id.horaire);

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

    public void setOnItemClickListener(MatiereAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public MatiereAdapter(@NonNull FirestoreRecyclerOptions<Matiere> options) {
        super(options);
    }

    @NonNull
    @Override
    public MatiereAdapter.MatiereListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usercard, parent, false);
        return new MatiereAdapter.MatiereListViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull MatiereAdapter.MatiereListViewHolder holder, int position, @NonNull Matiere model) {
        holder.matiere.setText(model.getMatiere());
        holder.module.setText(model.getModule());
        holder.horaire.setText(model.getHoraire());
        holder.responsable.setText(model.getResponsable());

    }
}
