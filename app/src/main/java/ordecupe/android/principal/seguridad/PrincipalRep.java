package ordecupe.android.principal.seguridad;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import ordecupe.android.entidades.pedidos.PedidosRep;
import ordecupe.android.entidades.usuario.CuentaRep;
import ordecupe.android.principal.HomeRep;
import ordecupe.android.principal.R;

public class PrincipalRep extends AppCompatActivity {
    private static final int SOLICITUD_PERMISO_WRITE_CALL_LOG = 0;
    public String usuario="";
    private String tip;
    private String nomTip;
    private String num;


    private CharSequence mDrawerTitle="ORDECUPE";
    private CharSequence mTitle="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_rep);
        HomeRep fragment=new HomeRep();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Base_rep, fragment);
        fragmentTransaction.commit();
        usuario=getIntent().getExtras().getString("usuario");
        BottomNavigationView bottomNavigationView =(BottomNavigationView)findViewById(R.id.bottom_navigation_rep);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_principal: {/*
                        if(id==R.id.action_cerrar){
                            Intent objIntent = new Intent(PrincipalAdmin.this, Principal.class);
                            startActivity(objIntent);
                        }*/
                        HomeRep fragment=new HomeRep();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Base_rep, fragment);
                        fragmentTransaction.commit();
                        mDrawerTitle="ORDECUPE";
                        mTitle="";
                    }break;
                    case R.id.menu_pedidos: {
                        PedidosRep fragment=new PedidosRep();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Base_rep, fragment);
                        fragmentTransaction.commit();
                        mDrawerTitle="ORDECUPE";
                        mTitle="Lista de Pedidos";
                    }break;
                    case R.id.menu_account_admin: {
                        CuentaRep fragment=new CuentaRep();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Base_rep, fragment);
                        fragmentTransaction.commit();
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

    public String getMyData() {
        return usuario;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNomTip() {
        return nomTip;
    }

    public void setNomTip(String nomTip) {
        this.nomTip = nomTip;
    }




    public void RealizarLlamada(String n){
        num=n;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Llamar();
        } else {
            solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso"+
                            "REALIZAR LLAMADA",
                    SOLICITUD_PERMISO_WRITE_CALL_LOG, this);


        }}


    public void Llamar(){

        String numTel = num;

        try{
            Intent llamar=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numTel));
            startActivity(llamar);
        }

        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error en llamada, datos incorrectos." + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_WRITE_CALL_LOG) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Llamar();
            } else {
                Toast.makeText(PrincipalRep.this, "Sin el permiso, no puedo realizar la " +
                        "acción", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static void solicitarPermiso(final String permiso, String justificacion,
                                        final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }
}
