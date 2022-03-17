package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;

public class Nodo {
    private final Estado estado;
    private final Accion accion;
    private final Nodo padre;
    public float coste;
    public Nodo (Estado est, Accion ac, Nodo n, float coste) {
        this.estado = est;
        this.accion = ac;
        this.padre = n;
        this.coste = coste;
    }


    public Estado getEstado() {
        return estado;

    }


    public Accion getAccion() {
        return accion;
    }

    public Nodo getPadre() {
        return padre;
    }

    @Override
    public String toString(){
        return estado.toString();
    }

}
