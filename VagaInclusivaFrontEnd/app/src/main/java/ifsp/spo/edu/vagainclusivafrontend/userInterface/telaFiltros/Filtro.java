package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros;

import java.util.List;

import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;

public class Filtro {

    private String user;
    private String nome;
    private List<Integer> acess;
    private Integer nota;

    public Filtro(String user, String nome, List<Integer> acess, Integer nota) {
        this.user = user;
        this.nome = nome;
        this.acess = acess;
        this.nota = nota;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Integer> getAcess() {
        return acess;
    }

    public void setAcess(List<Integer> acess) {
        this.acess = acess;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
