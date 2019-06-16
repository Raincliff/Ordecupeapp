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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.bean.DetalleBean;
import ordecupe.android.bean.PedidoBean;
import ordecupe.android.dao.PedidoDao;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;
import ordecupe.android.util.ListViewAdapterPed;

public class PedidosCliente extends Fragment{
    ListView lista;
    ListViewAdapterPed adapter;
    static ClienteBean objBean=null;
    int can;
    public PedidosCliente() {

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pedidos_list_cli, container, false);
        final Principal activity=(Principal)getActivity();
        String usu=activity.getUsu();
        objBean=activity.cargar(usu);
        lista = (ListView) rootView.findViewById(R.id.listPedCli);
        adapter = new ListViewAdapterPed(getActivity(), Nom(), Usu(), Est(),Sub());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {
                AlertDialog.Builder dialogo= new android.app.AlertDialog.Builder(getActivity());
                ArrayList<PedidoBean> lista = Listar();
                final PedidoBean objP = lista.get(i);
                ArrayList<DetalleBean> list=new ArrayList();
                DetalleBean obj=new DetalleBean();
                list=Detalles(objP.getCodPed()+"");
                String precios="PEDIDO REALIZADO EL "+objP.getFecPed()+" A LAS "+objP.getHorPed();
                precios+="\r\nENTREGA EN: "+objP.getDirPed();
                precios+="\r\n\r\nDETALLLES:";
                for (int j=0;j<list.size();j++){
                    obj=list.get(j);
                    precios+="\r\n"+obj.getCanDet() +" "+obj.getProDet() +" - S/. " + obj.getSubDet();
                }
                dialogo.setTitle("Detalles de Pedido:");
                dialogo.setMessage(precios);

                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                if(objP.getEstPed().equals("ENVIADO")){
                    dialogo.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {PedidoDao objPedDao=new PedidoDao();
                            int a=objPedDao.ModificarPedido(objP.getCodPed()+"","CANCELADO");
                            if(a==1){
                                Toast.makeText(getActivity(),"El Pedido fue Cancelado",Toast.LENGTH_SHORT).show();
                                Principal activity=(Principal)getActivity();
                                PedidosCliente fragment=new PedidosCliente();
                                FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.Base, fragment);
                                fragmentTransaction.commit();
                            }
                        }
                    });
                }
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

    public ArrayList<DetalleBean> Detalles(String ped){
        PedidoDao objEst=new PedidoDao();
        ArrayList<DetalleBean> listP=new ArrayList<DetalleBean>();
        listP=objEst.listarDetalle(ped);
        return listP;
    }


    public static ArrayList<PedidoBean> Listar() {
        PedidoDao objEspDao = new PedidoDao();
        ArrayList listado = new ArrayList<PedidoBean>();

        listado = objEspDao.listarPedCli(objBean.getDniCli() + "");

        return listado;
    }

    public static String[] Nom() {
        PedidoBean objEsp = new PedidoBean();
        ArrayList<PedidoBean> lista = Listar();
        String[] nom = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            nom[i] = "" + objEsp.getFecPed()+" "+objEsp.getHorPed();
        }
        return nom;
    }

    public static String[] Usu() {
        PedidoBean objEsp = new PedidoBean();
        ArrayList<PedidoBean> lista = Listar();
        String[] usu = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            usu[i] = ""+objEsp.getDisEnt();
        }
        return usu;
    }

    public static String[] Est() {
        PedidoBean objEsp = new PedidoBean();
        ArrayList<PedidoBean> lista = Listar();
        String[] est = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            est[i] = ""+objEsp.getEstPed();
        }
        return est;
    }

    public static String[] Sub() {
        PedidoBean objEsp = new PedidoBean();
        ArrayList<PedidoBean> lista = Listar();
        String[] est = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            est[i] = "Total: " + objEsp.getTotPed() + "";
        }
        return est;
    }

}