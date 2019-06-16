package ordecupe.android.bean;

public class PedidoBean  extends EntregaBean{
    private int codPed;
    private String cliPed;
    private String fecPed;
    private String horPed;
    private String estPed;
    private String dirPed;
    private double totPed;


    public int getCodPed() {
        return codPed;
    }

    public void setCodPed(int codPed) {
        this.codPed = codPed;
    }

    public String getCliPed() {
        return cliPed;
    }

    public void setCliPed(String cliPed) {
        this.cliPed = cliPed;
    }

    public String getFecPed() {
        return fecPed;
    }

    public void setFecPed(String fecPed) {
        this.fecPed = fecPed;
    }

    public String getHorPed() {
        return horPed;
    }

    public void setHorPed(String horPed) {
        this.horPed = horPed;
    }

    public String getEstPed() {
        return estPed;
    }

    public void setEstPed(String estPed) {
        this.estPed = estPed;
    }

    public String getDirPed() {
        return dirPed;
    }

    public void setDirPed(String dirPed) {
        this.dirPed = dirPed;
    }

    public double getTotPed() {
        return totPed;
    }

    public void setTotPed(double totPed) {
        this.totPed = totPed;
    }
}
