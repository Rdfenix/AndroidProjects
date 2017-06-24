package connectjson.rudneioliveira.connectjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rudnei.oliveira on 02/06/2016.
 */
public class adapterCustom extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<usuario> users;


    public adapterCustom(Context context, ArrayList<usuario> itens){
        this.users = itens;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSuporte itemHolder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_item, null);

            itemHolder = new ItemSuporte();
            itemHolder.txtTitle = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ItemSuporte) convertView.getTag();
        }

        usuario user = users.get(position);
        return convertView;
    }

    private class ItemSuporte{
        TextView txtTitle;
    }
}
