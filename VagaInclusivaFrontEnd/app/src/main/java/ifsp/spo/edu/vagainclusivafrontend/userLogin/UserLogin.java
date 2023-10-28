package ifsp.spo.edu.vagainclusivafrontend.userLogin;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class UserLogin {
    private final String username;
    private final String password;

    public UserLogin(String email, String senha) {
        this.username = email;
        this.password = senha;
    }

    public String getemail() {
        return username;
    }
    public String getsenha() {
        return password;
    }
}
