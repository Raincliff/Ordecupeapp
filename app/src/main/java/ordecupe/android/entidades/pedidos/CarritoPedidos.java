package ordecupe.android.entidades.pedidos;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ordecupe.android.bean.DetalleBean;
import ordecupe.android.dao.PedidoDao;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.Login;
import ordecupe.android.util.ListViewAdapterPed;

public class CarritoPedidos extends Fragment implements View.OnClickListener{
    ListView lista;
    ListViewAdapterPed adapter;
    static int ped;
    TextView sub;
    int can;
    Button BTNENVIAR;
    public CarritoPedidos() {

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.carrito_list, container, false);
        final Principal activity=(Principal)getActivity();
        ped=activity.getPed();
        sub=(TextView)rootView.findViewById(R.id.LBLSUB);
        BTNENVIAR=(Button) rootView.findViewById(R.id.BTNENVPED);
        BTNENVIAR.setOnClickListener(this);
        Subtotal();
        lista = (ListView) rootView.findViewById(R.id.listPro);
        adapter = new ListViewAdapterPed(getActivity(), Nom(), Usu(), Est(),Sub());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {
                ArrayList<DetalleBean> lista = Listar();
                final DetalleBean obj = lista.get(i);
                Cantidad();
                final int c=can;
                AlertDialog.Builder dialogo= new android.app.AlertDialog.Builder(view.getContext());
                dialogo.setTitle("Detalle de Pedido:");
                dialogo.setMessage("Producto: " + obj.getProDet() + "\r\nCantidad: " + obj.getCanDet() +
                        "\r\nPrecio por unidad: " +(obj.getSubDet()/obj.getCanDet())+ "\r\nSubtotal: " + obj.getSubDet());


                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogo.setNegativeButton("Eliminar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Principal activity=(Principal)getActivity();
                        PedidoDao objDao=new PedidoDao();
                        int i=objDao.EliminarDet(obj.getCodDet());
                        if(i==1){
                            int a=c-1;
                            if(a==0){
                                //Eliminamos el pedido
                                int b=activity.getPed();
                                objDao.EliminarPed(b);
                                activity.setPed(0);
                            }
                            activity.Carrito();
                        }else{
                            Toast.makeText(getActivity(), "No se puso eliminar Pedido",Toast.LENGTH_SHORT).show();
                        }
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


    public static ArrayList<DetalleBean> Listar() {
        PedidoDao objEspDao = new PedidoDao();
        ArrayList listado = new ArrayList<DetalleBean>();

        listado = objEspDao.listarDetalle(ped + "");

        return listado;
    }

    public static String[] Nom() {
        DetalleBean objEsp = new DetalleBean();
        ArrayList<DetalleBean> lista = Listar();
        String[] nom = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            nom[i] = "" + objEsp.getProDet();
        }
        return nom;
    }

    public static String[] Usu() {
        DetalleBean objEsp = new DetalleBean();
        ArrayList<DetalleBean> lista = Listar();
        String[] usu = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            usu[i] = "Cantidad: "+objEsp.getCanDet();
        }
        return usu;
    }

    public static String[] Est() {
        DetalleBean objEsp = new DetalleBean();
        ArrayList<DetalleBean> lista = Listar();
        String[] est = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            est[i] = "Precio por unidad: "+(objEsp.getSubDet()/objEsp.getCanDet())+"";
        }
        return est;
    }

    public static String[] Sub() {
        DetalleBean objEsp = new DetalleBean();
        ArrayList<DetalleBean> lista = Listar();
        String[] est = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            est[i] = "Subtotal: "+objEsp.getSubDet()+"";
        }
        return est;
    }
    public void Subtotal(){
        DetalleBean objEsp = new DetalleBean();
        ArrayList<DetalleBean> lista = Listar();
        Double est=0.0;
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            est = objEsp.getSubDet()+est;
    }
        sub.setText(est+"");
    }
    public void Cantidad(){
        DetalleBean objEsp = new DetalleBean();
        ArrayList<DetalleBean> lista = Listar();

        can=lista.size();
    }

    public void EnviarPedido(){
        try {
            Principal activity=(Principal)getActivity();
            if(activity.getUsu().equals("")){
                AlertDialog.Builder dialogo= new android.app.AlertDialog.Builder(getActivity());
                dialogo.setTitle("Inicio de sesión Requerido");
                dialogo.setMessage("Usted debe iniciar Sesión para enviar el Pedido ");


                dialogo.setPositiveButton("Inicar Seción", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Principal act=(Principal)getActivity();
                        Login fragment=new Login();
                        FragmentTransaction fragmentTransaction=act.getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Base, fragment);
                        fragmentTransaction.commit();
                    }
                });
                dialogo.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogo.create();
                dialogo.show();
            }else{
                Principal act=(Principal)getActivity();
                EnvioPedido fragment=new EnvioPedido();
                act.setSub(Double.parseDouble(sub.getText().toString()));
                FragmentTransaction fragmentTransaction=act.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Base, fragment);
                fragmentTransaction.commit();
            }
        }catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        if(v==BTNENVIAR){
            EnviarPedido();
        }
    }
}