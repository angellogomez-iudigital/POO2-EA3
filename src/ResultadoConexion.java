/**
 * Guarda el resultado de una prueba de conexión: si fue exitosa,
 * un título corto y el detalle del mensaje.
 */
public class ResultadoConexion {

    private final boolean exitoso;
    private final String titulo;
    private final String detalle;

    /**
     * Crea un resultado con todos sus datos.
     */
    private ResultadoConexion(boolean exitoso, String titulo, String detalle) {
        this.exitoso = exitoso;
        this.titulo = titulo;
        this.detalle = detalle;
    }

    /**
     * Crea un resultado de conexión exitosa.
     *
     */
    public static ResultadoConexion exito(String detalle) {
        return new ResultadoConexion(true, "Conexión exitosa", detalle);
    }

    /**
     * Crea un resultado de conexión fallida.

     */
    public static ResultadoConexion fallo(String titulo, String detalle) {
        return new ResultadoConexion(false, titulo, detalle);
    }

    /**
     * Indica si la conexión fue exitosa.
     */
    public boolean isExitoso() {
        return exitoso;
    }

    /**
     * Devuelve el mensaje corto que resume el resultado.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Devuelve la información adicional o el mensaje técnico del error.
     */
    public String getDetalle() {
        return detalle;
    }
}
