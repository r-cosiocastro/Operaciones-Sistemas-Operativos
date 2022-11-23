package objetos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MemoriaSegmentacion {

    private int inicio;
    private int tamano;
    public List<Segmento> segmentos;
    public List<Registro> registros;
    public List<BloqueSegmentacion> bloques;
    final int ARRIBA = 1;
    final int ABAJO = 2;
    final int CENTRO = 0;

    public MemoriaSegmentacion() {
        segmentos = new ArrayList<>();
        registros = new ArrayList<>();
        bloques = new ArrayList<>();
    }

    public MemoriaSegmentacion(int inicio, int tamano) {
        this.inicio = inicio;
        this.tamano = tamano;
        segmentos = new ArrayList<>();
        registros = new ArrayList<>();
        bloques = new ArrayList<>();
    }

    public int getInicio() {
        return inicio;
    }

    public int getTamano() {
        return tamano;
    }

    public void añadirSegmento(Segmento segmento) {
        segmentos.add(segmento);
    }

    public void añadirRegistro(Registro registro) {
        registros.add(registro);
    }

    public void imprimirSegmentos() {
        segmentos.forEach((segmento) -> {
            System.out.println(segmento.toString());
        });
    }

    public void evaluarSegmentos() {
        if (!segmentos.isEmpty() && segmentos.size() >= 2) {
            segmentos.sort(Comparator.comparing(a -> a.getBase()));

            segmentos.forEach((s) -> {
                System.out.println(s.toString());
            });
            System.out.println("");

            for (int x = 1; x < segmentos.size(); x++) {
                Segmento segmento = segmentos.get(x);
                Segmento segmentoAnterior = segmentos.get(x - 1);

                if (segmento.getBase() < segmentoAnterior.getFin()) {
                    System.out.println(segmento.toString() + " es menor a " + segmentoAnterior.toString() + ""
                            + "(Base + límite: " + segmentoAnterior.getFin() + ", Base (del otro proceso): " + segmento.getBase() + ")");
                    segmento.setEstado(1);
                    segmento.setMensajeEstado("ERROR DE MEMORIA: La base del segmento se traspone al segmento anterior");
                }
            }

            segmentos.sort(Comparator.comparing(a -> a.getId()));
        }
    }

    public void crearBloques() {
        int ultimaDireccion = 0;
        segmentos.sort(Comparator.comparing(a -> a.getBase()));
        if (inicio > 0) {
            ultimaDireccion = segmentos.get(0).getBase();
            bloques.add(new BloqueSegmentacion(CENTRO, 0, (short) calcularTamaño(ultimaDireccion), -1, true));
        }
        for (Segmento seg : segmentos) {
            if (seg.getEstado() == 0) {
                if (ultimaDireccion < seg.getBase()) {
                    int limite = seg.getBase() - ultimaDireccion;
                    bloques.add(new BloqueSegmentacion(CENTRO, limite, (short) calcularTamaño(limite), -1, true));
                }
                bloques.add(new BloqueSegmentacion(ARRIBA, seg.getBase(), (short) calcularTamaño(seg.getLimite() / 2), seg.getId(), false));
                bloques.add(new BloqueSegmentacion(ABAJO, seg.getFin(), (short) calcularTamaño(seg.getLimite() / 2), seg.getId(), false));
                ultimaDireccion = seg.getFin();
            }
        }
        if (ultimaDireccion < tamano) {
            int limite = tamano - ultimaDireccion;
            bloques.add(new BloqueSegmentacion(CENTRO, limite, (short) calcularTamaño(limite), -1, true));
        }
        segmentos.sort(Comparator.comparing(a -> a.getId()));
    }

    public void ejemplo() {
        inicio = 1024;
        tamano = 4000;
        añadirSegmento(new Segmento(this, 0, 1412, 324));
        añadirSegmento(new Segmento(this, 1, 2124, 438));
        añadirSegmento(new Segmento(this, 2, 3064, 100));
        añadirSegmento(new Segmento(this, 3, 3329, 59));
        añadirSegmento(new Segmento(this, 4, 3179, 150));
        añadirSegmento(new Segmento(this, 5, 1035, 108));
        añadirSegmento(new Segmento(this, 6, 1759, 324));
        añadirSegmento(new Segmento(this, 7, 3407, 50));
        añadirSegmento(new Segmento(this, 8, 2616, 438));
        añadirSegmento(new Segmento(this, 9, 1164, 216));

        registros.add(new Registro("x", segmentos.get(6), 300));
        registros.add(new Registro("y", segmentos.get(3), 60));
        registros.add(new Registro("z", segmentos.get(7), 50));

        evaluarSegmentos();

    }

    int calcularTamaño(int bytes) {
        return (45 * bytes) / 100;
    }

}
