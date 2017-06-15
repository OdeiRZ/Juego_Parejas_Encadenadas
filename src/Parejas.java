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
   
}