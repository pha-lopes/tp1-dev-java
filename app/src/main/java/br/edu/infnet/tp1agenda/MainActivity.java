package br.edu.infnet.tp1agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.infnet.tp1agenda.DAO.ContatoDAO;
import br.edu.infnet.tp1agenda.contato.Contato;

public class MainActivity extends AppCompatActivity {

    private EditText mNome, mTelefone, mEmail, mCidade;
    private TextView mTxtAlerta;

    private final String EMPTY_FIELD = "Campos não podem estar em branco.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNome = findViewById(R.id.edt_txt_nome);
        mTelefone = findViewById(R.id.edt_txt_telefone);
        mEmail = findViewById(R.id.edt_txt_email);
        mCidade = findViewById(R.id.edt_txt_cidade);
        mTxtAlerta = findViewById(R.id.text_alert);

        Button mBtnLimpar = findViewById(R.id.btn_limpar);
        Button mBtnSalvar = findViewById(R.id.btn_salvar);
        Button mBtnContatos = findViewById(R.id.btn_contatos);

        Intent intent = getIntent();
        // Verifica se há dados de contato para ser exibido
        if(intent.hasExtra("contato")){
            mBtnLimpar.setVisibility(View.INVISIBLE);
            mBtnSalvar.setVisibility(View.INVISIBLE);

            TextView title = findViewById(R.id.main_title);
            title.setText("Contato");

            Contato contato = (Contato) intent.getSerializableExtra("contato");
            mNome.setText(contato.getNome());
            mTelefone.setText(contato.getTelefone());
            mEmail.setText(contato.getEmail());
            mCidade.setText(contato.getCidade());

        } else {
            mBtnLimpar.setOnClickListener(limpar);
            mBtnSalvar.setOnClickListener(salvar);
        }

        // Verifica se há retorno de alerta
        if(intent.hasExtra("alerta")){
            String alerta = (String) intent.getSerializableExtra("alerta");
            Toast.makeText(getApplicationContext(), alerta, Toast.LENGTH_LONG).show();
        }

        mBtnContatos.setOnClickListener(goToContatos);
    }

    private View.OnClickListener limpar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mNome.setText(null);
            mTelefone.setText(null);
            mEmail.setText(null);
            mCidade.setText(null);

            mTxtAlerta.setText(null);
        }
    };

    private View.OnClickListener salvar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mTxtAlerta.setText(null);

            String nome = mNome.getText().toString().trim();
            String telefone = mTelefone.getText().toString().trim();
            String email = mEmail.getText().toString().trim();
            String cidade = mCidade.getText().toString().trim();

            if(nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || cidade.isEmpty()){
                mTxtAlerta.setText(EMPTY_FIELD);
            } else {

                ContatoDAO contatoDAO = new ContatoDAO();
                contatoDAO.salvar(new Contato(nome, telefone, email, cidade));
            }

        }
    };

    private View.OnClickListener goToContatos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ContatosActivity.class);
            startActivity(intent);
        }
    };

}
