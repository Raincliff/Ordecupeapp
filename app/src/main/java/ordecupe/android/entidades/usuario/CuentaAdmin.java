package ordecupe.android.entidades.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.dao.ClienteDao;
import ordecupe.android.principal.LoginPrincipal;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;
import ordecupe.android.principal.seguridad.ActualizarDatosAd;
import ordecupe.android.principal.seguridad.PrincipalAdmin;

public class CuentaAdmin extends Fragment implements View.OnClickListener{
    Button CERRAR, ACT;
    public String usuario="";
    public ClienteBean objCli=null;

    TextView LBLNOM,LBLDNI,LBLDIR, LBLTEL;

    public CuentaAdmin(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cuenta_admin, container, false);

        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        usuario=activity.getMyData();
        LBLNOM=(TextView) rootView.findViewById(R.id.LBLDATOSAD);
        LBLDNI=(TextView) rootView.findViewById(R.id.LBLDNIAD);
        LBLDIR=(TextView) rootView.findViewById(R.id.LBLDIRECCIONAD);
        LBLTEL=(TextView) rootView.findViewById(R.id.LBLTELEFONOAD);
        CERRAR=(Button)rootView.findViewById(R.id.BTNCERRARAD);
        CERRAR.setOnClickListener(this);
        ACT=(Button)rootView.findViewById(R.id.BTNACTUALIZARAD);
        ACT.setOnClickListener(this);

        reportar();
        return rootView;
    }

    public void CargarDatos(){

        objCli=new ClienteBean();
        ClienteDao objCliDao=new ClienteDao();
        objCli=objCliDao.CargarDatos(usuario);

    }
    public void reportar() {
        if(objCli==null){
            CargarDatos();
        }
        LBLDNI.setText(objCli.getDniCli());
        LBLNOM.setText(objCli.getNomCli() + " " + objCli.getApeCli());
        LBLDIR.setText(objCli.getDirCli());
        LBLTEL.setText(objCli.getTelCli());

    }
    public void CerrarSesion(){
        Intent objIntent = new Intent(getActivity(), LoginPrincipal.class);
        getActivity().startActivity(objIntent);

    }
    public void Actualizar(){
        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        ActualizarDatosAd fragment=new ActualizarDatosAd();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_admin, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v==CERRAR){
            CerrarSesion();
        }else{
            if(v==ACT){
                Actualizar();
            }
        }
    }
}
