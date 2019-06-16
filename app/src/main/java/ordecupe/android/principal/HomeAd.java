package ordecupe.android.principal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ordecupe.android.entidades.productos.ListaProductosAd;
import ordecupe.android.principal.seguridad.PrincipalAdmin;


public class HomeAd extends Fragment implements View.OnClickListener{
    ImageButton BTNCER,BTNRON,BTNWHI,BTNVOD,BTNVIN,BTNPIS,BTNHIE,BTNCIG,BTNREF;

    public HomeAd(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container, false);
        BTNCER=(ImageButton)rootView.findViewById(R.id.BTNCER);
        BTNWHI=(ImageButton)rootView.findViewById(R.id.BTNWHI);
        BTNPIS=(ImageButton)rootView.findViewById(R.id.BTNPIS);
        BTNCER.setOnClickListener(this);
        BTNWHI.setOnClickListener(this);
        BTNPIS.setOnClickListener(this);
        //USUARIO=activity.getMyDataObj().getUsu();

        return rootView;
    }
    public void Listar(String t,String nom){
        ListaProductosAd fragment=new ListaProductosAd();
        PrincipalAdmin activity=(PrincipalAdmin) getActivity();
        activity.setTip(t);
        activity.setNomTip(nom);
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_admin, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        if(v==BTNCER){
            Listar("3", "CERVEZA");
        }else{
            if(v==BTNPIS){
                Listar("4", "PISCO");
            }else{
                    if(v==BTNWHI){
                        Listar("2", "WHISKY");


                                        }
                                    }
                                }
                            }
                        }
