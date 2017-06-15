import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
        
public class Parejas extends JFrame implements ActionListener {
    /**
     * Variable usada para activar o no el debug con la solución del juego.
     */
    private final boolean debug = false;
    
    /**
     * Variable usada para almacenar la cantidad de columnas de imágenes usadas.
     */
    private final int columnas = 4;
    
    /**
     * Variable usada para almacenar la cantidad de filas de imágenes usadas.
     */
    private final int filas = 5;   
    
    /**
     * Variable usada para almacenar el contador de clicks realizados.
     */
    private int contadorClick;
    
    /**
     * Variables usadas para almacenar valores auxiliares de comparación.
     */
    private int pos1, i1, j1, j2, i2;

    /**
     * Variable usada para almacenar el contador de fichas descubiertas.
     */
    private int contadorFichas;
    
    /**
     * Variable usada para almacenar los intentos realizados por el jugador.
     */
    private int intentos;
    
    /**
     * Variable matriz usada para almacenar las posiciones de las fichas.
     */
    private final int fichas[][];
       
    /**
     * Variable usada para almacenar la imagen de la carta boca abajo.
     */
    private final ImageIcon carta;
    
    /**
     * Variable array usada para almacenar las imágenes usadas en el juego.
     */
    private final ImageIcon[] imagenes;
    
    /**
     * Variable etiqueta usada para almacenar los intentos del actual juego.
     */
    private final JLabel intentosLbl;
    
    /**
     * Variable etiqueta usada para almacenar los puntos del record de juego.
     */
    private final JLabel puntos;
    
    /**
     * Variable matriz usada para almacenar los botones de juego.
     */
    private final JButton botones[][];
    
    /**
     * Variable de tipo cadena usada para almacenar la Ruta del directorio 
     * donde estan almacenados los ficheros que usaremos durante el Programa.
     */
    private final String ruta = "recursos/";    
    
    /**
     * Constructor principal de la clase Parejas.
     * Inicializa las variables y diferentes elementos multimedia utilizados 
     * durante la ejecución de la aplicación.
     */
    protected Parejas() {
        imagenes = new ImageIcon[20];
        intentosLbl = new JLabel(" Número de intentos: 0 ");
        carta = new ImageIcon(Parejas.class.getResource(ruta + "carta.png"));   //Ficha por defecto
        puntos = new JLabel("", JLabel.RIGHT);
        fichas = new int[columnas][filas];
        intentos = 0;
        contadorClick = 0;
        for (int i = 0; i < 10; i++) {						//Cargamos las imágenes utilizadas en el juego
            imagenes[i] = new ImageIcon(Parejas.class.getResource(
                    ruta + Integer.toString(i) + ".png"));
        }
        JPanel central = new JPanel(new GridLayout(columnas, filas));
        botones = new JButton[columnas][filas];                                 //Creamos matriz de botones
        for(int i = 0; i < columnas; i++) {
            for(int j = 0; j < filas; j++) {					//Añadimos Botones al panel principal
               botones[i][j] = new JButton();
               botones[i][j].addActionListener(this);                           //Añadimos el actionlistener a los botones
               botones[i][j].setBackground(Color.WHITE);
               central.add(botones[i][j]);
            }
        }
        add(central, "Center");
        JPanel Pun = new JPanel();		 				//Panel que muestra la puntuación máxima
        Pun.setLayout(new GridLayout(1, 2));
        Pun.add(intentosLbl);
        Pun.add(puntos);
        add(Pun, "South");
        ImagenesAleatorias();							//Método que coloca las imágenes aleatoriamente en la matriz
        VerPuntuacion();

        addWindowListener(new WindowAdapter() {
            @Override
            /**
             * Método usado para capturar el evento de cierre de ventana.
             * @param we WindowEvent: evento lanzado
             */
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        setTitle("Parejas Encadenadas");					//Propiedades de la ventana
        setResizable(false);
        setSize(520,600);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().createImage(
                Parejas.class.getResource(ruta + "logo.png")));                 //Le ponemos una imágen de icono a la ventana
        setVisible(true);
    }
    
    /**
     * Método usado para cargar aleatoriamente las imágenes en la interfaz.
     * Generamos una matriz de números que asignamos a las variables oportunas.
     */
    protected void ImagenesAleatorias() {
        int x, y = 0, numero = -1;
        double x1, y1;

        for (int i = 0; i < columnas; i++)
            for (int j = 0; j < filas; j++)
                fichas[i][j] = -1;                                              //Iniciamos matriz de fichas a -1 (no tiene fichas)
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {                                   //Generamos posición aleatoria dentro de la matriz
                do {
                    x1 = Math.random()*columnas;
                    y1 = Math.random()*filas;
                    x = (int)x1;
                    y = (int)y1;
                } while (fichas[x][y] != -1);
                numero++;
                if (numero == 10) 
                    numero = 0;
                fichas[x][y] = numero;
                botones[i][j].setIcon(carta);
            }	
        }
        
        if (debug) {
            for (int i = 0; i < columnas; i++) {				//Visualizamos matriz de solución en consola
                for (int j = 0; j < filas; j++)
                    System.out.print(fichas[i][j] + "     ");
                System.out.println(" ");
            } 
        }
    }
    
