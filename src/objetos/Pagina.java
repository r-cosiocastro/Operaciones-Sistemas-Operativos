package objetos;

public class Pagina {
    private int index;
    private Proceso proceso;

    public Pagina(int index, Proceso proceso) {
        this.index = index;
        this.proceso = proceso;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
    
    
}
