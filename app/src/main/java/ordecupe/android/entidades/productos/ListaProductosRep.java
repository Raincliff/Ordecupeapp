package ordecupe.android.entidades.productos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ordecupe.android.bean.ProductoBean;
import ordecupe.android.dao.ProductoDao;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.PrincipalRep;
import ordecupe.android.util.ListViewAdapter;

public class ListaProductosRep extends Fragment {
    ListViewAdapter adapter;
    static String tip;
    View rootView;
    ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.productos_list, container, false);
        final PrincipalRep activity=(PrincipalRep) getActivity();
        tip=activity.getTip();
        lista = (ListView)rootView.findViewById(R.id.listPro);
        adapter = new ListViewAdapter(getActivity(), Nom(),Usu(),Est());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {
                ArrayList<ProductoBean> lista=Listar();
                final ProductoBean obj= lista.get(i);

                AlertDialog.Builder dialogo= new AlertDialog.Builder(view.getContext());
                dialogo.setTitle("DETALLES:");
                dialogo.setMessage("Producto: " + obj.getNomPro() + "\r\nMarca: " + obj.getMarPro() +
                        "\r\nPrecio: " + obj.getPrePro());

                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogo.create();
                dialogo.show();
                           }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "presiono LARGO " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return rootView;
    }


    public static ArrayList<ProductoBean> Listar(){
        ProductoDao objEspDao= new ProductoDao();
        ArrayList listado=new ArrayList<ProductoBean>();

        listado = objEspDao.listarProductos(tip+"");

        return listado;
    }

    public static String[] Nom(){
        ProductoBean objEsp=new ProductoBean();
        ArrayList<ProductoBean> lista=Listar();
        String[] nom=new String[lista.size()];
        for(int i=0; i<lista.size();i++) {
            objEsp=lista.get(i);
            nom[i] =""+objEsp.getNomPro();
        }
        return nom;
    }
    public static String[] Usu(){
        ProductoBean objEsp=new ProductoBean();
        ArrayList<ProductoBean> lista=Listar();
        String[] usu=new String[lista.size()];
        for(int i=0; i<lista.size();i++) {
            objEsp=lista.get(i);
            usu[i] =objEsp.getMarPro();
        }
        return usu;
    }
    public static Double[] Est(){
        ProductoBean objEsp=new ProductoBean();
        ArrayList<ProductoBean> lista=Listar();
        Double[] est=new Double[lista.size()];
        for(int i=0; i<lista.size();i++) {
            objEsp=lista.get(i);
            est[i] =objEsp.getPrePro();
        }
        return est;
    }

}
