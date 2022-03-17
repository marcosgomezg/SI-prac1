package es.udc.sistemasinteligentes;

import es.udc.sistemasinteligentes.ejemplo.ProblemaCuadradoMagico;

public class Heuristica_Cuadrado extends Heuristica{
    @Override
    public float evalua(Estado e) {

    int[][] m = ((ProblemaCuadradoMagico.Estado_cuadrado)e).matriz;
    int casillas_vacias = 0;

    for (int i = 0; i < m.length; i++){
        for (int j = 0; j < m.length; j++){
            if (m[i][j] == 0) casillas_vacias++;
        }
    }

    return (casillas_vacias*casillas_vacias);
    }


}
