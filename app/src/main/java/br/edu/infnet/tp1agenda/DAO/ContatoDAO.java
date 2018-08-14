package br.edu.infnet.tp1agenda.DAO;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tp1agenda.contato.Contato;

public class ContatoDAO {

    private static List<Contato> contatos = new ArrayList<>();

    public void salvar(Contato contato){
        contatos.add(contato);
    }

    public List<Contato> listar(){
        return contatos;
    }

}
