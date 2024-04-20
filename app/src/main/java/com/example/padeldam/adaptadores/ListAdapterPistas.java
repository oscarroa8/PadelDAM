package com.example.padeldam.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.padeldam.R;
import com.example.padeldam.back.dao.ClienteRepositorio;
import com.example.padeldam.back.dao.PistaRepositorio;
import com.example.padeldam.back.entidades.Pista;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListAdapterPistas extends ArrayAdapter<Pista> {
    private Context contexto;
    private int resourceLayout;
    private PistaRepositorio pr;
    public ListAdapterPistas(@NonNull Context context, int resource, List<Pista> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.resourceLayout = resource;
        this.pr = new PistaRepositorio(FirebaseFirestore.getInstance());
    }

    //Buscar las vistas de cada elemento
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(contexto).inflate(resourceLayout,null);
        }

        Pista pista = getItem(position);


        TextView tvNombre = view.findViewById(R.id.nombretv);
        tvNombre.setText(pista.getNombre());
        TextView tvMaterial = view.findViewById(R.id.materialtv);
        tvMaterial.setText(pista.getMaterial());
        TextView numerotv = view.findViewById(R.id.numerotv);
        numerotv.setText(pista.getNumero());
        TextView tvprecio = view.findViewById(R.id.preciotv);
        tvprecio.setText(pista.getPrecioHora());
        ImageView borrarPista = view.findViewById(R.id.ivBorrarPista);
        borrarPista.setOnClickListener((v) -> borrar(pista));

        return view;
    }


    public void setPistas(List<Pista> result) {
        clear();
        addAll(result);
    }

    private void borrar(Pista pista) {
        pr.borrar(pista)
                .addOnCompleteListener(task -> {
                    remove(pista);
                });

    }
}
