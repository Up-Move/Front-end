package ifsp.spo.edu.vagainclusiva;

public class User {

    private String username;
    private String email;
    private String password1;
    private String password2;

    public User(String nome, String email, String senha, String senha2) {
        this.username = nome;
        this.email = email;
        this.password1 = senha;
        this.password2 = senha2;
    }
}
