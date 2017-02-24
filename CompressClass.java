/*
I am trying to compress a String. 
For example, if the user input is "aaabbcccd" - the output should count the letters and if count is above 1, print the letter, 
then the number: a3b2c3d1.

if the String to be compressed is small than the result String it may return the string to be compressed as result.
*/

public class CompressClass {

public static String stringCompression(String str){
StringBuilder sb = new StringBuilder();
int count = 0;
if(null != str) {
  char current = str.charAt(0);
  for (int i = 0; i < str.length; i++){
    if(str.charAt(i) == current) {
      count++;
    } else {
      sb.append(current).append(count);
      count = 1;
      current = str.charAt(i);
    }
  }
  sb.append(current).append(count);
} else{
  return "Error: null string";
}
if (str.length() <= sb.toString().length())
  return str;
else
  return sb.toString();
}
}
public static void main(String[] args) {
    String tobeCompressed = "aaabbccccd";
    System.err.println("String to be compressed: " + tobeCompressed + " / Result: " + stringCompression(tobeCompressed));
}
}
