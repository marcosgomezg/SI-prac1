package es.udc.sistemasinteligentes;


import es.udc.sistemasinteligentes.ejemplo.ProblemaCuadradoMagico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public abstract class ProblemaBusqueda {
    private Estado estadoInicial;
    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public ProblemaBusqueda(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    /**
     * Indica si el estado "es" constituye una solución al problema
     * @param es Estado a verificar
     * @return True si el estado es meta o false en caso contrario
     */
    public abstract boolean esMeta(Estado es);

    /**
     * Devuelve una lista con las acciones aplicables a un estado dado
     * @param es Estado al que aplicar las acciones
     * @return Lista de acciones aplicables
     */
    public abstract Accion[] acciones(Estado es);

    /**
     * Devuelve el resultado de aplicar una acción sobre un estado, ambos pasados como parámetros
     * @param es Estado sobre el que aplicar la acción
     * @param acc Acción a aplicar
     * @return Estado resultante
     */
    public Estado result(Estado es, Accion acc){
        return acc.aplicaA(es);
    }

    public static ArrayList<Integer> disponibles (Estado es) {

        int[][] m = ((ProblemaCuadradoMagico.Estado_cuadrado)es).matriz;
        ArrayList<Integer> val_disponibles = new ArrayList<>(); //lista con valores disponibles


        for (int i = 1; i <= m.length * m.length; i++) {
            val_disponibles.add(i);
        }

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (val_disponibles.contains(m[i][j])) {
                    val_disponibles.remove((Integer)m[i][j]);
                }
            }
        }

        return val_disponibles;

    }

}
