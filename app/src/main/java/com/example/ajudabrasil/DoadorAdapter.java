package com.example.ajudabrasil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DoadorAdapter extends RecyclerView.Adapter<DoadorAdapter.DoadorViewHolder> {

    private List<User> doadorList;

    public DoadorAdapter(List<User> doadorList) {
        this.doadorList = doadorList;
    }

    @NonNull
    @Override
    public DoadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doador, parent, false);
        return new DoadorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoadorViewHolder holder, int position) {
        User currentUser = doadorList.get(position);
        holder.textViewNomeDoador.setText(currentUser.getUsername());
        holder.textViewTipoCadastroDoador.setText("Tipo: " + currentUser.getTipoCadastro());

    }

    @Override
    public int getItemCount() {
        return doadorList != null ? doadorList.size() : 0;
    }

    static class DoadorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomeDoador;
        TextView textViewTipoCadastroDoador;

        public DoadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeDoador = itemView.findViewById(R.id.textViewNomeDoador);
            textViewTipoCadastroDoador = itemView.findViewById(R.id.textViewTipoCadastroDoador);
        }
    }

    public void setDoadorList(List<User> doadorList) {
        this.doadorList = doadorList;
        notifyDataSetChanged();
    }
}