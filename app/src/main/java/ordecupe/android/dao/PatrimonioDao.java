package ordecupe.android.dao;

import android.util.Log;

import org.json.JSONObject;

import ordecupe.android.bean.PatrimonioBean;
import ordecupe.android.util.Conexion;

public class PatrimonioDao {
    Conexion objcon = null;
    String r="http://node78535-opercupe.whelastic.net/cultura/PatrimonioServlet";

    public int RegistrarPat(PatrimonioBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=1&IMAGEN="+obj.getImagen()
                    +"&NOMBRE="+obj.getNombre()+"&EXT="+obj.getExt();
            ruta = ruta.replace(" ", "%20");
            objcon = new Conexion();
            Log.e("Error", ruta);
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            estado = Integer.parseInt(objeto.getString("ESTADO"));
        } catch (Exception e) {
            estado = 0;
            Log.e("Error",e.getMessage());
        }
        return estado;
    }
}
