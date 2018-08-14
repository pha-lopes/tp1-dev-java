package br.edu.infnet.tp1agenda.contato;

import java.io.Serializable;

/***
 * Classe modelo de Contato para a aplicação Agenda.
 *
 * @author Pedro Lopes
 * @version 1.0
 *
 */
public class Contato implements Serializable {

    private String nome;
    private String telefone;
    private String email;
    private String cidade;

    public Contato(String nome, String telefone, String email, String cidade){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
    }

    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone){ this.telefone = telefone; }
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }
    public String getCidade(){ return cidade; }
    public void setCidade(String cidade){ this.cidade = cidade; }

}
