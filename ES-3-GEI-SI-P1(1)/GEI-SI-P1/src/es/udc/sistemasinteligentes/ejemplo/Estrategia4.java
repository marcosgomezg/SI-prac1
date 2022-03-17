package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;

public class Estrategia4 implements EstrategiaBusqueda {

    public Estrategia4() {
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception{
        ArrayList<Estado> explorados = new ArrayList<Estado>();
        Estado estadoActual = p.getEstadoInicial();
        explorados.add(estadoActual);
        ArrayList<Nodo> listanodos = new ArrayList<>();
        Nodo n = new Nodo (estadoActual,null,null);
        listanodos.add(n); //nodo inicial
        Nodo [] array_nodos;


        int i = 1;

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!p.esMeta(estadoActual)){
            System.out.println((i++) + " - " + estadoActual + " no es meta");
            Accion[] accionesDisponibles = p.acciones(estadoActual);
            boolean modificado = false;
            for (Accion acc: accionesDisponibles) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - RESULT(" + estadoActual + ","+ acc + ")=" + sc);
                if (!explorados.contains(sc)) {
                    estadoActual = sc;
                    System.out.println((i++) + " - " + sc + " NO explorado");
                    explorados.add(estadoActual);
                    listanodos.add(new Nodo(estadoActual,acc,n));
                    n = new Nodo (estadoActual,acc,n); // n sera el padre asi
                    modificado = true;
                    System.out.println((i++) + " - Estado actual cambiado a " + estadoActual);
                    break;
                }
                else {
                    System.out.println((i++) + " - " + sc + " ya explorado");
                    reconstruye_sol(listanodos.get(listanodos.size()-1)); //desde el ultimo nodo insertado
                }
            }
            if (!modificado) throw new Exception("No se ha podido encontrar una solución");
        }
        System.out.println((i++) + " - FIN - " + estadoActual);

        array_nodos = new Nodo[listanodos.size()];

        for (int j = 0; j < listanodos.size(); j++){
            array_nodos [j] = listanodos.get(j);
        }
        return array_nodos;
    }

    public ArrayList<Accion> reconstruye_sol (Nodo nodo) {

        ArrayList<Accion> solucion = new ArrayList<>();
        Accion a = nodo.getAccion();
        while (a != null) {
            solucion.add(a);
            a = (nodo.getPadre()).getAccion();
            nodo = nodo.getPadre();
        }
        return solucion;
    }
}
