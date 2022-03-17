package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Estrategia_A_estrella implements EstrategiaBusquedaInformada {
    @Override
    public Estado soluciona(ProblemaBusqueda p, Heuristica h) throws Exception {
        //Frontera siendo una cola LIFO--> Comportamiento de una PILA
        Estado estadoActual = p.getEstadoInicial(); //obtener el estado inicial del problema
        Estado estado;
        //inicializo explorados como vacio
        ArrayList<Nodo> nodos_explorados = new ArrayList<>();

        ArrayList<Nodo> suc ; //lista de los sucesores

        Nodo nodo ;


        Nodo [] array_nodos ;

        //inicializo frontera con el nodo del estado inicial, siendo la frontera una cola FIFO
        PriorityQueue<Nodo> frontera  = new PriorityQueue<>(); //cola de prioridad
        frontera.add(new Nodo(estadoActual,null, null, h.evalua(estadoActual))); //supongo que es 0 el coste del inicial

        float c = 0;

        while(true){

            if (frontera.isEmpty()) {System.out.println("Frontera vacia : Fallo"); break;}
            else {
                nodo = frontera.remove();
                estado = nodo.getEstado();


                if (p.esMeta(estado)) {

                    System.out.println("" + estado + "es meta, FIN");
                    System.out.println(reconstruye_sol(nodo));
                    break;
                } else {
                    nodos_explorados.add(nodo);
                    suc = sucesores(nodo, p);


                    for (Nodo nh : suc) {
                        nh.coste = nodo.coste + c;
                        nh.f = nh.coste + h.evalua(nh.getEstado());

                        if (!nodos_explorados.contains(nh.getEstado())) {
                            if (frontera.contains(nh.getEstado())) {

                                if (nh.f < nf.f) {
                                    frontera.remove(nf);
                                    //borro nf e inserto nh
                                    frontera.add (nh);
                                }
                            }
                            else { //si no la contiene se inserta
                                frontera.add (nh);

                            }
                        }
                    }
                    c+=
                }
                c+=nodo.coste;
            }
        }
        array_nodos = new Nodo[nodos_explorados.size()];
        for (int j = 0; j < nodos_explorados.size(); j++){
            array_nodos [j] = nodos_explorados.get(j);
        }
        return estado;
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

    public ArrayList<Nodo> sucesores (Nodo n, ProblemaBusqueda p) {
        ArrayList<Nodo> sucesores = new ArrayList<>();
        Nodo nh;
        Estado es;


        Accion[] accionesDisponibles = p.acciones(n.getEstado());
        for (Accion acc: accionesDisponibles) {
            es = p.result(n.getEstado(), acc);
            nh = new Nodo(es,acc,n);
            sucesores.add(nh);
        }
        return sucesores;
    }

    public boolean is_valid (Nodo n, Stack<Nodo> s, ArrayList<Nodo> ln) {
        boolean valid_frontera = false;
        boolean valid_expl = false;

        for (Nodo nodo:s) {
            if (nodo.getEstado().equals(n.getEstado())) {
                valid_frontera = true;
                break;
            }
        }

        for (Nodo nodo: ln) {
            if (nodo.getEstado().equals(n.getEstado())) {
                valid_expl = true;
                break;
            }
        }

        return (valid_frontera && valid_expl);
    }
}
