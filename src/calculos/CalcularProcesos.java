package calculos;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import objetos.ListaProcesos;
import objetos.Proceso;
import xlsx.CrearXLSXProcesos;

public class CalcularProcesos {

    public CalcularProcesos(List<Proceso> procesos, int quantum) {
        List<ListaProcesos> listasProcesos = new ArrayList<>();

        int tiempo = 0;
        int tiempoTotal = 0;
        boolean finalizado;
        float promedioTF = 0;
        float promedioTE = 0;
        boolean recorrerTiempoLlegada = false;
        
        List<Proceso> procesosFCFS = procesos.stream().map(d -> d.clone(d)).collect(toList());
        List<Proceso> procesosSJN = procesos.stream().map(d -> d.clone(d)).collect(toList());
        List<Proceso> procesosPrioridad = procesos.stream().map(d -> d.clone(d)).collect(toList());
        List<Proceso> procesosSRT = procesos.stream().map(d -> d.clone(d)).collect(toList());
        List<Proceso> procesosRR = procesos.stream().map(d -> d.clone(d)).collect(toList());

        //case FCFS:
        println("FCFS:\n");

        for (Proceso ps : procesosFCFS) {
            if (ps.getTiempoLlegada() == 0) {
                recorrerTiempoLlegada = true;
                break;
            }
        }

        if (recorrerTiempoLlegada) {
            procesosFCFS.forEach((ps) -> {
                ps.setTiempoLlegada(ps.getTiempoLlegada() + 1);
            });
        }

        procesosFCFS.sort(Comparator.comparing(a -> a.getTiempoLlegada()));
        tiempoTotal = obtenerTiempoTotalNoApropiativo(procesosFCFS);
        println("TIEMPO: " + tiempoTotal);

        listasProcesos.add(obtenerListaNoApropiativos(procesosFCFS, "FCFS"));

        //Diagrama de Gantt
        procesosFCFS.forEach((ps) -> {
            print("Proceso: " + ps.getNombre() + "\t");
            for (int x = 0; x < ps.getGantt().length; x++) {
                if (x > ps.getTiempoFinalizacion() - 1) {
                    break;
                }
                print(ps.getGantt()[x]);
            }
            println("");
        });

        procesosFCFS.clear();

        //case SJN:
        println("SJN:\n");

        recorrerTiempoLlegada = false;
        for (Proceso ps : procesosSJN) {
            if (ps.getTiempoLlegada() == 0) {
                recorrerTiempoLlegada = true;
                break;
            }
        }

        if (recorrerTiempoLlegada) {
            procesosSJN.forEach((ps) -> {
                ps.setTiempoLlegada(ps.getTiempoLlegada() + 1);
            });
        }

        Collections.sort(procesosSJN, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                //  Ordenar por rafaga de CPU
                return p1.getRafagaCPU() < p2.getRafagaCPU() ? -1 : p1.getRafagaCPU() > p2.getRafagaCPU() ? 1 : doSecodaryOrderSort(p1, p2);
            }

            //  Si la rafaga de CPU es igual, ordenar por tiempo de llegada
            public int doSecodaryOrderSort(Proceso p1, Proceso p2) {
                return p1.getTiempoLlegada() < p2.getTiempoLlegada() ? -1 : p1.getTiempoLlegada() > p2.getTiempoLlegada() ? 1 : 0;
            }
        });

        println("TIEMPO: " + obtenerTiempoTotalNoApropiativo(procesosSJN));

        listasProcesos.add(obtenerListaNoApropiativos(procesosSJN, "SJN"));

        //Diagrama de Gantt
        procesosSJN.forEach((ps) -> {
            print("Proceso: " + ps.getNombre() + "\t");
            for (int x = 0; x < ps.getGantt().length; x++) {
                if (x > ps.getTiempoFinalizacion() - 1) {
                    break;
                }
                print(ps.getGantt()[x]);
            }
            println("");
        });

        procesosSJN.clear();

        //case PRIORIDAD:
        println("Prioridad:\n");

        recorrerTiempoLlegada = false;
        for (Proceso ps : procesosPrioridad) {
            if (ps.getTiempoLlegada() == 0) {
                recorrerTiempoLlegada = true;
                break;
            }
        }

        if (recorrerTiempoLlegada) {
            procesosPrioridad.forEach((ps) -> {
                ps.setTiempoLlegada(ps.getTiempoLlegada() + 1);
            });
        }

        Collections.sort(procesosPrioridad, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                //  Ordenar por prioridad
                return p1.getPrioridad() < p2.getPrioridad() ? -1 : p1.getPrioridad() > p2.getPrioridad() ? 1 : doSecodaryOrderSort(p1, p2);
            }

            //  Si la prioridad es igual, ordenar por tiempo de llegada
            public int doSecodaryOrderSort(Proceso p1, Proceso p2) {
                return p1.getTiempoLlegada() < p2.getTiempoLlegada() ? -1 : p1.getTiempoLlegada() > p2.getTiempoLlegada() ? 1 : 0;
            }
        });

        println("TIEMPO: " + obtenerTiempoTotalNoApropiativo(procesosPrioridad));

        listasProcesos.add(obtenerListaNoApropiativos(procesosPrioridad, "Prioridad"));

        //Diagrama de Gantt
        procesosPrioridad.forEach((ps) -> {
            print("Proceso: " + ps.getNombre() + "\t");
            for (int x = 0; x < ps.getGantt().length; x++) {
                if (x > ps.getTiempoFinalizacion() - 1) {
                    break;
                }
                print(ps.getGantt()[x]);
            }
            println("");
        });

        procesosPrioridad.clear();

        //case SRT:
        println("SRT:\n");
        Collections.sort(procesosSRT, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                //  Ordenar por rafaga de CPU
                return p1.getRafagaCPU() < p2.getRafagaCPU() ? -1 : p1.getRafagaCPU() > p2.getRafagaCPU() ? 1 : doSecodaryOrderSort(p1, p2);
            }

            //  Si la rafaga de CPU es igual, ordenar por tiempo de llegada
            public int doSecodaryOrderSort(Proceso p1, Proceso p2) {
                return p1.getTiempoLlegada() < p2.getTiempoLlegada() ? -1 : p1.getTiempoLlegada() > p2.getTiempoLlegada() ? 1 : 0;
            }
        });

        tiempo = 0;
        finalizado = false;
        while (!finalizado) {
            finalizado = true;
            for (int x = 0; x < procesosSRT.size(); x++) {
                Proceso ps = procesosSRT.get(x);
                if (!ps.isFinalizado()) {
                    finalizado = false;

                    if (ps.getTiempoLlegada() <= tiempo) {
                        println("Tiempo: " + tiempo + ", " + ps.toString());
                        ps.setTiempoEspera(Math.abs(ps.getTiempoLlegada() - tiempo));
                        ps.setTiempoFinalizacion(ps.getTiempoEspera() + ps.getRafagaCPU());
                        for (int j = tiempo; j < tiempo + (ps.getRafagaCPU()); j++) {
                            ps.setGanttOcupado(j);
                        }
                        tiempo += ps.getRafagaCPU();
                        ps.setTiempoFinalizado(tiempo - 1);
                        ps.setFinalizado(true);
                    }
                }
            }
            tiempo++;
        }

        println("TIEMPO: " + tiempo);

        listasProcesos.add(obtenerLista(procesosSRT, "SRT"));

        //Diagrama de Gantt
        procesosSRT.forEach((ps) -> {
            print("Proceso: " + ps.getNombre() + "\t|");
            for (int x = 0; x < ps.getGantt().length; x++) {
                if (x > ps.getTiempoFinalizado()) {
                    break;
                } else if (x < ps.getTiempoLlegada()) {
                    print(" ");
                } else {
                    print(ps.getGantt()[x]);
                }
            }
            println("");
        });

        procesosSRT.clear();

        //case ROUNDROBIN:
        println("Round Robin:\n");
        procesosRR.sort(Comparator.comparing(a -> a.getTiempoLlegada()));
        tiempo = 0;
        String colaString = "";
        int cambioContexto = 0;
        int ciclos = 0;
        int procesosPorTerminar = 1;

        List<Proceso> cola = new ArrayList<>();
        List<Proceso> colaCompleta = new ArrayList<>();

        while (procesosPorTerminar > 0) {
            procesosPorTerminar = 0;
            for (Proceso ps : procesosRR) {
                if (!ps.isFinalizado()) {

                    procesosPorTerminar++;

                    if (ps.getTiempoLlegada() <= tiempo && !ps.isAgregado()) {
                        colaString += ps.getNombre();
                        ps.setAgregado(true);
                        cola.add(ps);
                        colaCompleta.add(ps);
                    }
                }
            }

            List<Proceso> colaActual = new ArrayList<>(cola);
            cola.clear();

            int cicloActual = quantum;

            if (procesosPorTerminar == 1 && !colaActual.isEmpty()) {
                cicloActual = colaActual.get(0).getRafagaCPUTemp();
            }

            for (Proceso ps : colaActual) {
                int temp = 0;
                while (temp < cicloActual) {
                    ps.setRafagaCPUTemp(ps.getRafagaCPUTemp() - 1);

                    if (ps.getRafagaCPUTemp() == 0) {
                        temp = cicloActual;
                        ps.setTiempoFinalizacion(ps.getTiempoEspera() + ps.getRafagaCPU());
                        ps.setFinalizado(true);
                        ps.setTiempoFinalizado(tiempo);
                    }

                    ps.setGanttOcupado(tiempo);

                    tiempo++;

                    for (int x = 0; x < procesosRR.size(); x++) {
                        Proceso proceso = procesosRR.get(x);

                        if (!proceso.equals(ps)) {
                            if (!proceso.isFinalizado() && proceso.getTiempoLlegada() < tiempo) {
                                proceso.setTiempoEspera(proceso.getTiempoEspera() + 1);
                            }
                        }

                        if (proceso.getTiempoLlegada() <= tiempo && !proceso.isAgregado()) {
                            colaString += proceso.getNombre();
                            proceso.setAgregado(true);
                            cola.add(proceso);
                            colaCompleta.add(proceso);
                        }
                    }
                    temp++;
                }
                if (ps.getRafagaCPUTemp() > 0) {
                    colaString += ps.getNombre();
                    cola.add(ps);
                    colaCompleta.add(ps);
                }
                cambioContexto++;
            }

            if (colaActual.isEmpty()) {
                tiempo++;
            }

        }
        int lastIndex = 0;
        int repeticiones[] = new int[procesosRR.size()];

        for (int y = 0; y < procesosRR.size(); y++) {
            Proceso proceso = procesosRR.get(y);
            for (int x = 0; x < colaCompleta.size(); x++) {
                Proceso procesoCola = colaCompleta.get(x);
                if (procesoCola.getId() == proceso.getId() && x > lastIndex) {
                    repeticiones[y]++;
                    lastIndex = x;
                }
            }
            println(proceso.getNombre() + ": " + repeticiones[y]);
        }

        for (int y = 0; y < procesosRR.size(); y++) {
            Proceso proceso = procesosRR.get(y);
            for (int x = 0; x < colaCompleta.size(); x++) {
                Proceso procesoCola = colaCompleta.get(x);
                if (procesoCola.getId() == proceso.getId() && repeticiones[y] > 1) {
                    ciclos++;
                    lastIndex = x;
                }
            }
        }
        ciclos++;

        cambioContexto--;
        println(colaString);
        println("Cambios de contexto: " + cambioContexto);
        println("Ciclos: " + ciclos);
        println("TIEMPO: " + tiempo);

        /**
         * DEBUG Round Robin
         */
        promedioTF = 0;
        promedioTE = 0;
        for (Proceso ps : procesosRR) {
            promedioTF += ps.getTiempoFinalizacion();
            promedioTE += ps.getTiempoEspera();
        }
        promedioTF /= procesosRR.size();
        promedioTE /= procesosRR.size();

        procesosRR.sort(Comparator.comparing(a -> a.id));

        procesosRR.forEach((ps) -> {
            println(ps.toString());
        });

        println("Promedio tiempo finalización: " + promedioTF);
        println("Promedio tiempo espera: " + promedioTE);
        println("---------------------------------------------------");

        /**
         * endDEBUG Round Robin
         */
        println("----------------------------------------------------------------");
        for (Proceso ps : procesosFCFS) {
            println(ps.toString());
        }
        println("----------------------------------------------------------------");

        listasProcesos.add(obtenerLista(procesosRR, "Round Robin"));

        //Diagrama de Gantt
        procesosRR.forEach((ps) -> {
            print("Proceso: " + ps.getNombre() + "|");
            for (int x = 0; x < ps.getGantt().length; x++) {
                if (x > ps.getTiempoFinalizado()) {
                    break;
                } else if (x < ps.getTiempoLlegada()) {
                    print(x);
                } else {
                    print(ps.getGantt()[x]);
                }
            }
            println("");
        });

        procesosRR.clear();

        new CrearXLSXProcesos(listasProcesos);
        for (int x = 0; x < listasProcesos.size(); x++) {
            ListaProcesos lista = listasProcesos.get(x);
            println("Algoritmo: " + lista.getAlgoritmo());
            for (Proceso ps : lista.getProcesos()) {
                println(ps.toString());
            }
            println("Promedio tiempo finalización: " + lista.getPromedioTiempoFinalizacion());
            println("Promedio tiempo espera: " + lista.getPromedioTiempoEspera());
            println("");
        }
    }

    Proceso procesoSinFinalizar(List<Proceso> lista) {
        for (Proceso proceso : lista) {
            if (!proceso.isFinalizado()) {
                return proceso;
            }
        }
        return null;
    }

    ListaProcesos obtenerListaNoApropiativos(List<Proceso> procesosList, String algoritmo) {
        List<Proceso> procesos = new ArrayList<>(procesosList);
        int tiempoFinalizacion = 0;
        int rafagaAnterior = 0;

        for (Proceso ps : procesos) {
            tiempoFinalizacion = ps.getRafagaCPU() + rafagaAnterior;
            for (int x = rafagaAnterior; x < tiempoFinalizacion; x++) {
                ps.setGanttOcupado(x);
            }
            ps.setTiempoFinalizacion(tiempoFinalizacion);
            ps.setTiempoFinalizado(tiempoFinalizacion);
            ps.setTiempoEspera(tiempoFinalizacion - ps.getRafagaCPU());
            rafagaAnterior += ps.getRafagaCPU();
        }

        float promedioTF = 0;
        float promedioTE = 0;
        for (Proceso ps : procesos) {
            promedioTF += ps.getTiempoFinalizacion();
            promedioTE += ps.getTiempoEspera();
        }
        promedioTF /= procesos.size();
        promedioTE /= procesos.size();

        procesos.sort(Comparator.comparing(a -> a.id));

        procesos.forEach((ps) -> {
            println(ps.toString());
        });

        println("Promedio tiempo finalización: " + promedioTF);
        println("Promedio tiempo espera: " + promedioTE);

        return new ListaProcesos(algoritmo, new ArrayList<>(procesos), promedioTE, promedioTF, false);
    }

    ListaProcesos obtenerLista(List<Proceso> procesosList, String algoritmo) {
        List<Proceso> procesos = new ArrayList<>(procesosList);
        procesos.sort(Comparator.comparing(a -> a.id));

        procesos.forEach((ps) -> {
            println(ps.toString());
        });

        float promedioTF = 0;
        float promedioTE = 0;
        for (Proceso ps : procesos) {
            promedioTF += ps.getTiempoFinalizacion();
            promedioTE += ps.getTiempoEspera();
        }
        promedioTF /= procesos.size();
        promedioTE /= procesos.size();

        println("Promedio tiempo finalización: " + promedioTF);
        println("Promedio tiempo espera: " + promedioTE);

        return new ListaProcesos(algoritmo, new ArrayList<>(procesos), promedioTE, promedioTF, true);
    }

    int obtenerTiempoTotalNoApropiativo(List<Proceso> procesosList) {
        List<Proceso> procesos = new ArrayList<>(procesosList);
        int rafagaTotal = 0;

        rafagaTotal = procesos.stream().map((ps) -> ps.getRafagaCPU()).reduce(rafagaTotal, Integer::sum);

        return rafagaTotal;
    }

    final void println(Object object) {
        System.out.println(object);
    }

    final void printError(Object object) {
        System.err.println("ERROR: " + object);
    }

    final void print(Object object) {
        System.out.print(object);
    }

    boolean existeProcesoEnLista(List<Proceso> lista, Proceso proceso) {
        return lista.stream().anyMatch((ps) -> (ps.getId() == proceso.getId()));
    }
}
