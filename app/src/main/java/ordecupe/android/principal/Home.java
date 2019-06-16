package ordecupe.android.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import ordecupe.android.entidades.Alertas;
import ordecupe.android.entidades.patrimonio.RegistroCiudadano1;
import ordecupe.android.entidades.productos.ListaProductos;


public class Home extends Fragment implements View.OnClickListener {
    ImageButton BTNCER, BTNRON, BTNWHI, BTNVOD, BTNVIN, BTNPIS, BTNHIE, BTNCIG, BTNREF;

    public Home() {

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container, false);
        BTNCER = (ImageButton) rootView.findViewById(R.id.BTNCER);
        BTNWHI = (ImageButton) rootView.findViewById(R.id.BTNWHI);
        BTNPIS = (ImageButton) rootView.findViewById(R.id.BTNPIS);
        BTNCER.setOnClickListener(this);
        BTNWHI.setOnClickListener(this);
        BTNPIS.setOnClickListener(this);
        //USUARIO=activity.getMyDataObj().getUsu();

        return rootView;
    }

    public void Listar(String t) {
        ListaProductos fragment = new ListaProductos();
        Principal activity = (Principal) getActivity();
        activity.setTip(t);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        if (v == BTNCER) {
            Intent objIntent = new Intent(getActivity(), RegistroCiudadano1.class);
            getActivity().startActivity(objIntent);
        } else if (v == BTNPIS) {
            Toast.makeText(getContext(),"Cargando, espere un momento",Toast.LENGTH_SHORT).show();
            Intent objIntent = new Intent(getActivity(), Alertas.class);
            getActivity().startActivity(objIntent);
        } else if (v == BTNWHI) {
            Listar("2");
        } else {

        }

    }
}
