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
import com.example.padeldam.back.entidades.Cliente;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterClientes extends ArrayAdapter<Cliente> {
    private Context contexto;
    private int resourceLayout;
    private ClienteRepositorio cr;
    public AdapterClientes(@NonNull Context context, int resource, List<Cliente> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.resourceLayout = resource;
        this.cr = new ClienteRepositorio(FirebaseFirestore.getInstance());
    }

    //Buscar las vistas de cada elemento
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(contexto).inflate(resourceLayout,null);
        }

        Cliente cliente = getItem(position);

        TextView tvNombre = view.findViewById(R.id.textViewNombre);
        tvNombre.setText(cliente.getNombre());
        TextView tvApellido1 = view.findViewById(R.id.textViewApellido1);
        tvApellido1.setText(cliente.getApellido1());
        TextView tvApellido2 = view.findViewById(R.id.textViewApellido2);
        tvApellido2.setText(cliente.getApellido2());
        TextView tvTelefono = view.findViewById(R.id.textViewTelefono);
        tvTelefono.setText(cliente.getTelefono());
        TextView tvMail = view.findViewById(R.id.textViewEmail);
        tvMail.setText(cliente.getMail());
        ImageView borrarCliente = view.findViewById(R.id.ivBorrarPista);
        borrarCliente.setOnClickListener((v) -> borrar(cliente));

        return view;
    }


    public void setClientes(List<Cliente> result) {
        clear();
        addAll(result);
    }

    private void borrar(Cliente cliente) {
        cr.borrar(cliente)
                .addOnCompleteListener(task -> {
                    remove(cliente);
                });

    }
}
