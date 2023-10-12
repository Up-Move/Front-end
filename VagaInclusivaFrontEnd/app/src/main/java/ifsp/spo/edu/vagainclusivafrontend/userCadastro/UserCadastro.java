package ifsp.spo.edu.vagainclusivafrontend.userCadastro;

@SuppressWarnings({"unused", "FieldCanBeLocal", "SpellCheckingInspection"})
public class UserCadastro {
    private final String username;
    private final String email;
    private final String password1;
    private final String password2;

    public UserCadastro(String nome, String email, String senha, String senha2) {
        this.username = nome;
        this.email = email;
        this.password1 = senha;
        this.password2 = senha2;
    }
}
