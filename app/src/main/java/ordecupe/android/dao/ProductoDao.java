package ordecupe.android.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ordecupe.android.bean.ProductoBean;
import ordecupe.android.util.Conexion;

public class ProductoDao {
    Conexion objcon = null;
    ArrayList<ProductoBean> lista=null;
    ProductoBean objProBean=null;
    String r="http://node78535-opercupe.whelastic.net/cultura/ProductoServlet";
    public ArrayList<ProductoBean> listarProductos(String tip) {
        try {
            String ruta = r+"?opc=1&TXTTIP="+tip;
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("PRODUCTOS");
            lista=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objProBean=new ProductoBean();
                objProBean.setIdPro(formato.getInt("CODPRO"));
                objProBean.setNomPro(formato.getString("NOMPRO"));
                objProBean.setMarPro(formato.getString("MARPRO"));
                objProBean.setPrePro(formato.getDouble("PREPRO"));
                objProBean.setMinPro(formato.getInt("MINPRO"));
                objProBean.setStoPro(formato.getInt("STOPRO"));
                lista.add(objProBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return lista;
    }

    public int ModificarStock(ProductoBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=2&TXTCOD="+obj.getIdPro()+"&TXTSTO="+obj.getStoPro();
            ruta = ruta.replace(" ", "%20");
            objcon = new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            estado = Integer.parseInt(objeto.getString("ESTADO"));
        } catch (Exception e) {
            estado = 0;
            System.out.println(e.getMessage());
        }
        return estado;
    }


    public int RegistrarProducto(ProductoBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=3&TXTNOM="+obj.getNomPro()+
                    "&TXTTIP="+obj.getTipPro()+"&TXTMAR="+obj.getMarPro()+"&TXTPRE="+obj.getPrePro()
                    +"&TXTMIN="+obj.getMinPro()+"&TXTSTO="+obj.getStoPro();
            ruta = ruta.replace(" ", "%20");
            objcon = new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            estado = Integer.parseInt(objeto.getString("ESTADO"));
        } catch (Exception e) {
            estado = 0;
            System.out.println(e.getMessage());
        }
        return estado;
    }
}
