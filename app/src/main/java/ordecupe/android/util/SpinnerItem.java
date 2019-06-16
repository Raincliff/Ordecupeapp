package ordecupe.android.util;

public class SpinnerItem {
    int id;
    String nombre;
    //Constructor
    public SpinnerItem(int id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;
    }
    public int getId() {
        return id;
    }
}
