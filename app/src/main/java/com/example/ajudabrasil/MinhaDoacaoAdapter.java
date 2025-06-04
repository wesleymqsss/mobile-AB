package com.example.ajudabrasil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class MinhaDoacaoAdapter extends RecyclerView.Adapter<MinhaDoacaoAdapter.MinhaDoacaoViewHolder> {

    private List<Doacao> doacaoList;
    private Context context;

    public MinhaDoacaoAdapter(List<Doacao> doacaoList, Context context) {
        this.doacaoList = doacaoList;
        this.context = context;
    }

    @NonNull
    @Override
    public MinhaDoacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_minha_doacao, parent, false);
        return new MinhaDoacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MinhaDoacaoViewHolder holder, int position) {
        Doacao currentDoacao = doacaoList.get(position);
        holder.textViewNomeOngDoada.setText("Doado para: " + currentDoacao.getOngNomeRecebedora());
        holder.textViewValorDoacao.setText(String.format(Locale.getDefault(), "Valor: R$ %.2f", currentDoacao.getValor()));
        holder.textViewDataDoacao.setText("Data: " + currentDoacao.getData());
    }

    @Override
    public int getItemCount() {
        return doacaoList != null ? doacaoList.size() : 0;
    }

    static class MinhaDoacaoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomeOngDoada;
        TextView textViewValorDoacao;
        TextView textViewDataDoacao;

        public MinhaDoacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeOngDoada = itemView.findViewById(R.id.textViewNomeOngDoada);
            textViewValorDoacao = itemView.findViewById(R.id.textViewValorDoacao);
            textViewDataDoacao = itemView.findViewById(R.id.textViewDataDoacao);
        }
    }

    public void setDoacaoList(List<Doacao> doacaoList) {
        this.doacaoList = doacaoList;
        notifyDataSetChanged();
    }
}