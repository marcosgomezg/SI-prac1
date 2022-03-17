package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.ArrayList;
import java.util.Stack;

public class EstrategiaProfundidad implements EstrategiaBusqueda {
    @Override
    public Nodo[] soluciona(ProblemaBusqueda p)  {
        //Frontera siendo una cola LIFO--> Comportamiento de una PILA
        Estado estadoActual = p.getEstadoInicial(); //obtener el estado inicial del problema
        Estado estado;
        //inicializo explorados como vacio
        ArrayList<Nodo> nodos_explorados = new ArrayList<>();

        ArrayList<Nodo> h ; //lista de los sucesores

        Nodo nodo ;

        Nodo [] array_nodos ;

        //inicializo frontera con el nodo del estado inicial, siendo la frontera una cola FIFO
        Stack<Nodo> frontera  = new Stack<>();
        frontera.push(new Nodo(estadoActual,null, null));

        int  expandidos = 0;
        int creados = 0;

        while(true){

            if (frontera.isEmpty()) {System.out.println("Frontera vacia : Fallo"); break;}
            else {
                nodo = frontera.pop();
                estado = nodo.getEstado();
                expandidos++;

                if (p.esMeta(estado)) {
                    System.out.println("Se expandieron "+expandidos+" y crearon " +creados);
                    System.out.println("" + estado + "es meta, FIN");
                    System.out.println(reconstruye_sol(nodo));
                    break;
                } else {
                    nodos_explorados.add(nodo);
                    h = sucesores(nodo, p);
                    creados += h.size();


                    for (Nodo nh : h) {
                        if (!is_valid(nh, frontera, nodos_explorados))
                            frontera.push(nh);
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

    public static void main(String[] args) throws Exception {
        int [][] m = {
                {2,8,0,0},
                {0,12,0,0},
                {11,0,0,0},
                {0,1,10,16}
        };

        ProblemaCuadradoMagico.Estado_cuadrado inicial = new ProblemaCuadradoMagico.Estado_cuadrado(m);
        ProblemaCuadradoMagico c = new ProblemaCuadradoMagico(inicial);

        EstrategiaBusqueda buscador = new EstrategiaProfundidad();

        buscador.soluciona(c);

        /*System.out.println("");
        System.out.println(Arrays.toString(buscador.soluciona(c)));
        System.out.println();*/


    }
}
