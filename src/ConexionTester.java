import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
Ventana de la aplicación para probar conexión a la base de datos.
 */
public class ConexionTester extends JFrame {

    private static final String URL_BD = "jdbc:sqlite:inventario.db";
    private static final Color COLOR_EXITO = new Color(0, 140, 0);
    private static final Color COLOR_ERROR = Color.RED;

    private final ProbadorConexion probadorConexion = new ProbadorConexion();

    private JTextField urlTxt;
    private JTextField userTxt;
    private JPasswordField passwordTxt;
    private JButton testConectionBtn;
    private JTextArea areaResultado;

    /**
     * Crea la ventana, configura sus propiedades básicas y construye
     * todos los componentes.
     */
    public ConexionTester() {
        configurarVentana();
        construirInterfaz();
    }

    /**
     * Se asignan configuraciones como titulo de la ventana, tamaño y comportamiento.
     */
    private void configurarVentana() {
        setTitle("Probador de conexión JDBC");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Arma el panel principal con BorderLayout
     */
    private void construirInterfaz() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(crearPanelFormulario(), BorderLayout.CENTER);
        panelSuperior.add(crearPanelBoton(), BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(crearPanelResultado(), BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    /**
     * Crea el formulario con las etiquetas y los campos de texto
     * para la URL, el usuario y la contraseña.
     */
    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));

        urlTxt = new JTextField(URL_BD); // el capo de la url viene seteado al inicializarse.
        userTxt = new JTextField();
        passwordTxt = new JPasswordField();
        // como pusimos 3 rows en el GridLayout de dos columnas se acomodan automáticamente
        panelFormulario.add(new JLabel("URL de conexión:"));
        panelFormulario.add(urlTxt);
        panelFormulario.add(new JLabel("Usuario:"));
        panelFormulario.add(userTxt);
        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(passwordTxt);

        return panelFormulario;
    }

    /**
     * Crea el panel que contiene el botón "Probar conexión".
     *
     */
    private JPanel crearPanelBoton() {
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        testConectionBtn = new JButton("Probar conexión");
        testConectionBtn.addActionListener(evento -> probarConexion());
        panelBoton.add(testConectionBtn);

        return panelBoton;
    }

    /**
     * Crea el área de texto donde se muestran los resultados de la conexión.
     * Se hace dentro de un JScrollPane para poder scrollear en caso de que las salidas superen el area de texto
     */
    private JScrollPane crearPanelResultado() {
        areaResultado = new JTextArea();
        areaResultado.setEditable(false); // asi no se puede escribir sobre el textArea
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);

        JScrollPane panelDesplazamiento = new JScrollPane(areaResultado);
        panelDesplazamiento.setBorder(BorderFactory.createTitledBorder("Log de conexión:"));

        return panelDesplazamiento;
    }

    /**
     * Se ejecuta al presionar el botón "Probar conexión". Conecta con la clase ProbadorConexion
     */
    private void probarConexion() {
        String url = urlTxt.getText().trim();
        String user = userTxt.getText().trim();
        String password = new String(passwordTxt.getPassword());

        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes escribir la URL de conexión.", "Dato faltante", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ResultadoConexion resultado = probadorConexion.probarConexion(url, user, password);
        mostrarResultado(resultado);
    }

    /**
     * Muestra el resultado en el textArea
     */
    private void mostrarResultado(ResultadoConexion resultado) {
        areaResultado.setText(resultado.getTitulo() + "\n" + resultado.getDetalle());

        if (resultado.isExitoso()) {
            areaResultado.setForeground(COLOR_EXITO);
        } else {
            areaResultado.setForeground(COLOR_ERROR);
        }
    }

}
