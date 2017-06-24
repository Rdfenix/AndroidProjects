package customadapter.rudneioliveira.customadapeter.objetos;

/**
 * Created by rudnei.oliveira on 12/05/2016.
 */
public class ItemListView {

    private String texto;
    private int id;

    public ItemListView(String s){
        this.texto = s;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTexto(){
        return texto;
    }


    @Override
    public String toString() {
        return getTexto();
    }
}
