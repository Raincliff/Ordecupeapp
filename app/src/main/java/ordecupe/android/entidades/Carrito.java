package ordecupe.android.entidades;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ordecupe.android.principal.R;

public class Carrito extends Fragment{

    public Carrito(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.carrito, container, false);
        return rootView;
    }
}
