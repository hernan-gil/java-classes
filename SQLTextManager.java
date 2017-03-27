package default;


/**
 * Utilidades para manejar Strings
 */
public class SQLTextManager extends TextManager {

	public SQLTextManager(){
	}

	public static void adicionarCondicion( StringBuffer condicion, String condicionParcial ){
		if( condicion.toString().equals("") )
			condicion.append( " WHERE " );
		else
			condicion.append( " AND " );
		condicion.append( condicionParcial );
	}

}