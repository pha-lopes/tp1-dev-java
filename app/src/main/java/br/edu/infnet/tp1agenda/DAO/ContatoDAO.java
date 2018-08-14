package br.edu.infnet.tp1agenda.DAO;

import java.util.List;

import br.edu.infnet.tp1agenda.contato.Contato;

public class ContatoDAO {

    private FileManager fm = new FileManager();

    public boolean salvar(Contato contato){
        return fm.salvar(contato);
    }

    public List<Contato> listar(){
        return fm.listar();
        //return contatos;
    }

}
