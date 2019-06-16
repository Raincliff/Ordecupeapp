package ordecupe.android.bean;

public class DetalleBean extends PedidoBean{
    private int codDet;
    private String proDet;
    private int canDet;
    private double subDet;


    public int getCodDet() {
        return codDet;
    }

    public void setCodDet(int codDet) {
        this.codDet = codDet;
    }

    public String getProDet() {
        return proDet;
    }

    public void setProDet(String proDet) {
        this.proDet = proDet;
    }

    public int getCanDet() {
        return canDet;
    }

    public void setCanDet(int canDet) {
        this.canDet = canDet;
    }

    public double getSubDet() {
        return subDet;
    }

    public void setSubDet(double subDet) {
        this.subDet = subDet;
    }
}
