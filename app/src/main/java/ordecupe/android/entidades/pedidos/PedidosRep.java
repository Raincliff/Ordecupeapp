package ordecupe.android.entidades.pedidos;

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

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.bean.DetalleBean;
import ordecupe.android.bean.PedidoBean;
import ordecupe.android.dao.PedidoDao;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.PrincipalRep;
import ordecupe.android.util.ListViewAdapterPed;

public class PedidosRep extends Fragment{
    ListView lista;
    int est;
    ListViewAdapterPed adapter;

    static ClienteBean objBean=null;
    public PedidosRep() {

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pedidos_list_rep, container, false);
        final PrincipalRep activity=(PrincipalRep) getActivity();
        lista = (ListView) rootView.findViewById(R.id.listPedRep);
        adapter = new ListViewAdapterPed(getActivity(), Nom(), Usu(), Est(),Sub());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {

                ArrayList<PedidoBean> lista = Listar();
                final PedidoBean objP = lista.get(i);
                                AlertDialog.Builder dialogo= new AlertDialog.Builder(getActivity());
                                ArrayList<DetalleBean> list=new ArrayList();
                                DetalleBean obj=new DetalleBean();
                                list=Detalles(objP.getCodPed()+"");
                                String precios="PEDIDO REALIZADO EL "+objP.getFecPed()+" A LAS "+objP.getHorPed();
                                precios+="\r\nCLIENTE: "+objP.getCliPed();
                                precios+="\r\nCONTACTO: "+objP.getEstPed();
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
                                dialogo.setNegativeButton("CONTACTAR CLIENTE",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PrincipalRep activity=(PrincipalRep)getActivity();
                                        activity.RealizarLlamada(objP.getEstPed());
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

    public ArrayList<DetalleBean> Detalles(String ped){
        PedidoDao objEst=new PedidoDao();
        ArrayList<DetalleBean> listP=new ArrayList<DetalleBean>();
        listP=objEst.listarDetalle(ped);
        return listP;
    }


    public static ArrayList<PedidoBean> Listar() {
        PedidoDao objEspDao = new PedidoDao();
        ArrayList listado = new ArrayList<PedidoBean>();

        listado = objEspDao.listarPedAd("vspedidosacep");

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
            usu[i] = ""+objEsp.getCliPed();
        }
        return usu;
    }

    public static String[] Est() {
        PedidoBean objEsp = new PedidoBean();
        ArrayList<PedidoBean> lista = Listar();
        String[] est = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            objEsp = lista.get(i);
            est[i] = ""+objEsp.getDisEnt();
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