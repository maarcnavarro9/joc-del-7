/*
MARC NAVARRO AMENGUAL & PAU MONSERRAT LLABRÉS
https://youtu.be/xPzX-4npAiA
 */
package jocdel7;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JocDel7 {
    //ATRIBUTOS
    
    ///////////////////////////////PANELES//////////////////////////////////////
    //declaración JFrame ventana
    private JFrame ventana;
    //declaración panel de contenidos
    private Container panelContenidos;
    //panelVisualizacion encargado de contener el tablero
    private JPanel panelVisualizacion;
    //panelBotones que contendrá todas las interfaces de botones
    private JPanel panelBotones;
    //panel en el que pondremos el panelCartasJugador y el panelSinCartas
    private JPanel panelDeCartas;
    //panel en el que pondremos las cartas que el jugador usuario tiene en la mano
    //durante el juego
    private JPanel panelCartasJugador;
    
    /////////////////////////////////BARAJAS////////////////////////////////////
    //Baraja que mezclaremos y repartiremos a los usuarios
    private Baralla barajaParaMezclar = new Baralla(eventosRaton());
    //Baraja que usaremos durante el juego para ir poniendo las cartas en el tablero
    private Baralla barajaJuego = new Baralla();

    //////////////////////////////COMPONENTES///////////////////////////////////
    //botones del panelBotones
    private JButton turnoJugador;
    private JButton reinicia1;
    private JButton juega;
    private JButton pasar;
    //Etiqueta en la que se indican las cartas que le quedan al jugador usuario
    private JLabel cartasRestantes;
    //Etiquetas que representan las cartas de la IA y las cartas que le quedan
    //en la mano
    private cartaAzul cartasJugador2;
    private cartaAzul cartasJugador3;
    private cartaAzul cartasJugador4;
    //JTextField del panelCentralInferior
    private JTextField zonaTexto;
    
    /////////////////////////////////VARIOS/////////////////////////////////////
    //variable booleana que indica si es el turno del jugador para tirar una carta
    private boolean turnoUsuario = false;
    //Fuente para poner en la JLabel cartasRestantes
    private final Font fuentePrincipal = new Font("ARIAL", Font.BOLD | Font.ITALIC, 30);
    //declaración color RGB del fondo
    private final Color color = new Color(0,102,0);
    //variable booleana verCarta que indica si se visualizan las cartasAzules
    private boolean verCarta=false;
    
    //Declaración de los jugadores que participaran en la partida
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Jugador jugador4;
    //variable que inidica los turnos que ya han pasado de los jugadores máquina
    private int turnosIA = 0;
    //variable que almacena el número de cartas que le restan a los jugadores
    //máquina
    int numeroCartasIA=0;
    
    //Objetos que nos ayudarán a reproducir el sonido
    private File archivowav;
    private Clip clip;
    private AudioInputStream audioInputStream;
    
    
    //método main
    public static void main(String [] args) {
        new JocDel7().metodoPrincipal();
    }
    
    //método metodoPrincipal
    private void metodoPrincipal() {
        //instanciación contenedor JFrame 
        ventana=new JFrame();
        //título contenedor pruebaBotones
        ventana.setTitle("TALLER 2 - PROGRAMACIÓN II - 2021-2022 - UIB");
        //asignación a panelContenidos del panel de contenidos del contenedor
        //JFrame
        panelContenidos=ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());
        //llamada al método inicializacion
        inicializacion();
    }
    
    private void inicializacion() {
        
        
////////////////////////////////////////////////////////////////////////////////
//                CREACIÓN JPanel panelSuperior Y COMPONENTES                 //
////////////////////////////////////////////////////////////////////////////////
        //panel que irá en BorderLayout.NORTH del panelContenidos
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(0,200,0));
        panelSuperior.setBackground(color);
        
        cartasJugador2=new cartaAzul("Cartes/card_back_blue.png");
        panelSuperior.add(cartasJugador2);
        cartasJugador3=new cartaAzul("Cartes/card_back_blue.png");
        panelSuperior.add(cartasJugador3);
        cartasJugador4=new cartaAzul("Cartes/card_back_blue.png");
        panelSuperior.add(cartasJugador4);
        
        panelContenidos.add(panelSuperior, BorderLayout.NORTH);
