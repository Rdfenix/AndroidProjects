package customadapter.rudneioliveira.customadapeter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import customadapter.rudneioliveira.customadapeter.objetos.ItemListView;

public class MainActivity extends Activity{

    private ListView listView;
    private AdapterListViewCustom adapterListViewCustom;
    private ArrayList<ItemListView> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pega a referencia do ListView
        listView = (ListView) findViewById(R.id.list);

        createListView();
    }

    private void createListView() {
        //Criamos nossa lista
        items = new ArrayList<>();

        ItemListView item1 = new ItemListView("Rudnei Carlos de Oliveira");
        ItemListView item2 = new ItemListView("Marcos Roberto de Oliveira");
        ItemListView item3 = new ItemListView("Ana Paula de Oliveira");
        ItemListView item4 = new ItemListView("Sueli Aparecida Barbosa");
        ItemListView item5 = new ItemListView("Roosevelt Celso de Oliveira");

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        //cria o adaptador
        adapterListViewCustom = new AdapterListViewCustom(this, items);

        //define o adapter
        listView.setAdapter(adapterListViewCustom);


        //cor quando a lista e selecionada para rolagem
//        listView.setCacheColorHint(Color.TRANSPARENT);
    }

}
