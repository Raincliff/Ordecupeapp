package ordecupe.android.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ordecupe.android.bean.PedidoBean;
import ordecupe.android.util.Conexion;

public class VentaDao {
    Conexion objcon = null;
    PedidoBean objPedBean=null;
    ArrayList<PedidoBean> listaPed=null;
    String r="http://node78535-opercupe.whelastic.net/cultura/PedidoServlet";

    public ArrayList<PedidoBean> listarVenCli(String cli) {
        try {
            String ruta = r+"?opc=9&TXTCLI="+cli;
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("VENTAS");
            listaPed=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objPedBean=new PedidoBean();
                objPedBean.setCodPed(formato.getInt("CODPED"));
                objPedBean.setFecPed(formato.getString("FECPED"));
                objPedBean.setDirPed(formato.getString("DIRPED"));
                objPedBean.setDisEnt(formato.getString("DISPED"));
                objPedBean.setTotPed(formato.getDouble("TOTPED"));
                listaPed.add(objPedBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return listaPed;
    }
    public ArrayList<PedidoBean> listarVenAd() {
        try {
            String ruta = r+"?opc=10";
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("VENTAS");
            listaPed=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objPedBean=new PedidoBean();
                objPedBean.setCodPed(formato.getInt("CODPED"));
                objPedBean.setCliPed(formato.getString("CLIPED"));
                objPedBean.setFecPed(formato.getString("FECPED"));
                objPedBean.setDirPed(formato.getString("DIRPED"));
                objPedBean.setDisEnt(formato.getString("DISPED"));
                objPedBean.setTotPed(formato.getDouble("TOTPED"));
                listaPed.add(objPedBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return listaPed;
    }

    public int RegistrarVenta(int obj) {
        int estado;
        try {
            String ruta = r+"?opc=12&TXTCOD="+obj;
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
