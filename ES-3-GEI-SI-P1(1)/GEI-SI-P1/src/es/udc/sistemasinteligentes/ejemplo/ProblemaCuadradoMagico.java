package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.ProblemaBusqueda;


import java.util.ArrayList;

public class ProblemaCuadradoMagico extends ProblemaBusqueda {

    //private final double num_magico = (N*(Math.pow(N,2) +1)) /2;

    public static int num_magico (Estado ec) {
        int N = ((Estado_cuadrado) ec).matriz.length;

        return (int)(((N*(Math.pow(N,2) + 1))/2));

    }

    public static class Accion_cuadrado extends Accion {
        //la accion es siempre inseertar
        //enum Tipo {No_completar, Completar_fila,Completar_columna, Completar_diago, Completar_fila_col, Completar_fila_diag, Completar_col_diag, Completar_todas}
        enum Tipo {Insertar}
        private int fila;  //en una fila determinada
        private int columna; //en una columna determinada
        private int valor;

        /*public int posicion_fila;
        public int posicion_columna;*/
        //indican en que posicion de la matriz se insertó
        //Se valoraran de 0 a n-1
        Tipo tipo;

        public Accion_cuadrado(Tipo t,int valor,int i, int j) {
            this.fila = i;
            this.valor = valor;
            this.columna = j;
            this.tipo = t;
        }
        @Override
        public String toString() {
            return "insertar el valor "+valor+ " en fila : "+fila+" ,columna : "+columna;
        }

        @Override
        public boolean esAplicable(Estado es) {
            int N = ((Estado_cuadrado) es).matriz.length;

            if ((fila == columna) || (fila == 0 && columna == N-1) || (fila == N-1 && columna == 0)) { //si se inserta en una esquina
                return check_diagonal (es) && check_columna(es) && check_fila (es);
            }
            else  {     //si no no hay que chequear las diagonales
                return check_columna(es) && check_fila(es);
            }
        }



        public boolean check_diagonal (Estado es) {
            int N = ((Estado_cuadrado) es).matriz.length;
            int[][] m = ((Estado_cuadrado) es).matriz;
            int sum = 0;
            int j = 0;
            boolean check_valormin = true;
            int val_blanco = 0;
            boolean diag_llena = true;

             if (fila == columna){//diagonal principal
                 for (int i = 0; i < N; i++) {
                     sum += m[i][i];
                     if (m[i][i] == 0) {
                         diag_llena = false;
                         val_blanco++;
                     }
                 }

             }
             else if (fila+columna== N -1) { //diagonal secundaria
                 for (int i = N-1; i >= 0; i --) {
                     sum+= m[i][j];
                     if (m[i][j] == 0) {
                         diag_llena = false;
                         val_blanco++;
                     }
                     j++;
                 }
             }

             if (val_blanco == 0) ;
             else check_valormin = (((num_magico(es)-sum)/val_blanco) <= (N*N));

            if (diag_llena){
                return (sum == num_magico(es)) ;
            }
            else return (sum <= num_magico(es)) && check_valormin;


        }


        public boolean check_columna (Estado es) {
            int N = ((Estado_cuadrado) es).matriz.length;
            int[][] m = ((Estado_cuadrado) es).matriz;
            int sum = 0;
            boolean columna_llena = true;
            boolean check_valormin = true;
            int val_blanco = 0;

            for (int i = 0; i < N; i++) { //nos movemos en la columna
                sum += m[i][columna];
                if(m[i][columna] == 0) {
                    columna_llena = false;
                    val_blanco++;
                }
            }

            if (val_blanco == 0);
            else check_valormin = (((num_magico(es)-sum)/val_blanco) <= (N*N));

            if (columna_llena){
                return (sum == num_magico(es));
            }
            else return (sum <= num_magico(es)) && (check_valormin);




        }

