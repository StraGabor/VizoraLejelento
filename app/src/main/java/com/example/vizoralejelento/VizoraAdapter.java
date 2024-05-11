package com.example.vizoralejelento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class VizoraAdapter extends RecyclerView.Adapter<VizoraAdapter.ViewHolder> implements Filterable {

    private ArrayList<Vizora> vizoraAdatok;
    private ArrayList<Vizora> osszesVizoraAdat;
    private Context context;
    private int lastPosition = -1;

    public VizoraAdapter(Context context, ArrayList<Vizora> vizorak) {
        this.vizoraAdatok = vizorak;
        this.osszesVizoraAdat = vizorak;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.vizoraadat,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VizoraAdapter.ViewHolder holder, int position) {
        Vizora aktualis = vizoraAdatok.get(position);

        holder.bindTo(aktualis);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return vizoraAdatok.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private Filter ugyfelJelentesei = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Vizora> filterLista = new ArrayList<>();
            FilterResults eredmeny = new FilterResults();

            if (constraint.length() == 0 || constraint == null){
                eredmeny.count = osszesVizoraAdat.size();
                eredmeny.values = osszesVizoraAdat;
            }else{
                String szuro = constraint.toString().toLowerCase().trim();
                for (Vizora vizora: osszesVizoraAdat){
                    if (vizora.getUgyfelNeve().equals(szuro)){
                        filterLista.add(vizora);
                    }
                }
                eredmeny.count = filterLista.size();
                eredmeny.values = filterLista;
            }

            return eredmeny;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            vizoraAdatok = (ArrayList<Vizora>) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText azonosito;
        private EditText oraallas;
        private EditText ugyfelnev;
        private EditText email;
        private TextView befizetett;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            azonosito = itemView.findViewById(R.id.azonosito);
            oraallas = itemView.findViewById(R.id.oraallas);
            ugyfelnev = itemView.findViewById(R.id.ugyfelnev);
            email= itemView.findViewById(R.id.email);
            befizetett = itemView.findViewById(R.id.befizetett);
        }

        public void bindTo(@NonNull Vizora aktualis) {
            azonosito.setText(aktualis.getAzonosito());
            oraallas.setText(aktualis.getVizoraAllas());
            ugyfelnev.setText(aktualis.getUgyfelNeve());
            email.setText(aktualis.getEmail());
            befizetett.setText(aktualis.getBefizetett());

            itemView.findViewById(R.id.torol).setOnClickListener(view -> ((VizoraList)context).torles(aktualis));
            itemView.findViewById(R.id.ujradiktal).setOnClickListener(view -> ((VizoraList)context).modositas(aktualis));
        }
    }

}


