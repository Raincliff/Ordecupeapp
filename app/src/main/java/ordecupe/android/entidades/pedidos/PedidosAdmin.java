package ordecupe.android.entidades.pedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.bean.DetalleBean;
import ordecupe.android.bean.PedidoBean;
import ordecupe.android.dao.PedidoDao;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.PrincipalAdmin;
import ordecupe.android.util.ListViewAdapterPed;

public class PedidosAdmin extends Fragment implements View.OnClickListener{
    ListView lista;
    int est;
    Button ACE,CAN;
    ListViewAdapterPed adapter;
    static ClienteBean objBean=null;
    public PedidosAdmin() {

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pedidos_list_ad, container, false);
        final PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        lista = (ListView) rootView.findViewById(R.id.listPedAd);
        adapter = new ListViewAdapterPed(getActivity(), Nom(), Usu(), Est(),Sub());
        ACE=(Button)rootView.findViewById(R.id.BTNPEDACE);
        ACE.setOnClickListener(this);
        CAN=(Button)rootView.findViewById(R.id.BTNPEDCAN);
        CAN.setOnClickListener(this);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {

                ArrayList<PedidoBean> lista = Listar();
                final PedidoBean objP = lista.get(i);
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.getMenuInflater().inflate(R.menu.menu_env, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    //MENU FILA
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ped_det: {
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
                                dialogo.create();
                                dialogo.show();
                                break;
                            }
                            case R.id.ped_ace:{
                                AlertDialog.Builder dialogo= new AlertDialog.Builder(getActivity());
                                final String c=objP.getCodPed()+"";
                                dialogo.setTitle("Responder Pedido:");
                                dialogo.setMessage("¿Desea Aceptar Pedido?");

                                dialogo.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PedidoDao objD=new PedidoDao();
                                        PedidoBean objB=new PedidoBean();
                                        int a=objD.ModificarPedido(c,"ACEPTADO");
                                        if(a==1){
                                            Toast.makeText(getActivity(),"Pedido Aceptado",Toast.LENGTH_SHORT).show();
                                            PrincipalAdmin activity=(PrincipalAdmin)getActivity();
                                            PedidosAdmin fragment=new PedidosAdmin();
                                            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.Base_admin, fragment);
                                            fragmentTransaction.commit();
                                        }else{
                                            Toast.makeText(getActivity(),"Error en el Proceso",Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.cancel();
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
                                break;
                            }

                            case R.id.ped_rec:{
                                AlertDialog.Builder dialogo= new AlertDialog.Builder(getActivity());
                                final String c=objP.getCodPed()+"";
                                dialogo.setTitle("Responder Pedido:");
                                dialogo.setMessage("¿Desea Rechazar Pedido?");

                                dialogo.setPositiveButton("RECHAZAR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PedidoDao objD=new PedidoDao();
                                        PedidoBean objB=new PedidoBean();
                                        int a=objD.ModificarPedido(c,"RECHAZADO");
                                        if(a==1){
                                            Toast.makeText(getActivity(),"Pedido Rechazado",Toast.LENGTH_SHORT).show();
                                            PrincipalAdmin activity=(PrincipalAdmin)getActivity();
                                            PedidosAdmin fragment=new PedidosAdmin();
                                            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.Base_admin, fragment);
                                            fragmentTransaction.commit();
                                        }else{
                                            Toast.makeText(getActivity(),"Error en el Proceso",Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.cancel();
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
                                break;
                            }
                            case R.id.ped_cli:{
                                PrincipalAdmin activity=(PrincipalAdmin)getActivity();
                                activity.RealizarLlamada(objP.getEstPed());
                                Toast.makeText(getActivity(),"Llamar Cliente",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        return true;
                    }
                    //FIN MENU FILA
                });
                popup.show();
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

        listado = objEspDao.listarPedAd("vspedidos");

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

    public void Cancelados(){
        PrincipalAdmin activity=(PrincipalAdmin)getActivity();
        PedidosAdminCan fragment=new PedidosAdminCan();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_admin, fragment);
        fragmentTransaction.commit();

    }
    public void Aceptados(){
        PrincipalAdmin activity=(PrincipalAdmin)getActivity();
        PedidosAdminAce fragment=new PedidosAdminAce();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_admin, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        if(v==CAN){
            Cancelados();
        }else {
            if(v==ACE){
                Aceptados();
            }
        }
    }
}