package default;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.com.gana.fwgana.dao.SimpleListObject;
import co.com.gana.fwgana.util.messages.DateMessages;

/**
 * Utilidades para manejar Strings
 */
public class TextDateManager {

	public TextDateManager(){
	}

	public static String getShortMonthName( int month ) {
		String retorno = getMonthName( month );
		return retorno.substring( 0, 3 ).toUpperCase();
	}
	
	public static String getMonthName( int month ) {
		return DateMessages.getString( "month."+(month-1) );
	}
	
	public static int getActualMonth() {
		Calendar c = Calendar.getInstance();
		return c.get( Calendar.MONTH )+1;		
	}
	
	public static ArrayList getMonths() {
		ArrayList meses = new ArrayList();
		for( int i=1; i<=12; i++ )
			meses.add( new SimpleListObject( String.valueOf(i), getMonthName(i) ) );
		return meses;
	}
	
	public static String format( Date fecha, String formato ){
		if( fecha==null )
			return "";	
		SimpleDateFormat sdf = new SimpleDateFormat( formato );
		return sdf.format( fecha );
	}

	public static Date parse( String fecha, String formato ) throws Exception {
		if( fecha==null )
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat( formato );
		return sdf.parse( fecha );
	}
	
	public static Date parse( String fecha ) throws Exception {
		if( fecha==null )
			return null;
		String formato = "dd/MM/yyyy";
		if( fecha.charAt(4)=='/' )
			formato = "yyyy/MM/dd";
		if( fecha.charAt(4)=='-' )
			formato = "yyyy-MM-dd";
		if( fecha.charAt(2)=='-' )
			formato = "dd-MM-yyyy";
		return parse( fecha, formato );
	}

	public static String convert( String fecha, String formatoOriginal, String formatoDestino ) throws Exception {
		Date fechaOriginal = parse( fecha, formatoOriginal );
		return format( fechaOriginal, formatoDestino );
	}
	
	public static String getDayName( int day){
		return DateMessages.getString( "day."+day );
	}
	
	public static String getShortDayName( int day ){
		return DateMessages.getString( "day."+day ).substring(0,3);
	}	

}