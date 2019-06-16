package ordecupe.android.principal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ordecupe.android.entidades.productos.ListaProductosRep;
import ordecupe.android.principal.seguridad.PrincipalRep;


public class HomeRep extends Fragment implements View.OnClickListener{
    ImageButton BTNCER,BTNRON,BTNWHI,BTNVOD,BTNVIN,BTNPIS,BTNHIE,BTNCIG,BTNREF;

    public HomeRep(){

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
    public void Listar(String t){
        ListaProductosRep fragment=new ListaProductosRep();
        PrincipalRep activity=(PrincipalRep) getActivity();
        activity.setTip(t);
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_rep, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        if(v==BTNCER){
            Listar("3");
        }else{
            if(v==BTNPIS){
                Listar("4");
            }else{
                    if(v==BTNWHI){
                        Listar("2");
                    }else{
                                        }
                                    }
                                }
                            }

                    }
