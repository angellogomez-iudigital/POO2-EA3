import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Contiene la lógica para probar la conexión a una base de datos usando JDBC.
 * Se separó de la interfaz gráfica para que cada clase tenga una sola responsabilidad.
 */
public class ProbadorConexion {

    private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
    private static final String PREFIJO_URL_SQLITE = "jdbc:sqlite:";

    /**
     * Intenta conectarse a la base de datos con los datos recibidos
     * y devuelve el resultado de la prueba.
     *
     */
    public ResultadoConexion probarConexion(String url, String user, String password) {
        try {
            cargarDriver(url);
            return conectar(url, user, password);
        } catch (ClassNotFoundException e) {
            return ResultadoConexion.fallo("Driver no encontrado:", e.getMessage());
        } catch (SQLException e) {
            return ResultadoConexion.fallo("Fallo de conexión:", e.getMessage());
        }
    }

    /**
     * Verifica que la URL sea de SQLite y carga en memoria su driver JDBC.
     * arroja ClassNotFoundException si el driver no corresponde con el de sqlite.
     */
    private void cargarDriver(String url) throws ClassNotFoundException {
        if (!url.startsWith(PREFIJO_URL_SQLITE)) {
            throw new ClassNotFoundException("Solo se admiten URL a Bases de datos de SQLite (" + PREFIJO_URL_SQLITE + "...). La URL recibida fue: " + url);
        }
        Class.forName(DRIVER_SQLITE);
    }

    /**
     * Abre la conexión con DriverManager dentro de un try-with-resources,
     * así la conexión se cierra automáticamente al terminar.
     */
    private ResultadoConexion conectar(String url, String usuario, String contrasena) throws SQLException {
        //El DriveManager ya tiene cargado en memoria el JDBC de SQLite, solo necesita los datos de conexión
        try (Connection conexion = DriverManager.getConnection(url, usuario, contrasena)) {
            String motorBD = conexion.getMetaData().getDatabaseProductName();
            String versionBD = conexion.getMetaData().getDatabaseProductVersion();
            return ResultadoConexion.exito("Conectado a " + motorBD + " versión " + versionBD);
        }
    }
}
