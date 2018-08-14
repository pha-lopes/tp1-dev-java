package br.edu.infnet.tp1agenda.contato;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.infnet.tp1agenda.R;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private Context contexto;

    public ContatoAdapter(@NonNull Context context, List<Contato> contatos) {
        super(context, R.layout.list_view_contatos, contatos);
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        Contato contato = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(contexto).inflate(R.layout.list_view_contatos, parent, false);
        }

        TextView txtItemNome = convertView.findViewById(R.id.txt_nome);

        txtItemNome.setText(contato.getNome());

        return convertView;
    }
}
