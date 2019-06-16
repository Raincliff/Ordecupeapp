package ordecupe.android.entidades.usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.dao.ClienteDao;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.PrincipalAdmin;
import ordecupe.android.util.ListViewAdapterCli;

public class ListaCliente extends Fragment {
    ListViewAdapterCli adapter;
    View rootView;
    ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_cli, container, false);
        final PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        lista = (ListView)rootView.findViewById(R.id.listCli);
        adapter = new ListViewAdapterCli(getActivity(), Nom(),Usu(),Est());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {
                ArrayList<ClienteBean> lista=Listar();
                final ClienteBean obj= lista.get(i);

                AlertDialog.Builder dialogo= new AlertDialog.Builder(view.getContext());
                dialogo.setTitle("Datos de Cliente:");
                dialogo.setMessage(obj.getApeCli()+" " + obj.getNomCli() + "\r\nDNI: " + obj.getDniCli() +
                        "\r\nDirección: " + obj.getDirCli() +"\r\nTelefono: "+obj.getTelCli());


                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogo.setNegativeButton("Contactar Cliente",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrincipalAdmin activity=(PrincipalAdmin)getActivity();
                        activity.RealizarLlamada(obj.getTelCli());
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


    public static ArrayList<ClienteBean> Listar(){
        ClienteDao objEspDao= new ClienteDao();
        ArrayList listado=new ArrayList<ClienteBean>();

        listado = objEspDao.ListaCli();

        return listado;
    }

    public static String[] Nom(){
        ClienteBean objEsp=new ClienteBean();
        ArrayList<ClienteBean> lista=Listar();
        String[] nom=new String[lista.size()];
        for(int i=0; i<lista.size();i++) {
            objEsp=lista.get(i);
            nom[i] =""+objEsp.getApeCli()+" "+objEsp.getNomCli();
        }
        return nom;
    }
    public static String[] Usu(){
        ClienteBean objEsp=new ClienteBean();
        ArrayList<ClienteBean> lista=Listar();
        String[] usu=new String[lista.size()];
        for(int i=0; i<lista.size();i++) {
            objEsp=lista.get(i);
            usu[i] ="DNI: "+objEsp.getDniCli();
        }
        return usu;
    }
    public static String[] Est(){
        ClienteBean objEsp=new ClienteBean();
        ArrayList<ClienteBean> lista=Listar();
        String[] tel=new String[lista.size()];
        for(int i=0; i<lista.size();i++) {
            objEsp=lista.get(i);
            tel[i] ="Telefono: "+objEsp.getTelCli();
        }
        return tel;
    }


/*
    class asyncRegistroPedido extends AsyncTask<String,Void,Void> {
        int tipo=0;
        int ped=0;
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Agregando Pedido", "Espere unos segundos...", true);

        }

        @Override
        protected Void doInBackground(String... parametros) {
            try {
                DetalleBean objBean=new DetalleBean();
                objBean.setProDet(parametros[0]);
                objBean.setCanDet(Integer.parseInt(parametros[1]));
                objBean.setSubDet(Double.parseDouble(parametros[2]));
                Principal activity=(Principal)getActivity();
                int i=activity.getPed();
                PedidoDao objDao=new PedidoDao();
                if(i>0){
                    objBean.setCodDet(i);
                    tipo=objDao.RegistrarDet(objBean);
                    ped=i;
                }else{
                    tipo=objDao.RegistrarPed(objBean);
                    ped=tipo;
                }
            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {

            if(tipo>0){
                Principal activity=(Principal)getActivity();
                activity.setPed(ped);
                Toast.makeText(getActivity(), "Producto registrado, ir al carrito para enviar pedido", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(), "El producto no pudo ser registrado", Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();
        }
    }*/
}
