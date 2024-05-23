/*
MARC NAVARRO AMENGUAL & PAU MONSERRAT LLABRÉS
https://youtu.be/xPzX-4npAiA
 */
package jocdel7;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class TaulaJoc extends JPanel {
    //DECLARACIÓN DE ATRIBUTOS
    private static final int FILAS=4,COLUMNAS=13;
    private static final int DIMENSION=FILAS*COLUMNAS;
    private final Color color = new Color(0,102,0);
    
    //Método constructor sin parámetros crea un tablero del tamaño correspondiente
    //con una pequeña separación
    public TaulaJoc() {
        super();
        setLayout(new GridLayout(FILAS,COLUMNAS,5,5));
        setBackground(color);
    }
    
    //Método tableroOrdenado visualiza las cartas de forma ordenada
    public void tableroOrdenado(Baralla baraja) {
        for(int i=0;i<DIMENSION;i++){
            add(baraja.getCarta(i));
        }
    }
    
    //Método tableroMezclado lleva a cabo la visualización de las cartas mezcladas
    public void tableroMezclado(Baralla baraja){
        baraja.mezclar();
        for(int i=0;i<DIMENSION;i++){
            add(baraja.getCarta(i));
        }
    }
}
