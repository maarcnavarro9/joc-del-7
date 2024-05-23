/*
MARC NAVARRO AMENGUAL & PAU MONSERRAT LLABRÉS
https://youtu.be/xPzX-4npAiA
 */
package jocdel7;

import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;

public class Baralla {
    //DECLARACIÓN DE ATRIBUTOS
    private Carta [] cartas;
    private static String [] nombresCartas={"clubs","diamonds","hearts","spades"};
    private static final int NUM_CARTAS = 52;
    
    //MÉTODO CONSTRUCTOR con parámetro MouseListener
    public Baralla(MouseListener dato) {
        cartas=new Carta[NUM_CARTAS];
        inicializar(dato);
    }
    
    //MÉTODO CONSTRUCTOR sin parámetros
    public Baralla(){
        cartas=new Carta[NUM_CARTAS];
        for(int i=0;i<NUM_CARTAS;i++) {
            cartas[i] = new Carta(1);
        }
    }
    
    //MÉTODOS FUNCIONALES
    
    //Método inicializar lleva a cabo la inicialización de la baraja con las
    //cartas correspondientes 
    private void inicializar(MouseListener dato){
        for (int i=0,j=1,k=0;i<cartas.length;i++,j++) {
            
            ImageIcon imagen=new ImageIcon("Cartes/"+j+"_of_"+nombresCartas[k]+".png");
            cartas[i]=new Carta(imagen);
            cartas[i].addMouseListener(dato);
            cartas[i].setPosicion(i);
            
            if(i==12||i==25||i==38) {
                j=0;
                k++;
            }
        }
    }
    
    //Método que mezcla la baraja de cartas
    public void mezclar(){
        //DECLARACIÓN VARIABLES LOCALES
        //Variable temporal que nos sirve para guardar una carta.
        Carta tmp;
        //Clase Random para generar números aleatorios
        Random r = new Random();
        //Variable que guarda el número aleatorio dado
        int rndm;
        
        //ACCIONES
        //Bucle que gestionará la mezcla de la baraja
        for(int k=0;k<NUM_CARTAS;k++){
            //se genera un número aleatorio entre 0 i NUM_CARTAS-1
            rndm = r.nextInt(NUM_CARTAS-1);
            //se guarda la carta a poner en la posición k del array en tmp
            tmp = cartas[rndm];
            //se mueve la carta que se encuentra en k del array a la
            //posición de la carta obtenida aleatoriamente
            cartas[rndm] = cartas[k];
            //se pone la carta obtenida aleatoriamente en la posición k
            cartas[k]=tmp;
        }
    }
    
    //Método que reparte las cartas mezcladas entre los jugadores
    public static Carta[] repartir(Baralla dato,int numJugador){
        //DECLARACIONES
        //Variable local para almacenar el array que se devolvera
        Carta[] cartasRepartidas = new Carta[13];
        
        //ACCIONES
        //Con un switch se da las cartas correspondientes segun el número del 
        //jugador indicado por el parámetro numJugador
        switch(numJugador){
            case 1:
                for(int i=0;i<cartasRepartidas.length;i++){
                    cartasRepartidas[i]=dato.getCarta(i);
                }
                break;
            case 2:
                for(int i=0,j=13;i<cartasRepartidas.length;i++,j++){
                    cartasRepartidas[i]=dato.getCarta(j);
                }
                break;
            case 3:
                for(int i=0,j=26;i<cartasRepartidas.length;i++,j++){
                    cartasRepartidas[i]=dato.getCarta(j);
                }
                break;
            case 4:
                for(int i=0,j=39;i<cartasRepartidas.length;i++,j++){
                    cartasRepartidas[i]=dato.getCarta(j);
                }
                break;
        }
        return cartasRepartidas;
    }
    
    //MÉTODOS GET
    public Carta getCarta(int num){
        return cartas[num];
    }
    
    public int getNUM_CARTAS(){
        return NUM_CARTAS;
    }
    
    public boolean getCartaTiradaPosicion(int dato){
        int i=0;
        for(;i<NUM_CARTAS;i++){
            if(cartas[i].getPosicion()==dato){
                break;
            }
        }
        return cartas[i].getCartaTirada();
    }
    
    public Carta getCartaSegunPosicion(int dato){
        int i=0;
        for(;i<NUM_CARTAS;i++){
            if(cartas[i].getPosicion()==dato){
                break;
            }
        }
        return cartas[i];
    }
    
    public String getNombreCarta(int posicion){
        if(posicion <= 12){
            return "["+info(posicion+1)+" clubs]";
        }else if(posicion <= 25){
            return "["+info(posicion-12)+" diamonds]";
        }else if(posicion <= 38){
            return "["+info(posicion-25)+" hearts]";
        }
        return "["+info(posicion-38)+" spades]";
    }
    
    //Método que devuelve un string para poner en las JLabels del panelInformativo
    private String info(int dato){
        //variable
        int k = dato;
        if(k<10){
            return "0"+k;
        }
        return Integer.toString(k);
    }
    
    //MÉTODOS SET
    public void setCarta(int num,Carta carta){
        cartas[num]=carta;
    }
}
