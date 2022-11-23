package objetos;

public class Registro {

    private String registro;
    private Segmento segmento;
    private int desplazamiento;
    private int estado;
    private String ubicacion;

    public Registro(String registro, Segmento segmento, int desplazamiento) {
        this.registro = registro;
        this.segmento = segmento;
        this.desplazamiento = desplazamiento;
        if (desplazamiento > segmento.getLimite()) {
            ubicacion = "ERROR DE MEMORIA: El registro " + registro + " se sale del segmento " + segmento.getId();
            estado = 1;
        } else {
            ubicacion = "El registro " + registro + " se ubicó en " + (segmento.getBase() + desplazamiento);
            estado = 0;
        }
    }

    public boolean esRegistroValido() {
        return desplazamiento <= segmento.getBase();
    }

    public String getRegistro() {
        return registro;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public int getDesplazamiento() {
        return desplazamiento;
    }

    public int getEstado() {
        return estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    
    

}
