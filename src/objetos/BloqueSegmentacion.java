package objetos;

public class BloqueSegmentacion {

    public int direccion;
    public int valor;
    public short tamaño;
    public int titulo;
    public boolean disponible;

    public BloqueSegmentacion(int direccion, int valor, short tamaño, int titulo, boolean disponible) {
        this.direccion = direccion;
        this.valor = valor;
        this.tamaño = tamaño;
        this.titulo = titulo;
        this.disponible = disponible;
    }

}
