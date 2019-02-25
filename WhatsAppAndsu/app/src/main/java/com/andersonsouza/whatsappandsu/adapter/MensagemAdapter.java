package com.andersonsouza.whatsappandsu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.andersonsouza.whatsappandsu.model.Mensagem;

import java.util.List;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;

    private List<Mensagem> mensagens;

    public MensagemAdapter(Context context, List<Mensagem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mensagens = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (mensagens != null && !mensagens.isEmpty()) {
            //recupera dados do rementente
            Preferencias preferencias = new Preferencias(context);
            String idRemetente = preferencias.getIdentificador();

            //inicializar obj p/ montagem da view
            LayoutInflater inflater = (LayoutInflater ) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //recupera mensagem
            Mensagem mensagem = mensagens.get(position);

            //montar view a partir do xml
            if (idRemetente.equals(mensagem.getIdUsuario())) {
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            } else {
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }


            //recupera elemento p/ exibição
            TextView textoMensagem =  view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());


        }

        return view;
    }
}
