package ifsp.spo.edu.vagainclusiva;

public class UserLogin {
    private String username;
    private String password;

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
