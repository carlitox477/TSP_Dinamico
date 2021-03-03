package EstructurasCatedra;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Camino {

    private int distancia;
    private Lista listaDeNodos;

    public Camino() {
        distancia = 0;
        listaDeNodos = new Lista();
    }

    public Camino(Ciudad c1, Ciudad c2, int d){
        listaDeNodos=new Lista();
        listaDeNodos.insertar(c1, listaDeNodos.longitud());
        listaDeNodos.insertar(c2, listaDeNodos.longitud());
        distancia=d;
    }

    public Camino(Lista listaDeNodos, int dist) {
        this.listaDeNodos = listaDeNodos;
        this.distancia = dist;
    }

    public boolean agregarCiudad(Ciudad c, int d){
        boolean exito=false;
        if(this.listaDeNodos!=null){
            exito=true;
            this.listaDeNodos.insertar(c, 1);
            this.distancia+=d;
        }
    return exito;
    }
    
    public boolean esVacio() {
        return listaDeNodos.esVacia();
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int dis) {
        this.distancia = dis;
    }

    public Lista getListaDeNodos() {
        return listaDeNodos;
    }

    public void setListaDeNodos(Lista listaDeNodos) {
        this.listaDeNodos = listaDeNodos;
    }

    public int get_Cant_De_Nodos() {
        return this.listaDeNodos.longitud();
    }

    @Override
    public String toString() {
        String cadena = "";
        Ciudad ciudad;
        for (int i = 1; i <= listaDeNodos.longitud(); i++) {
            ciudad = (Ciudad) listaDeNodos.recuperar(i);
            cadena += ciudad.getCiudad() + " -> ";
        }

        cadena=cadena.substring(0,cadena.length()-4);
        cadena += " con una distancia total de " + distancia + " km.";

        return cadena;
    }

    public Camino clone(){
        return new Camino(this.listaDeNodos.clone(),this.distancia);
    }
}