        public boolean check_fila (Estado es) {
            int N = ((Estado_cuadrado) es).matriz.length;
            int[][] m = ((Estado_cuadrado) es).matriz;
            int sum = 0;
            boolean check_valormin = true;
            int val_blanco = 0;
            boolean fila_llena = true;

            for (int i = 0; i < N; i++) { //nos movemos en la fila
                sum += m[fila][i];
                if(m[fila][i] == 0) {
                    fila_llena = false;
                    val_blanco++;
                }
            }

            if (val_blanco == 0) ;

            else check_valormin = (((num_magico(es)-sum)/val_blanco) <= (N*N));

            if (fila_llena){
                return (sum == num_magico(es)) ;
            }
            else return (sum <= num_magico(es)) && check_valormin;



        }

        @Override
        public Estado aplicaA(Estado es) {
            int N = (((Estado_cuadrado) es).matriz.length);
            int[][] m=  new int[N][N];
            Estado_cuadrado ec = new Estado_cuadrado(m);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    ec.matriz[i][j] = ((Estado_cuadrado) es).matriz[i][j];
                }
            }
            ArrayList<Integer> val_disponibles = new ArrayList<>(); //lista con valores disponibles
            ArrayList<Integer> ya_aplicadas = new ArrayList<>(); //lista que ya se aplicaron y no son validas

            val_disponibles = disponibles(es);


            //Ya tenemos una lista con valores disponibles a insertar
            //Se generara un valor aleatorio de los disponibles

            ec.matriz[fila][columna] = valor;

           /* if (!esAplicable(ec)) {
                 //se elimina el valr ya que no es aplicable
            } */

            return ec;

