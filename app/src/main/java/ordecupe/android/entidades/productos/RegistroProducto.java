package ordecupe.android.entidades.productos;

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

import ordecupe.android.bean.ProductoBean;
import ordecupe.android.dao.ProductoDao;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.PrincipalAdmin;

public class RegistroProducto extends Fragment implements View.OnClickListener{

    Button BTNINI,BTNVOLPRO;
    EditText TXTPRO, TXTMAR, TXTPRE, TXTMIN, TXTSTO;
    TextView PRO;
    public RegistroProducto(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.registro_producto, container, false);

        PRO =(TextView) rootView.findViewById(R.id.LBLTIPOPRO);
        TXTPRO =(EditText)rootView.findViewById(R.id.TXTPRO);
        TXTMAR =(EditText)rootView.findViewById(R.id.TXTMAR);
        TXTPRE =(EditText)rootView.findViewById(R.id.TXTPRE);
        TXTMIN =(EditText)rootView.findViewById(R.id.TXTMIN);
        TXTSTO =(EditText)rootView.findViewById(R.id.TXTSTO);
        BTNINI=(Button)rootView.findViewById(R.id.BTNNUEVOPRO);
        BTNINI.setOnClickListener(this);
        BTNVOLPRO=(Button)rootView.findViewById(R.id.BTNVOLPRO);
        BTNVOLPRO.setOnClickListener(this);
        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        String tipo=activity.getNomTip();
        PRO.setText("REGISTRAR "+tipo);

        return rootView;
    }

    public void Iniciar(){

        String usu= TXTPRO.getText().toString();
        String cla= TXTMAR.getText().toString();
        String dni= TXTPRE.getText().toString();
        String nom= TXTMIN.getText().toString();
        String ape= TXTSTO.getText().toString();
        String parametros[]=new String[6];
        if(usu.equals("")){
            TXTPRO.setError("Por favor ingrese producto");
            TXTPRO.requestFocus();
        }else {
            if (cla.length() == 0) {
                TXTMAR.setError("Ingrese Marca");
                TXTMAR.requestFocus();
            } else {
                if (dni.length() == 0) {
                    TXTPRE.setError("Ingrese Precio");
                    TXTPRE.requestFocus();
                } else {
                    if (nom.length() == 0) {
                        TXTMIN.setError("Ingrese Stock Minimo");
                        TXTMIN.requestFocus();
                    } else {
                        if (ape.length() == 0) {
                            TXTSTO.setError("Ingrese Stock Actual");
                            TXTSTO.requestFocus();
                        } else {
                            PrincipalAdmin activity=(PrincipalAdmin)getActivity();
                            //Iniciamos
                            parametros[0] = usu;
                            parametros[1] = cla;
                            parametros[2] = dni;
                            parametros[3] = nom;
                            parametros[4] = ape;
                            parametros[5] = activity.getTip();

                            new asyncRegistrar().execute(parametros);
                        }
                    }

                }

            }
        }
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
                ProductoBean objUsuBean=new ProductoBean();
                objUsuBean.setNomPro(parametros[0]);
                objUsuBean.setMarPro(parametros[1]);
                objUsuBean.setPrePro(Double.parseDouble(parametros[2]));
                objUsuBean.setMinPro(Integer.parseInt(parametros[3]));
                objUsuBean.setStoPro(Integer.parseInt(parametros[4]));
                objUsuBean.setTipPro(parametros[5]);
                ProductoDao objUsuDao=new ProductoDao();
                est=objUsuDao.RegistrarProducto(objUsuBean);
            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {

            switch (est){
                case 1:{
                    Toast.makeText(getActivity(), "Registro realizado con exito", Toast.LENGTH_SHORT).show();
                    ListaProductosAd fragment=new ListaProductosAd();
                    PrincipalAdmin activity=(PrincipalAdmin) getActivity();
                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Base_admin, fragment);
                    fragmentTransaction.commit();
                }break;
                 default:{
                    Toast.makeText(getActivity(), "Lo sentimos, no se pudo realizar registro", Toast.LENGTH_SHORT).show();
                }
            }
            progressDialog.dismiss();
        }
    }
    public void Volver(){
        ListaProductosAd fragment=new ListaProductosAd();
        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_admin, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        if(v==BTNINI){
            Iniciar();
        }else{
            if(v==BTNVOLPRO){
                Volver();
            }
        }
    }
}
