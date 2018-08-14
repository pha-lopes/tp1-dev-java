package br.edu.infnet.tp1agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tp1agenda.DAO.ContatoDAO;
import br.edu.infnet.tp1agenda.contato.Contato;
import br.edu.infnet.tp1agenda.contato.ContatoAdapter;

public class ContatosActivity extends AppCompatActivity {

    private ListView mLvwContatos;
    private ContatoDAO contatoDAO = new ContatoDAO();
    private List<Contato> contatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        mLvwContatos = findViewById(R.id.lvw_contatos);

        contatos = contatoDAO.listar();

        if(contatos.isEmpty()){
            returnEmpty(this);
        }

        mLvwContatos.setOnItemClickListener(detalhes);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLvwContatos.setAdapter(new ContatoAdapter(getApplicationContext(), contatos));
    }

    private String listToString(List<Contato> contatos){
        StringBuilder strContatos = new StringBuilder();

        for(Contato contato : contatos){
            strContatos.append(contato.getNome()+";");
            strContatos.append(contato.getTelefone()+";");
            strContatos.append(contato.getEmail()+";");
            strContatos.append(contato.getCidade()+"|");
        }

        return strContatos.toString().substring(strContatos.length()-1);
    }

    private List<Contato> stringToList(String strContatos){
        List<Contato> contatos = new ArrayList<>();

        String[] arrStrContatos = strContatos.split("|");
        for(String contato : arrStrContatos){
            String[] arrContato = contato.split(";");
            contatos.add(new Contato(
                    arrContato[0],
                    arrContato[1],
                    arrContato[2],
                    arrContato[3]
            ));
        }

        return contatos;
    }

    private void returnEmpty(Context contexto){
        String semContatos = "nenhum registro foi encontrado";

        Intent intent = new Intent(contexto, MainActivity.class);
        intent.putExtra("alerta", semContatos);
        startActivity(intent);
    }

    private AdapterView.OnItemClickListener detalhes = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contato contato = (Contato) parent.getItemAtPosition(position);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("contato", contato);
            startActivity(intent);
        }
    };

}
