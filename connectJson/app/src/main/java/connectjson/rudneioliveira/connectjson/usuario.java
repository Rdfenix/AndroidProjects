package connectjson.rudneioliveira.connectjson;

import com.google.gson.annotations.SerializedName;

public class usuario {

    @SerializedName("id")
    public int ID;

    @SerializedName("nome")
    public String Name;

    @SerializedName("login")
    public String Login;

    @SerializedName("senha")
    public String Password;

}
