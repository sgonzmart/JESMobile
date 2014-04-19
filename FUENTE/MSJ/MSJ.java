//------------------------------------------------------------------------------
package MSJ;

//------------------------------------------------------------------------------
public class MSJ {
    //--------------------------------------------------------------------------

    /**
     * CODIGOS DE ERROR DE LAS FUNCIONES DE LOS TADS
     */
    //--------------------------------------------------------------------------
    // CODIGOS POSITIVOS: CORRECTOS
    // CODIGOS NEGATIVOS: INCORRECTOS
    public static final int OK = 0;
    public static final int INSERCION_INCORRECTA = -1;
    public static final int CLIENTE_NO_EXISTE = -2;
    public static final int CONTRASENA_INCORRECTA = -3;
    public static final int NIF_YA_REGISTRADO = -4;
    public static final int NO_HA_HECHO_LOGIN = -5;
    public static final int NIF_NO_EXISTE = -6;
    public static final int ACTUALIZACION_CLIENTE_ERRONEA = -7;
    public static final int ELIMINACION_CLIENTE_ERRONEA = -8;
    public static final int ELIMINACION_LINEA_ERRONEA = -9;
    public static final int ACTUALIZACION_LINEA_ERRONEA = -18;
    public static final int ELIMINACION_FACTURA_INCORRECTA = -10;
    public static final int ACTUALIZACION_FACTURA_ERRONEA = -11;
    public static final int ACTUALIZACION_SERVICIO_INCORRECTA = -12;
    public static final int ELIMINACION_SERVICIO_INCORRECTO = -13;
    public static final int ELIMINACION_FORMAR_INCORRECTO = -14;
    public static final int ACTUALIZACION_FORMAR_INCORRECTA = -15;
    public static final int ACTUALIZACION_GENERAR_INCORRECTA = -16;
    public static final int ELIMINACION_GENERAR_INCORRECTA = -17;
    public static final int ACTUALIZACION_POSEER_INCORRECTA = -19;
    public static final int ELIMINACION_POSEER_INCORRECTO = -20;
    public static final int ACTUALIZACION_TARIFA_ERRONEA = -21;
    public static final int LINEA_NO_EXISTE = -22;
    public static final int SERVICIO_NO_EXISTE = -23;
    public static final int FACTURA_NO_EXISTE = -24;
    public static final int ADMIN_NO_EXISTE = -25;
    public static final int EL_CLIENTE_NO_TIENE_ESA_LINEA = -26;
    public static final int OTRO = -1000;

    //--------------------------------------------------------------------------
    static public String Mensaje(int i) {
        //----------------------------------------------------------------------
        /**
         * Devuelve el String asociado al codigo de error <i>i</i>
         */
        //----------------------------------------------------------------------
        switch (i) {
            case 0:
                return ("Ejecucion Correcta.");
            case -1:
                return ("Inserción en la base de datos incorrecta.");
            case -2:
                return ("El nombre de usuario que ha introducido no existe.");
            case -3:
                return ("La contraseña introducida es incorrecta.");
            case -4:
                return ("Usted ya está registrado en la aplicación.");
            case -5:
                return ("Aun no hay ningun cliente cargado. Entre primero con su usuario y contraseña");
            case -6:
                return ("No existe ningun cliente con ese NIF/DNI/Pasaporte");
            case -7:
                return ("Actualizacion del cliente en la Base de Datos erronea");
            case -8:
                return ("Eliminacion del cliente en la Base de Datos erronea");
            case -9:
                return ("Eliminacion de la liena en la Base de Datos erronea");
            case -10:
                return ("Eliminacion de la factura en la Base de Datos erronea");
            case -11:
                return ("Actualizacion de la factura en la Base de Datos erronea");
            case -12:
                return ("Actualizacion del servicio en la Base de Datos erronea");
            case -13:
                return ("Eliminacion del servicio en la Base de Datos erronea");
            case -14:
                return ("Eliminacion de formar en la Base de Datos erronea");
            case -15:
                return ("Actualizacion de formar en la Base de Datos erronea");
            case -16:
                return ("Actualizacion de generar en la Base de Datos erronea");
            case -17:
                return ("Eliminacion de generar en la base de datos erronea");
            case -18:
                return ("Actualizacion de linas en la Base de Datos erronea");
            case -19:
                return ("Actualizacion de poseer en la Base de Datos erronea");
            case -20:
                return ("Eliminacion de poseer en la Base de Datos erronea");
            case -21:
                return ("Actualizacion de tarifa en la base de Datos erronea");
            case -22:
                return ("La linea no existe");
            case -23:
                return ("El servicio no existe");
            case -24:
                return ("La factura no existe");
            case -25:
                return ("El administrador dado no existe");
            case -26:
                return ("La linea indicada no pertenece a ese cliente, o no existe");
            case -1000:
                return ("Error interno");
            default:
                return ("Error desconocido");

        }
    }
    //--------------------------------------------------------------------------
}
//------------------------------------------------------------------------------