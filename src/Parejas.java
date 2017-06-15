import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
        
public class Parejas extends JFrame implements ActionListener {
    
    private final boolean debug = false;
    
    private final int columnas = 4;
    
    private final int filas = 5;   
    
    private final ImageIcon[] imagenes;
    
    private final ImageIcon abajo;
    
    private final JLabel Lintentos;
    
    private final JLabel Puntos;
           
    private int ficha[][] = new int[columnas][filas], comprobar = 0, pos1, i1, j1, j2, i2, quedan, intentos = 0;
    
    private final JButton boton[][];
    
    private static final String ruta = "recursos/";    
    
    protected Parejas() {
        imagenes = new ImageIcon[20];
        abajo = new ImageIcon(Parejas.class.getResource(ruta + "carta.png"));   //ficha por defecto
        Lintentos = new JLabel(" Número de intentos: 0 ");
        Puntos = new JLabel("", JLabel.RIGHT);
        for (int i = 0; i < 10; i++)						//carga de imágenes de la misma carpeta nombradas como x.JPG
            imagenes[i] = new ImageIcon(Parejas.class.getResource(
                    ruta + Integer.toString(i) + ".png"));
        JPanel central = new JPanel(new GridLayout(columnas, filas));

        boton = new JButton[columnas][filas];                                   //matriz de botones
        for(int i = 0; i < columnas; i++) {
            for(int j = 0; j < filas; j++) {					//Añadimos Botones al panel principal de botones
               boton[i][j] = new JButton();
               boton[i][j].addActionListener(this);                             //añade el actionlistener al boton
               boton[i][j].setBackground(Color.WHITE);
               central.add(boton[i][j]);
            }
        }
        add(central, "Center");
        JPanel Pun = new JPanel();		 				//Panel que muestra la puntuación máxima
        Pun.setLayout(new GridLayout(1, 2));
        Pun.add(Lintentos);
        Pun.add(Puntos);
        add(Pun, "South");
        ImagenesAleatorias();							//Método que coloca las imágenes aleatoriamente en la matriz
        VerPuntuacion();

        addWindowListener(new WindowAdapter() {                                  //Método para cerrar la ventana
            @Override
            
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        setTitle("Parejas Encadenadas");					// Propiedades de la ventana
        setResizable(false);
        setSize(520,600);
        setIconImage(Toolkit.getDefaultToolkit().createImage(
                Parejas.class.getResource(ruta + "logo.png")));                 // Le ponemos una imágen de icono a la ventana
        setVisible(true);
    }
    
    protected void ImagenesAleatorias() {
        int x, y = 0, numero = -1;
        double x1, y1;

        for (int i = 0; i < columnas; i++)
            for (int j = 0; j < filas; j++)
                ficha[i][j] = -1;                                               //Ponemos matriz de fichas a -1 (no tiene ficha)
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {                                       //Generamos posición aleatoria dentro de la matriz
                do {
                    x1 = Math.random()*columnas;
                    y1 = Math.random()*filas;
                    x = (int)x1;
                    y = (int)y1;
                } while (ficha[x][y] != -1);
                numero++;
                if (numero == 10) 
                    numero = 0;
                ficha[x][y] = numero;
                boton[i][j].setIcon(abajo);
            }	
        }
        
        if (debug) {
            for (int i = 0; i < columnas; i++) {						//Solucion: visualizacion de matriz en consola de java  
                for (int j = 0; j < filas; j++)
                    System.out.print(ficha[i][j] + "     ");
                System.out.println(" ");
            } 
        }
    }
    
}