package objetos;


import objetos.Proceso;
import java.util.ArrayList;
import java.util.List;

public class ListaProcesos {

    private String algoritmo;
    private List<Proceso> procesos;
    private float promedioTiempoEspera;
    private float promedioTiempoFinalizacion;
    private boolean apropiativos;
    private int tiempoFinalizacionMasAlto;

    public ListaProcesos(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public ListaProcesos(String algoritmo, List<Proceso> procesos, float promedioTiempoEspera, float promedioTiempoFinalizacion, boolean apropiativos) {
        this.algoritmo = algoritmo;
        this.procesos = procesos;
        this.promedioTiempoEspera = promedioTiempoEspera;
        this.promedioTiempoFinalizacion = promedioTiempoFinalizacion;
        this.apropiativos = apropiativos;
        this.tiempoFinalizacionMasAlto = 0;
        procesos.stream().filter((ps) -> (ps.getTiempoFinalizado()>this.tiempoFinalizacionMasAlto)).forEachOrdered((ps) -> {
            this.tiempoFinalizacionMasAlto = ps.getTiempoFinalizado();
        });
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public float getPromedioTiempoEspera() {
        return promedioTiempoEspera;
    }

    public void setPromedioTiempoEspera(float promedioTiempoEspera) {
        this.promedioTiempoEspera = promedioTiempoEspera;
    }

    public float getPromedioTiempoFinalizacion() {
        return promedioTiempoFinalizacion;
    }

    public void setPromedioTiempoFinalizacion(float promedioTiempoFinalizacion) {
        this.promedioTiempoFinalizacion = promedioTiempoFinalizacion;
    }

    public boolean isApropiativos() {
        return apropiativos;
    }

    public void setApropiativos(boolean apropiativos) {
        this.apropiativos = apropiativos;
    }

    public int getTiempoFinalizacionMasAlto() {
        return tiempoFinalizacionMasAlto;
    }

    public void setTiempoFinalizacionMasAlto(int tiempoFinalizacionMasAlto) {
        this.tiempoFinalizacionMasAlto = tiempoFinalizacionMasAlto;
    }
    
    

}