            /*while(ya_aplicadas.size() != val_disponibles.size()) {

                int valor = (int) (Math.random() * (val_disponibles.size())); //genera un valor de 0 al total de disponibles

                while(true) {
                    if (ya_aplicadas.contains(val_disponibles.get(valor))) {
                        valor = (int) (Math.random() * (val_disponibles.size()));
                    }
                    else break;
                }

                //se modifica el estado
                if (ec.matriz[fila][columna] == 0)
                    ec.matriz[fila][columna] = val_disponibles.get(valor);
                //no se puede insertar ya que no se puede insertar donde ya hay un valor, asi que el estado no varia

                if (esAplicable(ec)) {
                    val_disponibles.remove(valor); //se borra de disponibles
                    break; //se sale del bucle ya que no hace falta buscar otro disponible
                } else {
                    ec.matriz[fila][columna] = 0; //vuelve al estado anterior al no ser aplicable
                    ya_aplicadas.add(val_disponibles.get(valor));
                } //falta una posible lista de ya aplicadas , para que si ya se aplico y se vio que no funcionaba que no se vuelva aplicar
            } */


        }


    }
    public static class Estado_cuadrado extends Estado {
        //private int N;
        public int [] [] matriz ;

        public Estado_cuadrado (int [] [] m) {
            //this.N = n;
            this.matriz = m;

        }


        @Override
        public String toString() {


            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz.length; j++) {
                    System.out.printf(""+matriz[i][j]+" ");
                }
                System.out.println();
            }
            return "";


        }

        @Override
        public boolean equals(Object obj) {
            boolean cond = true;

            if (this == obj) return true;

            if (obj == null ) return false;

            if (this.getClass() != obj.getClass()) return false;

           Estado_cuadrado m2 = (Estado_cuadrado) obj;

           for (int i = 0;  i < this.matriz.length;i++) {
               for (int j = 0; j < this.matriz.length ;j++) {
                   if (this.matriz [i] [j] == m2.matriz [i][j]);
                   else return false;

               }
           }
           return cond;
        }

        @Override
        public int hashCode() {
            int result = matriz [0] [0];

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz.length; j++) {
                    result = 31*result;
                }
            }

            return result;
        }
    }
    private Accion[] listaAcciones;

    public ProblemaCuadradoMagico(Estado estadoInicial) {
        super(estadoInicial);

    }


    @Override
    public boolean esMeta(Estado es) {
       return suma_fila (es) && suma_columna(es) && suma_diagonal (es) && distintos_cero (es) && no_repetidos (es);
    }

    public Accion[] acciones(Estado es){
        //No es necesario generar las acciones dinámicamente a partir del estado porque todas las acciones se pueden
        //aplicar a todos los estados
        int[][] m = ((Estado_cuadrado)es).matriz;
        int N = ((Estado_cuadrado) es).matriz.length;
        ArrayList<Integer> disp ;
        Estado e;

        disp = disponibles(es);
        ArrayList<Accion> a = new ArrayList<>();


            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (m[i][j] == 0) {
                        for (Integer v: disp) {
                            Accion ac = new Accion_cuadrado(Accion_cuadrado.Tipo.Insertar, v, i, j);
                            e = result(es, ac);
                            if (ac.esAplicable(e)) {
                                a.add(ac);
                            }
                        }

                    }
                }
            }


        listaAcciones = new Accion[a.size()];
        for (int j = 0; j < a.size(); j++){
            listaAcciones [j] = a.get(j);
        }
        return listaAcciones;
    }

    public boolean suma_fila (Estado es) {
         int N = ((Estado_cuadrado)es).matriz.length;
         int[][] m = ((Estado_cuadrado)es).matriz;
         int cnt = 0;
         //boolean cond;

         for (int i = 0; i < N; i++) {
             for (int j = 0; j < N; j++) {
                cnt += m [i] [j];
             }
             if (cnt != num_magico(es)) {

                 return false;
             }
             else
                cnt = 0;
         }
         return true;
    }

    public boolean suma_columna (Estado es) {
        int N = ((Estado_cuadrado)es).matriz.length;
        int[][] m = ((Estado_cuadrado)es).matriz;
        int cnt = 0;
        //boolean cond;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cnt += m [j] [i];
            }
            if (cnt != num_magico(es)) {

                return false;
            }
            else
                cnt = 0;
        }
        return true;
    }

    public boolean suma_diagonal (Estado es) {
        int N = ((Estado_cuadrado)es).matriz.length;
        int[][] m = ((Estado_cuadrado)es).matriz;
        int cnt1 = 0;
        int cnt2 = 0;


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) cnt1 += m [i] [j];

                if (i+j == m.length -1) cnt2 += m [i] [j];
            }

        }
        return (cnt1 == num_magico(es)) && (cnt2 == num_magico(es));


    }

    public boolean distintos_cero (Estado es) {
        int N = ((Estado_cuadrado) es).matriz.length;
        int[][] m = ((Estado_cuadrado) es).matriz;
        boolean cond = true;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (m [i] [j] == 0)
                    return false;
            }
        }
        return cond;
    }

    public boolean no_repetidos (Estado es) {
        ArrayList<Integer> numeros_tablero = new ArrayList<>(); //Lista auxiliar para guardar los numeros de la matriz
        int N = ((Estado_cuadrado) es).matriz.length;
        int[][] m = ((Estado_cuadrado) es).matriz;
        boolean cond = true;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (numeros_tablero.contains(m[i][j])) { //si la contiene se acaba ya que seria repetido
                    return false;
                }
                else {
                    numeros_tablero.add(m[i][j]);
                }
            }
        }

        return cond;
    }

    /*public static void main(String[] args) {
        int [][] m = {
                {4,9,2},
                {0,5,0},
                {0,1,0}
        };

        Estado inicial = new Estado_cuadrado(m);
        ProblemaCuadradoMagico c = new ProblemaCuadradoMagico(inicial);

        inicial.toString();

        if (c.esMeta(inicial)) {
            System.out.println("Es metaa");
        }
        else System.out.println("Todavia no es meta");

        Accion_cuadrado a = new Accion_cuadrado(Accion_cuadrado.Tipo.Insertar,1,2);
        Accion_cuadrado a2 = new Accion_cuadrado(Accion_cuadrado.Tipo.Insertar,2,0);
        Accion_cuadrado a3 = new Accion_cuadrado(Accion_cuadrado.Tipo.Insertar,2,2);
        Accion_cuadrado a4 = new Accion_cuadrado(Accion_cuadrado.Tipo.Insertar,1,0);

        Estado nuevo = c.result(inicial,a);
        Estado nuevo2 = c.result(nuevo,a2);
        Estado nuevo3 = c.result(nuevo2, a3);
        Estado nuevo4 = c.result(nuevo3, a4);

        nuevo.toString();
        System.out.println("");
        nuevo2.toString();
        System.out.println();
        nuevo3.toString();
        System.out.println();
        nuevo4.toString();





    }*/
}
