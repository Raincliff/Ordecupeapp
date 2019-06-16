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
import android.widget.Toast;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.dao.ClienteDao;
import ordecupe.android.principal.LoginPrincipal;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;

public class Registro extends Fragment implements View.OnClickListener{

    Button BTNINI, BTNVOLVER;
    EditText TXTUSU, TXTCLA, TXTDNI,TXTNOM,TXTAPE,TXTDIR,TXTTEL;
    public Registro(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.registro, container, false);

        TXTUSU=(EditText)rootView.findViewById(R.id.TXTUSU);
        TXTCLA=(EditText)rootView.findViewById(R.id.TXTCLA);
        TXTDNI=(EditText)rootView.findViewById(R.id.TXTDNI);
        TXTNOM=(EditText)rootView.findViewById(R.id.TXTNOM);
        TXTAPE=(EditText)rootView.findViewById(R.id.TXTAPE);
        TXTDIR=(EditText)rootView.findViewById(R.id.TXTDIR);
        TXTTEL=(EditText)rootView.findViewById(R.id.TXTCOL);
        BTNINI=(Button)rootView.findViewById(R.id.BTNCONT);
        BTNINI.setOnClickListener(this);
        BTNVOLVER=(Button)rootView.findViewById(R.id.BTN_VOLVER);
        BTNVOLVER.setOnClickListener(this);
        Principal activity=(Principal)getActivity();
        //USUARIO=activity.getMyDataObj().getUsu();

        return rootView;
    }

    public void Iniciar(){

        String usu=TXTUSU.getText().toString();
        String cla=TXTCLA.getText().toString();
        String dni=TXTDNI.getText().toString();
        String nom=TXTNOM.getText().toString();
        String ape=TXTAPE.getText().toString();
        String dir=TXTDIR.getText().toString();
        String tel=TXTTEL.getText().toString();
        String parametros[]=new String[7];
        if(usu.equals("")){
            TXTUSU.setError("Por favor ingrese usuario");
            TXTUSU.requestFocus();
        }else {
            if (cla.length() == 0) {
                TXTCLA.setError("Ingrese Clave");
                TXTCLA.requestFocus();
            } else {
                if (cla.length() == 0) {
                    TXTCLA.setError("Ingrese Clave");
                    TXTCLA.requestFocus();
                } else {
                    if (dni.length() == 0) {
                        TXTDNI.setError("Ingrese DNI");
                        TXTDNI.requestFocus();
                    } else {
                        if (nom.length() == 0) {
                            TXTNOM.setError("Ingrese Nombre");
                            TXTNOM.requestFocus();
                        } else {
                            if (ape.length() == 0) {
                                TXTAPE.setError("Ingrese Apellido");
                                TXTAPE.requestFocus();
                            } else {
                                if (dir.length() == 0) {
                                    TXTDIR.setError("Ingrese Direccion");
                                    TXTDIR.requestFocus();
                                }else {
                                    if (tel.length() == 0) {
                                        TXTTEL.setError("Ingrese Telefono");
                                        TXTTEL.requestFocus();
                                    }else {
                                        //Iniciamos
                                        parametros[0] = usu;
                                        parametros[1] = cla;
                                        parametros[2] = dni;
                                        parametros[3] = nom;
                                        parametros[4] = ape;
                                        parametros[5] = dir;
                                        parametros[6] = tel;

                                        new asyncRegistrar().execute(parametros);

                                        LimpiarCampos();

                                    }

                                }

                            }
                        }

                    }

                }

            }
        }
    }

    public void LimpiarCampos(){
        TXTUSU.setText("");
        TXTCLA.setText("");
        TXTNOM.setText("");
        TXTAPE.setText("");
        TXTDNI.setText("");
        TXTDIR.setText("");
        TXTUSU.requestFocus();
    }


    class asyncRegistrar extends AsyncTask<String,Void,Void> {
        int est=0;
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Registrando", "Espere unos segundos...", true);

        }

        @Override
        protected Void doInBackground(String... parametros) {
            try {
                ClienteBean objUsuBean=new ClienteBean();
                objUsuBean.setUsuario(parametros[0]);
                objUsuBean.setClave(parametros[1]);
                objUsuBean.setDniCli(parametros[2]);
                objUsuBean.setNomCli(parametros[3]);
                objUsuBean.setApeCli(parametros[4]);
                objUsuBean.setDirCli(parametros[5]);
                objUsuBean.setTelCli(parametros[6]);
                ClienteDao objUsuDao=new ClienteDao();
                est=objUsuDao.Registrar(objUsuBean);
            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {

            switch (est){
                case 1:{
                    Toast.makeText(getActivity(), "Registro realizado con exito", Toast.LENGTH_LONG).show();
                    Login fragment=new Login();
                    LoginPrincipal activity=(LoginPrincipal)getActivity();
                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.baselogin, fragment);
                    fragmentTransaction.commit();
                    /*Cuenta fragment=new Cuenta();
                    Principal activity=(Principal)getActivity();
                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Base, fragment);
                    fragmentTransaction.commit();*/
                }break;
                default:{
                    Toast.makeText(getActivity(), "Lo sentimos, no se pudo realizar registro", Toast.LENGTH_LONG).show();
                }
            }
            progressDialog.dismiss();
        }
    }
    @Override
    public void onClick(View v) {
        if(v==BTNINI){
            Iniciar();
        }else if(v == BTNVOLVER){
            Login fragment=new Login();
            LoginPrincipal activity=(LoginPrincipal)getActivity();
            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.baselogin, fragment);
            fragmentTransaction.commit();
        }
    }
}
