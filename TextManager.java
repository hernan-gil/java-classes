package default;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.text.StrTokenizer;

/**
 * Utilidades para manejar Strings
 */
public class TextManager {

	public static int ALIGN_LEFT = 0;
	public static int ALIGN_RIGHT = 1;
	public static int ALIGN_CENTER = 2;
	
	// Hex charset
	private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

	public TextManager(){
	}

	public static String capitalize( String string ) {
		return capitalize( string, false );
	}

	public static String capitalize( String string, boolean onlyFirstLetter ) {
		// separate the words
		String word;
		StringBuffer newString = new StringBuffer("");
		int pos=0, posAnt=0;
		try	{
			pos = string.indexOf(' ');
			while ( pos!=-1 ) {
				word = string.substring( posAnt, pos );
				if ( !word.trim().equals("") ){
					if( posAnt!=0 ) newString.append(" ");
					if( onlyFirstLetter )
						newString.append(word.substring(0,1).toUpperCase()).append(word.substring(1));
					else
						newString.append(word.substring(0,1).toUpperCase()).append(word.substring(1).toLowerCase());
				}
				posAnt = pos+1;
				pos = string.indexOf(' ', posAnt);
			}
			word = string.substring( posAnt );
			if ( !word.trim().equals("") ){
				if( posAnt!=0 ) newString.append(" ");
				if( onlyFirstLetter )
					newString.append(word.substring(0,1).toUpperCase()).append(word.substring(1));
				else
					newString.append(word.substring(0,1).toUpperCase()).append(word.substring(1).toLowerCase());
			}
		} catch( IndexOutOfBoundsException e ) {}
		return newString.toString();
	}

	public static String replace( String texto, String szOld, String szNew, int posicion ){
		int pos = texto.indexOf(szOld), posAnt=0, t = szOld.length(), tam=texto.length();
		int ocurrencias = 0;
		StringBuffer sz = new StringBuffer("");
		String szAd;
		while( pos!=-1 ){
			ocurrencias++;
			szAd = szNew;
			if(posicion > 0 && ocurrencias != posicion) szAd = szOld;
			sz.append( texto.substring( posAnt, pos ) ).append(szAd);
			posAnt = pos+t;
			pos = texto.indexOf(szOld, pos+t);
		}
		if( posAnt<tam ) sz.append(texto.substring( posAnt ));
		return sz.toString();

	}

	/**
	 * Reemplaza en un String todas las apariciones de un texto por otro
	 *
	 * @param            texto Texto a modifcar
	 * @param            szOld Palabra o texto viejo el cual sera cambiado
	 * @param            szNew Palabra o texto nuevo el cual reemplazara a szOld
	 * @return           Texto cambiado
	 */
	public static String replace( String texto, String szOld, String szNew ){
		return replace(texto, szOld, szNew, 0);
	}

	public static String getStringFromHash( Map hash, String keys, String defaultValue ){
		// Primero verifica si los valores están completos en el hash
		String val = (String)hash.get( keys.toLowerCase() );
		if( val != null )
			return val;
		StringBuffer result = new StringBuffer("");
		StringTokenizer st = new StringTokenizer( keys.toLowerCase(), "|" );
		int j=0;
		while( st.hasMoreTokens() ){
			if( j>0 ) result.append( " | " );
			val = (String)hash.get( st.nextToken().trim().toLowerCase() );
			if( val==null )
				val = defaultValue;
			result.append( val );
			j++;
		}
		return result.toString();
	}

	public static String intercalarValores( String nombres, String valores ){
		StringBuffer result = new StringBuffer("");
		StringTokenizer st = new StringTokenizer( nombres.toLowerCase(), "|" );
		StringTokenizer stVal = new StringTokenizer( valores, "|" );
		int j=0;
		while( st.hasMoreTokens() ){
			if( j>0 ) result.append( "|" );
			String nombre = st.nextToken().trim();
			String val = "";
			if( stVal.hasMoreTokens() )
				val = stVal.nextToken().trim();
			result.append(nombre).append( "|=|" ).append(val);
			j++;
		}
		return result.toString();
	}