    @Override
    /**
     * Método usado para capturar los eventos de acción producidos.
     * @param ae ActionEvent: evento lanzado
     */
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {	
                if (ae.getSource() == botones[i][j]) {
                    if (botones[i][j].getIcon().equals(carta)) {
                        contadorClick++;                                        //Cuando la pulsación no es la tercera, mostramos la carta en esa posición
                        if (contadorClick != 3) 
                            botones[i][j].setIcon(imagenes[fichas[i][j]]);
                        if (contadorClick == 1) {                               //Guardamos las propiedades de la 1º carta 
                            pos1 = fichas[i][j];                                //guardamos la fichas que se encuentra en esa posición
                            i1 = i;                                             //guardamos coordenada i de la ficha
                            j1 = j;                                             //guardamos coordenada j de la ficha
                            intentos++;                                         //aumentamos contador de número de intentos para establecer record
                        }
                        if (contadorClick == 2) {                               //Guardamos las propiedades de la 2º carta
                            if (pos1 == fichas[i][j]) {                         //Si las fichas coinciden
                                contadorFichas++;                               //Aimentamos contador de fichas que han salido
                                contadorClick = 0;                              //Ponemos a 0 la pulsación
                                intentos++;                                     //aumentamos contador del número de intentos
                            } else {                                            //Si fallamos el intento
                                i2 = i;
                                j2 = j;
                            }
                        }
                        if (contadorClick == 3) {                               //El 3º Click, al ser distintas vuelve a ocultar las fichas
                            botones[i1][j1].setIcon(carta);
                            botones[i2][j2].setIcon(carta);	
                            contadorClick=0;	      		    	 	
                        }
                    }
                }
            }
        }
        intentosLbl.setText(" Número de intentos: " + intentos + "	");     //Cargamos número de intentos actuales
        
        if (contadorFichas == 10) {                                             //Si hemos acabao el juego
            JOptionPane.showMessageDialog(this, "nümero de Intentos: " + 
                intentos,"¡Has ganado!",JOptionPane.INFORMATION_MESSAGE, carta);
            Puntuacion();
            VerPuntuacion();
            contadorFichas = 0;
            intentos = 0;
            ImagenesAleatorias();
            intentosLbl.setText(" Número de intentos: " + intentos );
        } 
    }
    
    /**
     * Método usado para consultar el record actual almacenado.
     * Se lee el fichero record y se cargan sus datos en la interfaz.
     */
    protected void VerPuntuacion() {
        String record = "", nom = "";
        try {
            try (FileReader pts = new FileReader("src/" + ruta + "record.txt")){
                BufferedReader leer = new BufferedReader(pts);
                record = leer.readLine();
                nom = leer.readLine();
            }
         } catch (IOException ioe) { }
         puntos.setText(nom + ": " + record + " ");
    }
    
    /**
     * Método usado para sobreescribir el record actual almacenado.
     * Si se produce un record, obtenemos el nombre del jugador y lo
     * almacenamos junto a su puntuación en un fichero para futuras referencias.
     */
    protected void Puntuacion() {
        String record = "";
        try {
            try (FileReader pts = new FileReader("src/" + ruta + "record.txt")){//Leemos el record actual
                BufferedReader leer = new BufferedReader(pts);
                record = leer.readLine();
                try {                                                           //y comprobamos el número de intentos
                    Integer.parseInt(record);
                } catch (NumberFormatException NFE) {
                    record = "100";                                             //si hay un error en el archivo reinicializamos el record a 100 
                }
            }
        } catch(IOException ioe) { }
        
        if (intentos < Integer.parseInt(record)) {                              //Si existe nuevo record
            try {	
                try (FileWriter pts = new FileWriter("src/"+ruta+"record.txt")){
                    String nom = JOptionPane.showInputDialog(
                            "Nuevo Record, Introduce tu Nombre:");              //Obtenemos el nombre del jugador
                    pts.write(Integer.toString(intentos) + "\n");               //y lo almacenamos en el fichero
                    if (nom.length() > 0) {
                        pts.write(nom + "\n");
                    } else {
                        pts.write("Anónimo\n");
                    }
                }
            } catch (IOException ioe) { }
        }
    }
    
    /**
     * Método principal del clase Parejas.
     * Lanza una instancia del programa llamando al Constructor.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main(String[] args) { 
        Parejas parejas = new Parejas();
    }
}