package ordecupe.android.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ordecupe.android.bean.DetalleBean;

import ordecupe.android.bean.PedidoBean;
import ordecupe.android.util.Conexion;

public class PedidoDao {

    Conexion objcon = null;
    DetalleBean objBean= null;
    ArrayList<DetalleBean> lista=null;
    PedidoBean objPedBean=null;
    ArrayList<PedidoBean> listaPed=null;
    String r="http://node78535-opercupe.whelastic.net/cultura/PedidoServlet";

    public int RegistrarPed(DetalleBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=1&TXTCOD="+obj.getProDet()
                    +"&TXTCAN="+obj.getCanDet()+"&TXTSUB="+obj.getSubDet();
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

    public int RegistrarDet(DetalleBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=2&TXTPED="+obj.getCodDet()+"&TXTCOD="+obj.getProDet()
                    +"&TXTCAN="+obj.getCanDet()+"&TXTSUB="+obj.getSubDet();
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
    public ArrayList<DetalleBean> listarDetalle(String tip) {
        try {
            String ruta = r+"?opc=3&TXTPED="+tip;
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("PEDIDOS");
            lista=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objBean=new DetalleBean();
                objBean.setCodDet(formato.getInt("CODDET"));
                objBean.setCodPed(formato.getInt("CODPED"));
                objBean.setProDet(formato.getString("PRODET"));
                objBean.setCanDet(formato.getInt("CANDET"));
                objBean.setSubDet(formato.getDouble("SUBDET"));
                lista.add(objBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return lista;
    }

    public int EliminarDet(int obj) {
        int estado;
        try {
            String ruta = r+"?opc=4&TXTCOD="+obj;
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
    public int EliminarPed(int obj) {
        int estado;
        try {
            String ruta = r+"?opc=5&TXTCOD="+obj;
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

    public int EnviarPedido(PedidoBean obj) {
        int estado;
        try {
            String ruta = r+"?opc=6&TXTCOD="+obj.getCodPed()+"&TXTDNI="+obj.getCliPed()+
                    "&TXTDIR="+obj.getDirPed()+"&TXTENT="+obj.getTipEnt();
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


    public ArrayList<PedidoBean> listarPedCli(String cli) {
        try {
            String ruta = r+"?opc=7&TXTCLI="+cli;
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("PEDIDOS");
            listaPed=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objPedBean=new PedidoBean();
                objPedBean.setCodPed(formato.getInt("CODPED"));
                objPedBean.setEstPed(formato.getString("ESTPED"));
                objPedBean.setFecPed(formato.getString("FECPED"));
                objPedBean.setHorPed(formato.getString("HORPED"));
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
    public ArrayList<PedidoBean> listarPedAd(String vis) {
        try {
            String ruta = r+"?opc=8&TXTTIP="+vis;
            objcon=new Conexion();
            JSONObject objeto = objcon.InvocarHttpClient(ruta);
            JSONArray datos= objeto.getJSONArray("PEDIDOS");
            listaPed=new ArrayList<>();
            for (int i=0;i<datos.length();i++){
                JSONObject formato=datos.getJSONObject(i);
                objPedBean=new PedidoBean();
                objPedBean.setCodPed(formato.getInt("CODPED"));
                objPedBean.setCliPed(formato.getString("CLIPED"));
                objPedBean.setFecPed(formato.getString("FECPED"));
                objPedBean.setHorPed(formato.getString("HORPED"));
                objPedBean.setDirPed(formato.getString("DIRPED"));
                objPedBean.setDisEnt(formato.getString("DISPED"));
                objPedBean.setTotPed(formato.getDouble("TOTPED"));
                objPedBean.setEstPed(formato.getString("TELPED"));
                listaPed.add(objPedBean);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("Error", e.getMessage());
        }
        return listaPed;
    }

    public int ModificarPedido(String  obj,String est) {
        int estado;
        try {
            String ruta = r+"?opc=11&TXTCOD="+obj+"&TXTEST="+est;
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
