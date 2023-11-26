package ifsp.spo.edu.vagainclusivafrontend.userLogin;

import java.util.List;

public class UserManager {
    private static UserManager instance;
    private UserLogin userLogin;
    private List<Integer> acess;
    private Integer nota = 0;

    private UserManager() {
        // Private constructor to prevent external instantiation.
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
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
