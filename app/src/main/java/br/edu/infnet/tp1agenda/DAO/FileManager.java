package br.edu.infnet.tp1agenda.DAO;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tp1agenda.contato.Contato;

public class FileManager {

    private final String LOG_MSG = "FILE_MANAGER";
    private final String fileName = "contatos.txt";

    /***
     * Método para persistir contato no aparelho
     * @param contato Objeto Contato
     * @return True se persistiu corretamente
     */
    public boolean salvar(Contato contato){
        List<Contato> contatos = listar();

        contatos.add(contato);

        String strContatos = listToString(contatos);

        Log.i(LOG_MSG, strContatos);

        return writeFile(strContatos);
    }

    /***
     * Método para resgatar contatos no aparelho
     * @return ArrayList de Contato
     */
    public List<Contato> listar() {

        List<Contato> lstContato;

        lstContato = stringToList(readFile());

        return lstContato;
    }

    private boolean writeFile(String contatos) {

        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("ExternalStorageDemo", "Save to: " + path);

        try {
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(contatos);
            myOutWriter.close();
            fOut.close();

            Log.i(LOG_MSG, "Contato Salvo");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_MSG, e.getMessage());
            return false;
        }
    }

    private String readFile() {

        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/contatos.txt
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("ExternalStorageDemo", "Read file: " + path);

        String s;
        String fileContent = "";
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));

            while ((s = myReader.readLine()) != null) {
                fileContent += s;
            }
            myReader.close();

            return fileContent;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_MSG, e.getMessage());
            return fileContent;
        }
    }

    /*
            O método abaixo é voltado para a conversão de uma List<Contato>
            em uma String para poder ser gravada em um arquivo de texto.
             */
    private String listToString(List<Contato> contatos){
        StringBuilder strContatos = new StringBuilder();

        for(Contato contato : contatos){
            strContatos.append(contato.getNome());
            strContatos.append(",");
            strContatos.append(contato.getTelefone());
            strContatos.append(",");
            strContatos.append(contato.getEmail());
            strContatos.append(",");
            strContatos.append(contato.getCidade());
            strContatos.append(";");
        }

        return strContatos.toString();
    }

    /*
        O método abaixo é voltado para a conversão de uma String
        em uma List<Contato> para poder utilizada pelo aplicativo.
        */
    private List<Contato> stringToList(String strContatos){
        List<Contato> contatos = new ArrayList<>();

        if(!strContatos.trim().isEmpty()){
            String[] arrStrContatos = strContatos.split(";");
            //Log.i(LOG_MSG, "Registros: " + arrStrContatos.length);

            for (String contato : arrStrContatos) {
                String[] arrContato = contato.split(",");
                contatos.add(new Contato(
                        arrContato[0],
                        arrContato[1],
                        arrContato[2],
                        arrContato[3]
                ));
            }
        }

        return contatos;
    }
}
