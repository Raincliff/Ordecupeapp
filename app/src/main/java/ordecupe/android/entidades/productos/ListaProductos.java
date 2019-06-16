package ordecupe.android.entidades.productos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ordecupe.android.bean.DetalleBean;
import ordecupe.android.bean.ProductoBean;
import ordecupe.android.dao.PedidoDao;
import ordecupe.android.dao.ProductoDao;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;
import ordecupe.android.util.ListViewAdapter;

public class ListaProductos extends Fragment {
    ListViewAdapter adapter;
    static String tip;
    View rootView;
    ListView lista;
    EditText cantidad;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.productos_list, container, false);
        final Principal activity=(Principal)getActivity();
        tip=activity.getTip();
        lista = (ListView)rootView.findViewById(R.id.listPro);
        adapter = new ListViewAdapter(getActivity(), Nom(),Usu(),Est());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, final View view, int i, long l) {
                ArrayList<ProductoBean> lista=Listar();
                final ProductoBean obj= lista.get(i);

                AlertDialog.Builder dialogo= new android.app.AlertDialog.Builder(view.getContext());
                dialogo.setTitle("Agregar al Pedido:");
                dialogo.setMessage("Producto: " + obj.getNomPro() + "\r\nMarca: " + obj.getMarPro() +
                        "\r\nPrecio: " + obj.getPrePro() +"\r\n.\r\nIngrese Cantidad:");

                cantidad=new EditText(view.getContext());
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                dialogo.setView(cantidad);

                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String can=cantidad.getText().toString();
                        String parametros[]=new String[4];
                        if(can.equals("")){
                            Toast.makeText(getActivity(),"Debe ingresar cantidad",Toast.LENGTH_SHORT).show();
                        }else if(can.length()>4){
                            Toast.makeText(getActivity(),"Cantidad ingresada no válida",Toast.LENGTH_SHORT).show();
                        }else if(Integer.parseInt(can)> obj.getStoPro()){
                            Toast.makeText(getActivity(),"No hay stock suficiente, stock: "+obj.getStoPro(),Toast.LENGTH_SHORT).show();
                        }else{
                            int c=Integer.parseInt(can);
                            parametros[0] = obj.getIdPro()+"";
                            parametros[1] = can;
                            parametros[2] = (c*obj.getPrePro())+"";

                            new asyncRegistroPedido().execute(parametros);

                        }
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
    }
}
