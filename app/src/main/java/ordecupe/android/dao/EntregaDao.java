package ordecupe.android.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ordecupe.android.bean.EntregaBean;
import ordecupe.android.util.Conexion;

public class EntregaDao {

    Conexion objcon = null;
    EntregaBean objBean= null;
    ArrayList<EntregaBean> lista=null;
    String r="http://node78535-opercupe.whelastic.net/cultura/EntregaServlet";
    public ArrayList<String> listarTipoEnt() {
        ArrayList<String> lista=new ArrayList<>();
        try {
            String ruta = r+"?opc=2";
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("ENTREGAS");
            String tipo;
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                tipo=formato.getString("TIPENT");
                lista.add(tipo);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return lista;
    }

    public ArrayList<EntregaBean> listarDistritos(String tip) {
        try {
            String ruta = r+"?opc=3&TXTTIP="+tip;
            ruta = ruta.replace(" ", "%20");
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("ENTREGAS");
            lista=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objBean=new EntregaBean();
                objBean.setCodEnt(formato.getInt("CODENT"));
                objBean.setTipEnt(formato.getString("TIPENT"));
                objBean.setDisEnt(formato.getString("DISENT"));
                objBean.setPreEnt(formato.getDouble("PREENT"));
                lista.add(objBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return lista;
    }

    public ArrayList<EntregaBean> listarPrecios() {
        try {
            String ruta = r+"?opc=4";
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("ENTREGAS");
            lista=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objBean=new EntregaBean();
                objBean.setCodEnt(formato.getInt("CODENT"));
                objBean.setTipEnt(formato.getString("TIPENT"));
                objBean.setDisEnt(formato.getString("DISENT"));
                objBean.setPreEnt(formato.getDouble("PREENT"));
                lista.add(objBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return lista;
    }
}
