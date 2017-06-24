package rudneioliveira.retrofitconnection.Users;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rudnei.oliveira on 23/06/2016.
 */
public class User {
    @SerializedName("id")
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @SerializedName("nome")
    private String Name;

    @SerializedName("login")
    private String Login;

    @SerializedName("senha")
    private String Password;


}