////////////////////////////////////////////////////////////////////////////////
//                CREACIÓN JPanel panelInferior Y COMPONENTES                 //
////////////////////////////////////////////////////////////////////////////////
        //panel que irá en BorderLayout.SOUTH del panelContenidos
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        
        //Instanciar el panelBotones y ponerle el CardLayout
        panelBotones = new JPanel();
        panelBotones.setLayout(new CardLayout());
        
        ////////////////////Creación panelBotonesIniciales//////////////////////
        JPanel panelBotonesIniciales = new JPanel();
        panelBotonesIniciales.setLayout(new FlowLayout());
        
        //Añadir las componentes JButton al panelBotones
        JButton mezcla = new JButton("MEZCLA");
        mezcla.addActionListener(new GestorEventosAccion());
        panelBotonesIniciales.add(mezcla);
        //Al inicio, los botones JUEGA y REINICIA se deben poner como no
        //seleccionables
        juega = new JButton("JUEGA");
        juega.addActionListener(new GestorEventosAccion());
        juega.setEnabled(false);
        panelBotonesIniciales.add(juega);
        reinicia1 = new JButton("REINICIA");
        reinicia1.addActionListener(new GestorEventosAccion());
        reinicia1.setEnabled(false);
        panelBotonesIniciales.add(reinicia1);
        //Añadir el panelBotonesIniciales al panelBotones
        panelBotones.add(panelBotonesIniciales,"BOTONES_INICIO");
        
        
        /////////////////////Creación panelBotonesJugador///////////////////////
        JPanel panelBotonesJugador = new JPanel(new FlowLayout());
        
        //Añadir las componentes
        pasar = new JButton("PASAR");
        pasar.addActionListener(new GestorEventosAccion());
        panelBotonesJugador.add(pasar);
        JButton reinicia2 = new JButton("REINICIA");
        reinicia2.addActionListener(new GestorEventosAccion());
        panelBotonesJugador.add(reinicia2);
        //Añadir el panelBotonesJugador al panelBotones
        panelBotones.add(panelBotonesJugador,"BOTONES_JUGADOR");
        
        ///////////////////////Creación panelBotonesIA//////////////////////////
        JPanel panelBotonesIA = new JPanel(new FlowLayout());
        
        //Añadir las componentes
        turnoJugador= new JButton("TURNO_JUGADOR");
        turnoJugador.addActionListener(new GestorEventosAccion());
        panelBotonesIA.add(turnoJugador);
        JButton reinicia3 = new JButton("REINICIA");
        reinicia3.addActionListener(new GestorEventosAccion());
        panelBotonesIA.add(reinicia3);
        //Añadir el panelBotonesIA al panelBotones
        panelBotones.add(panelBotonesIA,"BOTONES_IA");
        
        
        //Añadir el panelBotones al panelInferior
        panelInferior.add(panelBotones,BorderLayout.CENTER);
        
        //Añadir JTextField zonaTexto al panelInferior
        zonaTexto = new JTextField();
        zonaTexto.setEditable(false);
        zonaTexto.setBackground(Color.WHITE);
        zonaTexto.setText("Antes de jugar debes mezclar la baraja");
        panelInferior.add(zonaTexto,BorderLayout.SOUTH);
        
        //Finalmente, poner el panelInferior al panelContenidos
        panelContenidos.add(panelInferior, BorderLayout.SOUTH);
       
