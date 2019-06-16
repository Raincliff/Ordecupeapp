package ordecupe.android.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ordecupe.android.principal.R;

public class ListViewAdapterPed extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] nombre;
    String[] marca;
    String[] precio;
    String[] sub;
    LayoutInflater inflater;

    public ListViewAdapterPed(Context context, String[] nombre, String[] marca, String[] precio, String[] sub) {
        this.context = context;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.sub = sub;
    }

    @Override
    public int getCount() {
        return nombre.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView NOMBRE;
        TextView USUARIO;
        TextView ESTADO;
        TextView SUB;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row_ped, parent, false);

        // Locate the TextViews in listview_item.xml
        NOMBRE = (TextView) itemView.findViewById(R.id.UsuNom);
        USUARIO = (TextView) itemView.findViewById(R.id.Usu);
        ESTADO = (TextView) itemView.findViewById(R.id.uni);
        SUB = (TextView) itemView.findViewById(R.id.sub);

        // Capture position and set to the TextViews
        NOMBRE.setText(nombre[position]);
        USUARIO.setText(marca[position]);
        ESTADO.setText(precio[position]+"");
        SUB.setText(sub[position]+"");

        return itemView;
    }
}
