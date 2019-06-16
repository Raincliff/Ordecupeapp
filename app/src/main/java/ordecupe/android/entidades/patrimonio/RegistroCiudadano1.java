package ordecupe.android.entidades.patrimonio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import ordecupe.android.bean.PatrimonioBean;
import ordecupe.android.dao.PatrimonioDao;
import ordecupe.android.principal.R;

public class RegistroCiudadano1 extends AppCompatActivity implements View.OnClickListener{

    private Button btnBuscar;
    private Button btnSubir;
    private TextView btnCancelar;

    private ImageView imageView;

    private EditText resenia;

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ciudadano1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnBuscar = (Button) findViewById(R.id.BTNNIMG);
        btnSubir = (Button) findViewById(R.id.BTNNGUARDAR);
        btnCancelar = (TextView) findViewById(R.id.BTNNVOLVER);

        resenia = (EditText) findViewById(R.id.TXTNNOMBRE);

        imageView  = (ImageView) findViewById(R.id.IMGLIBRO);

        btnBuscar.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage.replaceAll("\n","");
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://node78535-opercupe.whelastic.net/cultura/PatrimonioServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(RegistroCiudadano1.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(RegistroCiudadano1.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen

                String res=resenia.getText().toString();
                Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

                String strDateFormat = "dd_MM_yyyy_HH_mm_ss"; // El formato de fecha está especificado
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha

                String id = objSDF.format(objDate)+"_"+res.substring(0,3);
                id=id.replace(" ","");

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put("opc", "1");
                params.put("USUARIO", "abel");
                params.put("IMAGEN", imagen);
                params.put("NOMBRE", id);
                params.put("RESENIA", res);
                params.put("EXT", "jpg");

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    public  void guardarDatos(){
        String imagen = getStringImagen(bitmap);

        String res=resenia.getText().toString();
        String parametros[]=new String[3];
            if(res.equals("")){
                resenia.setError("Por favor ingrese dirección de envío");
            }else{
                parametros[0] = res;
uploadImage();
//                new RegistroCiudadano1.asyncEnviarPatrimonio().execute(parametros);


            }

    }

    class asyncEnviarPatrimonio extends AsyncTask<String,Void,Void> {
        int tipo=0;
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(RegistroCiudadano1.this, "Registrando patrimonio", "Espere unos segundos...", true);

        }

        @Override
        protected Void doInBackground(String... parametros) {
            try {

                Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

                String strDateFormat = "dd_MMM_aaaa_HH_mm_ss"; // El formato de fecha está especificado
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha

                String id = objSDF.format(objDate)+"_"+parametros[0].substring(0,3);
                id=id.replace(" ","");

                PatrimonioBean objPed=new PatrimonioBean();
                objPed.setResenia(parametros[0]);
                objPed.setImagen(getStringImagen(bitmap));
                objPed.setNombre(id);
                objPed.setExt("png");
                PatrimonioDao objPedDao=new PatrimonioDao();
                tipo=objPedDao.RegistrarPat(objPed);

            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Toast.makeText(RegistroCiudadano1.this,"Pedido Registrado correctamente", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

        if(v == btnBuscar){
            showFileChooser();
        }

        if(v == btnSubir){
            guardarDatos();
        }
        if(v == btnCancelar){
            finish();
        }
    }

}
