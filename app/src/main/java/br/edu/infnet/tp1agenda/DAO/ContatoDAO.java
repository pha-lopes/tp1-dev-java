package br.edu.infnet.tp1agenda.DAO;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tp1agenda.contato.Contato;

public class ContatoDAO {

    private FileManager fm = new FileManager();
    private static List<Contato> contatos = new ArrayList<>();

    public void salvar(Contato contato){
        contatos.add(contato);
        fm.salvar(contatos);
    }

    public List<Contato> listar(){
        return contatos;
    }

}
