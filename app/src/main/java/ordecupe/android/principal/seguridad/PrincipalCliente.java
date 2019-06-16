package ordecupe.android.principal.seguridad;

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
import ordecupe.android.entidades.pedidos.PedidosCliente;
import ordecupe.android.entidades.ventas.VentasCliente;
import ordecupe.android.principal.Cuenta;
import ordecupe.android.principal.LoginPrincipal;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;

public class PrincipalCliente extends Fragment implements View.OnClickListener{
    public String usuario="";
    public ClienteBean objCli=null;

    Button CERRAR, PEDIDO, VENTA, ACTUALIZAR;
    TextView LBLNOM,LBLDNI,LBLDIR,LBLTEL;

    public PrincipalCliente(){

    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cuenta_cli, container, false);
        Principal activity=(Principal)getActivity();
        usuario=activity.getUsu();
        objCli=activity.cargar(usuario);
        LBLNOM=(TextView) rootView.findViewById(R.id.LBLDATOS);
        LBLDNI=(TextView) rootView.findViewById(R.id.LBLDNI);
        LBLDIR=(TextView) rootView.findViewById(R.id.LBLDIRECCION);
        LBLTEL=(TextView) rootView.findViewById(R.id.LBLTELEFONO);
        VENTA=(Button)rootView.findViewById(R.id.BTNVENTA);
        VENTA.setOnClickListener(this);
        PEDIDO=(Button)rootView.findViewById(R.id.BTNPEDIDO);
        PEDIDO.setOnClickListener(this);
        CERRAR=(Button)rootView.findViewById(R.id.BTNCERRAR);
        CERRAR.setOnClickListener(this);
        ACTUALIZAR=(Button)rootView.findViewById(R.id.BTNACTUALIZAR);
        ACTUALIZAR.setOnClickListener(this);
        reportar();
        return rootView;
    }
    public void reportar() {

        LBLDNI.setText(objCli.getDniCli());
        LBLNOM.setText(objCli.getNomCli() + " " + objCli.getApeCli());
        LBLDIR.setText(objCli.getDirCli()+"");
        LBLTEL.setText(objCli.getTelCli()+"");

    }

    public void CerrarSesion(){
        Intent objIntent = new Intent(getActivity(), LoginPrincipal.class);
        getActivity().startActivity(objIntent);
        /*Cuenta fragment=new Cuenta();
        Principal activity=(Principal)getActivity();
        activity.setUsu("");
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();*/
    }

    public void VerPedido(){
        Principal activity=(Principal)getActivity();
        PedidosCliente fragment=new PedidosCliente();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();
    }
    public void VerVenta(){
        Principal activity=(Principal)getActivity();
        VentasCliente fragment=new VentasCliente();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();
    }
    public void Actualizar(){
        Principal activity=(Principal)getActivity();
        ActualizarDatos fragment=new ActualizarDatos();
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        if(v==CERRAR){
            CerrarSesion();
        }else {
            if(v==PEDIDO){
                VerPedido();
            }else {
                if(v==VENTA){
                    VerVenta();
                }else {
                    if(v==ACTUALIZAR){
                        Actualizar();
                    }
                }
            }
        }
    }
}
