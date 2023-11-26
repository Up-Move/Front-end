package ifsp.spo.edu.vagainclusivafrontend.userLogin;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class UserLogin {
    private String username;
    private String password;

    public UserLogin(String email, String senha) {
        this.username = email;
        this.password = senha;
    }
    public String getUsername() {
        return username;
    }

    public String getemail() {

        return username;
    }
    public String getsenha() {

        return password;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setemail(String email) {
        this.username = email;
    }

    public void setsenha(String senha) {
        this.password = senha;
    }
}
