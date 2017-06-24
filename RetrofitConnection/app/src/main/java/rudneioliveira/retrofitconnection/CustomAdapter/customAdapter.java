package rudneioliveira.retrofitconnection.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rudneioliveira.retrofitconnection.R;
import rudneioliveira.retrofitconnection.Users.User;

/**
 * Created by rudnei.oliveira on 23/06/2016.
 */
public class CustomAdapter extends BaseAdapter {

    private List<User> users;
    private LayoutInflater mInflater;
    private Context context;


    public CustomAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;

        mInflater = LayoutInflater.from(this.context);
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

        MyViewHolder myViewHolder;

        if(convertView == null){

            convertView = mInflater.inflate(R.layout.adapter_row, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);

        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        User user = users.get(position);

        myViewHolder.nome.setText(user.getName());
        myViewHolder.login.setText(user.getLogin());
        myViewHolder.senha.setText(user.getPassword());

        return convertView;
    }

    private class MyViewHolder{
        TextView nome, login, senha;

        public MyViewHolder(View item){
            nome     = (TextView) item.findViewById(R.id.nome);
            login    = (TextView) item.findViewById(R.id.login);
            senha    = (TextView) item.findViewById(R.id.senha);
        }
    }

}
