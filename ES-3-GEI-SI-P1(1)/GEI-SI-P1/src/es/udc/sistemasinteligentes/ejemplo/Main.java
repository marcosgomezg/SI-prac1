package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;


import java.util.ArrayList;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws Exception {
        //ArrayList<Nodo> listanodos ;
        ArrayList<Accion> listaccion ;
        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                                                                                                    ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);

        EstrategiaBusqueda buscador = new Estrategia4();
        //listanodos = buscador.soluciona(aspiradora);
        //listaccion = reconstruye_sol(listanodos.get(listanodos.size()-1));
        System.out.println("Lista nodos");
        //System.out.println(listaccion);
        //System.out.println("Lista de nodos explorados:");
        //System.out.println(listanodos);
        /*for (Nodo n: listanodos) {
            System.out.println("("+n.getAccion()+")"+n.getEstado());
        }*/
        System.out.println("");
       System.out.println(Arrays.toString(buscador.soluciona(aspiradora)));
    }
}