	public static String nvl( String valor, String defaultValue ){
		if( valor==null || valor.equals("") ) valor = defaultValue;
		return valor;
	}

	public static String nvl( Integer valor, String defaultValue ){
		if( valor==null )
			return defaultValue;
		return String.valueOf(valor);
	}

	public static Object nvl( Object valor, Object defaultValue ){
		if( valor!=null && defaultValue!=null && valor instanceof String && defaultValue instanceof String )
			return nvlString( (String)valor, (String)defaultValue );
		if( valor==null ) valor = defaultValue;
		return valor;
	}

	public static String nvlString( String valor, String defaultValue ){
		if( valor==null || valor.equals("") ) valor = defaultValue;
		return valor;
	}

	public static Object nvl( Object valor, String className, String defaultValue ) {
		if( valor!=null && defaultValue!=null && valor instanceof String )
			return nvlString( (String)valor, (String)defaultValue );
		if( valor==null ) {
			if( defaultValue == null )
				return defaultValue;
			try {
				return Class.forName(className).getDeclaredMethod( "valueOf", new Class[]{ Class.forName("java.lang.String") } ).invoke( null, new Object[]{ defaultValue } );
			} catch( Exception e ){
				return null;
			}
		}
		return valor;
	}

	public static String nvlNull( String valor, String defaultValue ){
		if( valor==null ) valor = defaultValue;
		return valor;
	}

	public static boolean isNull( Object value ){
		if( value==null ) return true;
		if( value instanceof java.lang.String && value.equals("") ) return true;
		return false;
	}

	public static String nvlIf( String valor, String valorIfNull, String valorIfNotNull ){
		if( isNull( valor ) )
			return valorIfNull;
		return valorIfNotNull;
	}
	
	public static String getPath( String path ){
		if( !path.endsWith( "\\" ) && !path.endsWith( "/" ) )
			path = path+"/";
		return path;
	}

	public static String padleft( String texto, int size, char caracter ){
		return fill( texto, size, ALIGN_RIGHT, caracter );
	}

	public static Collection crearArrayList(String[] dato) {
		Collection lista = new ArrayList(10);
		for (int i = 0; i < dato.length; i++) {
			lista.add(dato[i]);
		}
		return lista;
	}

	public static StringBuffer crearStringBuffer(String[] dato) {
		StringBuffer lista = new StringBuffer();
		for (int i = 0; i < dato.length; i++) {
			lista.append(dato[i]).append(",");
		}
		return lista;
	}

	public static ArrayList crearArrayList(String dato) {
		ArrayList lista = new ArrayList(10);
		StringTokenizer st = new StringTokenizer(dato, ",");
		while (st.hasMoreTokens()) {
			lista.add(st.nextElement());
		}
		return lista;
	}

	public static String reverse( String str ){
		StringBuffer res = new StringBuffer("");
		for( int i=str.length()-1; i>=0; i-- ){
			res.append( str.charAt(i) );
		}
		return res.toString();
	}

	public static String fill( String valor, int longitud ){
		return fill( valor, longitud, ALIGN_LEFT );
	}

	public static String fill( String valor, int longitud, int alignment ){
		return fill( valor, longitud, alignment, ' ' );
	}

	public static String fill( String valor, int longitud, int alignment, char fillChar ){
		if( valor == null )
			valor = "";
		if( valor.length()>longitud ){
			return valor.substring( 0, longitud );
		}
		StringBuffer val = new StringBuffer( "" );
		if( alignment==ALIGN_LEFT ){
			val.append(valor);
			while( val.length()<longitud )
				val.append( fillChar );
		}
		if( alignment==ALIGN_RIGHT ){
			while( val.length()+valor.length()<longitud )
				val.append( fillChar );
			val.append(valor);
		}
		if( alignment==ALIGN_CENTER ){
			int espacios = Math.max( 0, (longitud-valor.length())/2 );
			while( val.length()<espacios )
				val.append( fillChar );
			val.append(valor);
			while( val.length()<longitud )
				val.append( fillChar );
		}
		return val.toString();
	}

