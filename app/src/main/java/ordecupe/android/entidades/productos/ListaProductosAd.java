package ordecupe.android.entidades.productos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ordecupe.android.bean.ProductoBean;
import ordecupe.android.dao.ProductoDao;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.PrincipalAdmin;
import ordecupe.android.util.ListViewAdapter;

public class ListaProductosAd extends Fragment implements View.OnClickListener{
    ListViewAdapter adapter;
    static String tip;
    View rootView;
    ListView lista;
    EditText cantidad;
    Button AGREGAR;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.productos_list_ad, container, false);
        final PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        tip=activity.getTip();
        lista = (ListView)rootView.findViewById(R.id.listPro);
        adapter = new ListViewAdapter(getActivity(), Nom(),Usu(),Est());
        AGREGAR=(Button)rootView.findViewById(R.id.BTNAGREGAR);
        AGREGAR.setOnClickListener(this);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {
                ArrayList<ProductoBean> lista=Listar();
                final ProductoBean obj= lista.get(i);

                AlertDialog.Builder dialogo= new AlertDialog.Builder(view.getContext());
                dialogo.setTitle("Detalles del Producto:");
                dialogo.setMessage("Producto: " + obj.getNomPro() + "\r\nMarca: " + obj.getMarPro() +
                        "\r\nPrecio: " + obj.getPrePro() +"\r\nStock Actual:"+obj.getStoPro()+"\r\nStock Mínimo:"+obj.getMinPro());


                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogo.setNegativeButton("MODIFICAR STOCK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ModificarStock(view,obj);
                    }
                });
                dialogo.create();
                dialogo.show();
                           }
        });
/*
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "presiono LARGO " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
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

    public void ModificarStock(View view, final ProductoBean obj){
        AlertDialog.Builder dialogo= new AlertDialog.Builder(view.getContext());
        dialogo.setTitle("Modificar Stock del Producto:");
        dialogo.setMessage("Producto: " + obj.getNomPro() + "\r\nMarca: " + obj.getMarPro() +
                "\r\nPrecio: " + obj.getPrePro() +"\r\nStock Actual:"+obj.getStoPro()+"\r\nStock Mínimo:"+obj.getMinPro()
                +"\r\n\r\nIngrese Nuevo Stock:");

        cantidad=new EditText(view.getContext());
        dialogo.setView(cantidad);

        dialogo.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String can=cantidad.getText().toString();
                if(can.equals("")){
                    cantidad.setError("Ingrese Nuevo Stock");
                }else{
                    int c=Integer.parseInt(can);
                    ProductoBean objProBean=new ProductoBean();
                    ProductoDao objProDao=new ProductoDao();
                    objProBean.setStoPro(c);
                    objProBean.setIdPro(obj.getIdPro());
                    int i=objProDao.ModificarStock(objProBean);

                    if(i==1){
                        Toast.makeText(getActivity(),"Stock Actualizado", Toast.LENGTH_SHORT).show();
                        ListaProductosAd fragment=new ListaProductosAd();
                        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
                        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Base_admin, fragment);
                        fragmentTransaction.commit();
                    }else{
                        Toast.makeText(getActivity(),"Error en el Proceso", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialogo.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogo.create();
        dialogo.show();
    }
    public void Agregar(){
        RegistroProducto fragment=new RegistroProducto();
        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_admin, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v==AGREGAR){
            Agregar();
        }
    }
}
