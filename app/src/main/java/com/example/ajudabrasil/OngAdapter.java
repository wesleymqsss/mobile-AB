package com.example.ajudabrasil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OngAdapter extends RecyclerView.Adapter<OngAdapter.OngViewHolder> {

    private List<User> ongList;

    public OngAdapter(List<User> ongList) {
        this.ongList = ongList;
    }

    @NonNull
    @Override
    public OngViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ong, parent, false);
        return new OngViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OngViewHolder holder, int position) {
        User currentUser = ongList.get(position);
        holder.textViewNomeOng.setText(currentUser.getUsername());
        holder.textViewTipoCadastroOng.setText("Tipo: " + currentUser.getTipoCadastro());

    }

    @Override
    public int getItemCount() {
        return ongList != null ? ongList.size() : 0;
    }

    static class OngViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomeOng;
        TextView textViewTipoCadastroOng;

        public OngViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeOng = itemView.findViewById(R.id.textViewNomeOng);
            textViewTipoCadastroOng = itemView.findViewById(R.id.textViewTipoCadastroOng);
        }
    }

    public void setOngList(List<User> ongList) {
        this.ongList = ongList;
        notifyDataSetChanged();
    }
}
