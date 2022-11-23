package objetos;


import java.util.ArrayList;
import java.util.List;

public class MemoriaPaginacion {

    private int tamanoMemoriaFisica;
    private int tamanoMemoriaVirtual;
    private int tamanoPagina;
    private int paginasFisicasRestantes;
    private int paginasVirtualesRestantes;
    private int paginasFisicasMaximas;
    private int paginasVirtualesMaximas;
    public Pagina[] paginasMF;
    public Pagina[] paginasMV;
    public List<TablaPaginacion> tablasPaginacion;

    public MemoriaPaginacion() {
    }

    public MemoriaPaginacion(int tamanoMemoriaFisica, int tamanoMemoriaVirtual, int tamanoPagina) {
        this.tamanoMemoriaFisica = tamanoMemoriaFisica;
        this.tamanoMemoriaVirtual = tamanoMemoriaVirtual;
        this.tamanoPagina = tamanoPagina;
        tablasPaginacion = new ArrayList<>();
        paginasFisicasMaximas = tamanoMemoriaFisica / tamanoPagina;
        paginasVirtualesMaximas = tamanoMemoriaVirtual / tamanoPagina;
        paginasFisicasRestantes = paginasFisicasMaximas;
        paginasVirtualesRestantes = paginasVirtualesMaximas;
        paginasMF = new Pagina[paginasFisicasRestantes];
        paginasMV = new Pagina[paginasVirtualesRestantes];
    }

    public void ejemplo() {
        tamanoMemoriaFisica = 144;
        tamanoMemoriaVirtual = 144;
        tamanoPagina = 9;

        tablasPaginacion = new ArrayList<>();
        paginasFisicasMaximas = tamanoMemoriaFisica / tamanoPagina;
        paginasVirtualesMaximas = tamanoMemoriaVirtual / tamanoPagina;
        paginasFisicasRestantes = paginasFisicasMaximas;
        paginasVirtualesRestantes = paginasVirtualesMaximas;
        paginasMF = new Pagina[paginasFisicasRestantes];
        paginasMV = new Pagina[paginasVirtualesRestantes];

        Proceso procesoA = new Proceso(0, "A");
        Proceso procesoB = new Proceso(0, "B");
        Proceso procesoC = new Proceso(0, "C");
        Proceso procesoD = new Proceso(0, "D");
        Proceso procesoE = new Proceso(0, "E");
        Proceso procesoG = new Proceso(0, "G");

        int MVA[] = {2, 6, 7};
        int MFA[] = {10, 11, 13};

        paginasMV[2] = new Pagina(2, procesoA);
        paginasMV[6] = new Pagina(6, procesoA);
        paginasMV[7] = new Pagina(7, procesoA);
        paginasMF[10] = new Pagina(10, procesoA);
        paginasMF[11] = new Pagina(11, procesoA);
        paginasMF[13] = new Pagina(13, procesoA);

        añadirTablaPaginacion(MFA, MVA, procesoA);

        int MVB[] = {4, 5};
        int MFB[] = {4, 7};

        paginasMV[4] = new Pagina(4, procesoB);
        paginasMV[5] = new Pagina(5, procesoB);
        paginasMF[4] = new Pagina(4, procesoB);
        paginasMF[7] = new Pagina(7, procesoB);

        añadirTablaPaginacion(MFB, MVB, procesoB);

        int MVC[] = {8, 11, 14};
        int MFC[] = {0, 5, 9};

        paginasMV[8] = new Pagina(8, procesoC);
        paginasMV[11] = new Pagina(11, procesoC);
        paginasMV[14] = new Pagina(14, procesoC);
        paginasMF[0] = new Pagina(0, procesoC);
        paginasMF[5] = new Pagina(5, procesoC);
        paginasMF[9] = new Pagina(9, procesoC);

        añadirTablaPaginacion(MFC, MVC, procesoC);

        int MVD[] = {0, 9};
        int MFD[] = {2, 8};

        paginasMV[0] = new Pagina(0, procesoD);
        paginasMV[9] = new Pagina(9, procesoD);
        paginasMF[2] = new Pagina(2, procesoD);
        paginasMF[8] = new Pagina(8, procesoD);

        añadirTablaPaginacion(MFD, MVD, procesoD);

        int MVE[] = {12, 13};
        int MFE[] = {3, 6};

        paginasMV[12] = new Pagina(12, procesoE);
        paginasMV[13] = new Pagina(13, procesoE);
        paginasMF[3] = new Pagina(3, procesoE);
        paginasMF[6] = new Pagina(6, procesoE);

        añadirTablaPaginacion(MFE, MVE, procesoE);

        int MVG[] = {1, 3, 10};
        int MFG[] = {1, 12, 14};

        paginasMV[1] = new Pagina(1, procesoG);
        paginasMV[3] = new Pagina(3, procesoG);
        paginasMV[10] = new Pagina(10, procesoG);
        paginasMF[1] = new Pagina(1, procesoG);
        paginasMF[12] = new Pagina(12, procesoG);
        paginasMF[14] = new Pagina(14, procesoG);

        añadirTablaPaginacion(MFG, MVG, procesoG);
    }

