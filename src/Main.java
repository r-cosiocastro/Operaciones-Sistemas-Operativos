
import objetos.ListaProcesos;
import objetos.Proceso;
import objetos.MemoriaPaginacion;
import xlsx.CrearXLSXProcesos;
import xlsx.CrearXLSXPaginacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static java.util.stream.Collectors.toList;
import objetos.MemoriaSegmentacion;
import objetos.Pagina;
import objetos.Registro;
import objetos.Segmento;
import vistas.VistaMenuPrincipal;
import xlsx.CrearXLSXSegmentacion;

/**
 *
 * @author Rafael Alberto Cosio Castro
 */
public class Main {

    public Main() {
        new VistaMenuPrincipal().setVisible(true);

        Scanner sc = new Scanner(System.in);

        int opc = 0;

        while (opc != 4) {

            println("1.- Administrador de procesos");
            println("2.- Administrador de memoria");
            println("4.- Salir");
            try {
                opc = sc.nextInt();
                switch (opc) {
                    case 1:

                        List<Proceso> procesos = new ArrayList<>();
                        List<ListaProcesos> listasProcesos = new ArrayList<>();

                        int tiempo = 0;
                        int tiempoTotal = 0;
                        int quantum;
                        boolean finalizado;
                        float promedioTF = 0;
                        float promedioTE = 0;
                        boolean recorrerTiempoLlegada = false;

                        println("Ingrese la cantidad de quantums de tiempo");
                        quantum = sc.nextInt();

                        procesos.add(new Proceso(1, "A", 4, 3, 4));
                        procesos.add(new Proceso(2, "B", 2, 7, 3));
                        procesos.add(new Proceso(3, "C", 3, 5, 2));
                        procesos.add(new Proceso(4, "D", 4, 6, 1));
                        /*
                        procesos.add(new Proceso(1, "A", 4, 3, 4));
                        procesos.add(new Proceso(2, "B", 6, 7, 3));
                        procesos.add(new Proceso(3, "C", 3, 5, 2));
                        procesos.add(new Proceso(4, "D", 2, 6, 1));

                        
                        procesos.add(new Proceso(1, "A", 1, 8));
                        procesos.add(new Proceso(2, "B", 2, 3));
                        procesos.add(new Proceso(3, "C", 3, 12));
                        procesos.add(new Proceso(4, "D", 4, 1));

                        int cantProcesos = 0;
                        println("Ingrese la cantidad de procesos: ");
                        cantProcesos = sc.nextInt();

                        String nombre;
                        int tiempoLlegada;
                        int rafagaCPU;
                        int prioridad;

                        for (int x = 0; x < cantProcesos; x++) {
                            Proceso procs;
                            try {
                                println("Nombre del proceso " + (x + 1));
                                nombre = sc.next();

                                println("Tiempo de llegada del proceso " + (x + 1));
                                tiempoLlegada = sc.nextInt();

                                println("Rafaga de CPU del proceso " + (x + 1));
                                rafagaCPU = sc.nextInt();

                                if (algoritmo == PRIORIDAD) {
                                    println("Prioridad del proceso " + (x + 1));
                                    prioridad = sc.nextInt();
                                    procs = new Proceso(x, nombre, prioridad, tiempoLlegada, rafagaCPU);
                                } else {
                                    procs = new Proceso(x, nombre, tiempoLlegada, rafagaCPU);
                                }
                                procesos.add(procs);
                            } catch (InputMismatchException ex) {
                                x--;
                                printError("Ingrese un valor válido");
                                sc.next();
                            }
                        }
                         */
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
                                        tiempo += ps.getRafagaCPU() - 1;
                                        ps.setTiempoFinalizado(tiempo);
                                        ps.setFinalizado(true);
                                    }
                                }
                                tiempo++;
                            }
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

                        break;

                    case 2:
                        //  Memoria

                        println("1.- Paginación");
                        println("2.- Segmentación");

                        int op = sc.nextInt();

                        switch (op) {
                            case 1:

                                MemoriaPaginacion memoria;
                                //memoriaPaginacion.ejemplo();

                                List<Proceso> procesosMemoria = new ArrayList<>();

                                int tamañoPagina,
                                 tamañoMemoriaFisica,
                                 tamañoMemoriaVirtual;
                                int numeroProcesos;

                                println("Ingrese el tamaño de pagina: ");
                                tamañoPagina = sc.nextInt();

                                do {
                                    println("Ingrese el tamaño de memoria física:");
                                    tamañoMemoriaFisica = sc.nextInt();
                                    if (tamañoMemoriaFisica % tamañoPagina != 0) {
                                        printError("El tamaño de la memoria física debe ser un múltiplo de " + tamañoPagina);
                                    }
                                } while (tamañoMemoriaFisica % tamañoPagina != 0);

                                do {
                                    println("Ingrese el tamaño de memoria virtual:");
                                    tamañoMemoriaVirtual = sc.nextInt();
                                    if (tamañoMemoriaVirtual % tamañoPagina != 0) {
                                        printError("El tamaño de la memoria virtual debe ser un múltiplo de " + tamañoPagina);
                                    }
                                } while (tamañoMemoriaVirtual % tamañoPagina != 0);

                                memoria = new MemoriaPaginacion(tamañoMemoriaFisica, tamañoMemoriaVirtual, tamañoPagina);

                                do {
                                    println("Ingrese la cantidad de procesos que se agregarán a la memoria:");
                                    numeroProcesos = sc.nextInt();
                                    if (numeroProcesos > memoria.getPaginasFisicasRestantes() || numeroProcesos > memoria.getPaginasVirtualesRestantes()) {
                                        printError("La cantidad de procesos es mayor a la cantidad de paginas disponibles en la(s) memoria(s)");
                                    }
                                } while (numeroProcesos > memoria.getPaginasFisicasRestantes() || numeroProcesos > memoria.getPaginasVirtualesRestantes());

                                String nombre;

                                for (int x = 0; x < numeroProcesos; x++) {
                                    Proceso procs;
                                    try {
                                        println("Nombre del proceso " + (x + 1));
                                        nombre = sc.next();

                                        procs = new Proceso(x, nombre);
                                        procesosMemoria.add(procs);
                                    } catch (InputMismatchException ignore) {
                                        x--;
                                        printError("Ingrese un valor válido");
                                        sc.next();
                                    }
                                }

                                // Llenar datos de la tabla de paginación
                                int procesosRestantes = procesosMemoria.size() - 1;
                                int repeticionesProceso;
                                int posicion;
                                for (Proceso ps : procesosMemoria) {
                                    boolean numeroAceptable = false;
                                    do {

                                        println("¿Cuántas veces aparece el proceso " + ps.getNombre() + " en memoria?");
                                        repeticionesProceso = sc.nextInt();

                                        numeroAceptable = memoria.sePuedeAgregarPaginasMemoriaFisica(repeticionesProceso)
                                                && memoria.sePuedeAgregarPaginasMemoriaVirtual(repeticionesProceso)
                                                && memoria.getPaginasFisicasRestantes() >= procesosRestantes + repeticionesProceso
                                                && memoria.getPaginasVirtualesRestantes() >= procesosRestantes + repeticionesProceso;

                                        if (numeroAceptable) {

                                            int direccionesMF[] = new int[repeticionesProceso];
                                            int direccionesMV[] = new int[repeticionesProceso];
                                            for (int x = 0; x < repeticionesProceso; x++) {
                                                boolean paginaOcupada = false;
                                                boolean numeroExcedido = false;

                                                // Ubicar proceso en la memoria virtual
                                                do {
                                                    println("Ingrese la ubicación (página) #" + (x + 1) + " del proceso " + ps.getNombre() + " en la memoria virtual");
                                                    posicion = sc.nextInt();

                                                    numeroExcedido = posicion > memoria.getPaginasVirtualesMaximas() - 1;

                                                    if (!numeroExcedido) {

                                                        paginaOcupada = memoria.paginaOcupadaMemoriaVirtual(posicion);
                                                        if (paginaOcupada) {
                                                            printError("La página ya está ocupada por el proceso " + memoria.procesoEnMemoriaVirtual(posicion).getNombre());
                                                        } else {
                                                            memoria.añadirPaginaMemoriaVirtual(new Pagina(posicion, ps), posicion);
                                                            direccionesMV[x] = posicion;
                                                        }
                                                    } else {
                                                        printError("La ubicación se sale de la memoria virtual");
                                                    }
                                                } while (numeroExcedido || paginaOcupada);

                                                // Ubicar proceso en la memoria física
                                                do {
                                                    println("Ingrese la ubicación (marco) #" + (x + 1) + " del proceso " + ps.getNombre() + " en la memoria física");
                                                    posicion = sc.nextInt();

                                                    numeroExcedido = posicion > memoria.getPaginasFisicasMaximas() - 1;

                                                    if (!numeroExcedido) {
                                                        paginaOcupada = memoria.paginaOcupadaMemoriaFisica(posicion);
                                                        if (paginaOcupada) {
                                                            printError("La página ya está ocupada por el proceso " + memoria.procesoEnMemoriaFisica(posicion).getNombre());
                                                        } else {
                                                            memoria.añadirPaginaMemoriaFisica(new Pagina(posicion, ps), posicion);
                                                            direccionesMF[x] = posicion;
                                                        }
                                                    } else {
                                                        printError("La ubicación se sale de la memoria física");
                                                    }
                                                } while (paginaOcupada || numeroExcedido);
                                            }
                                            memoria.añadirTablaPaginacion(direccionesMF, direccionesMV, ps);
                                            procesosRestantes--;
                                        } else {
                                            printError("El tamaño de la memoria no es suficiente para colocar este proceso "
                                                    + repeticionesProceso + " veces");
                                            numeroAceptable = false;
                                        }
                                    } while (!numeroAceptable);
                                }

                                memoria.imprimirMemorias();
                                new CrearXLSXPaginacion(memoria);
                                break;

                            case 2:
                                MemoriaSegmentacion memoriaSegmentacion = new MemoriaSegmentacion();
                                memoriaSegmentacion.ejemplo();

                                /*
                                List<Segmento> segmentos = new ArrayList<>();

                                println("¿Cuántos segmentos tendrá la memoria?");
                                int numeroSegmentos = sc.nextInt();

                                for (int x = 0; x < numeroSegmentos; x++) {
                                    println("Ingrese la base del segmento " + x);
                                    int base = sc.nextInt();
                                    println("Ingrese el límite del segmento " + x);
                                    int limite = sc.nextInt();
                                    Segmento segmento = new Segmento(memoriaSegmentacion, x, base, limite);
                                    segmentos.add(segmento);
                                    memoriaSegmentacion.añadirSegmento(segmento);
                                }

                                println("¿Cuántos registros se encuentran en los segmentos?");
                                int numeroRegistros = sc.nextInt();

                                for (int x = 0; x < numeroRegistros; x++) {
                                    println("Ingrese el nombre del registro " + x);
                                    String nombre = sc.next();
                                    println("Ingrese el número de segmento al que pertenece el registro" + nombre);
                                    int numeroSegmento = sc.nextInt();
                                    Segmento segmento = segmentos.get(numeroSegmento);
                                    println("Ingrese el límite del segmento " + x);
                                    int desplazamiento = sc.nextInt();
                                    memoriaSegmentacion.añadirRegistro(new Registro(nombre, segmento, desplazamiento));
                                }
                                memoriaSegmentacion.evaluarSegmentos();
                                 */
                                memoriaSegmentacion.crearBloques();
                                new CrearXLSXSegmentacion(memoriaSegmentacion);
                                break;
                        }
                        break;

                }

            } catch (InputMismatchException ex) {
                printError("Ingrese un número válido");
                sc.next();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Main();
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
