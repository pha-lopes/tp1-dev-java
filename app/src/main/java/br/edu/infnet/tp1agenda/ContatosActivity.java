package br.edu.infnet.tp1agenda;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tp1agenda.DAO.ContatoDAO;
import br.edu.infnet.tp1agenda.contato.Contato;
import br.edu.infnet.tp1agenda.contato.ContatoAdapter;

public class ContatosActivity extends AppCompatActivity {

    private static final int RREQUEST_READ_PERMISSIONS_CODE = 84;

    private ListView mLvwContatos;
    private ContatoDAO contatoDAO = new ContatoDAO();
    private List<Contato> contatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        mLvwContatos = findViewById(R.id.lvw_contatos);

        askPermissionAndReadFile();

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

    //PERMISSÃO PARA LEITURA
    //Tutorial utilizado para construção:
    //https://o7planning.org/en/10541/android-external-storage-tutorial
    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(RREQUEST_READ_PERMISSIONS_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canRead) {
            contatos = contatoDAO.listar();
        }
    }

    // No Android Level >= 23, tem que pedir ao usuário por permissão
    // com o dispositivo
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Verifica se há permissão
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // Solicita permissão
                this.requestPermissions(
                        new String[]{permissionName},
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
                                           String permissions[],
                                           int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Nota: Se a requisição for cancelada, o array retorna vazio
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contatos = contatoDAO.listar();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

}
