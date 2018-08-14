package br.edu.infnet.tp1agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import br.edu.infnet.tp1agenda.DAO.ContatoDAO;
import br.edu.infnet.tp1agenda.contato.Contato;

public class MainActivity extends AppCompatActivity {

    private ContatoDAO contatoDAO = new ContatoDAO();
    private Contato contato;

    private static final int REQUEST_WRITE_PERMISSIONS_CODE = 42;

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
            title.setText(R.string.main_alternative_title);

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

                /*ContatoDAO contatoDAO = new ContatoDAO();
                contatoDAO.salvar(new Contato(nome, telefone, email, cidade));*/
                contato = new Contato(nome, telefone, email, cidade);
                verificaSePodeEscreverDados();
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

    //PERMISSÃO PARA ESCRITA
    //Tutorial utilizado para construção:
    //https://o7planning.org/en/10541/android-external-storage-tutorial
    private void verificaSePodeEscreverDados() {
        boolean podeEscrever = this.solicitaPermissao(REQUEST_WRITE_PERMISSIONS_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (podeEscrever) {
            contatoDAO.salvar(contato);
        }
    }

    // No Android Level >= 23, tem que pedir ao usuário por permissão
    // com o dispositivo
    private boolean solicitaPermissao(int requestId, String nomePermissao) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Verifica se há permissão
            int permissao = ActivityCompat.checkSelfPermission(this, nomePermissao);


            if (permissao != PackageManager.PERMISSION_GRANTED) {
                // Solicita permissão
                this.requestPermissions(
                        new String[]{nomePermissao},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    // Quando tem o resultado da requisição
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Nota: Se a requisição for cancelada, o array retorna vazio
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(contatoDAO.salvar(contato)){
                    Toast.makeText(getApplicationContext(), "Contato Salvo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocorreu um Erro", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permissão Cancelada!", Toast.LENGTH_SHORT).show();
        }
    }

}
