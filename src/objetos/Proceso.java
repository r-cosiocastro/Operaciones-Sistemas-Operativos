package objetos;


public class Proceso {

    public int id;
    private String nombre;
    private int prioridad;
    private int tiempoLlegada;
    private int rafagaCPU;
    private int rafagaCPUTemp;
    private int tiempoFinalizacion;
    private int tiempoEspera;
    private int tiempoFinalizado;
    private boolean finalizado;
    private boolean agregado;
    private char[] gantt;

    public Proceso(int id, String nombre, int prioridad, int tiempoLlegada, int rafagaCPU) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
        this.rafagaCPU = rafagaCPU;
        this.rafagaCPUTemp = rafagaCPU;
        this.gantt = new char[10000000];
        for (int x = 0; x < 10000000; x++) {
            this.gantt[x] = 'E';
        }
    }

    public Proceso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    

    public Proceso(int id, String nombre, int tiempoLlegada, int rafagaCPU) {
        this.id = id;
        this.nombre = nombre;
        this.tiempoLlegada = tiempoLlegada;
        this.rafagaCPU = rafagaCPU;
        this.rafagaCPUTemp = rafagaCPU;
        this.gantt = new char[10000000];
        for (int x = 0; x < 10000000; x++) {
            this.gantt[x] = 'E';
        }
    }

    public Proceso clone(Proceso original) {
        return new Proceso(original.getId(), original.getNombre(), original.getPrioridad(), original.getTiempoLlegada(), original.getRafagaCPU());
    }

    public Proceso() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getRafagaCPU() {
        return rafagaCPU;
    }

    public void setRafagaCPU(int rafagaCPU) {
        this.rafagaCPU = rafagaCPU;
    }

    public int getRafagaCPUTemp() {
        return rafagaCPUTemp;
    }

    public void setRafagaCPUTemp(int rafagaCPUTemp) {
        this.rafagaCPUTemp = rafagaCPUTemp;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public int getTiempoFinalizado() {
        return tiempoFinalizado;
    }

    public void setTiempoFinalizado(int tiempoFinalizado) {
        this.tiempoFinalizado = tiempoFinalizado;
    }

    public boolean isAgregado() {
        return agregado;
    }

    public void setAgregado(boolean agregado) {
        this.agregado = agregado;
    }
    
    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public void setTiempoFinalizacion(int tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
    
    public void setGanttOcupado(int tiempo){
        this.gantt[tiempo] = 'O';
    }

    public char[] getGantt() {
        return gantt;
    }

    public void setGantt(char[] gantt) {
        this.gantt = gantt;
    }
    
    

    @Override
    public String toString() {
        return "Proceso{" + "nombre=" + nombre + ", tiempoLlegada=" + tiempoLlegada + ", rafagaCPU=" + rafagaCPU + ", tiempoFinalizacion=" + tiempoFinalizacion + ", tiempoEspera=" + tiempoEspera + '}';
    }

}
