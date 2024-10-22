package com.pmsoluciones.innovacion.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "HH:mm:ss";	 
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


	public static Date parseDate(String date) throws Exception {
	    try {
	        return new Date(new SimpleDateFormat(DATE_FORMAT).parse(date).getTime());
	    } catch (Exception e) {
	        throw new Exception("El formato de fecha enviado no es el correcto ["+date+"] 'yyyy-MM-dd'");
	    }
	}
	

	public static Date parseTime(String time) throws Exception {
	    try {
	        return new Date(new SimpleDateFormat(TIME_FORMAT).parse(time).getTime());
	    } catch (Exception e) {
	    	 throw new Exception("El formato de fecha enviado no es el correcto ["+time+"] 'HH:mm:ss'");
	    }
	}
	

	public static Date parseTimestamp(String timestamp) throws Exception {
	    try {
	        return new Date(new SimpleDateFormat(DATE_TIME_FORMAT).parse(timestamp).getTime());
	    } catch (Exception e) {
	    	 throw new Exception("El formato de fecha enviado no es el correcto ["+timestamp+"] 'yyyy-MM-dd HH:mm:ss'");
	    }
	}
	

	public static Date now() {
	    try {
	    	String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	    	String newDate = todaysDate.replace("T", " ").substring(0, 19);
	        return new Date(new SimpleDateFormat(DATE_TIME_FORMAT).parse(newDate).getTime());
	    } catch (Exception e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	
	
	public static String dayOfWeekString(Date dateTime) {
		Calendar cal= Calendar.getInstance();
		cal.setTime(dateTime);
		Integer dia =  cal.get(Calendar.DAY_OF_WEEK);
		
		String semana = null;
		    
	    switch (dia){
        case 1: semana = "Domingo";
            break;
        case 2: semana = "Lunes";
            break;
        case 3: semana = "Martes";
            break;
        case 4: semana = "Miércoles";
            break;
        case 5: semana = "Jueves";
            break;
        case 6: semana = "Viernes";
            break;
        case 7: semana = "Sábado";
            break;
	    }
	    
	    return semana;
	}
	

	public static String formatTimeAMAndPM(Date dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	    String formattedDate = df.format( dateTime );
	    String forDate = formattedDate.replace("a. m.", "AM").replace("p. m.","PM");	    
	    return forDate;
	}
	
	
	public static String dateFormat(Date dateTime, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
	    String formattedDate = df.format( dateTime );	   
	    return formattedDate;
	}
	
	

	public static String dateText(Date dateTimestamp) {
		String MESES[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		
		Calendar cal= Calendar.getInstance();
		cal.setTime(dateTimestamp);
		Integer dia =  cal.get(Calendar.DAY_OF_MONTH);
		
	   
	    return dia+" de "+ MESES[cal.get(Calendar.MONTH)]+ " de "+ cal.getWeekYear() +" "+ formatTimeAMAndPM(dateTimestamp);
	}
}
