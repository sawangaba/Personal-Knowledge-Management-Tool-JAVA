package contacts;

import java.util.Arrays;

import java.util.List;
import java.util.Scanner;

import contacts.ContactEntry;

/*******************************************************************************
 * This Class acts as contact-archive for PKMT Tool which will be used in the first tab of PKMT
 * @author JSGREWAL
 * @version 1.00 2018-11-14 The Base-Object
 */
public class Contacts {
	private int numEntries = 0;							// The number of entries in the contacts
	private ContactEntry[] entry;							// The actual contacts as an array
	private String searchString = "";					// The string to use for searching
	private int currentIndex = -2;						// The index into the contacts; this is
	// use when two separate commands need
	// to access the same entry. -2 means
	// that no search string has been set
	// or no more items can be found

	private final int maxNumberOfEntries = 100000;		// The maximum number of contacts entries

	private String name = null;								// Establish the word to be defined
	private String address = null;							// Establish the definition of the word
	private String phone = null;
	private String email=null;
	private String largestWord = "";					// Keep track of the largest word read in

	private int numberOfSearchItemsFound = 0;			// How many search items found?
	public Contacts() {
		numEntries = 0;									// Start at the beginning of the array
		entry = new ContactEntry[maxNumberOfEntries];		// Allocate the array
		name = null;
		address = null;
		phone =null;
		email=null;
	}
	public void defineContacts(Scanner contReader) {
		if(contReader == null)return;					// If there is no Scanner, just return


		// Establish the first word to be defined
		if (contReader.hasNextLine()){					// See if there is a first line in the file
			name = contReader.nextLine().trim();		// Establish the word to be defined

			// Skip blank lines
			while (contReader.hasNextLine() && name.length() == 0){
				name = contReader.nextLine().trim();
			}
		}
		else
			return;

		// Use the word plus a definition to define a contacts entry. The loop assumes that a
		// word to be defined has been found and loops adding a definition to it followed by an
		// attempt to find the next word to be defined.
		while (contReader.hasNextLine()){

			// Establish the definition of the word
			String theNextLine = contReader.nextLine() + "\n";

			// Keep adding in more lines as long as the line starts with white space
			while (theNextLine.substring(0,1).trim().length()==0)address += theNextLine;
			if (contReader.hasNextInt()) {
				phone = contReader.nextLine();
				if (contReader.hasNextLine()) {
					email+=contReader.nextLine();
				}
			}




			// Check to see if this word, if added to the contacts, would cause an overflow
			// If so, just return and display an error message
			if (entry.length <= numEntries + 1) {
				System.out.println("*** Too many words in the contacts. The excess are discarded!");
				return;
			}

			// A word and a definition have been established... create a new contacts entry
			this.addEntry(name, address, phone, email);

			// Set up for the next entry 
			address = "";				// by resetting the definition
			name = theNextLine.trim();		// and look for the next word to be defined

			// Skip blank lines while looking for the word to be defined or the end of the file
			while (contReader.hasNextLine() && name.length() == 0){
				name = contReader.nextLine().trim();
			}

		}
	}
	public void addEntry(String n, String a, String p, String e){	

		// See if there is enough room for another entry.
		if (entry.length > numEntries + 1) {

			// See if it is larger than the largest word in the contacts (lexicographically)
			if (n.compareTo(largestWord) > 0) {
				// The new word being defined is larger than the largest so it can be appended
				largestWord = n;									// Keep track of the new
				entry[numEntries++] = new ContactEntry(n, a,p,e);			// largest word
			} else {
				// The new words is not larger than the largest so it must be inserted into the
				// array somewhere before the end.  Loop through the contacts entries, moving
				// each entry to the right one position until you find an entry smaller than 
				// the new one or you reach the start of the array.  The insert the new entry.
				int ndx = numEntries++;
				while (ndx >= 1 && entry[ndx-1].getName().compareTo(n) >= 0) {
					entry[ndx] = entry[ndx-1];
					ndx--;
				}
				entry[ndx] = new ContactEntry(n,a,p,e);
			}
		}

	}
	/**********
	 * Fetch the number of words in the contacts
	 * 
	 * @return the number of words defined in the contacts
	 */
	public int getNumEntries(){return numEntries;}

	/***********
	 * Set the string that will be used in searching for words in the contacts
	 * 
	 * @param s - This is the string to be used by the search
	 */
	public void setSearchString(String s){
		searchString = s.toUpperCase();			// Use upper case for searching
		currentIndex = -1;						// -1 means the search string has been defined, but
		// no values have been found
	}

