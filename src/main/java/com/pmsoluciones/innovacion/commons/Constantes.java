package com.pmsoluciones.innovacion.commons;

public class Constantes {
	
    // Spring Security
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
	
	//MENSAJES EXITO
	public static final String EXITO_CONSULTA = "Consulta exitosa";
	public static final String EXITO_GUARDAR = "Registro guardado correctamente";
	public static final String EXITO_ACTUALIZAR = "Registro actualizado correctamente";
	public static final String EXITO_ELIMINAR = "Registro eliminado correctamente";
	//MENSAJES ERROR
	public static final String ERROR_CONSULTA = "Ocurrió un error al realizar la consulta";
	public static final String ERROR_GUARDAR = "Ocurrió un error al guardar la información";
	public static final String ERROR_ACTUALIZAR = "Ocurrió un error al actualizar el registro";
	public static final String ERROR_ELIMINAR = "Ocurrió un error al eliminar el registro";
	//MENSAJES SIN CONTENIDO
	public static final String NO_ENCONTRADO = "No se encontraron registros";
	//MENSAJES LOGIN
	public static final String USUARIO_O_PASS_NO_VALIDO = "Usuario o contraseña inválido.";
	public static final String ACCESO_NO_AUTORIZADO = "Acceso no autorizado";
	public static final String TOKEN_NO_VALIDO = "Token inválido";
	public static final String TOKEN_EXITO = "Token creado correctamente";
	public static final String TOKEN_ACTUALIZADO_EXITO  = "Token actualizado correctamente";
	public static final String TOKEN_VALIDO = "Token valido";
	public static final String TOKEN_EXPIRADO = "Token expirado";
	public static final String TOKEN_MAL_FORMADO = "Token mal formado";
	public static final String TOKEN_VACIO = "Token vacio";
	public static final String TOKEN_NO_SOPORTADO = "Token no soportado";
	public static final String TOKEN_FIRMA_INCORRECTA = "Firma incorrecta dentro del token";
	
	public static final String TIPO_BUSQUEDA_PROCESO = "Procesos";
	public static final String TIPO_BUSQUEDA_SUBPROCESO = "Subprocesos";
	public static final String TIPO_BUSQUEDA_PROCEDIMIENTO = "Procedimientos";

	public static final Integer INTEGER_VALUE_0 = 0;	
	public static final Integer INTEGER_VALUE_1= 1;	
	public static final Integer CAMBIOS_MAYORES= 2;	
	public static final Long LONG_VALUE_MARCADO= 1L;	
	public static final Long LONG_VALUE_0=0L;	
	public static final String UNION = " UNION ";
	
}
