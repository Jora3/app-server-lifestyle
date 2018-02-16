package services;

import com.api.ILogin;
import com.lifestile.system.accessdb.client.gestion.LoginDAO;
import services.mapping.JsonResultats;

public class LoginSE extends JsonResultats {
    private final ILogin login = new LoginDAO();

    public String login(String email, String password) {
        return jsonFormat(false, "login", email, password);
    }

    @Override
    protected Object object() {
        return login;
    }
}
