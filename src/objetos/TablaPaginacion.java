package objetos;


import objetos.Proceso;

public class TablaPaginacion {
    public int indexMF[];
    public int indexMV[];
    public Proceso proceso;

    public TablaPaginacion(int[] indexMF, int[] indexMV, Proceso proceso) {
        this.indexMF = indexMF;
        this.indexMV = indexMV;
        this.proceso = proceso;
    }
}