////////////////////////////////////////////////////////////////////////////////
//        CREACIÓN panelCentral CON panelVisualizacion y panelUsuario         //
////////////////////////////////////////////////////////////////////////////////
        //panelCentral que irá en el BorderLayout.CENTER del panelContenidos
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        
        ///////////////Poner en el panelVisualizacion el tablero////////////////
        panelVisualizacion = new JPanel();
        panelVisualizacion.setLayout(new CardLayout());
        panelVisualizacion.setBackground(color);
        TaulaJoc tablero = new TaulaJoc();
        tablero.tableroOrdenado(barajaParaMezclar);
        panelVisualizacion.add(tablero, "TABLERO");
        panelCentral.add(panelVisualizacion,BorderLayout.CENTER);
        
        
        ///////////JPanel panelUsuario que contendrá el panelDeCartas///////////
        ///////para visualizar el panel inferior con cartas y sin cartas////////
        JPanel panelUsuario = new JPanel();
        panelUsuario.setLayout(new BorderLayout());
        //Instanciar panelDeCartas que mostrará el panelCartasJugador y el
        //panelSinCartas
        panelDeCartas = new JPanel(new CardLayout());
        //panelCartasJugador en el que pondremos las cartas del jugador cuando
        //inicie la partida
        panelCartasJugador=new JPanel();
        panelCartasJugador.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
        panelCartasJugador.setBackground(color);
        //panelSinCartas para poner en panelDeCartas
        JPanel panelSinCartas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSinCartas.setBackground(color);
        Carta cartaVacia = new Carta(1);
        panelSinCartas.add(cartaVacia);
        //Añadimos los paneles creados al panelDeCartas con sus respectivos nombres
        panelDeCartas.add(panelSinCartas,"SIN_CARTAS");
        panelDeCartas.add(panelCartasJugador, "CARTAS");
        //Añadir el panelDeCartas en el panelUsuario
        panelUsuario.add(panelDeCartas,BorderLayout.CENTER);
        
        
        /////////////panelCartasRestantes en el que se pondra las///////////////
        //////////////////cartas que le quedan al usuario///////////////////////
        JPanel panelCartasRestantes=new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCartasRestantes.setBackground(color);
        cartasRestantes = new JLabel("   0");
        cartasRestantes.setFont(fuentePrincipal);
        cartasRestantes.setForeground(Color.WHITE);
        panelCartasRestantes.add(cartasRestantes);
        panelUsuario.add(panelCartasRestantes,BorderLayout.NORTH);
        
        //Añadir el panelUsuario al panelCentral
        panelCentral.add(panelUsuario,BorderLayout.SOUTH);
        
        //Añadir el panelCentral en el panelContenidos
        panelContenidos.add(panelCentral,BorderLayout.CENTER);
        
