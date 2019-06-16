package ordecupe.android.entidades.pedidos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.bean.EntregaBean;
import ordecupe.android.bean.PedidoBean;
import ordecupe.android.dao.EntregaDao;
import ordecupe.android.dao.PedidoDao;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;
import ordecupe.android.util.SpinnerItem;

public class EnvioPedido extends Fragment implements View.OnClickListener{

    TextView LBLSUB;
    EditText TXTDIR;
    Button BTNENVIAR,BTNPRECIO;
    Spinner SPTIP, SPDIS;
    String TIPO="0",dni,ped;
    ArrayList<String> listT;
    ArrayList<EntregaBean> listD;
    int ti;

    public EnvioPedido(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.envio_pedido, container, false);

        LBLSUB= (TextView) rootView.findViewById(R.id.LBLSUB);
        TXTDIR=(EditText)rootView.findViewById(R.id.TXTDIRE) ;
        SPTIP = (Spinner) rootView.findViewById(R.id.SPTIPO);
        SPDIS = (Spinner) rootView.findViewById(R.id.SPDIST);
        BTNPRECIO= (Button) rootView.findViewById(R.id.BTNPRECIOS);
        BTNPRECIO.setOnClickListener(this);
        BTNENVIAR= (Button) rootView.findViewById(R.id.BTNENVIARPED);
        BTNENVIAR.setOnClickListener(this);

        ListarTip();
        Tipo();

        SPTIP.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               View v, int position, long id) {
                        if (position > 0) {
                            String sal = "" + listT.get(position - 1);
                            ListarDis(sal);
                            Distrito();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        SPDIS.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               View v, int position, long id) {
                        if (position > 0) {
                            ti = listD.get(position - 1).getCodEnt();
                            TIPO=ti+"";
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        Reporte();
        return rootView;
    }

    public void Reporte(){
        Principal activity=(Principal)getActivity();
        ClienteBean obj=new ClienteBean();
        String usu=activity.getUsu();
        ped=activity.getPed()+"";
        obj=activity.cargar(usu);
        LBLSUB.setText(activity.getSub()+"");
        dni=obj.getDniCli();
        TXTDIR.setText(obj.getDirCli());
    }

   public void ListarTip(){
        EntregaDao objEst=new EntregaDao();
        listT=new ArrayList<String>();
        listT=objEst.listarTipoEnt();

    }
    public void ListarDis(String tip){
        EntregaDao objEst=new EntregaDao();
        listD=new ArrayList<EntregaBean>();
        listD=objEst.listarDistritos(tip);
    }

   public void Tipo(){
       String tipo="";
       //Creamos la lista
       LinkedList esta = new LinkedList();
//La poblamos con los ejemplos
       esta.add(new SpinnerItem(0, "Seleccione Tipo Entrega"));
       for(int i=0; i<listT.size();i++){
           tipo=listT.get(i);
           esta.add(new SpinnerItem(i, tipo));
       }
//Creamos el adaptador
       ArrayAdapter spinner_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, esta);
//Añadimos el layout para el menú y se lo damos al spinner
       spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       SPTIP.setAdapter(spinner_adapter);
    }

    public void Distrito(){
        EntregaBean objEntBean=new EntregaBean();
        //Creamos la lista
        LinkedList esta = new LinkedList();
//La poblamos con los ejemplos
        esta.add(new SpinnerItem(0, "Seleccione Distrito"));
        for(int i=0; i<listD.size();i++){
            objEntBean=listD.get(i);
            esta.add(new SpinnerItem(i, objEntBean.getDisEnt()));
        }
//Creamos el adaptador
        ArrayAdapter spinner_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, esta);
//Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPDIS.setAdapter(spinner_adapter);
    }

    public ArrayList<EntregaBean> Precios(){
        EntregaDao objEst=new EntregaDao();
        ArrayList<EntregaBean> listP=new ArrayList<EntregaBean>();
        listP=objEst.listarPrecios();
        return listP;
    }

    public void ListarPrecios(){
        AlertDialog.Builder dialogo= new android.app.AlertDialog.Builder(getActivity());
        ArrayList<EntregaBean> list=new ArrayList();
        EntregaBean obj=new EntregaBean();
        list=Precios();
        String precios="RECOJO EN TIENDA - GRATIS";
        precios+="\r\n\r\nDELIVERY:";
        for (int i=1;i<list.size();i++){
            obj=list.get(i);
            precios+="\r\n"+obj.getDisEnt() +" - S/. " + obj.getPreEnt();
        }
        dialogo.setTitle("Lista de Precios:");
        dialogo.setMessage(precios);

        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogo.create();
        dialogo.show();

    }

    public void EnviarPedido(){
        String dir=TXTDIR.getText().toString();
        String parametros[]=new String[4];
        if(TIPO=="0"){
            Toast.makeText(getActivity(),"Por favor seleccione tipo y Distrito de envío",Toast.LENGTH_SHORT).show();
        }else{
            if(dir.equals("")){
                TXTDIR.setError("Por favor ingrese dirección de envío");
            }else{
                if(TIPO.equals("1")){
                    dir="TIENDA";
                }
                //Iniciamos
                parametros[0] = dir;
                parametros[1] = TIPO;
                parametros[2] = dni;
                parametros[3] = ped;

                new EnvioPedido.asyncEnviarPedido().execute(parametros);


            }
        }

    }

    class asyncEnviarPedido extends AsyncTask<String,Void,Void> {
        int tipo=0;
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Enviando Pedido", "Espere unos segundos...", true);

        }

        @Override
        protected Void doInBackground(String... parametros) {
            try {
                PedidoBean objPed=new PedidoBean();
                objPed.setDirPed(parametros[0]);
                objPed.setTipEnt(parametros[1]);
                objPed.setCliPed(parametros[2]);
                objPed.setCodPed(Integer.parseInt(parametros[3]));
                PedidoDao objPedDao=new PedidoDao();
                tipo=objPedDao.EnviarPedido(objPed);

            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Principal activity=(Principal)getActivity();
            activity.setPed(0);
            Toast.makeText(getActivity(),"Pedido Registrado correctamente", Toast.LENGTH_SHORT).show();
            PedidosCliente fragment=new PedidosCliente();
            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Base, fragment);
            fragmentTransaction.commit();
            //switch (tipo){
            //    case 1:{
            //
            //}break;
//
            //    default:{
            //        Toast.makeText(getActivity(), "Lo sentimos el pedido no puso ser enviado", Toast.LENGTH_LONG).show();
//
            //    }
            //}
            progressDialog.dismiss();
        }
    }
    @Override
    public void onClick(View v) {
        if(v==BTNPRECIO){
            ListarPrecios();
        }else{
            if(v==BTNENVIAR){
                EnviarPedido();
            }
        }
    }
}