    public void añadirPaginaMemoriaFisica(Pagina pagina, int index) {
        paginasMF[index] = pagina;
        paginasFisicasRestantes--;
    }

    public void añadirPaginaMemoriaVirtual(Pagina pagina, int index) {
        paginasMV[index] = pagina;
        paginasVirtualesRestantes--;
    }

    public int getMemoriaFisica() {
        return tamanoMemoriaFisica;
    }

    public void setMemoriaFisica(int memoriaFisica) {
        this.tamanoMemoriaFisica = memoriaFisica;
    }

    public int getMemoriaLogica() {
        return tamanoMemoriaVirtual;
    }

    public void setMemoriaLogica(int memoriaLogica) {
        this.tamanoMemoriaVirtual = memoriaLogica;
    }

    public int getTamanoPagina() {
        return tamanoPagina;
    }

    public int getPaginasFisicasRestantes() {
        return paginasFisicasRestantes;
    }

    public int getPaginasVirtualesRestantes() {
        return paginasVirtualesRestantes;
    }

    public int getPaginasFisicasMaximas() {
        return paginasFisicasMaximas;
    }

    public int getPaginasVirtualesMaximas() {
        return paginasVirtualesMaximas;
    }

    public void setTamanoPagina(int tamanoPagina) {
        this.tamanoPagina = tamanoPagina;
    }

    public void añadirTablaPaginacion(int memoriaF[], int memoriaV[], Proceso proceso) {
        tablasPaginacion.add(new TablaPaginacion(memoriaF, memoriaV, proceso));
    }

    public boolean paginaOcupadaMemoriaFisica(int index) {
        return paginasMF[index] != null;
    }

    public boolean paginaOcupadaMemoriaVirtual(int index) {
        return paginasMV[index] != null;
    }

    public boolean sePuedeAgregarPaginasMemoriaFisica(int paginas) {
        return paginas <= paginasFisicasRestantes;
    }

    public boolean sePuedeAgregarPaginasMemoriaVirtual(int paginas) {
        return paginas <= paginasVirtualesRestantes;
    }

    public Proceso procesoEnMemoriaFisica(int index) {
        return paginasMF[index].getProceso();
    }

    public Proceso procesoEnMemoriaVirtual(int index) {
        return paginasMV[index].getProceso();
    }

    public void imprimirMemorias() {
        System.out.println("Memoria física\n\n");
        for (int x = 0; x < paginasMF.length; x++) {
            if (paginasMF[x] != null) {
                System.out.println(x + ": [" + paginasMF[x].getProceso().getNombre() + "]");
            } else {
                System.out.println(x + ": [ ]");
            }
        }

        System.out.println("Memoria virtual\n\n");
        for (int x = 0; x < paginasMV.length; x++) {
            if (paginasMV[x] != null) {
                System.out.println(x + ": [" + paginasMV[x].getProceso().getNombre() + "]");
            } else {
                System.out.println(x + ": [ ]");
            }
        }
    }
}
