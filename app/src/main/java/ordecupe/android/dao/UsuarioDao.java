package ordecupe.android.dao;

import org.json.JSONObject;

import ordecupe.android.bean.UsuarioBean;
import ordecupe.android.util.Conexion;

public class UsuarioDao {

    Conexion objcon = null;
    UsuarioBean objUsuBean = null;
    String r="http://node78535-opercupe.whelastic.net/cultura/UsuarioServlet";


    public int ValidarUsuario(UsuarioBean obj){
        String usuario, clave;
        int tipo = 0;
        try {
            usuario = obj.getUsuario();
            clave = obj.getClave();

            String ruta = r+"?opc=1&TXTUSUARIO=" + usuario + "&TXTCLAVE=" + clave;

            objcon = new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            tipo = objeto.getInt("USUARIO");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tipo;
    }


}