	public static Vector csvToVector( String csv, String symbol ){
		StringTokenizer st = new StringTokenizer( nvl(csv,""), symbol );
		Vector res = new Vector();
		while( st.hasMoreTokens() ){
			res.add( st.nextToken() );
		}
		return res;
	}

	public static String vectorToCsv( Vector v1, String symbol ){
		StringBuffer sb = new StringBuffer("");
		int i;
		for( i=0; i<v1.size(); i++ ){
			if( i>0 )
				sb.append(symbol);
			sb.append( v1.get(i) );
		}
		return sb.toString();
	}

	public static boolean contieneIguales( Vector v1, Vector v2 ){
		int i, j;
		for( i=0; i<v1.size(); i++ ){
			for( j=0; j<v2.size(); j++ ){
				Object eli = (Object) v1.get(i);
				Object elj = (Object) v2.get(j);
				if( eli.equals(elj) ){
					return true;
				}
			}
		}
		return false;
	}

	public static boolean contieneIguales( String elem, Vector v1 ){
		int i;
		for( i=0; i<v1.size(); i++ ){
			Object eli = (Object) v1.get(i);
			if( eli.equals(elem) ){
				return true;
			}
		}
		return false;
	}

	public static String suprimirTildes( String texto ){
        StringBuffer sb = new StringBuffer();
        for( int i=0; i<texto.length(); i++ ){
        	char c = texto.charAt( i );
        	if( c=='Á' )
        		c = 'A';
        	else if( c=='á' )
        		c = 'a';
        	else if( c=='É' )
        		c = 'E';
        	else if( c=='é' )
        		c = 'e';
        	else if( c=='Í' )
        		c = 'I';
        	else if( c=='í' )
        		c = 'i';
        	else if( c=='Ó' )
        		c = 'O';
        	else if( c=='ó' )
        		c = 'o';
        	else if( c=='Ú' )
        		c = 'U';
        	else if( c=='ú' )
        		c = 'u';
        	sb.append( c );
        }
        return sb.toString();
	}
	
	public static String suprimirEnes( String texto ){
        StringBuffer sb = new StringBuffer();
        for( int i=0; i<texto.length(); i++ ){
        	char c = texto.charAt( i );
        	if( c=='Ñ' )
        		c = 'N';
        	else if( c=='ñ' )
        		c = 'n';
        	
        	sb.append( c );
        }
        return sb.toString();
	}

	public static StrTokenizer createTokenizer( String texto, String separator ){
		return createTokenizer( texto, separator, false );
	}

	public static StrTokenizer createTokenizer( String texto, String separator, boolean ignoreEmptyTokens ){
		StrTokenizer st = new StrTokenizer( texto, separator );
		st.setEmptyTokenAsNull( false );
		st.setIgnoreEmptyTokens( ignoreEmptyTokens );
		return st;
	}

	public static String[] divideTokenizer( String texto, int lengthParts, String separator ){
		int i;
		ArrayList parts = new ArrayList();
		boolean salida = false;
		int posActual = 0;
		while( !salida ){
			String part = texto.substring( posActual, posActual+lengthParts );
			int pos = part.lastIndexOf( separator );
			if( pos < 0 ){
				parts.add( part );
				posActual += part.length();
			}
			else {
				parts.add( texto.substring( posActual, posActual+pos ) );
				posActual += pos+1;
			}
			if( texto.length() - posActual <= lengthParts ){
				if( posActual < texto.length() )
					parts.add( texto.substring( posActual ) );
				salida = true;
			}
		}
		String resultado[] = new String[ parts.size() ];
		for( i=0; i<parts.size(); i++ ){
			resultado[i] = (String) parts.get( i );
		}
		return resultado;
	}

