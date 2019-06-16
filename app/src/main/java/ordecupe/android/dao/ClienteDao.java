package ordecupe.android.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ordecupe.android.bean.ClienteBean;
import ordecupe.android.util.Conexion;

public class ClienteDao {

    Conexion objcon = null;
    ClienteBean objCliBean = null;
    String r="http://node78535-opercupe.whelastic.net/cultura/ClienteServlet";
    public int Registrar(ClienteBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=1&TXTUSU="+obj.getUsuario()+
                    "&TXTCLA="+obj.getClave()+"&TXTTEL="+obj.getTelCli()+"&TXTDNI="+obj.getDniCli()+"&TXTNOM="+obj.getNomCli()+
                    "&TXTAPE="+obj.getApeCli()+"&TXTDIR="+obj.getDirCli();
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
    public ClienteBean CargarDatos(String usu) {
        try {
            String ruta = r+"?opc=2&TXTUSU="+usu;
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("PERSONA");
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objCliBean=new ClienteBean();
                objCliBean.setDniCli(formato.getString("DNIPER"));
                objCliBean.setNomCli(formato.getString("NOMPER"));
                objCliBean.setApeCli(formato.getString("APEPER"));
                objCliBean.setTelCli(formato.getString("TELPER"));
                objCliBean.setDirCli(formato.getString("DIRPER"));
                objCliBean.setUsuario(formato.getString("USUPER"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return objCliBean;
    }

    public ArrayList<ClienteBean> ListaCli() {
        ArrayList<ClienteBean> lista=null;
        try {
            String ruta = r+"?opc=3";
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("CLIENTE");
            lista=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objCliBean=new ClienteBean();
                objCliBean.setDniCli(formato.getString("DNIPER"));
                objCliBean.setNomCli(formato.getString("NOMPER"));
                objCliBean.setApeCli(formato.getString("APEPER"));
                objCliBean.setTelCli(formato.getString("TELPER"));
                objCliBean.setDirCli(formato.getString("DIRPER"));
                objCliBean.setUsuario(formato.getString("USUPER"));
                lista.add(objCliBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return lista;
    }

    public int ActualizarDatos(ClienteBean obj){
        int tipo = 0;
        try {
            String ruta = r+"?opc=4&TXTCOD="+obj.getDniCli()+
                    "&TXTDIR="+obj.getDirCli()+"&TXTTEL=" + obj.getTelCli();
            ruta = ruta.replace(" ", "%20");
            objcon = new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            tipo = objeto.getInt("ESTADO");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tipo;
    }
}
