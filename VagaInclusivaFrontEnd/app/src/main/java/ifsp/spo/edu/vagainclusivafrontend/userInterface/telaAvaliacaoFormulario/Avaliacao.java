package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import java.util.List;

@SuppressWarnings({"CanBeFinal", "FieldCanBeLocal"})
public class Avaliacao {
    private String nome;
    private String descricao;
    private int estrela;
    public Avaliacao( String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
    public Avaliacao(int estrela, String nome, String descricao) {
        this.estrela = estrela;
        this.nome = nome;
        this.descricao = descricao;
    }
    public int getEstrela(){ return estrela;}

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}