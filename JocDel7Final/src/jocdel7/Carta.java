/*
MARC NAVARRO AMENGUAL & PAU MONSERRAT LLABRÉS
https://youtu.be/xPzX-4npAiA
 */
package jocdel7;
        
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Carta extends JLabel {
    //DECLARACIÓN DE ATRIBUTOS
    private static final int dimX=500;
    private static final int dimY=726;
    private int posicion = 0;
    //variable booleana para llevar a término la eliminacion lógica
    private boolean cartaTirada = false;
    //verde oscuro
    private Color color1 = new Color(0,82,0);
    //verde claro
    private Color color2 = new Color(0,102,0);
    
    //MÉTODO CONSTRUCTOR para abrir imagen
    public Carta(ImageIcon imagen) {
        //redimensionar imagen dada por parametro mediante el método
        //redimensionarImagenEtiqueta
        setIcon(redimensionarImagenEtiqueta(imagen));
        setBackground(color1);
        //establecer alineación de la imagen
        setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        setOpaque(true);
    }
    
    //MÉTODO CONSTRUCTOR para crear etiqueta del tamaño de la carta con el color
    //pasado por parámetro
    public Carta(int tipoColor) {
        //verificar de qué color se trata 
        if(tipoColor==1){
            setBackground(color1);
        }else{
            setBackground(color2);
        }
        setOpaque(true);
        //establecer dimensión
        setPreferredSize(new Dimension(dimX*2/15,dimY*2/15));
    }
    
    //redimensionamiento de una imagen en base a las dimensiones de una etiqueta
    private ImageIcon redimensionarImagenEtiqueta(ImageIcon imagen) {
        Image imgEscalada = imagen.getImage();
        //REDIMENSIONAMIENTO CARTAS DE LA BARAJAS            
        imgEscalada = imgEscalada.getScaledInstance(imagen.getIconWidth()*2/15
                ,imagen.getIconHeight()*2/15,java.awt.Image.SCALE_SMOOTH);
        imagen.setImage(imgEscalada);                    
        return new ImageIcon(imgEscalada); 
    } 
    
    //MÉTODOS FUNCIONALES
    //MÉTODOS GET
    public int getPosicion(){
        return posicion;
    }
    
    public boolean getCartaTirada(){
        return cartaTirada;
    }
    
    //MÉTODOS SET
    public void setPosicion(int dato){
        posicion = dato;
    }
    
    public void setCartaTirada(boolean dato){
        cartaTirada = dato;
    }
}
