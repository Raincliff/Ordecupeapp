package ordecupe.android.principal;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.dao.ClienteDao;
import ordecupe.android.entidades.Carrito;
import ordecupe.android.entidades.pedidos.CarritoPedidos;
import ordecupe.android.principal.seguridad.PrincipalCliente;

public class Principal extends AppCompatActivity {
    private CharSequence mDrawerTitle="ORDECUPE";
    private CharSequence mTitle="";

    Home fragment=new Home();
    private String usu="";
    private String tip;
    private int ped;
    private double sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        usu=getIntent().getExtras().getString("usuario");

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base, fragment);
        fragmentTransaction.commit();
        BottomNavigationView bottomNavigationView =(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home: {/*
                        if(id==R.id.action_cerrar){
                            Intent objIntent = new Intent(PrincipalAdmin.this, Principal.class);
                            startActivity(objIntent);
                        }*/
                        Home fragment=new Home();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Base, fragment);
                        fragmentTransaction.commit();
                        mDrawerTitle="ORDECUPE";
                        mTitle="";
                    }break;
                    case R.id.menu_shop: {
                        Carrito();
                        mDrawerTitle="ORDECUPE";
                        mTitle="Realice Compra";
                    }break;
                    case R.id.menu_account: {
                        try {

                            if(getUsu().equals("")){
                                Cuenta fragment=new Cuenta();
                                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.Base, fragment);
                                fragmentTransaction.commit();
                            }else{
                                PrincipalCliente fragment=new PrincipalCliente();
                                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.Base, fragment);
                                fragmentTransaction.commit();
                            }
                        }catch (Exception e){

                        }
                        mDrawerTitle="Cuenta";
                        mTitle="Datos de Usuario";
                    }break;
                }
                getSupportActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().setSubtitle(mTitle);
                return true;
            }
        });
        getSupportActionBar().setTitle(mDrawerTitle);
        getSupportActionBar().setSubtitle(mTitle);
    }



    public ClienteBean cargar(String usu){
        ClienteBean obj=new ClienteBean();
        ClienteDao objD=new ClienteDao();
        obj=objD.CargarDatos(usu);
        return obj;
    }

    public void Carrito(){
        try {

            if(getPed()>0){
                CarritoPedidos fragment=new CarritoPedidos();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Base, fragment);
                fragmentTransaction.commit();
            }else{
                Carrito fragment=new Carrito();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Base, fragment);
                fragmentTransaction.commit();
            }
        }catch (Exception e){

        }
    }
    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    public int getPed() {
        return ped;
    }

    public void setPed(int ped) {
        this.ped = ped;
    }

    public double getSub() {
        return sub;
    }

    public void setSub(double sub) {
        this.sub = sub;
    }
}
