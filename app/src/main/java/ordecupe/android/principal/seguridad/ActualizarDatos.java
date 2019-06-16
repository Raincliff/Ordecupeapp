package ordecupe.android.principal.seguridad;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.dao.ClienteDao;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;

public class ActualizarDatos extends Fragment implements View.OnClickListener{
    public String usuario="";
    public ClienteBean objCli=null;

    Button VOLVER, ACT;
    TextView LBLNOM,LBLDNI;
    EditText LBLDIR,LBLTEL;

    public ActualizarDatos(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.actualizar_datos, container, false);
        Principal activity=(Principal)getActivity();
        usuario=activity.getUsu();
        objCli=activity.cargar(usuario);
        LBLNOM=(TextView) rootView.findViewById(R.id.LBLDATOS);
        LBLDNI=(TextView) rootView.findViewById(R.id.LBLDNI);
        LBLDIR=(EditText) rootView.findViewById(R.id.TXTDIRACT);
        LBLTEL=(EditText) rootView.findViewById(R.id.TXTTELACT);
        VOLVER=(Button)rootView.findViewById(R.id.BTNVOLVER);
        VOLVER.setOnClickListener(this);
        ACT=(Button)rootView.findViewById(R.id.BTNACT);
        ACT.setOnClickListener(this);
        reportar();
        return rootView;
    }
    public void reportar() {

        LBLDNI.setText(objCli.getDniCli());
        LBLNOM.setText(objCli.getNomCli() + " " + objCli.getApeCli());
        LBLDIR.setText(objCli.getDirCli()+"");
        LBLTEL.setText(objCli.getTelCli()+"");

    }

    public void Volver(){
        PrincipalCliente fragment=new PrincipalCliente();
        Principal activity=(Principal)getActivity();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();
    }

    public void Actualizar(){

        String usu=LBLDIR.getText().toString();
        String cla=LBLTEL.getText().toString();
        String dni=LBLDNI.getText().toString();
        String parametros[]=new String[3];
        if(usu.equals("")){
            LBLDIR.setError("Por favor ingrese dirección");
            LBLDIR.requestFocus();
        }else {
            if (cla.length() == 0) {
                LBLTEL.setError("Ingrese Telefono");
                LBLTEL.requestFocus();
            } else {
                //Iniciamos
                parametros[0] = usu;
                parametros[1] = cla;
                parametros[2] = dni;

                new asyncActualizar().execute(parametros);

            }
        }
    }


    class asyncActualizar extends AsyncTask<String,Void,Void> {
        int tipo=0;
        String usuario;
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Registrando Datos", "Espere unos segundos...", true);

        }

        @Override
        protected Void doInBackground(String... parametros) {
            try {
                ClienteBean objUsuBean=new ClienteBean();
                objUsuBean.setDirCli(parametros[0]);
                objUsuBean.setTelCli(parametros[1]);
                objUsuBean.setDniCli(parametros[2]);
                ClienteDao objUsuDao=new ClienteDao();
                tipo=objUsuDao.ActualizarDatos(objUsuBean);

            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {

            switch (tipo){
                case 1:{
                    PrincipalCliente fragment=new PrincipalCliente();
                    Principal activity=(Principal)getActivity();
                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Base, fragment);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Datos Registrados", Toast.LENGTH_SHORT).show();
                }break;
                default:{
                    Toast.makeText(getActivity(), "Lo sentimos, tuvimos un error en el proceso", Toast.LENGTH_SHORT).show();

                }
            }
            progressDialog.dismiss();
        }
    }
    @Override
    public void onClick(View v) {
        if(v==VOLVER){
            Volver();
        }else {
            if(v==ACT){
                Actualizar();
            }
        }
    }
}
