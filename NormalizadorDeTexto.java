import java.text.Normalizer;
 
/*
  Clase que sirve para quitar caracteres especiales de un texto dado.
*/
public class NormalizadorDeTexto {
 
    public static void main(String[] args) throws Exception {
        String cadena = "áéíóú";
        System.out.println(cadena + " = " + cleanString(cadena));
        cadena = "àèìòù";
        System.out.println(cadena + " = " + cleanString(cadena)); 
        cadena = "äëïöü";
        System.out.println(cadena + " = " + cleanString(cadena));
        cadena = "âêîôû";
        System.out.println(cadena + " = " + cleanString(cadena));
    }
 
    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
}

//Resultado de la ejecución
//áéíóú = aeiou
//àèìòù = aeiou
//äëïöü = aeiou
//âêîôû = aeiou
