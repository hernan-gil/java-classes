package default;

public class ParseStringBuffer {

	StringBuffer s = new StringBuffer("");
	boolean isEmpty = true;

	public ParseStringBuffer(){
	}

	public void append( Object o ){
		if( !isEmpty ){
			s.append("|");
		}
		s.append( o );
		if( isEmpty ){
			if( s.length()>0 )
				isEmpty = false;
		}
	}

	public String toString() {
		return s.toString();
	}

	public StringBuffer getStringBuffer(){
		return s;
	}

}