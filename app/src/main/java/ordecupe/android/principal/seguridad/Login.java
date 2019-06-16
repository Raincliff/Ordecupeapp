package ordecupe.android.principal.seguridad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ordecupe.android.bean.UsuarioBean;
import ordecupe.android.dao.UsuarioDao;
import ordecupe.android.principal.LoginPrincipal;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;

public class Login extends Fragment implements View.OnClickListener{

    Button BTNINI, BTNREG;
    EditText TXTUSU, TXTCLA;
    public Login(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login, container, false);

        TXTUSU=(EditText)rootView.findViewById(R.id.TXTUSU);
        TXTCLA=(EditText)rootView.findViewById(R.id.TXTCLA);
        BTNINI=(Button)rootView.findViewById(R.id.BTNINI);
        BTNINI.setOnClickListener(this);
        BTNREG=(Button)rootView.findViewById(R.id.BTN_REGISTRO);
        BTNREG.setOnClickListener(this);
        Principal activity=(Principal)getActivity();
        //USUARIO=activity.getMyDataObj().getUsu();

        return rootView;
    }

    public void Iniciar(){

        String usu=TXTUSU.getText().toString();
        String cla=TXTCLA.getText().toString();
        String parametros[]=new String[2];
        if(usu.equals("")){
            TXTUSU.setError("Por favor ingrese usuario");
            TXTUSU.requestFocus();
        }else {
            if (cla.length() == 0) {
                TXTCLA.setError("Ingrese Clave");
                TXTCLA.requestFocus();
            } else {
                //Iniciamos
                parametros[0] = usu;
                parametros[1] = cla;

                new asyncIniciarSession().execute(parametros);

                LimpiarCampos();

            }
        }
    }

    public void LimpiarCampos(){
        TXTUSU.setText("");
        TXTCLA.setText("");
        TXTUSU.requestFocus();
    }


    class asyncIniciarSession extends AsyncTask<String,Void,Void> {
        int tipo=0;
        String usuario;
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Iniciando Sesión", "Espere unos segundos...", true);

        }

        @Override
        protected Void doInBackground(String... parametros) {
            try {
                UsuarioBean objUsuBean=new UsuarioBean();
                objUsuBean.setUsuario(parametros[0]);
                objUsuBean.setClave(parametros[1]);
                UsuarioDao objUsuDao=new UsuarioDao();
                tipo=objUsuDao.ValidarUsuario(objUsuBean);
                usuario=parametros[0];

            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {

            switch (tipo){
                case 2:{
                    Intent objIntent = new Intent(getActivity(), Principal.class);
                    Bundle objBundle = new Bundle();
                    objBundle.putString("usuario", usuario);
                    objIntent.putExtras(objBundle);
                    getActivity().startActivity(objIntent);
                    /*PrincipalCliente fragment=new PrincipalCliente();
                    Principal activity=(Principal)getActivity();
                    activity.setUsu(usuario);
                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Base, fragment);
                    fragmentTransaction.commit();*/
                }break;
                case 1:{
                    Intent objIntent = new Intent(getActivity(), PrincipalAdmin.class);
                    Bundle objBundle = new Bundle();
                    objBundle.putString("usuario", usuario);
                    objIntent.putExtras(objBundle);
                    getActivity().startActivity(objIntent);

                }break;
                case 3:{
                    Intent objIntent = new Intent(getActivity(), PrincipalRep.class);
                    Bundle objBundle = new Bundle();
                    objBundle.putString("usuario", usuario);
                    objIntent.putExtras(objBundle);
                    getActivity().startActivity(objIntent);

                }break;
                default:{
                    Toast.makeText(getActivity(), "Usuario y Clave Incorrecto, por favor Ingrese Nuevamente", Toast.LENGTH_LONG).show();

                }
            }
            progressDialog.dismiss();
        }
    }
    public void Registro(){
        ordecupe.android.principal.seguridad.Registro fragment=new Registro();
        LoginPrincipal activity=(LoginPrincipal)getActivity();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.baselogin, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        if(v==BTNINI){
            Iniciar();
        }else if(v==BTNREG){
            Registro();
        }
    }
}
