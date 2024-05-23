/*
MARC NAVARRO AMENGUAL & PAU MONSERRAT LLABRÉS
https://youtu.be/xPzX-4npAiA
 */
package jocdel7;

public class Jugador {
    //DECLARACIÓN DE ATRIBUTOS
    //Número de cartas que se repartirán a cada jugador cuando empiece la partida
    private final static int MAX_CARTAS_MANO=13;
    //Cartas que tiene el jugador en cuestión en su mano
    private Carta[] mano = new Carta[MAX_CARTAS_MANO];
    //Variable int que guarda las cartas que le quedan al jugador por tirar
    private int cartasRestantes=MAX_CARTAS_MANO;
    
    //MÉTODO CONSTRUCTOR
    public Jugador(Carta[] dato){
        mano = dato;
    }
    
    //MÉTODOS FUNCIONALES
    //método restarCartasRestantes disminuye en una unidad el número de cartas
    //que tiene el jugador en cuestión
    public int restarCartasRestantes(){
        return cartasRestantes--;
    }
    
    //MÉTODOS GET
    public Carta getMano(int indice){
        return mano[indice];
    }
    
    public int getMAX_CARTAS_MANO(){
        return MAX_CARTAS_MANO;
    }
    
    public int getCartasRestantes(){
        return cartasRestantes;
    }
    
    public int getIndiceCartaEnMano(int num){
        int i=0;
        for(;i<MAX_CARTAS_MANO;i++){
            if(mano[i].getPosicion()==num)break;
        }
        return i;
    }
    
    //MÉTODOS SET
    public void setCarta(int posicion){
        Carta carta;
        if(posicion == 0){
            carta=new Carta(1);
            mano[posicion]=carta;
        }else{
            carta=new Carta(2);
            mano[posicion]=carta;
        }
    }
}