	public static String repeatChar( String fillChar, int cuantos ){
		StringBuffer val = new StringBuffer( "" );
		while( val.length()<cuantos )
			val.append( fillChar );
		return val.toString();
	}

	public static boolean isEmpty( String string ) {
		return string == null || string.length() == 0;
	}

	public static boolean isNotEmpty( String string ) {
		return !(null == string || string.trim().length() == 0);
	}	

	public static String strstr( String texto, String chrAremplazar, String chrRemplazo ){
		int i;
		for( i=0; i<chrAremplazar.length(); i++ ){
			texto = texto.replace( chrAremplazar.charAt(i), chrRemplazo.charAt(i) );
		}
		return texto;
	}

	  public static byte[] hexToBytes(String hexString) {
		  char [] hex = hexString.toCharArray();
		  int length = hex.length / 2;
		  byte[] raw = new byte[length];
		  for (int i = 0; i < length; i++) {
		      int high = Character.digit(hex[i * 2], 16);
		      int low = Character.digit(hex[i * 2 + 1], 16);
		      int value = (high << 4) | low;
		      if (value > 127)
		        value -= 256;
		      raw[i] = (byte) value;
		  }
	    return raw;
	  }

	  public static String toHexString(byte[] ba)
	  {
	    return toHexString(ba, 0, ba.length);
	  }
	  
	  public static final String toHexString(byte[] ba, int offset, int length)
	  {
	    char[] buf = new char[length * 2];
	    for (int i = 0, j = 0, k; i < length;)
	      {
	        k = ba[offset + i++];
	        buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
	        buf[j++] = HEX_DIGITS[k & 0x0F];
	      }
	    return new String(buf);
	  }
	 
	  public static String validarMultiplo8(String texto)  {
		  String textoResultado = "";

          int modulo = texto.length() % 8;

          if(modulo == 0)
          {
              textoResultado = texto;
          } else {
              do
              {
                  texto = (new StringBuilder(String.valueOf(texto))).append(" ").toString();
                  modulo = texto.length() % 8;
              } while(modulo != 0);
              textoResultado = texto;
          }
          return textoResultado;
	}

	public static String[] split(String strdata, String token) {
	    if(strdata == null || strdata.equals(""))
	    	return null;
	    if(token == null || token.equals(""))
	    	return (new String[] {
	    			strdata
	    	});
		ArrayList arraux = new ArrayList();
		String straux = strdata;
		for(; strdata.indexOf(token) != -1; arraux.add(straux)){
	    	straux = strdata.substring(0, strdata.indexOf(token));
	    	strdata = strdata.substring(strdata.indexOf(token) + token.length());
		}
	
		if(strdata != null && !strdata.equals(""))
	        arraux.add(strdata);
	    String result[] = new String[arraux.size()];
	    for(int i = 0; i < result.length; i++)
	        result[i] = (String)arraux.get(i);

	    return result;
	}
	
