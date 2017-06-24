package customadapter.rudneioliveira.customadapeter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import customadapter.rudneioliveira.customadapeter.objetos.ItemListView;

/**
 * Created by rudnei.oliveira on 12/05/2016.
 */
public class AdapterListViewCustom extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ItemListView> itens;

    public AdapterListViewCustom(Context context, ArrayList<ItemListView> itens){
        //itens que preenche o ListView
        this.itens = itens;

        //responsavel por pegar o layout do item;
        mInflater = LayoutInflater.from(context);
    }

    //retorna a quantidade de itens
    @Override
    public int getCount() {
        return itens.size();
    }

    //retorna o item de acordo com a posicao dele na tela
    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;

        //se a classe estiver nulla , inflamos com o layout da tela
        if (view == null){
            //infla o layout para podermos pegar as views
            view = mInflater.inflate(R.layout.item_list, null);

            //cria um item de suporte para nao precisarmos sempre inflar  as mesmas informaçoes
            itemHolder = new ItemSuporte();
            itemHolder.txtTitle = (TextView) view.findViewById(R.id.text);

            view.setTag(itemHolder);
        }else{
            //se a view ja existe pega os itens
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista e define os valores nos itens
        ItemListView item = itens.get(position);

        itemHolder.txtTitle.setText(item.getTexto());

        return view;
    }

    private class ItemSuporte{
        TextView txtTitle;
    }
}
