import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 */
public class Main {

    /**
     * Inicia la aplicación mostrando la ventana del probador de conexión.
     * La ventana se crea dentro del hilo de ejecucion de swing(por buenas prácitcas)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConexionTester ventana = new ConexionTester();
            ventana.setVisible(true);
        });
    }
}
