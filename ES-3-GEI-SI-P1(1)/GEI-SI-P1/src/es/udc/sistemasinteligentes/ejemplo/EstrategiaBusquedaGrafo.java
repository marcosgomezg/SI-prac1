package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class EstrategiaBusquedaGrafo implements EstrategiaBusqueda {

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) {

        Estado estadoActual = p.getEstadoInicial(); //obtener el estado inicial del problema
        Estado estado;
        //inicializo explorados como vacio
        ArrayList<Nodo> nodos_explorados = new ArrayList<>();

        ArrayList<Nodo> h ; //lista de los sucesores

        Nodo nodo ;

        Nodo [] array_nodos ;

        //inicializo frontera con el nodo del estado inicial, siendo la frontera una cola FIFO
        Queue<Nodo> frontera  = new LinkedList<>();
        frontera.offer(new Nodo(estadoActual,null, null));

        int i = 1;

        while(true){

            if (frontera.isEmpty()) {System.out.println("Frontera vacia : Fallo"); break;}
            else {
                nodo = frontera.poll();
                estado = nodo.getEstado();

                if (p.esMeta(estado)) {
                    System.out.println("" + estado + "es meta, FIN");
                    System.out.println(reconstruye_sol(nodo));
                } else {
                    nodos_explorados.add(nodo);
                    h = sucesores(nodo, p);

                    for (Nodo nh : h) {
                        if (!is_valid(nh, frontera, nodos_explorados))
                            frontera.offer(nh);
                    }
                }
            }
        }

        array_nodos = new Nodo[nodos_explorados.size()];
        for (int j = 0; j < nodos_explorados.size(); j++){
            array_nodos [j] = nodos_explorados.get(j);
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

    public boolean is_valid (Nodo n, Queue<Nodo> q, ArrayList<Nodo> ln) {
        boolean valid_frontera = false;
        boolean valid_expl = false;

        for (Nodo nodo:q) {
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
