package dictionary;
/**
 * <p>
 * Title: DictEntry Class - A class component of the Dictionary system
 * </p>
 *
 * <p>
 * Description: An entity object class that implements a dictionary entry
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2018-05-05
 * </p>
 *
 * @author Lynn Robert Carter, Jaskirat, sawan
 * @version 2.00 - Baseline for transition from Swing to JavaFX 2018-05-05
 * @version 2.01 - Installed in PKMT Tool
 * 
 * @param <T,U> T and U are generic parameters that will be used as string in this project
 * 				 but can be converted into any data type because of generic type.
 */

public class DictEntry<T,U> {

	/**********************************************************************************************

	Class Attributes

	 **********************************************************************************************/
	

	private T word;
	private U definition;
	/**********************************************************************************************

	Constructors

	 **********************************************************************************************/

	/**********
	 * This is the default constructor.  We do not expect it to be used.
	 */
	public DictEntry() {
		word = null;
		definition = null;
	}

	/**********
	 * This is defining constructor.  This is the one we expect people to use.
	 */
	public DictEntry(T w, U d) {
		word =  w;
		definition = d;
	
	}

	/**********************************************************************************************

	Standard support methods

	 **********************************************************************************************/

	/**********
	 * This is the debugging toString method.
	 */
	public String toString(){
		return word + "\n" + definition;
	}

	/**********
	 * This is the formatted toString method.
	 * @return String in a purticular format
	 */
	public String formattedToString(){
		return word + "\n" + definition + "\n--------------------\n";
	}

	/**********
	 * These are the getters and setters for the class
	 * @return returns string from first generic type.
	 */
	
	public String wordToString(){return word + "\n";}
	
	/**********
	 * 
	 * @return returns string casted from first generic type.
	 */
	public String getWord(){return (String) word;}
	
	/**********
	 * 
	 * @return returns Integer casted from first generic type.
	 */
	
	public Integer getInt(){return (Integer) word;}
	
	/**********
	 * 
	 * @return returns Double casted from first generic type.
	 */
	
	public Double getDouble(){return (Double) word;}
	
	/**********
	 * 
	 * @return returns string casted from Second generic type.
	 */
	
	public U getDefinition(){return definition;}
	
	/**********
	 * @param<T> w is generic type object. 
	 * @return returns generic type of first generic object.
	 */
	
	public void setWord(T w) {
		word= w;
	}
	
	/**********
	 * @param<T> w is generic type object. 
	 * @return returns generic type of second generic object.
	 */
	
	public void setDefinition(U def) {
		definition=def;
	}


}
