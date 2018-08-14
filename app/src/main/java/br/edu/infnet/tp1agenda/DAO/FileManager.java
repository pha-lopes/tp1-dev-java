package br.edu.infnet.tp1agenda.DAO;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tp1agenda.contato.Contato;

public class FileManager {

    private final String LOG_MSG = "FILE_MANAGER";

    public void salvar(List<Contato> contatos){
        String strContatos = listToString(contatos);
        Log.i(LOG_MSG, strContatos);
        listar(strContatos);
    }

    public List<Contato> listar(String strContatos){

        List<Contato> lstContato = stringToList(strContatos);
        for(Contato contato : lstContato){
            Log.i(LOG_MSG, contato.getNome());
        }
        return lstContato;
    }


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


    private List<Contato> stringToList(String strContatos){
        List<Contato> contatos = new ArrayList<>();

        String[] arrStrContatos = strContatos.split(";");
        Log.i(LOG_MSG, "Registros: " + arrStrContatos.length);

        for(String contato : arrStrContatos){
            String[] arrContato = contato.split(",");
            contatos.add(new Contato(
                    arrContato[0],
                    arrContato[1],
                    arrContato[2],
                    arrContato[3]
            ));
        }

        return contatos;
    }
}
