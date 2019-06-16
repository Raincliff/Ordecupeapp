package ordecupe.android.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import ordecupe.android.principal.LoginPrincipal;
import ordecupe.android.principal.Principal;
import ordecupe.android.principal.R;

public class SplashScreen extends Activity {
    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                try{
                    //PrincipalAdmin pa=new PrincipalAdmin();
                    //String usu=pa.getMyData();
                    //if(usu.equals("")){
                        Intent intent = new Intent(SplashScreen.this, LoginPrincipal.class);
                        startActivity(intent);
                    //}else{
                      //  Intent intent = new Intent(SplashScreen.this, PrincipalAdmin.class);
                        //startActivity(intent);
                    //}
                }catch (Exception e){

                }
                finish();
            };
        }, DURACION_SPLASH);
    }
}