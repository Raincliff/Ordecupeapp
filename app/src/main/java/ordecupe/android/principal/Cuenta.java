package ordecupe.android.principal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ordecupe.android.principal.seguridad.Login;
import ordecupe.android.principal.seguridad.Registro;

public class Cuenta  extends Fragment implements View.OnClickListener{

        Button BTNINGRESAR, BTNREGISTRO;
    public Cuenta(){

        }
        View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.cuenta, container, false);

            Principal activity=(Principal)getActivity();
            //USUARIO=activity.getMyDataObj().getUsu();

            BTNINGRESAR=(Button)rootView.findViewById(R.id.BTNINICIAR);
            BTNINGRESAR.setOnClickListener(this);
            BTNREGISTRO=(Button)rootView.findViewById(R.id.BTNREGISTRO);
            BTNREGISTRO.setOnClickListener(this);

            return rootView;
        }

        public void Inicio(){
            Login fragment=new Login();
            Principal activity=(Principal)getActivity();
            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Base, fragment);
            fragmentTransaction.commit();
        }
        public void Registro(){
            ordecupe.android.principal.seguridad.Registro fragment=new Registro();
            Principal activity=(Principal)getActivity();
            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Base, fragment);
            fragmentTransaction.commit();
        }
        @Override
        public void onClick(View v) {
            if(v==BTNINGRESAR){
                Inicio();
            }else{
                if(v==BTNREGISTRO){
                    Registro();
                }
            }
        }
}
