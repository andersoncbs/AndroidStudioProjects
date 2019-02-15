package com.andersonsouza.whatsappandsu.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.andersonsouza.whatsappandsu.model.Contato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;
    private DatabaseReference firebase;


    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);
        listView = view.findViewById(R.id.lvContatos);

        contatos = new ArrayList<>();

        adapter = new ArrayAdapter(
            getActivity(),
            R.layout.lista_contato,
            contatos
        );
        listView.setAdapter(adapter);

        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUserLogado = preferencias.getIdentificador();

        firebase = ConfiguracaoFirebase.getFirebase()
                        .child("contatos")
                        .child(identificadorUserLogado);

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //limpar lista
                contatos.clear();

                //listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()) {
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato.getNome());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