	public static String concatenarSeparador(String cadena, String separador, int cantidad){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cadena.length(); i+=cantidad) {
			try{
				builder.append(cadena.substring(i,i+cantidad));
				if(i<cadena.length()-cantidad)
					builder.append(separador);
			}catch(StringIndexOutOfBoundsException sioobe){
				builder.append(cadena.substring(i,cadena.length()));
			}
		}
		return builder.toString();
	}
	
	public static String[] getStringArray(ArrayList<String> xmls) {
		String[] xmlsString = new String[xmls.size()];
		int cont = 0;
		for (String xml : xmls) {
			xmlsString[cont] = xml;
			cont++;
		}
		return xmlsString;
	}	
	

	public String fullTraceError(Throwable e, Integer... limit){
		String error = ExceptionUtils.getFullStackTrace(e);
		if (limit.length!=0 && limit[0]>=0){
			if (limit.length==1){
				if (limit[0]<error.length()){			
					return error.substring(limit[0]);
				}else{
					return error;
				}
			}else if (limit.length==2 && limit[0]<limit[1] && limit[1]<=error.length()){			
				return error.substring(limit[0], limit[1]);
			}else{
				return error;
			}				
		}else{
			return error;
		}		
	}
	
	/**
	 * @param text
	 * @param size
	 * @return
	 */
	public static String getStringByLength(String text, int size){
		if (text!=null && text.length()>size){
			text = text.substring(0,size-1);
		}
		return text;
	}
	
	/**
	 * Nombre del método:       splitToColumns
	 * Descripción:             <i>Método que retorna en un <code>String</code> organizado por columnas el contenido que recibe en el parametro <code>columns</code>,
	 * 							utilizando dividido en <code>columnsCount</code> columnas usando el método <code>fixInColumns</code></i>.
	 * @author                  johan.serna
	 * @param columns			Arreglo de strings donde cada posición será una columna
	 * @param columnsWidth			Arreglo de int utilizado para el ancho de cada columna
	 * @return
	 *
	 *  MODIFICACIONES:
	 * 
	 *  Fecha:                  21/08/2013
	 *  Autor - Empresa:        johan.serna - E.T.N. S.A.S.
	 *  Descripción:            Creación del método
	 */
	public static String splitToColumns(String[] columns,int columnsCount,int columnsWidth){
		StringBuffer sbCols = new StringBuffer();		
		String[] columnas = new String[columnsCount];
		int filasColumna = Math.round(columns.length/columnsCount);
		int diferencia = columns.length - filasColumna*columnsCount;
		filasColumna +=  diferencia;
		int i = 0;
		int tope = 0;
		for( int iteracion = 0 ; iteracion < columnsCount; iteracion++){
			tope = (iteracion*filasColumna) +filasColumna;
			tope = tope > columns.length?columns.length:tope;
			for(i = iteracion*filasColumna; i < tope;i++){
				sbCols.append(TextManager.fill(String.valueOf(columns[i]),columnsWidth,TextManager.ALIGN_RIGHT,' '));
			}
			columnas[iteracion] = sbCols.toString();
			sbCols = new StringBuffer();
		}		
		return fixInColumns(columnas,columnsWidth);
	}
	
	/**
	 * Nombre del método:       fixInColumns
	 * Descripción:             <i>Método que retorna en un <strong>String</strong> organizado por columnas elcontenido que recibe en el parametro <strong>columns</strong>,
	 * 							utilizando <strong>columnsWidth</strong> como ancho de cada columna y el separador de éstas será "|", este método utiliza el método <strong>fixInColumns(String[] columns,int[] colsAlign,int[] colsWidth,String columnSeparator)</strong></i>.
	 * @author                  johan.serna
	 * @param columns			Arreglo de strings donde cada posición será una columna
	 * @param columnsWidth			Arreglo de int utilizado para el ancho de cada columna
	 * @return
	 *
	 *  MODIFICACIONES:
	 * 
	 *  Fecha:                  21/08/2013
	 *  Autor - Empresa:        johan.serna - E.T.N. S.A.S.
	 *  Descripción:            Creación del método
	 */
	public static String fixInColumns(String[] columns,int columnsWidth){
		return fixInColumns(columns,-1,columnsWidth);
	}
	
	public static String fixInColumns(String[] columns,int columnsAlign,int columnsWidth){
		int[] columnsW = new int[columns.length];
		int[] columnsA = new int[columns.length];
		columnsAlign = columnsAlign==-1?ALIGN_LEFT:columnsAlign;
		for(int i = 0 ; i <columnsW.length;i++){
			columnsW[i] = columnsWidth;
			columnsA[i] = columnsAlign;
		}
		return fixInColumns(columns,columnsA,columnsW,null);
	}
	
	/**
	 * Nombre del método:       fixInColumns
	 * Descripción:             <i>Método que retorna en un <strong>String</strong> organizado por columnas elcontenido que recibe en el parametro <strong>columns</strong>,
	 * 							utilizando <strong>colsAlign</strong> para alinear cada columna, <strong>colsWidth</strong> para el ancho de cada columna y
	 * 							<strong>columnSeparator</strong> como separador entre columnas</i>.
	 * @author                  johan.serna
	 * @param columns			Arreglo de strings donde cada posición será una columna
	 * @param colsAlign			Arreglo de int utilizado para el alineamiento de cada columna, usar Textmanager.ALIGN_(LEFT|CENTER|RIGHT)
	 * @param colsWidth			Arreglo de int utilizado para el ancho de cada columna
	 * @param columnSeparator	String que se utilizará como separador, "|" si es null
	 * @return					String con presentación de columnas
	 *
	 *  MODIFICACIONES:
	 * 
	 *  Fecha:                  21/08/2013
	 *  Autor - Empresa:        johan.serna - E.T.N. S.A.S.
	 *  Descripción:            Creación del método
	 */
	public static String fixInColumns(String[] columns,int[] colsAlign,int[] colsWidth,String columnSeparator){
		StringBuffer sb = new StringBuffer();
		if( colsAlign != null && colsAlign.length == 0)
			colsAlign = null;		
		if( colsWidth != null && colsWidth.length == 0)
			colsWidth = null;
		columnSeparator = nvl(columnSeparator,"|");
		String column = null;
		int columnPos = 0;
		int cutPos = 0;
		String columnValue = null;
		//Por ahora solo funciona con 2 columnas, para más columnas hay que arreglarle
		//como darse cuenta del cambio de columna o de linea
		
		while(hasMoreColumns(columns)){
			column = columns[columnPos];
			if(column == null){
				sb.append(fill(" ", colsWidth[columnPos]));
				if(columnPos < columns.length-1){
					sb.append(columnSeparator);
					columnPos++;
				}else{
					sb.append("\n");
					columnPos=0;
				}
			}else{
				cutPos = colsWidth[columnPos];
				if(column.length()>colsWidth[columnPos]){
					if(column.substring(0,cutPos).indexOf(' ')>-1 && (column.charAt(colsWidth[columnPos])!=' ' && column.charAt(colsWidth[columnPos]-1)!=' ')){
						cutPos = column.substring(0,colsWidth[columnPos]).lastIndexOf(' ')+1;
					}
				}
				if(!isNull(column)){
					while(column.length()>0 && column.charAt(0) == ' '){
						column = column.substring(1);
						cutPos--;
					}
				}
				columnValue = fill(fill(column,cutPos,colsAlign==null?ALIGN_LEFT:colsAlign[columnPos],' '),colsWidth[columnPos],colsAlign==null?ALIGN_LEFT:colsAlign[columnPos],' ');
				
				sb.append(columnValue);
				if(cutPos>=column.length()){
					columns[columnPos] = null;
					//Completa la figura de la columna
					if(columnPos != columns.length-1 && !hasMoreColumns(columns)){
						columns[columns.length-1]=" ";
					}
				}else
					columns[columnPos] = column.substring(cutPos);
				if(columnPos < columns.length-1){
					sb.append(columnSeparator);
					columnPos++;
				}else{
					sb.append("\n");
					columnPos=0;
				}
			}
		}
		return sb.toString();
	}
	
	private static boolean hasMoreColumns(String[] columns){
		for(String col : columns){
			if(!isNull(col))
				return true;
		}
		return false;
	}

	public static String deleteSpecialChars( String cadena){
		String resultado = "";
		if(cadena==null)
			return resultado;
		char data[] = cadena.toCharArray();
		for( int i=0; i<data.length; i++ ){
			if( data[i]>47 && data[i]<58 )
				resultado = resultado + data[i];
			else if( data[i]>64 && data[i]<91 )
				resultado = resultado + data[i];
			else if( data[i]>96 && data[i]<123 )
				resultado = resultado + data[i];
			else if( data[i]==32 )
				resultado = resultado + data[i];
			
			if( data[i]=='Á' || data[i]==193 )
				resultado = resultado + 'A';
	    	else if( data[i]=='á' || data[i]==225 )
	    		resultado = resultado + 'a';
	    	else if( data[i]=='É' || data[i]==201 )
	    		resultado = resultado + 'E';
	    	else if( data[i]=='é' || data[i]==233 )
	    		resultado = resultado + 'e';
	    	else if( data[i]=='Í' || data[i]==205 )
	    		resultado = resultado + 'I';
	    	else if( data[i]=='í' || data[i]==237 )
	    		resultado = resultado + 'i';
	    	else if( data[i]=='Ó' || data[i]==211 )
	    		resultado = resultado + 'O';
	    	else if( data[i]=='ó' || data[i]==243 )
	    		resultado = resultado + 'o';
	    	else if( data[i]=='Ú' || data[i]==218 )
	    		resultado = resultado + 'U';
	    	else if( data[i]=='ú' || data[i]==250 )
	    		resultado = resultado + 'u';
	    	else if( data[i]=='ñ' )
	    		resultado = resultado + 'n';
	    	else if( data[i]=='Ñ' )
	    		resultado = resultado + 'N';
		}
		return resultado;
	}

	public static String getOnlyNumbers( String cadena){
		String resultado = "";
		if(cadena==null)
			return resultado;
		char data[] = cadena.toCharArray();
		for( int i=0; i<data.length; i++ ){
			if( data[i]>47 && data[i]<58 )
				resultado = resultado + data[i];
		}
		return resultado;
	}
	
	public static boolean validateLength(String string, int minLength,
			int maxLength) {
		if (isNull(string)) {
			return false;
		}
		// Solo valida el tamano minimo
		if (maxLength == 0) {
			return string.length() >= minLength;
		} 
		
		return string.length() >= minLength && string.length() <= maxLength;
	}
	
	public static void main( String args[] ){
//		System.err.println(TextManager.deleteSpecialChars(" 15423123560  63416234461230 44612wefas6d5f0a6sd 0a4s6d5f 40as6df540 a6sAñÑó"));
//		System.err.println(fixInColumns(new String[]{"EL PARRAFO QUE DEBE SALIR BIEN EN LOS COSOS QUE TIENEN MUCHAS LETRAS Y SOLO SE QUIERE COLUMNAS DE una"/*,"OTRA COLUMNA PARA QUE PARTAMOS LA VAINA","PRUEBA CON 3 COLUMNAS :)"*/}, 20));
		/*String[] cols = new String[]{"Interes corrientes obligacion 2 del 1 al 30 de junio de 2013","00583,576"};
		int[] colsAlign = new int[]{ALIGN_LEFT,ALIGN_RIGHT,ALIGN_LEFT,ALIGN_CENTER};
		int[] colsWidth = new int[]{15,10,15,10};
		cols = new String[]{"Interes corrientes obligacion 2 del 1 al 30 de junio de 2013","00583,576","Deuda saldada :)","nla la laa la alala"};
		System.err.println(fixInColumns(cols,colsAlign,colsWidth,"|"));		
		cols = new String[]{"12/05/2013","Interes corrientes obligacion 2 del 1 al 30 de junio de 2013","00583,576","Deuda saldada :)"};
		System.err.println("\n\n");
		cols = new String[]{"FECHA","DESCRIPCION","VALOR","MENSAJE"};
		System.err.println(fixInColumns(cols,15));
		cols = new String[]{"12/05/2013","Interes corrientes obligacion 2 del 1 al 30 de junio de 2013","00583,576","Deuda saldada :)"};
		System.err.println(fixInColumns(cols,15));
		cols = new String[]{"Gracias por su donación, será destinada para los Discapacitados de Antioquia del Programa Vamos Contigo, cualquier solicitud puede llamar a FUNDAGANA 4444441 ext. 1224"};
		System.err.println(fixInColumns(cols,ALIGN_CENTER,50));
		/*
		int i;
		String texto = "hola|mundo;wqeqeqeewqcruel";
		String s[] = divideTokenizer( texto, 20, ";" );
		for( i=0; i<s.length; i++ ){
			System.out.println(i+":"+s[i]);
		}*/
//		Pattern p = Pattern.compile("[Ññ]");
//		Matcher m = p.matcher("jfkfjdksñhfdjasfysdui Ñ ydfuisyf");
//		System.err.println(m.replaceAll("-"));
//		m.reset();
//
//		StringBuffer sb = new StringBuffer();
//		while ( m.find() ){
//			m.appendReplacement( sb, "Nn" );
//		}
//		m.appendTail(sb);
//		System.err.println(sb.toString());
	}
	
	public static int getHashCode( Object obj ){
		if( obj == null )
			return 0;
		return obj.hashCode();
	}
	
	/**
	 * 
	 * Nombre del método: convertirStringAUnicode
	 * 
	 * Descripción: <i>Método para convertir una cadena con caracteres
	 * especiales a la forma #u{Número_Unicode_Caracter}. Los caracteres
	 * normales los deja iguales </i>
	 * 
	 * @author Lina Monsalve
	 * @param datoOriginal
	 * @return
	 *
	 *         MODIFICACIONES:
	 * 
	 *         Fecha: 6/11/2015 Autor - Empresa: Lina Monsalve - Matrixtech.
	 *         Descripción: Creación del método
	 */
	public static String convertirStringAUnicode(String datoOriginal) {
		char[] caracteres = datoOriginal.toCharArray();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < datoOriginal.length(); i++) {
			// revisa si el caracter no es especial, y se pueda agregar
			// normalmente.
			// Utiliza 125 como el máximo permitido de caracteres normales
			if (Character.codePointAt(datoOriginal, i) < 125) {
				stringBuilder.append(caracteres[i]);
			}

			// Convierte el resto a #u{Numero_Unicode}
			else {
				stringBuilder.append(String.format("#u%04x",
						(int) caracteres[i]));
			}
		}
		return stringBuilder.toString();
	}


	/**
	 * 
	 * Nombre del método: convertirUnicodesACaracteres
	 * 
	 * Descripción: <i>Método para reemplazar #u{Número_Unicode_Caracter} por su
	 * representación string</i>
	 * 
	 * @author Lina Monsalve
	 * @param cadenaUnicode
	 * 
	 *            MODIFICACIONES:
	 * 
	 *            Fecha: 6/11/2015 Autor - Empresa: Lina Monsalve - Matrixtech.
	 *            Descripción: Creación del método
	 */
	public static String convertirUnicodesACaracteres(String cadenaUnicode) {
		Matcher matcher = Pattern.compile("\\#u((?i)\\w{4})").matcher(
				cadenaUnicode);
		while (matcher.find()) {
			//Se toma el número unicode del caracter
			int codePoint = Integer.valueOf(matcher.group(0).substring(2), 16);
			//Se reemplaza en la cadena
			cadenaUnicode = cadenaUnicode.replace(matcher.group(0),
					String.valueOf((char) codePoint));
		}

		return cadenaUnicode;
	}
	
	public static int stringToInt(String s) {
		int retorno = 0;
		try {
			retorno = Integer.parseInt( s );
		}catch(NumberFormatException ex){
			retorno = 0;
		}catch(NullPointerException ex){
			retorno = 0;
		}
		return retorno;
	}

	public static double stringToDouble(String s) {
		double retorno = 0;
		try {
			retorno = Double.parseDouble( s );
		}catch(NumberFormatException ex){
			retorno = 0;
		}
		return retorno;
	}
	
	public static String longToString(long l) {
		String retorno = "";
		try {
			retorno = String.valueOf( l );
		}catch(NumberFormatException ex){
			retorno = "";
		}
		return retorno;
	}
}