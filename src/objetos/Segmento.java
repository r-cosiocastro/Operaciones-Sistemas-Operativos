package objetos;

public class Segmento {

    MemoriaSegmentacion memoriaSegmentacion;
    private int id;
    private int base;
    private int limite;
    private int fin;
    private int estado;
    private String mensajeEstado;

    public Segmento(MemoriaSegmentacion memoriaSegmentacion, int id, int base, int limite) {
        this.id = id;
        this.base = base;
        this.limite = limite;
        this.memoriaSegmentacion = memoriaSegmentacion;
        fin = base + limite;
        if (base < memoriaSegmentacion.getInicio()) {
            mensajeEstado = "ERROR DE UBICACIÓN: el segmento tiene una dirección anterior del inicio de la segmentación";
            estado = 1;
        } else if (fin > memoriaSegmentacion.getTamano()) {
            mensajeEstado = "ERROR DE UBICACIÓN: el segmento tiene una dirección fuera de la memoria";
            estado = 1;
        } else {
            estado = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public int getFin() {
        return fin;
    }

    public int getEstado() {
        return estado;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
    }

    @Override
    public String toString() {
        return "Segmento{" + "Segmento=" + id + ", Base=" + base + ", Limite=" + limite + '}';
    }

}