	/**********
	 * Find the next entry that contains the search string. If it finds a match, it returns a
	 * contacts entry that matches. If there is no match or there is no search string, the
	 * method returns a null
	 * 
	 * @return a matching contacts entry or return a null
	 */
	public ContactEntry findNextEntry(){
		if (currentIndex < -1) return null;		// Return null if no search string set
		while (currentIndex < numEntries-1) {	// While there are entries left to search
			ContactEntry ce = entry[++currentIndex];	// Fetch the entry, convert it to upper case
			if (ce.getName().toUpperCase().indexOf(searchString)>=0) // If there is a match
				return ce;						// return the match to the caller and do not
		}										// finish the loop
		currentIndex = -2;			// If the loop finishes, signal that by resetting the index
		return null;				// so there is no search string and return a null
	}
	public ContactEntry findAllContacts() {
		if (currentIndex < -1) return null;		// Return null if no search string set
		while (currentIndex < numEntries-1) {	// While there are entries left to search
			ContactEntry ce = entry[++currentIndex];	// Fetch the entry, convert it to upper case
			// If there is a match
			return ce;						// return the match to the caller and do not
		}										// finish the loop
		currentIndex = -2;			// If the loop finishes, signal that by resetting the index
		return null;				// so there is no search string and return a null
	}
	/**********
	 * Find all of the entries that have a word that contains the search string specified by the
	 * method parameter
	 * 
	 * @param target - is the String that should be used to drive the search
	 * @return - an empty String or a String of the sequence of entries that match the search String
	 */
	public String findAll(String target){
		String result = "";						// Set the default condition for the result string
		setSearchString(target);				// Set the search target string
		numberOfSearchItemsFound = 0;			// Reset the number of search items found
		ContactEntry aMatch = findNextEntry();		// Try to fetch the next entry
		if (aMatch == null) return "";			// If there isn't one, return an empty string

		// Since there is one, there may be many, so we will loop looking to add them together
		while (aMatch != null) {				// See of there is one to add to the string
			numberOfSearchItemsFound++;			// Increment the number of search items found
			result += "\n" + aMatch.getName()  + "\n" + aMatch.getAddress()+"\n"+aMatch.phone+"\n"+aMatch.getEmail() + "\n----------\n";
			aMatch = findNextEntry();			// If so, add it and then search for the next
		}										// The loop ends when there are no more matches
		return result;		// Return the String of matches to the caller
	}

	/**********
	 * Get a specified contacts entry
	 * 
	 * @param ndx - The index of the entry to be fetched
	 * 
	 * @return the DictrEntry corresponding to the specified index or return a null
	 */
	public ContactEntry getContactEntry(int ndx){		// Check the index to see if it is in range
		if (ndx < 0 || ndx >= numEntries) return null;	// If not, return a null
		return entry[ndx];						// If it is, return the specified entry
	}

	/**********
	 * Remove a specified contacts entry
	 * 
	 * @param ndx - The index of the entry to be removed
	 * 
	 * @return the DictrEntry corresponding to the specified index or return a null
	 */
	public void remove(int ndx){		// Check the index to see if it is in range
		if (ndx < 0 || ndx >= numEntries) return;	// If not, just return doing nothing
		for (int i=ndx; i < numEntries-1; i++)		// Otherwise, shift the following entries to
			entry[i] = entry[i+1];					// the left by one
		entry[--numEntries] = null;		// Update the number of entries and clear the removed entry
		return;							// If it is, return the specified entry
	}

	/**********
	 * Remove the current contacts entry
	 * 
	 * @return the DictrEntry corresponding to the specified index or return a null
	 */
	public void remove(){				// Check the index to see if it is in range
		if (currentIndex < 0 || currentIndex >= numEntries) return;	// If not, just return
		for (int i=currentIndex; i < numEntries-1; i++)	// Otherwise, shift the following entries
			entry[i] = entry[i+1];						// to the left by one
		entry[--numEntries] = null;		// Update the number of entries and clear the removed entry
		currentIndex = -1;				// Reset the current index so it does not point to anything
		return;							// If it is, return the specified entry
	}

	/**********
	 * List all of the entries in the contacts
	 * 
	 * @param target - is the String that should be used to drive the search
	 * @return - an empty String or a String of the sequence of entries that match the search String
	 */
	public String listAll(){
		if (numEntries <=0) return "The contacts is empty.\n----------\n";
		String result = "";	// Set the result empty and then add all of the entries to it
		for (int ndx = 0; ndx < numEntries; ndx++)
			result += "\n" + (ndx+1) + ". " + entry[ndx].getName() + "\n" + entry[ndx].getAddress() +"\n"+entry[ndx].getPhone()+"\n"+entry[ndx].getEmail()+ "\n----------\n";
		return result;		// Return the String  to the caller
	}

	/**********
	 * Get the index of the current entry
	 * 
	 * @return - an index
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**********
	 * Set the index of the current entry
	 * 
	 * @return - an index
	 */
	public void setCurrentIndex(int ndx) {
		currentIndex = ndx;
	}

	/**********
	 * Get the index of the current entry
	 * 
	 * @return - an index
	 */
	public int getNumberSearchItemsFound() {
		return numberOfSearchItemsFound;
	}

	/***
	 * Set the element at given index
	 * @param index 
	 * @param c element
	 */
	public void setContactEntry(int index, ContactEntry c) {
		entry[index]=c;
	}

	/***
	 * Get Index of Given Entry
	 * @param c the entry
	 * @return the Index
	 */
	public int getIndex(ContactEntry c) {
		List<ContactEntry> theList=Arrays.asList(entry);
		return theList.indexOf(c);
	}

	/***
	 * Set the contacts empty
	 */
	public void setEmpty() {
		numEntries = 0;									// Start at the beginning of the array
		entry = new ContactEntry[maxNumberOfEntries];		// Allocate the array
		name = null;
		address = null;
		phone =null;
		email=null;

	}
}