////////////////////////////////////////////////////////////////////////////////
//                  ÚLTIMAS INTERVENCIONES CONTENEDOR JFrame                  //
////////////////////////////////////////////////////////////////////////////////  
        //DIMENSIONAMIENTO DEL CONTENEDOR JFrame ventana 
        ventana.pack();
        //CENTRADO DEL CONTENEDOR ventana EN EL CENTRO DE LA PANTALLA
        ventana.setLocationRelativeTo(null);
        //ACTIVACIÓN DEL CIERRE INTERACTIVO VENTANA DE WINDOWS EN EL CONTENEDOR 
        //JFrame ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //bloquear su redimensión
        ventana.setResizable(true);
        //VISUALIZACIÓN CONTENEDOR JFrame ventana
        ventana.setVisible(true);
    }
    
    //CLASE GESTOR DE EVENTOS
    private class GestorEventosAccion implements ActionListener{
        //Declaración de un jugador Local muy útil cuando debemos gestionar las
        //tiradas de los jugadores máquina
        Jugador jugadorLocal;
        @Override
        public void actionPerformed(ActionEvent e){
            //Declaración de local's CardLayout para ir mostrando la información
            //a medida que el usuario interactue con el programa
            CardLayout localVisualizacion = (CardLayout)(panelVisualizacion.getLayout());
            CardLayout localBotones = (CardLayout)(panelBotones.getLayout());
            CardLayout localCartas = (CardLayout)(panelDeCartas.getLayout());
            //Declaración de un tablero nuevo para insertar en los paneles
            TaulaJoc tablero;
            
            //switch que gestiona el pulsamiento de los botones
            switch(e.getActionCommand()){
                case "MEZCLA":
                    //Crear un nuevo tablero con las cartas mezcladas y
                    //posteriormente visualizarlo a través del CardLayout
                    tablero = new TaulaJoc();
                    tablero.tableroMezclado(barajaParaMezclar);
                    panelVisualizacion.add(tablero, "TABLERO");
                    localVisualizacion.show(panelVisualizacion, "TABLERO");
                    
                    //ahora que ya se ha mezclado, podemos poner los botones juega
                    //y reinicia como seleccionables
                    juega.setEnabled(true);
                    reinicia1.setEnabled(true);
                    
                    //Actualizar el mensaje de la zona de texto
                    zonaTexto.setText("La baraja ya está mezclada");
                    break;
                    
                case "JUEGA":
                    //Dar las cartas a los jugadores
                    jugador1 = new Jugador(Baralla.repartir(barajaParaMezclar, 1));
                    jugador2 = new Jugador(Baralla.repartir(barajaParaMezclar, 2));
                    jugador3 = new Jugador(Baralla.repartir(barajaParaMezclar, 3));
                    jugador4 = new Jugador(Baralla.repartir(barajaParaMezclar, 4));
                    //Poner las cartas del jugador1 en pantalla
                    localCartas.show(panelDeCartas, "CARTAS");
                    for(int i=0;i<jugador1.getMAX_CARTAS_MANO();i++){
                        panelCartasJugador.add(jugador1.getMano(i));
                    }
                    //Poner el número de cartas restantes pertinente en la etiqueta
                    cartasRestantes.setText(Espacios(jugador1.getCartasRestantes()));
                    
                    //Crear un tablero nuevo con cartas sin foto correspondientes
                    //a las que hay en la barajaJuego
                    tablero = new TaulaJoc();
                    tablero.tableroOrdenado(barajaJuego);
                    panelVisualizacion.add(tablero, "TABLERO");
                    localVisualizacion.show(panelVisualizacion, "TABLERO");
                    
                    //Actualizar el mensaje de la zona de texto
                    zonaTexto.setText("Las cartas están repartidas, es tu turno, "
                            + "pon un 7 si lo tienes");
                    localBotones.show(panelBotones, "BOTONES_JUGADOR");

                    //Ver las cartas azules de los jugadore con el número indicado
                    verCarta=true;
                    numeroCartasIA=13;
                    cartasJugador2.repaint();
                    cartasJugador3.repaint();
                    cartasJugador4.repaint();
                    
                    //poner la variable booleana turno a true
                    turnoUsuario = true;
                    break;
                    
                case "REINICIA":
                    //Volver a instanciar la barajaParaMezclar para que las cartas
                    //salgan ordenadas
                    barajaParaMezclar=new Baralla(eventosRaton());
                    //Volver a instanciar la barajaJuego para que las cartas sean
                    //sin color
                    barajaJuego= new Baralla();
                    //Crear un nuevo tablero con las cartas ordenadas
                    tablero = new TaulaJoc();
                    tablero.tableroOrdenado(barajaParaMezclar);
                    panelVisualizacion.add(tablero, "TABLERO");
                    localVisualizacion.show(panelVisualizacion, "TABLERO");
                    
                    //Actualizar la habilitación de los botones para que esten
                    //como si el juego hubiera empezado de 0
                    juega.setEnabled(false);
                    reinicia1.setEnabled(false);
                    turnoJugador.setEnabled(true);
                    pasar.setEnabled(true);
                    //Mostrar los botonesInicio
                    localBotones.show(panelBotones, "BOTONES_INICIO");
                    
                    //Actualizar el mensaje de la zona de texto
                    zonaTexto.setText("Antes de jugar debes mezclar la baraja");
                    
                    //Quitar las cartas que hay en el panelCartasJugador y enseñar
                    //el panelSinCartas
                    panelCartasJugador.removeAll();
                    localCartas.show(panelDeCartas, "SIN_CARTAS");
                    cartasRestantes.setText("   0");
                    
                    //Restablecer las cartasAzules como si del inicio se tratara
                    verCarta=false;
                    numeroCartasIA=0;
                    cartasJugador2.repaint();
                    cartasJugador3.repaint();
                    cartasJugador4.repaint();
                    
                    //Volver a poner la variable turnoUsuario a false
                    turnoUsuario = false;
                    break;
                    
                case "PASAR":
                    //actualizar zona de texto
                    zonaTexto.setText("El Jugador1 pasa");
                    //mostrar los botonesIA
                    localBotones.show(panelBotones,"BOTONES_IA");
                    //se ha terminado el turno del usuario
                    turnoUsuario = false;
                    break;
                    
                case "TURNO_JUGADOR":
                    //Incrementar en uno la variable de turnos
                    turnosIA+=1;
                    //switch que según el valor de la variable turnosIA hace la 
                    //tirada de un jugador máquina u otro.
                    switch(turnosIA){
                        case 1:
                            jugador2=tirarCarta(jugador2);
                            //Actualizar la cartaAzul asociada al jugador
                            numeroCartasIA=jugador2.getCartasRestantes();
                            cartasJugador2.repaint();
                            break;
                        case 2:
                            jugador3=tirarCarta(jugador3);
                            //Actualizar la cartaAzul asociada al jugador
                            numeroCartasIA=jugador3.getCartasRestantes();
                            cartasJugador3.repaint();
                            break;
                        case 3:
                            jugador4=tirarCarta(jugador4);
                            //Actualizar la cartaAzul asociada al jugador
                            numeroCartasIA=jugador4.getCartasRestantes();
                            cartasJugador4.repaint();
                            break;
                    }
                    //Si los turnos son igual a tres quiere decir que todos los
                    //jugadoers máquina han tirado
                    if(turnosIA==3){
                        //reiniciar contador
                        turnosIA=0;
                        //mostrar los botones del jugador usuario
                        localBotones.show(panelBotones,"BOTONES_JUGADOR");
                        //En caso de que el jugador4 haya ganado, impedir que
                        //el usuario pueda seguir tirando cartas.
                        if(jugador4.getCartasRestantes()==0){
                            turnoUsuario=false;
                        }else{
                            turnoUsuario=true;
                        }
                        
                    }
                    break;
            }
        }
        
        //Método que devuelve el jugador que se le ha pasado por parámetro
        //modificado
        private Jugador tirarCarta(Jugador jugador){
            //DECLARACIONES
            //Instanciación del jugadorLocal
            jugadorLocal = jugador;
            int posicionCarta;
            int cartaVecina;
            int indiceJugador=0;
            int indiceBaraja=0;
            boolean yaTirada=false;
            
            //ACCIONES
            //Bucle que recorre todas las cartas de la mano del jugador y se rompe
            //al haber tratado todas las cartas o en caso de que el jugador ya 
            //haya tirado una carta
            for(;indiceJugador<jugadorLocal.getMAX_CARTAS_MANO()&&!yaTirada;indiceJugador++){
                //Se debe comprobar cual ha sido la carta que ha tirado el jugador
                //mirando que carta de la barajaParaMezclar es igual a la que tiene
                for(;indiceBaraja<barajaParaMezclar.getNUM_CARTAS();indiceBaraja++){
                    if(jugadorLocal.getMano(indiceJugador)==barajaParaMezclar.getCarta(indiceBaraja))break;
                }
                //Una vez encontrada, obtener su posición en la baraja si estas
                //estuviesen ordenadas
                posicionCarta=barajaParaMezclar.getCarta(indiceBaraja).getPosicion();
                
                //Switch que comprueba si la carta elegida se puede lanzar. Cada
                //vez comprobamos si el jugador ya ha tirado la carta mediante
                //una eliminación lógica de las cartas que tiene en la mano
                switch(posicionCarta){
                        //Si la carta es un 7, siempre se podrá lanzar
                        case 6,19,32,45:
                            if(jugadorLocal.getMano(indiceJugador).getCartaTirada()==false){
                                //Indicar en las cartas de la baraja que ya se ha tirado
                                barajaParaMezclar.getCarta(indiceBaraja).setCartaTirada(true);
                                //Llamada al método que gestiona la tirada
                                gestionarTiradaIA(indiceBaraja,posicionCarta,indiceJugador);
                                //El jugador ya ha lanzado una carta
                                yaTirada = true;
                            }
                            break;
                        
                        //Si la posición de la carta resulta ser uno de esto números,
                        //indica que se debe mirar a la carta que hay a su derecha
                        //para saber si se puede tirar.
                        case 0,1,2,3,4,5,13,14,15,16,17,18,26,27,28,29,30,31,39,40,41,42,43,44:
                            //Apuntador que señala a la carta que hay inmediatamente
                            //a la derecha
                            cartaVecina = posicionCarta + 1;
                            //Además de comprovar si el usuario tiene la carta en
                            //su mano, se comprueba si la carta que se encuentra
                            //a su derecha ya ha sido tirada
                            if(barajaParaMezclar.getCartaTiradaPosicion(cartaVecina)==true&&
                            jugadorLocal.getMano(indiceJugador).getCartaTirada()==false){
                                //Llamada al método que gestiona la tirada
                                gestionarTiradaIA(indiceBaraja,posicionCarta,indiceJugador);
                                //El jugador ya ha lanzado una carta
                                yaTirada= true;
                            }
                            break;
                        
                        //Si no cumple ninguna condición anterior, significa que
                        //debemos mirar a las cartas de su izquierda
                        default:
                            //Mismo algoritmo que en el caso anterior
                            cartaVecina = posicionCarta - 1;
                            if(barajaParaMezclar.getCartaTiradaPosicion(cartaVecina)==true&&
                            jugadorLocal.getMano(indiceJugador).getCartaTirada()==false){
                                //Llamada al método que gestiona la tirada
                                gestionarTiradaIA(indiceBaraja,posicionCarta,indiceJugador);
                                //El jugador ya ha lanzado una carta
                                yaTirada= true;
                            }
                            break;
                }
                //Si no ha lanzado ninguna carta, quiere decir que no podía tirar
                //ninguna y por tanto ha pasado de turno
                if(!yaTirada){
                    zonaTexto.setText("El jugador"+(turnosIA+1)+" pasa");
                }
            }
            return jugadorLocal;
        }
        
        //Método que gestiona las tiradas de la máquina
        private void gestionarTiradaIA(int j,int posicionCarta,int indiceJugador){
            //DECLARACIONES
            CardLayout localVisualizacion = (CardLayout)(panelVisualizacion.getLayout());
            TaulaJoc tablero;
            
            //ACCIONES
            //En primer lugar, indicamos que la carta ha sido tirada para proceder
            //a su eliminación lógica
            jugadorLocal.getMano(indiceJugador).setCartaTirada(true);
            //Indicar en las cartas de la baraja que ya se ha tirado
            barajaParaMezclar.getCarta(j).setCartaTirada(true);
            //Poner en la barajaJuego la carta que ha tirado el jugador máquina
            barajaJuego.setCarta(posicionCarta,barajaParaMezclar.getCartaSegunPosicion(posicionCarta));
            //Restar el número de cartas que tiene en la mano el jugador máquina
            jugadorLocal.restarCartasRestantes();
            
            //Volver a crear un tablero para visualizarlo con la baraja actualizada
            tablero = new TaulaJoc();
            tablero.tableroOrdenado(barajaJuego);
            panelVisualizacion.add(tablero, "TABLERO");
            localVisualizacion.show(panelVisualizacion, "TABLERO");
            
            //Actualizar la zona de texto
            zonaTexto.setText("El jugador"+(turnosIA+1)+" pone el "+barajaParaMezclar.getNombreCarta(posicionCarta));
            
            //Si el jugador máquina se queda sin cartas, enseñar el JOptionPane
            //ya que significa que ha ganado
            if(jugadorLocal.getCartasRestantes()==0){
                    //Obtener la imagen correspondiente al jugador máquina
                    ImageIcon imagen = new ImageIcon("Cartes/Jug"+turnosIA+"Riu.png");
                    JOptionPane.showMessageDialog(panelVisualizacion,"HA GANADO EL jugador"+(turnosIA+1),
                            "                         HAS PERDIDO..."
                            , 1, imagen);
                    //Deshabilitar los botones para que solo se pueda pulsar el 
                    //reiniciar
                    turnoJugador.setEnabled(false);
                    pasar.setEnabled(false);
                    //Actualizar la zona de texto
                    zonaTexto.setText("Simulación acabada");
                }
        }
    }
    
    public MouseListener eventosRaton() {
        MouseListener accion=new MouseListener(){
            @Override
            public void mousePressed(MouseEvent evento) {
                //DECLARACIONES
                int numeroComponente=0;
                int posicionCarta;
                int cartaVecina;
                archivowav = new File("Sonido.wav");
                
                //ACCIONES
                //si el usuario pulsa una carta cuando es su turno, empieza el
                //procesamiento de la carta pulsada
                if(turnoUsuario){
                    //primero, se descubre que carta es la que se ha tirado dentro
                    //de la baraja
                    for (;numeroComponente<barajaParaMezclar.getNUM_CARTAS();numeroComponente++) {
                        if (evento.getSource()==barajaParaMezclar.getCarta(numeroComponente)) 
                            break;
                    }
                    //Una vez se sabe la carta, se descubre cual es su posición
                    //en un baraja ordenada
                    posicionCarta=barajaParaMezclar.getCarta(numeroComponente).getPosicion();
                    
                    //Este switch funciona de la misma manera que el switch de los
                    //jugadores máquinas. Pero en este caso, no se lleva a cabo
                    //una eliminación lógica de las cartas de la mano del jugador
                    switch(posicionCarta){
                        case 6,19,32,45:
                            gestionarTiradaJugador(numeroComponente,posicionCarta);
                            break;
                            
                        case 0,1,2,3,4,5,13,14,15,16,17,18,26,27,28,29,30,31,39,40,41,42,43,44:
                            cartaVecina = posicionCarta + 1;
                            if(barajaParaMezclar.getCartaTiradaPosicion(cartaVecina)==true){
                                gestionarTiradaJugador(numeroComponente,posicionCarta);
                            }else{
                                play();
                            }
                            break;
                            
                        default:
                            cartaVecina = posicionCarta - 1;
                            if(barajaParaMezclar.getCartaTiradaPosicion(cartaVecina)==true){
                                gestionarTiradaJugador(numeroComponente,posicionCarta);
                            }else{
                                play();
                            }
                            break;
                    }
                }
            }
            
            private void gestionarTiradaJugador(int numeroComponente,int posicionCarta){
                //DECLARACIONES
                CardLayout localVisualizacion = (CardLayout)(panelVisualizacion.getLayout());
                CardLayout localBotones = (CardLayout)(panelBotones.getLayout());
                CardLayout localCartas = (CardLayout)(panelDeCartas.getLayout());
                TaulaJoc tablero;

                //ACCIONES
                //En primer lugar, se restan las cartas restantes del jugador.
                //Si se queda sin cartas, quiere decir que ha ganado y se muestra
                //por pantalla un ventana de dialogo
                jugador1.restarCartasRestantes();
                cartasRestantes.setText(Espacios(jugador1.getCartasRestantes()));
                if(jugador1.getCartasRestantes()==0){
                    ImageIcon imagen = new ImageIcon("Cartes/Jug0Riu.png");
                    JOptionPane.showMessageDialog(panelVisualizacion,"     ¡¡¡HAS GANADO!!!",
                            "                         ¡ENHORABUENA!"
                            , 1, imagen);
                    turnoJugador.setEnabled(false);
                    zonaTexto.setText("Simulación acabada");
                }
                
                //Actualizar la mano del jugador, llevando a cabo la eliminación
                //física de la carta que se ha tirado(se reemplaza la carta tirada
                //por una carta sin foto y sin mouseListener)
                jugador1.setCarta(jugador1.getIndiceCartaEnMano(posicionCarta));
                //Se instancia de nuevo el panelCartasJugador para actualizar
                //las cartas que hay en la mano del jugador
                panelCartasJugador=new JPanel();
                panelCartasJugador.setBackground(color);
                panelCartasJugador.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
                for(int i=0;i<jugador1.getMAX_CARTAS_MANO();i++){
                    panelCartasJugador.add(jugador1.getMano(i));
                }
                panelDeCartas.add(panelCartasJugador,"CARTAS");
                localCartas.show(panelDeCartas,"CARTAS");
                
                //Indicar en la baraja que la carta ya se ha tirado
                barajaParaMezclar.getCarta(numeroComponente).setCartaTirada(true);
                //Copiar la carta tirada en la baraja de juego
                barajaJuego.setCarta(posicionCarta,barajaParaMezclar.getCartaSegunPosicion(posicionCarta));
                //Actualizar el tablero con la baraja de juego actualizada
                tablero = new TaulaJoc();
                tablero.tableroOrdenado(barajaJuego);
                panelVisualizacion.add(tablero, "TABLERO");
                localVisualizacion.show(panelVisualizacion, "TABLERO");
                localBotones.show(panelBotones,"BOTONES_IA");
                
                //El turno del usuario ha finalizado
                turnoUsuario = false;
                //Actualizar la zona de texto
                zonaTexto.setText("Has tirado el "+barajaParaMezclar.getNombreCarta(posicionCarta));
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}   
        };
        return accion;
    }
    
    //Metodo para reproducir un archivo de audio 
    private void play(){
        try {
            audioInputStream = AudioSystem.getAudioInputStream(archivowav);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException| UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
        }
    }
    
    //Método que pone espacios en un int que se le pasa y lo devuelve como string
    //Útil para centrar el número que hay en panelCartasRestantes
    private String Espacios(int k){
        if(k>=10){
            return "   "+k;
        }
        return "    "+k;
    }
    
    private class cartaAzul extends JLabel {
        private BufferedImage imagen=null;

        public cartaAzul(String nombre) {
            try {
                imagen = ImageIO.read(new File(nombre));
            } catch (IOException e) {
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            int DIMENSION_X = 500*2/15;
            int DIMENSION_Y = 726*2/15;  
            Graphics2D g2 = (Graphics2D) g;
            Color color = new Color(0,82,0);
            
            if (verCarta) {
            g2.drawImage(imagen, 20, 20, DIMENSION_X, DIMENSION_Y, null);
            g2.setColor(Color.WHITE);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("ARIAL", Font.BOLD | Font.ITALIC, 55));
            if(numeroCartasIA<=9) {
                g2.drawString(Integer.toString(numeroCartasIA),DIMENSION_X-30,DIMENSION_Y-10);
            }else {
                g2.drawString(Integer.toString(numeroCartasIA),DIMENSION_X-47,DIMENSION_Y-10);
            }
            }
            else  {
                Rectangle2D rectangulo=new Rectangle2D.Float(20,20,500*2/15,726*2/15);
                g2.setColor(color);
                //dibujo del objeto rectangulo aplicando el clipping definido
                g2.fill(rectangulo);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("ARIAL", Font.BOLD | Font.ITALIC, 55));
                g2.drawString("0",DIMENSION_X-30,DIMENSION_Y-10);
            }
        }
        //El contenedor JPanel decide su tamaño en función de la dimensión de
        //imagen
        @Override
        public Dimension getPreferredSize() {
            if (imagen == null) {
                return new Dimension(200, 200);
            } else {
                return new Dimension(imagen.getWidth()*2/15, imagen.getHeight()*2/15);
            }
        }
    }
}