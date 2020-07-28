package mainline;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import contacts.ContactEntry;
import contacts.Contacts;
import dictionary.DictEntry;
import dictionary.Dictionary;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;



/*******
 * <p> Title: UserInterface Class. </p>
 * 
 * <p> Description: A JavaFX application: This controller class that provide the User Interface and 
 * the business logic for the application.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2018-08-28 </p>
 * 
 * @author Lynn Robert Carter, Jaskirat Singh Grewal, Sawan
 * 
 * @version 1.00	2018-08-28 Baseline
 * @version 2.00 	2018-11-14 Complete PKMT Supporting Contacts, Dictionary, Notes and To-Do Features.
 * @version 3.00 	2019-02-05 Complete PKMT Supporting Contacts, Dictionary, Notes and To-Do Features with addition of generic API.
 */
public class UserInterface {

	/**********************************************************************************************

	Class Attributes

	 **********************************************************************************************/

	// Attributes used to establish the display and control panel within the window provided to us
	// This values are passed in when this class is instantiated
	private static double CONTROL_PANEL_HEIGHT;
	private static double WINDOW_HEIGHT;
	private static double WINDOW_WIDTH;


	private Stage primaryStage;

	private static double MARGIN_WIDTH = 5;


	// These attributes put a graphical frame around the portion of the window that receives the
	// text for the various tabs
	private Rectangle rect_outer;
	private Rectangle rect_middle;
	private Rectangle rect_inner;

	// This is the root of the user interface
	private Group theRoot = null;

	//------------------------------------------------------------------------
	private Tab contactsTab = new Tab("Contacts     ");
	private Group contactControls = new Group();

	private Tab dictionaryTab = new Tab("Dictionary     ");
	private Group dictionaryControls = new Group();

	private boolean dictionaryFileReadControlsAreSetUp = false;
	private Group dictionaryFileReadControls = new Group();

	private boolean dictionaryFindControlsAreSetUp = false;
	private Group dictionaryFindControls = new Group();

	private Tab ntdTab = new Tab("Notes / To-Do List  ");
	private Group ntdControls = new Group();


	//------------------------------------

	//------------------------------------
	// Class attributes for Dictionary Tab

	// This is the attribute that holds the reference to the baseline dictionary
	private Dictionary theDictionary = null;


	// The User Interface widgets used to control the Dictionary functions
	private Label lbl_FileName = new Label("Enter the dictionary's file name here:");
	private TextField fld_DictionaryFileName = new TextField();
	private String str_DictionaryFileName;
	private Label lbl_FileFound = new Label("");
	private Label lbl_FileNotFound = new Label("");
	private Label lbl_ErrorDetails = new Label("");
	private Scanner scnr_Input;
	private String str_FileContentsError = "";

	private Button btn_Dictionary_Load = new Button("Load the dictionary");
	private Label lbl_NumberOfDefinitions = new Label("");
	private Label lbl_EnterSearchText = new Label("Enter search text:");

	private Button btn_SearchContact = new Button("Search Contact");
	private Button btn_Search = new Button("Search");
	private Label lbl_NumberOfSearchItemsFound = new Label("");
	private int numberOfSearchItemsFound = -1;
	private TabPane tabPane;
	private DictEntry<String,String> selectedEntry;

	private List<String> toDoList = new Vector<String>();

	// GUI elements for the Edit Pop-up
	private Button btn_EditPopup = new Button("Edit");
	private boolean editDeletePopupDisplayed = false;
	private Label lbl_1SelectDefinition = new Label("1. Select a definition");
	@SuppressWarnings("rawtypes")
	private List <DictEntry> editDefinitionList = new Vector <DictEntry>();
	private List <ContactEntry> editContactList = new Vector<ContactEntry>();
	private ComboBox <String> editComboBox = new ComboBox <String>();
	private ComboBox <String> contactComboBox = new ComboBox<String>();
	private ComboBox <String> todoComboBox =new ComboBox<String>();
	private Label lbl_2EditDefinition = new Label("2. Edit a definition");
	private Label lbl_2aWord = new Label("The word or phrase to be defined");
	private TextField fld_2aWord = new TextField();
	private Label lbl_2bDefinition = new Label("The definition of the word or phrase");
	private TextArea txt_2bDefinition = new TextArea();
	private Label lbl_3SaveDefinition = new Label("3. Save a definition");
	private int ndxSaveEdit;
	private Button btn_SaveEditChanges = new Button("Save");

	// GUI elements for the Delete Pop-up
	private Button btn_DeletePopup = new Button("Delete");
	private Label lbl_3DeleteDefinition= new Label("3. Delete the Definition");
	private Button btn_Delete = new Button("Delete");

	// GUI elements for the Add Pop-up
	private Button btn_AddPopup = new Button("Add");
	private Label lbl_addDefinition= new Label("Add the Definition");
	private Label lbl_2AddDefinition= new Label("2. Write the Definition");
	private Button btn_AddChanges= new Button("Add");

	// The User Interface widgets used to control the Contacts functions

	private Button btn_addContact = new Button("Add Contact");
	private Label lbl_addContact = new Label("Add Contact");
	private Label lbl_name = new Label("Name");
	private TextField fld_name = new TextField();
	private TextField fld_address= new TextField();
	private Label lbl_address= new Label("Address");
	private TextField fld_phone = new TextField("");
	private Label lbl_phone = new Label("Phone");
	private Label lbl_email = new Label("E-Mail");
	private TextField fld_email = new TextField();
	private Label lbl_NumberOfContacts = new Label();
	private boolean contactControlsAreSetUp=false;
	private TextField fld_SearchText = new TextField();
	private TextField fld_SearchContact = new TextField();
	private Button btn_delContact = new Button("Delete");

	private Contacts theContacts = new Contacts();

	// GUI Element for the Add, Edit and Delete Contacts Popup
	private Label lbl_SelectContact = new Label();
	private Label lbl_delName = new Label("Name of Contact to be Deleted");
	private TextField fld_delName = new TextField();
	private Label lbl_delDetails = new Label("Details of Contact to be Deleted");
	private TextArea fld_delDetails = new TextArea();
	private Label lbl_delContact= new Label("Delete This Contact");
	private Button btn_DeleteContact = new Button("Delete Contact");
	private Button btn_editContact = new Button("Edit");
	private Label lbl_editName = new Label("Edit the Name of Contact");
	private TextField fld_editName = new TextField();
	private Label lbl_editAddress = new Label("Edit the Address of Contact");
	private TextField fld_editAddress = new TextField();
	private Label lbl_editPhone= new Label("Edit the Phone Number of Contact");
	private TextField fld_editPhone = new TextField();
	private TextField fld_editEmail = new TextField();
	private Label lbl_editEmail = new Label("Edit the e-Mail of Contact");
	private Button btn_SaveEditContact = new Button("Save");
	private ContactEntry selectedContact;
	// The User Interface widgets used to control the Contacts functions

	ToggleGroup rbGroup = new ToggleGroup();
	RadioButton rb_Notes = new RadioButton("Notes");
	RadioButton rb_todo = new RadioButton ("To-Do List");
	Button todo_Add = new Button ("Add Task");
	Button todo_Delete = new Button("Delete Task");
	private Label lbl_tdEntry = new Label();
	private Button btn_AddToDoEntry= new Button("Add To-Do");
	private TextField fld_tdEntry = new TextField();
	private Label lbl_SelectEntry= new Label();
	private TextField fld_delEntry=new TextField();
	private Button btn_DeleteEntry= new Button("Delete Entry");


	// The Dialogs Of PKMT
	final Stage addDialog = new Stage();
	final Stage editDialog = new Stage();
	final Stage deleteDialog = new Stage();
	final Stage addContactsDialog = new Stage();
	final Stage deleteContactsDialog = new Stage();
	final Stage editContactsDialog = new Stage();
	final Stage addToDoDialog = new Stage();
	final Stage deleteToDoDialog = new Stage();
	/**********
	 * This constructor establishes the user interface with the needed tabs and widgets
	 * 
	 * @param r		The root of the widgets
	 * @param t		The TabPane we are to use
	 * @param h		The height we should use for the application window
	 * @param w		The width of the application window
	 * @param cph	The location of the controls widgets
	 */
	public UserInterface(Stage ps, Group r, TabPane t, double h, double w, double cph) {

		/**********************************************************************************************

		Class Attributes

		 **********************************************************************************************/

		primaryStage = ps;

		// Set the Stage boundaries to the visual bounds so the window does not totally fill the screen 
		WINDOW_WIDTH = w;
		WINDOW_HEIGHT = h;
		CONTROL_PANEL_HEIGHT = cph;

		tabPane = t;
		theRoot = r;

		// Setting Owner and Modality for Each Dialog 
		//---------------------------------------------------------------------
		addDialog.initModality(Modality.APPLICATION_MODAL);
		addDialog.initOwner(primaryStage);

		editDialog.initModality(Modality.APPLICATION_MODAL);
		editDialog.initOwner(primaryStage);

		deleteDialog.initModality(Modality.APPLICATION_MODAL);
		deleteDialog.initOwner(primaryStage);
		//------------------------------------------------------------------------
		addContactsDialog.initModality(Modality.APPLICATION_MODAL);
		addContactsDialog.initOwner(primaryStage);

		deleteContactsDialog.initModality(Modality.APPLICATION_MODAL);
		deleteContactsDialog.initOwner(primaryStage);

		editContactsDialog.initModality(Modality.APPLICATION_MODAL);
		editContactsDialog.initOwner(primaryStage);
		//-------------------------------------------------------------------------
		addToDoDialog.initModality(Modality.APPLICATION_MODAL);
		addToDoDialog.initOwner(primaryStage);

		deleteToDoDialog.initModality(Modality.APPLICATION_MODAL);
		deleteToDoDialog.initOwner(primaryStage);

		// These attributes put a graphical frame around the portion of the window that holds the
		// information this knowledge management tool hold, displays, and manipulates.  These
		// graphical elements give a three-dimensional look to the user interface.
		rect_outer =  new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		rect_middle = new Rectangle(5, 5, WINDOW_WIDTH-10, CONTROL_PANEL_HEIGHT-15);
		rect_inner =  new Rectangle(6, 6, WINDOW_WIDTH-12, CONTROL_PANEL_HEIGHT-17);

		// Set the fill colors for the border frame to give that three-dimensional look
		rect_outer.setFill(Color.LIGHTGRAY);
		rect_middle.setFill(Color.BLACK);
		rect_inner.setFill(Color.WHITE);

		// Use a BorderPane to hold the various tabs
		BorderPane borderPane = new BorderPane();
		borderPane.setLayoutX(9);							// The left edge
		borderPane.setLayoutY(9);							// The upper edge
		borderPane.setPrefWidth(WINDOW_WIDTH-18);			// The right edge
		borderPane.setPrefHeight(CONTROL_PANEL_HEIGHT-24);	// The lower edge

		// Sets up the basic elements for the contacts tab 
		borderPane.setCenter(tabPane);
		contactsTab.setOnSelectionChanged((event) -> { try {
			contactsChanged();
		} catch (IOException e) {

			e.printStackTrace();

		} });
		TextArea content = new TextArea("This is the text for contacts.");
		content.setEditable(false);
		contactsTab.setContent(content);
		tabPane.getTabs().add(contactsTab);

		// Sets up the basic elements for the dictionary tab 
		dictionaryTab.setOnSelectionChanged((event) -> {  try {
			dictionaryChanged();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} });
		content = new TextArea("This is the text for dictionary definitions.");
		content.setEditable(false);
		dictionaryTab.setContent(content);
		tabPane.getTabs().add(dictionaryTab);

		// Sets up the basic elements for the notes and to-do tab
		ntdTab.setOnSelectionChanged((event) -> {  try {
			ntdChanged();
		} catch (IOException e) {

			e.printStackTrace();
		} });

		// The radio buttons for the selection of Notes and To Do Content
		rb_Notes.setToggleGroup(rbGroup);
		rb_todo.setToggleGroup(rbGroup);
		rb_todo.setSelected(true);

		content = new TextArea("Enter your Notes Here");
		content.setEditable(false);
		ntdTab.setContent(content);
		tabPane.getTabs().add(ntdTab);

		// Initializing the To-Do Content for the Launch
		ntdControls.getChildren().addAll(rb_Notes, rb_todo, todo_Add, todo_Delete);
		// Actions of RadioButtons and To-Do Action Buttons
		todo_Add.setOnAction((event)->{setupAddToDoDialog();});
		todo_Delete.setOnAction((event)->{setupDeleteToDoDialog();});
		rb_Notes.setOnAction((event) -> { 
			todo_Add.setVisible(false);
			todo_Delete.setVisible(false);
		});

		rb_todo.setOnAction((event) -> { 
			todo_Add.setVisible(true);
			todo_Delete.setVisible(true);
		});

		// Locating the RadioButtons
		rb_Notes.setLayoutX(300); rb_Notes.setLayoutY(330);
		rb_todo.setLayoutX(200); rb_todo.setLayoutY(330);

		todo_Add.setLayoutX(180); todo_Add.setLayoutY(300);
		todo_Delete.setLayoutX(280); todo_Delete.setLayoutY(300);

		ntdTab.setOnSelectionChanged((event) -> { try {
			ntdChanged();
		} catch (IOException e) {

			e.printStackTrace();
		}});




		// Place all of the just-initialized GUI elements into the pane with the exception of the
		// Stop button.  
		theRoot.getChildren().addAll(rect_outer, rect_middle, rect_inner, borderPane, contactControls, ntdControls);

	}





	//-------------------------------------------------------------------------------------
	//
	// The following private methods are used to simplify the code to initialize GUI widgets
	//
	//-------------------------------------------------------------------------------------

	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a text area
	 */
	private void setupTextAreaUI(TextArea a, String ff, double f, double w, double h, double x, double y, boolean e){
		a.setFont(Font.font(ff, f));
		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}


	//---------------------------------------------------------------------------------------------
	//
	// The following private methods are used to manage the various kinds of knowledge this
	// tools supports (Contacts ; Dictionary ; Notes/To-Do
	//
	//---------------------------------------------------------------------------------------------

	/**********
	 * Whenever the user enters or leaves the Contacts Tab, this method is called.
	 * @throws IOException When the Repository File Gets Corrupted
	 */
	private void contactsChanged() throws IOException {

		// Determine if entering the Tab or leaving it
		if (!contactsTab.isSelected()) {
			contactControls.setVisible(false);
			// When the execution gets here, the user is leaving the tab
			return;
		}
		// Remove all the irrelevant data
		rb_Notes.setVisible(false);
		rb_todo.setVisible(false);
		todo_Add.setVisible(false);
		todo_Delete.setVisible(false);

		// If there is no repository directory and file, create it
		File theDirectory = new File("RepositoryOut");
		if (!theDirectory.exists()) {
			theDirectory.mkdir();
			theDirectory.setReadable(true);
			theDirectory.setWritable(true);

		}
		File theDataFile = new File("RepositoryOut/Contacts.txt");
		if (!theDataFile.exists()) theDataFile.createNewFile();
		// If contact controls are not already set up 
		if (!contactControlsAreSetUp) { // then set them up
			contactControls.getChildren().clear();
			contactsSetupFileControls();
			contactControls.setVisible(true);
			return;
		}
		// Make the controls visible
		contactControls.setVisible(true);

	}
	/******
	 * This method sets up the File Controls for Showing On the Contacts Tab.
	 * @throws FileNotFoundException
	 */
	private void contactsSetupFileControls() throws FileNotFoundException {
		TextArea newContent = new TextArea();
		newContent.setEditable(false);
		contactsTab.setContent(newContent);
		// Display the Content From the Repository
		Scanner contScanner = new Scanner(new File("RepositoryOut/Contacts.txt"));
		while (contScanner.hasNextLine()) {
			newContent.appendText(contScanner.nextLine()+"\n");
		}
		contScanner.close();
		//  Setting Up on The GUI 
		setupButtonUI(btn_addContact, "Arial", 14, 100, Pos.BASELINE_LEFT, WINDOW_WIDTH - 375,  
				CONTROL_PANEL_HEIGHT + 10);
		setupButtonUI(btn_delContact,"Arial",14,100,Pos.BASELINE_LEFT,WINDOW_WIDTH-275,CONTROL_PANEL_HEIGHT+10);
		setupButtonUI(btn_editContact,"Arial",14,100,Pos.BASELINE_LEFT,WINDOW_WIDTH-175,CONTROL_PANEL_HEIGHT+10);
		setupLabelUI(lbl_EnterSearchText, "Arial", 14, WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT + 25);
		setupLabelUI(lbl_NumberOfContacts, "Arial", 14, WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT);
		setupTextUI(fld_SearchContact, "Arial", 14, WINDOW_WIDTH / 3, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT + 45, true);
		contactControls.getChildren().clear();
		lbl_NumberOfContacts .setText("The number of contacts: " + theContacts.getNumEntries());
		setupButtonUI(btn_SearchContact, "Arial", 14, 100, Pos.BASELINE_LEFT, WINDOW_WIDTH - 375,  
				CONTROL_PANEL_HEIGHT + 45);

		// Setting up the actions related to each button
		btn_delContact.setOnAction((event)->{setupContactsDeletePopup();});
		btn_addContact.setOnAction((event)->{setupContactAddPopup();});
		btn_editContact.setOnAction((event)->{setupContactEditPopup();});
		btn_SearchContact.setOnAction((event)->{searchForContact();});

		// Retrieve the Contacts from the Repository
		setUpContacts();

		// Add All Widgets to The GUI
		contactControls.getChildren().add(btn_addContact);
		contactControls.getChildren().add(btn_editContact);
		contactControls.getChildren().add(lbl_NumberOfContacts);
		contactControls.getChildren().add(btn_delContact);
		contactControls.getChildren().add(lbl_EnterSearchText);
		contactControls.getChildren().add(fld_SearchContact);	
		contactControls.getChildren().add(btn_SearchContact);	
		// Once the controls are set up, tell the same to the caller
		contactControlsAreSetUp=true;
	}

	/****
	 * The following routine search for user-entered string in the Contacts object
	 * and display it on the TextArea of the Contacts Tab.
	 */
	private void searchForContact() {
		TextArea newContent = new TextArea(theContacts.findAll(fld_SearchContact.getText()));
		newContent.setEditable(false);
		contactsTab.setContent(newContent);

	}
	/****
	 * The following routine sets up the widgets for the Edit Contacts Dialog Box.
	 */
	private void setupContactEditPopup() {
		editContactsDialog.setTitle("1. Select a Contact, 2. Delete It!");
		Group contactsEditControls = new Group();


		// Reset the comboBox list and the list of contacts back to empty
		contactComboBox.getItems().clear();
		editContactList.clear();

		// Fetch all the contacts and place them into a list
		for (int ndx = 0; ndx < theContacts.getNumEntries(); ndx++) {
			ContactEntry c = theContacts.getContactEntry(ndx);
			contactComboBox.getItems().add(c.getName());
			editContactList.add(c);
		}

		// Set up the pop-up window
		Scene dialogScene = new Scene(contactsEditControls, 500, 400);
		setupLabelUI(lbl_SelectContact, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
		lbl_SelectContact.setText("Select the Contact to be Edited");
		// Set up the comboBox to select one of the dictionary items that matched
		contactComboBox.setLayoutX(25);
		contactComboBox.setLayoutY(35);
		contactComboBox.getSelectionModel().selectFirst();
		contactComboBox.setOnAction((event) -> { loadTheDataForEditing(); });
		// Call the helper method for filling the fields according to the selected combobox item
		loadTheDataForEditing();
		setupLabelUI(lbl_editName, "Arial", 14, 100, Pos.BASELINE_LEFT, 20, 60);
		setupTextUI(fld_editName, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 80, true);
		setupLabelUI(lbl_editAddress, "Arial", 14, 100, Pos.BASELINE_LEFT, 20, 120);
		setupTextUI(fld_editAddress, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 140, true);
		setupLabelUI(lbl_editPhone,"Arial",14,100,Pos.BASELINE_LEFT,20,180);
		setupTextUI(fld_editPhone, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 200, true);
		setupLabelUI(lbl_editEmail,"Arial",14,100,Pos.BASELINE_LEFT,20,240);
		setupTextUI(fld_editEmail, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 260, true);
		setupButtonUI(btn_SaveEditContact, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
		btn_SaveEditContact.setOnAction((event) -> { try {
			saveTheEditedContact();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} });

		// Add all the widgets to the edit contacts dialog
		contactsEditControls.getChildren().add(lbl_editName);
		contactsEditControls.getChildren().add(fld_editName);
		contactsEditControls.getChildren().add(lbl_editAddress);
		contactsEditControls.getChildren().add(fld_editAddress);
		contactsEditControls.getChildren().add(lbl_editPhone);
		contactsEditControls.getChildren().add(fld_editPhone);
		contactsEditControls.getChildren().add(lbl_editEmail);
		contactsEditControls.getChildren().add(fld_editEmail);
		contactsEditControls.getChildren().add(btn_SaveEditContact);
		contactsEditControls.getChildren().add(contactComboBox);
		contactsEditControls.getChildren().add(lbl_SelectContact);
		editContactsDialog.setScene(dialogScene);
		editContactsDialog.show();

	}

	/**********
	 * This method saves the edited contact entry object to the contacts.
	 * @throws FileNotFoundException
	 */
	private void saveTheEditedContact() throws FileNotFoundException {

		ContactEntry d = new ContactEntry(fld_editName.getText(),fld_editAddress.getText(),fld_editPhone.getText(),fld_editEmail.getText());
		int theIndex=theContacts.getIndex(selectedContact);
		theContacts.setContactEntry(theIndex, d);
		sortAndWriteContacts();

		// Hide and close the pop-up window
		editContactsDialog.hide();
		editContactsDialog.close();


	}

	/***********
	 * Helper Method for Editing Already Existing Contact Entry Object which
	 * automatically fills the fields with the data relevant to combobox selection.
	 */
	private void loadTheDataForEditing() {
		// Fetch the index of the selected item
		ndxSaveEdit = contactComboBox.getSelectionModel().getSelectedIndex();

		// If the index is < 0, this implies that the default item has been selected... it is
		// index location zero.
		if (ndxSaveEdit < 0) ndxSaveEdit = 0;

		// Fetch the selected items
		ContactEntry c = editContactList.get(ndxSaveEdit);
		selectedContact =c;

		// Extract the name being defined from the item
		fld_editName.setText(c.getName());

		// Save the contact
		fld_editAddress.setText(c.getAddress());
		fld_editPhone.setText(c.getPhone());
		fld_editEmail.setText(c.getEmail());
	}

	/****
	 * This method creates a Contact Object  for further dealing with the Contacts of the User.
	 * The method retrieves the contacts from the repository and add them to the object.
	 * @throws FileNotFoundException if repository is not found.
	 */
	private void setUpContacts() throws FileNotFoundException {
		// Initialize the attributes for the contacts
		String name=null; String address =null; String phone =null; String email=null;
		// Set the contacts object empty
		theContacts.setEmpty();
		// Setup a scanner for the repository
		Scanner contScanner = new Scanner(new File("RepositoryOut/Contacts.txt"));
		// Fetch the attributes from the repository file and add the object
		while (contScanner.hasNextLine()) {
			name=contScanner.nextLine();
			address=contScanner.nextLine();
			phone=contScanner.nextLine();
			email=contScanner.nextLine();
			theContacts.addEntry(name, address, phone, email);
			if (contScanner.nextLine().equals("----------")) continue;
		}
		// Close the scanner
		contScanner.close();
	}
	/******
	 * This routine sets up the widgets for the GUI of the 
	 * Add Contacts dialog box.
	 */
	private void setupContactAddPopup() {

		// Set up the pop-up modal dialogue window
		addContactsDialog.setTitle("Create New Contact");
		Group contactAddControls = new Group();


		// Set up the pop-up window
		Scene dialogScene = new Scene(contactAddControls, 500, 400);

		// Set up the fields for the edit pop-up window

		setupLabelUI(lbl_addContact, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 40);
		lbl_addContact.setText("Add New Entry");
		setupLabelUI(lbl_name, "Arial", 14, 100, Pos.BASELINE_LEFT, 20, 60);
		setupTextUI(fld_name, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 80, true);
		setupLabelUI(lbl_address, "Arial", 14, 100, Pos.BASELINE_LEFT, 20, 120);
		setupTextUI(fld_address, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 140, true);
		setupLabelUI(lbl_phone,"Arial",14,100,Pos.BASELINE_LEFT,20,180);
		setupTextUI(fld_phone, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 200, true);
		setupLabelUI(lbl_email,"Arial",14,100,Pos.BASELINE_LEFT,20,240);
		setupTextUI(fld_email, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 260, true);
		setupButtonUI(btn_AddChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 320);

		// Set the screen so we can see it
		addContactsDialog.setScene(dialogScene);

		
		btn_AddChanges.setOnAction((event) -> { try {
			addTheContact();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} });

		// Populate the pop-up window with the GUI elements
		contactAddControls.getChildren().clear();
		contactAddControls.getChildren().add(lbl_addContact);
		contactAddControls.getChildren().add(lbl_name);
		contactAddControls.getChildren().add(fld_name);
		contactAddControls.getChildren().add(lbl_address);
		contactAddControls.getChildren().add(fld_address);
		contactAddControls.getChildren().add(lbl_phone);
		contactAddControls.getChildren().add(fld_phone);
		contactAddControls.getChildren().add(lbl_email);
		contactAddControls.getChildren().add(fld_email);
		contactAddControls.getChildren().add(btn_AddChanges);

		// Show the pop-up window
		addContactsDialog.show();


	}

	/*************************************************
	 * addTheContact() adds new contact entry object to existing Contacts Object of the User
	 * @throws FileNotFoundException
	 */
	private void addTheContact() throws FileNotFoundException {
		theContacts.addEntry(fld_name.getText(), fld_address.getText(), fld_phone.getText(), fld_email.getText());
		sortAndWriteContacts();
		fld_name.clear();
		fld_address.clear();
		fld_phone.clear();
		fld_email.clear();
		addContactsDialog.hide();
		addContactsDialog.close();	

	}
	/**********************************************************
	 * This routine launches the dialog whose function is to delete existing contact.
	 */
	private void setupContactsDeletePopup() {
		// Set up the pop-up modal dialogue window
		deleteContactsDialog.setTitle("1. Select a Contact, 2. Delete It!");
		Group contactsDeleteControls = new Group();




		// Reset the comboBox list and the list of definitions back to empty
		contactComboBox.getItems().clear();
		editContactList.clear();

		// Fetch the matching items and place them into a list
		for (int ndx = 0; ndx < theContacts.getNumEntries(); ndx++) {
			ContactEntry c = theContacts.getContactEntry(ndx);
			contactComboBox.getItems().add(c.getName());
			editContactList.add(c);
		}

		// Set up the pop-up window
		Scene dialogScene = new Scene(contactsDeleteControls, 500, 400);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_SelectContact, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
		lbl_SelectContact.setText("Select the Contact to be Deleted");
		setupLabelUI(lbl_delName, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
		setupTextUI(fld_delName, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, false);
		setupLabelUI(lbl_delDetails, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
		setupTextAreaUI(fld_delDetails, "Arial", 14, 450, 140, 30, 180, false);
		setupLabelUI(lbl_delContact, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

		// Set up the comboBox to select one of the dictionary items that matched
		contactComboBox.setLayoutX(25);
		contactComboBox.setLayoutY(35);
		contactComboBox.getSelectionModel().selectFirst();
		contactComboBox.setOnAction((event) -> { loadTheDataForDeletion(); });
		loadTheDataForDeletion();

		// Set the screen so we can see it
		deleteContactsDialog.setScene(dialogScene);

		// Load in the current word and definition data into the edit fields

		setupButtonUI(btn_DeleteContact, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
		btn_DeleteContact.setOnAction((event) -> { try {
			deleteContact();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} });

		// Populate the pop-up window with the GUI elements
		contactsDeleteControls.getChildren().addAll(contactComboBox, lbl_SelectContact, 
				lbl_delName, fld_delName, lbl_delDetails, 
				fld_delDetails,  lbl_delContact,btn_DeleteContact);

		// Show the pop-up window
		deleteContactsDialog.show();
	}

	/***
	 * deleteContact() deletes existing contact entry object from the user's contacts object 
	 * @throws FileNotFoundException
	 */
	private void deleteContact() throws FileNotFoundException {
		int theIndex=theContacts.getIndex(selectedContact);
		theContacts.remove(theIndex);
		sortAndWriteContacts();
		deleteContactsDialog.hide();
		deleteContactsDialog.close();


	}
	/***
	 * The following code firstly sorts the Contacts according to name of each entry and
	 * then saves it to the repository.
	 * @throws FileNotFoundException when the repository is not found
	 */
	private void sortAndWriteContacts() throws FileNotFoundException {
		/************ Sorting *************/
		for (int ndxOfSmallest=0;ndxOfSmallest<theContacts.getNumEntries()-1;ndxOfSmallest++)
			for (int theRest = ndxOfSmallest+1;theRest<theContacts.getNumEntries();theRest++)
				if (theContacts.getContactEntry(ndxOfSmallest).getName().compareTo(
						theContacts.getContactEntry(theRest).getName()
						)>=0) {
					ContactEntry temp = theContacts.getContactEntry(ndxOfSmallest);
					theContacts.setContactEntry(ndxOfSmallest, theContacts.getContactEntry(theRest));
					theContacts.setContactEntry(theRest, temp);
				}
		/************* Writing to Dictionary ***********************/
		File theDirectory = new File("RepositoryOut");
		if (!theDirectory.exists()) {
			theDirectory.mkdir();
			theDirectory.setReadable(true);
			theDirectory.setWritable(true);

		}
		File theDataFile = new File("RepositoryOut/Contacts.txt");

		try (FileWriter writer = new FileWriter(theDataFile)) {
			for (int ndx=0;ndx<theContacts.getNumEntries();ndx++) {
				ContactEntry ce = theContacts.getContactEntry(ndx);
				writer.write(ce.getName()+"\n");
				writer.write(ce.getAddress()+"\n");
				writer.write(ce.getPhone()+"\n");
				writer.write(ce.getEmail()+"\n");
				writer.write("----------"+"\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setUpContacts();
		contactsSetupFileControls();
	}
	/**********
	 * Helper-Method for Deletion of Contacts Dialog
	 */
	private void loadTheDataForDeletion () {
		// Fetch the index of the selected item
		ndxSaveEdit = contactComboBox.getSelectionModel().getSelectedIndex();

		// If the index is < 0, this implies that the default item has been selected... it is
		// index location zero.
		if (ndxSaveEdit < 0) ndxSaveEdit = 0;

		// Fetch the selected items
		ContactEntry c = editContactList.get(ndxSaveEdit);
		selectedContact =c;

		// Extract the word or phrase being defined from the item
		fld_delName.setText(c.getName());

		// Extract the definition from the item
		String theDetails = c.getAddress()+"\n"+c.getPhone()+"\n"+c.getEmail();
		// Save the definition
		fld_delDetails.setText(theDetails);
	}



	//---------------------------------------------------------------------------------------------

	/**********
	 * Whenever the user enters or leaves the Dictionary Tab, this method is called
	 * @throws FileNotFoundException 
	 */
	private void dictionaryChanged() throws FileNotFoundException {

		if (!dictionaryTab.isSelected()) {

			// The application is leaving the dictionary tab, so hide any controls that might have
			// been made visible
			dictionaryControls.setVisible(false);
			return;
		}

		rb_Notes.setVisible(false);
		rb_todo.setVisible(false);
		todo_Add.setVisible(false);
		todo_Delete.setVisible(false);
		// If we get here, the dictionary tab is active, so we need to determine which of the
		// controls should be visible and then make sure they are set up and visible
		if (theDictionary == null) {
			File repoFile = new File("RepositoryOut/Dictionary.txt");
			if (repoFile.exists()) {

				loadTheRepository();
				dictionarySetUpFileReadControls();			}
			// No dictionary has been been defined yet, so the only option is the controls for the
			// user to specify the file name, the label for the input field, the message field, and
			// the button to cause the application to read in the file (should the input be there
			// be of the proper syntax).
			else if (dictionaryFileReadControlsAreSetUp) {
				// If the controls have already been set up, make sure they are now visible
				dictionaryFileReadControls.setVisible(true);
			}
			else {
				// Since the controls have not yet been set up, set them up, make them visible,
				// and set the flag so we don't try to set them up twice.
				dictionarySetUpFileReadControls();
				dictionaryFileReadControlsAreSetUp = true;
				dictionaryFileReadControls.setVisible(true);
			}

			Platform.runLater(new Runnable() {public void run() {fld_DictionaryFileName.requestFocus();
			fld_DictionaryFileName.selectEnd();} });
		}
		else {
			// The dictionary has been read it, so we know we must hide the controls used to read
			// in the dictionary
			dictionaryFileReadControls.setVisible(false);

			// If the find a word/String has not been set up, set them up and remember that it has been done.
			if (!dictionaryFindControlsAreSetUp) {
				dictionarySetUpFindControls();
				dictionaryFindControlsAreSetUp = true;
				dictionaryFindControls.setVisible(true);
			}

			Platform.runLater(new Runnable() {public void run() {fld_SearchText.requestFocus();
			fld_SearchText.selectEnd();} });
		}

		// Make the dictionary controls visible, regardless of which version is current active
		dictionaryControls.setVisible(true);
	}



	/********************
	 * Scene consisting File Reading Controls for Dictionary
	 */
	private void dictionarySetUpFileReadControls() {

		// Label the text field that is to receive the file name.
		setupLabelUI(lbl_FileName, "Arial", 14, WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT);

		// Establish the text input widget so the user can enter in the name of the file that holds
		// the data about which cells are alive at the start of the simulation
		setupTextUI(fld_DictionaryFileName, "Arial", 14, WINDOW_WIDTH / 2, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT + 24, true);

		// Establish the link between the text input widget and a routine that checks to see if
		// if a file of that name exists and if so, whether or not the data is valid
		fld_DictionaryFileName.textProperty().addListener((observable, oldValue, newValue) -> {checkFileName(); });

		// Establish a GUI button the user presses when the file name have been entered and the
		// code has verified that the data in the file is valid (e.g. it conforms to the requirements)
		setupButtonUI(btn_Dictionary_Load, "Arial", 14, 100, Pos.BASELINE_LEFT, WINDOW_WIDTH - 275,  
				CONTROL_PANEL_HEIGHT + 24);

		// Establish the link between the button widget and a routine that loads the data into theData
		// data structure
		btn_Dictionary_Load.setOnAction((event) -> { loadTheData(); });
		btn_Dictionary_Load.setDisable(true);

		// The following set up the control panel messages for messages and information about errors
		setupLabelUI(lbl_FileFound, "Arial", 14, 150, Pos.BASELINE_LEFT, 320, CONTROL_PANEL_HEIGHT);
		lbl_FileFound.setStyle("-fx-text-fill: green; -fx-font-size: 14;");

		setupLabelUI(lbl_FileNotFound, "Arial", 14, 150, Pos.BASELINE_LEFT, 320, CONTROL_PANEL_HEIGHT);
		lbl_FileNotFound.setStyle("-fx-text-fill: red; -fx-font-size: 14;");

		setupLabelUI(lbl_ErrorDetails, "Arial", 14, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20,
				CONTROL_PANEL_HEIGHT);

		lbl_ErrorDetails.setStyle("-fx-text-fill: red; -fx-font-size: 14;");


		dictionaryFileReadControls.getChildren().addAll(lbl_FileName, fld_DictionaryFileName, btn_Dictionary_Load,
				lbl_FileFound, lbl_FileNotFound, lbl_ErrorDetails);

		dictionaryControls.getChildren().add(dictionaryFileReadControls);

		theRoot.getChildren().add(dictionaryControls);
	}


	/**********
	 * This routine checks, after each character is typed, to see if the dictionary file is there
	 * and if so, sets up a scanner to it and enables a button to read it and verify that it is ready
	 * to be used by the application.
	 * 
	 * If a file of that name is found, it checks to see if the contents conforms to the specification.
	 * 		If it does, a button is enabled and a green message is displayed
	 * 		If it does not, a button is disabled and a red error message is displayed
	 * If a file is not found, a warning message is displayed and the button is disabled.
	 * If the input is empty, all the related messages are removed and the  button is disabled.
	 */
	private void checkFileName(){								// Whenever this text area is changed, this routine
		str_DictionaryFileName = fld_DictionaryFileName.getText();	// is called to see if it is valid.
		if (str_DictionaryFileName.length()<=0){		// If the file name is empty, there are no errors	
			lbl_FileFound.setText("");					// so the messages as set to empty
			lbl_FileNotFound.setText("");
			scnr_Input = null;							// and the input scanner is disabled
		} 
		else 	// If there is something in the file name text area this routine tries to open it
			try {										// and establish a scanner.
				scnr_Input = new Scanner(new File(str_DictionaryFileName));

				// There is a readable file there... this code checks the data to see if it is valid 
				// for this application (Basic user input errors are GUI issues, not analysis issues.)
				if (fileContentsAreValid()) {
					lbl_FileFound.setText("File found and the contents are valid!");
					lbl_ErrorDetails.setText("");
					lbl_FileNotFound.setText("");
					btn_Dictionary_Load.setDisable(false);	// Enable the Load button
				}

				// If the methods returns false, it means there is a problem with input file
				else {	// and the method has set up a String to explain what the issue is
					lbl_FileFound.setText("");
					lbl_FileNotFound.setText("File found, but the contents are not valid!");
					lbl_ErrorDetails.setText(str_FileContentsError);
					btn_Dictionary_Load.setDisable(true);	// Disable the buttons
				}

			} catch (FileNotFoundException e) {				// If an exception is thrown, 
				lbl_FileFound.setText("");					// set up the messages  and disable 
				lbl_FileNotFound.setText("File not found!");// the buttons.
				lbl_ErrorDetails.setText("");
				scnr_Input = null;								
				btn_Dictionary_Load.setDisable(true);		// Disable the button
			}
	}


	/**********
	 * This method reads in the contents of the data file and discards it as quickly as it reads it
	 * in order to verify that the data meets the input data specifications and helps reduce the 
	 * chance that invalid input data can lead to some kind of hacking.
	 * 
	 * @return	true - 	when the input file *is* valid
	 * 					when the input file data is *not* valid - The method also sets a string with
	 * 						details about what is wrong with the input data so the user can fix it
	 */
	private boolean fileContentsAreValid() {

		// Declare and initialize data variables used to control the method

		String firstLine = "";

		// Read in the first line and verify that it has the proper header
		if (scnr_Input.hasNextLine()) {
			firstLine = scnr_Input.nextLine().trim();		// Fetch the first line from the file
			if (!firstLine.equalsIgnoreCase("Dictionary"))	// See if it is what is expected

			{												// If not, issue an error message

				return false;								// and return false
			}
		} else {
			// If the execution comes here, there was no first line in the file

			return false;
		}

		// Process each and every subsequent line in the input to make sure that none are too long
		while (scnr_Input.hasNextLine()) {

			// Read in the line 
			String inputLine = scnr_Input.nextLine();

			// Verify that the input line is not larger than 250 characters...
			if (inputLine.length() > 250) {
				// If it is larger than 250 characters, display an error message on the console

				// Stop reading the input and tell the user this data file has a problem
				return false;
			}
		}

		// Should the execution reach here, the input file appears to be valid
		str_FileContentsError = "";							// Clear any messages
		return true;										// End of file - data is valid
	}



	/**********
	 * This private method is called when the Load button is pressed. It tries to load the data into
	 * theData data structure for future analysis, if the user wishes to do that.  The method also
	 * manages the change of state of the various buttons associated with the user interface during
	 * the process.
	 * 
	 * To properly enable the concurrent activities with the user interface, this method uses a
	 * different thread to actually read in the data, leaving this thread available to deal with
	 * any user commands and to update the user interface as the reading takes place (e.g. this
	 * allows the progress bar to be updated *while* the reading is taking place.)
	 */
	private void loadTheData() {
		// Set up the user interface buttons give the user has pressed the Load button
		btn_Dictionary_Load.setDisable(true);			// Disable the Load button, since it was pushed

		// Since we have already verified the input, the try should never fail, but Java does not
		// know that and besides hardware and software failures do occur, even though it is rare.
		try {
			final Scanner dataReader = new Scanner(new File(str_DictionaryFileName));	// Set up scanner
			new Thread(() -> {readTheData(dataReader);}).start();	// Use it on another
			// thread, running concurrently
		}
		catch (FileNotFoundException e)  {
			// Since we have already done this check, this exception should never happen

		}
	}
	private void loadTheRepository() {
		// Set up the user interface buttons give the user has pressed the Load button
		btn_Dictionary_Load.setDisable(true);			// Disable the Load button, since it was pushed

		// Since we have already verified the input, the try should never fail, but Java does not
		// know that and besides hardware and software failures do occur, even though it is rare.
		try {
			final Scanner dataReader = new Scanner(new File("RepositoryOut/Dictionary.txt"));	// Set up scanner
			new Thread(() -> {readTheData(dataReader);}).start();	// Use it on another
			// thread, running concurrently
		}
		catch (FileNotFoundException e)  {
			// Since we have already done this check, this exception should never happen
			e.printStackTrace();
		}
	}


	/**********
	 * This private method reads the data from the data file and places it into a data structure
	 * for later processing, should the user decide to do that.  (Recall that the input has already
	 * been scanned by the function fileContentsAreValid(), so redundant checks are not needed.)
	 * 
	 * @param in	The parameter is a Scanner object that is set up to read the input file
	 */
	private void readTheData(Scanner in) {
		// Establish the dictionary
		theDictionary = new Dictionary();

		// Read in the dictionary and store what is read in the Dictionary object
		theDictionary.defineDictionary(in);

		Platform.runLater(() -> {
			dictionaryFileReadControls.setVisible(false);
			dictionarySetUpFindControls();
		});
	}

	/**********
	 * Display the Dictionary Find controls
	 */
	private void dictionarySetUpFindControls() {

		// Label the text field that specifies the number definitions that have been read in.
		setupLabelUI(lbl_NumberOfDefinitions, "Arial", 14, WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT);

		lbl_NumberOfDefinitions.setText("The number of definitions: " + theDictionary.getNumEntries());

		// Label the text field that is to receive the search text.
		setupLabelUI(lbl_EnterSearchText, "Arial", 14, WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT + 25);

		// Establish the text input widget so the user can enter in the name of the file that holds
		// the data about which cells are alive at the start of the simulation
		setupTextUI(fld_SearchText, "Arial", 14, WINDOW_WIDTH / 2, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT + 45, true);
		// Establish the link between the text input widget and a routine that checks to see if
		// if a file of that name exists and if so, whether or not the data is valid
		fld_SearchText.textProperty().addListener((observable, oldValue, newValue) -> {checkSearchText(); });

		Platform.runLater(new Runnable() {public void run() {fld_SearchText.requestFocus();} });


		// Establish a GUI button the user presses when the file name have been entered and the
		// code has verified that the data in the file is valid (e.g. it conforms to the requirements)
		setupButtonUI(btn_Search, "Arial", 14, 100, Pos.BASELINE_LEFT, WINDOW_WIDTH - 275,  
				CONTROL_PANEL_HEIGHT + 45);
		setupButtonUI(btn_AddPopup, "Arial", 14, 100, Pos.BASELINE_LEFT, WINDOW_WIDTH - 175,  
				CONTROL_PANEL_HEIGHT + 45);
		//

		// Establish the link between the button widget and a routine that loads the data into theData
		// data structure
		btn_Search.setOnAction((event) -> { searchDictionary(); });
		btn_AddPopup.setOnAction((event)->{setupDictionaryAddPopup();});

		if(fld_SearchText.getText().length() <= 0 )
			btn_Search.setDisable(true);
		else 
			btn_Search.setDisable(false);

		dictionaryFindControlsAreSetUp = true;

		dictionaryFindControls.getChildren().addAll(lbl_NumberOfDefinitions, lbl_EnterSearchText, 
				fld_SearchText, btn_Search, lbl_NumberOfSearchItemsFound,btn_AddPopup);

		dictionaryControls.getChildren().add(dictionaryFindControls);
	}


	/**********
	 * This method is used to search the dictionary when the search button is pressed
	 */
	private void searchDictionary() {

		lbl_NumberOfDefinitions.setText("The number of definitions: " + theDictionary.getNumEntries());
		// Tell the dictionary what to search for and set the results into the tab's text area
		TextArea newContent = new TextArea(theDictionary.findAll(fld_SearchText.getText()));
		dictionaryTab.setContent(newContent);
		newContent.setEditable(false);

		// Get the number of items that were found and display it to the user
		numberOfSearchItemsFound = theDictionary.getNumberSearchItemsFound();
		setupLabelUI(lbl_NumberOfSearchItemsFound, "Arial", 14, WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 
				MARGIN_WIDTH, CONTROL_PANEL_HEIGHT + 75);

		lbl_NumberOfSearchItemsFound.setText("The number of search items found: " + numberOfSearchItemsFound);

		// Disable the search button (until something changes)
		btn_Search.setDisable(true);

		if (!editDeletePopupDisplayed) {
			setupButtonUI(btn_EditPopup, "Arial", 12, 50, Pos.BASELINE_CENTER, WINDOW_WIDTH - 200,  
					CONTROL_PANEL_HEIGHT);
			setupButtonUI(btn_DeletePopup, "Arial", 12, 50, Pos.BASELINE_CENTER, WINDOW_WIDTH - 130,  
					CONTROL_PANEL_HEIGHT);
			dictionaryControls.getChildren().add(btn_EditPopup);
			dictionaryControls.getChildren().add(btn_DeletePopup);

			btn_EditPopup.setOnAction((event) -> { setupDictionaryEditPopup(); });
			btn_DeletePopup.setOnAction((event)->{setupDictionaryDeletePopup();});

			editDeletePopupDisplayed = true;
		}



		if (numberOfSearchItemsFound > 0) {
			// Enable the Delete and Edit buttons in order to allow a pop-up to be used to be used
			// be used to edit, or delete a dictionary entry
			btn_EditPopup.setDisable(false);
			btn_DeletePopup.setDisable(false);
		}
		else {
			// No dictionary items found so it is not possible to edit or delete and entry
			btn_EditPopup.setDisable(true);
			btn_DeletePopup.setDisable(true);

		}

	}

	private void addToDictionary() {
		String saveThisFormattedDefinition ="";
		Scanner definitionScanner = new Scanner(txt_2bDefinition.getText());
		while (definitionScanner.hasNextLine()) {
			String line = definitionScanner.nextLine();
			if (line.length()>1) saveThisFormattedDefinition+='\t'+line;
			saveThisFormattedDefinition+='\n';
		}

		definitionScanner.close();
		theDictionary.addEntry(fld_2aWord.getText()+"\n",saveThisFormattedDefinition);
		sortAndWriteDictionary();
		searchDictionary();
		addDialog.hide();
		addDialog.close();


	}


	/**********
	 * This method established the pop-up window that is used to select an item from a comboBox
	 * select list of the current words or phrases that match a search list, allows the user to
	 * specify one of those items, edit that item, and them save it, if desired.
	 */
	private void setupDictionaryAddPopup() {

		// Set up the pop-up modal dialogue window
		addDialog.setTitle("1. Write the Word, 2. Write the Definition, and then 3. Add it!");
		Group dictionaryAddControls = new Group();


		// Set up the pop-up window
		Scene dialogScene = new Scene(dictionaryAddControls, 500, 400);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_2AddDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 30);
		lbl_2AddDefinition.setText("Add New Entry");
		setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 60);
		setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 80, true);
		setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 115);
		setupTextAreaUI(txt_2bDefinition, "Arial", 14, 450, 140, 30, 140, true);
		setupLabelUI(lbl_addDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 295);
		
		// Clear the Fields
		fld_2aWord.clear();
		txt_2bDefinition.clear();
		


		// Set the screen so we can see it
		addDialog.setScene(dialogScene);



		setupButtonUI(btn_AddChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 320);
		btn_AddChanges.setOnAction((event) -> { addToDictionary(); });

		// Populate the pop-up window with the GUI elements
		dictionaryAddControls.getChildren().addAll(  
				lbl_2AddDefinition, lbl_2aWord, fld_2aWord, lbl_2bDefinition, 
				txt_2bDefinition, lbl_addDefinition, btn_AddChanges);

		// Show the pop-up window
		addDialog.show();


	}
	private void setupDictionaryEditPopup() {

		// Set up the pop-up modal dialogue window
		editDialog.setTitle("1. Select a definition, 2. Edit it, and then 3. Save it!");
		Group dictionaryEditControls = new Group();

		// Fetch the search string that user had specified.  We know that since the number of
		// search items must be greater than zero for the code to call this method, so there 
		// had to be a search string, it had to be greater than zero characters, and at least 
		// one dictionary item matched it.
		theDictionary.setSearchString(fld_SearchText.getText());

		// Reset the comboBox list and the list of definitions back to empty
		editComboBox.getItems().clear();
		editDefinitionList.clear();

		// Fetch the matching items and place them into a list
		for (int ndx = 0; ndx < numberOfSearchItemsFound; ndx++) {
			DictEntry<?,?> d = theDictionary.findNextEntry();
			editComboBox.getItems().add(d.getWord());
			editDefinitionList.add(d);
		}

		// Set up the pop-up window
		Scene dialogScene = new Scene(dictionaryEditControls, 500, 400);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_1SelectDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
		setupLabelUI(lbl_2EditDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 75);
		setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
		setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, true);
		setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
		setupTextAreaUI(txt_2bDefinition, "Arial", 14, 450, 140, 30, 180, true);
		setupLabelUI(lbl_3SaveDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

		// Set up the comboBox to select one of the dictionary items that matched
		editComboBox.setLayoutX(25);
		editComboBox.setLayoutY(35);
		editComboBox.getSelectionModel().selectFirst();
		editComboBox.setOnAction((event) -> { loadTheEditDefinitionData(); });


		// Set the screen so we can see it
		editDialog.setScene(dialogScene);

		// Load in the current word and definition data into the edit fields
		loadTheEditDefinitionData ();

		setupButtonUI(btn_SaveEditChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
		btn_SaveEditChanges.setOnAction((event) -> { saveTheEditDefinitionData(); });

		// Populate the pop-up window with the GUI elements
		dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectDefinition, 
				lbl_2EditDefinition, lbl_2aWord, fld_2aWord, lbl_2bDefinition, 
				txt_2bDefinition, lbl_3SaveDefinition, btn_SaveEditChanges);

		// Show the pop-up window
		editDialog.show();
	};
	private void setupDictionaryDeletePopup() {
		// Set up the pop-up modal dialogue window
		deleteDialog.setTitle("1. Select a definition, 2. Delete It!");
		Group dictionaryDeleteControls = new Group();

		// Fetch the search string that user had specified.  We know that since the number of
		// search items must be greater than zero for the code to call this method, so there 
		// had to be a search string, it had to be greater than zero characters, and at least 
		// one dictionary item matched it.
		theDictionary.setSearchString(fld_SearchText.getText());

		// Reset the comboBox list and the list of definitions back to empty
		editComboBox.getItems().clear();
		editDefinitionList.clear();

		// Fetch the matching items and place them into a list
		for (int ndx = 0; ndx < numberOfSearchItemsFound; ndx++) {
			DictEntry<?,?> d = theDictionary.findNextEntry();
			editComboBox.getItems().add(d.getWord());
			editDefinitionList.add(d);
		}

		// Set up the pop-up window
		Scene dialogScene = new Scene(dictionaryDeleteControls, 500, 400);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_1SelectDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
		setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
		setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, false);
		setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
		setupTextAreaUI(txt_2bDefinition, "Arial", 14, 450, 140, 30, 180, false);
		setupLabelUI(lbl_3DeleteDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

		// Set up the comboBox to select one of the dictionary items that matched
		editComboBox.setLayoutX(25);
		editComboBox.setLayoutY(35);
		editComboBox.getSelectionModel().selectFirst();
		editComboBox.setOnAction((event) -> { loadTheEditDefinitionData(); });


		// Set the screen so we can see it
		deleteDialog.setScene(dialogScene);

		// Load in the current word and definition data into the edit fields
		loadTheEditDefinitionData ();

		setupButtonUI(btn_Delete, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
		btn_Delete.setOnAction((event) -> { deleteData(); });

		// Populate the pop-up window with the GUI elements
		dictionaryDeleteControls.getChildren().addAll(editComboBox, lbl_1SelectDefinition, 
				lbl_2aWord, fld_2aWord, lbl_2bDefinition, 
				txt_2bDefinition,  btn_Delete,lbl_3DeleteDefinition);

		// Show the pop-up window
		deleteDialog.show();
	}


	/**********
	 * This method populates the word/phrase to be defined and the definition fields to be
	 * edited based on the comboBox selection the user has performed
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	private <T,U> void loadTheEditDefinitionData () {
		// Fetch the index of the selected item
		ndxSaveEdit = editComboBox.getSelectionModel().getSelectedIndex();

		// If the index is < 0, this implies that the default item has been selected... it is
		// index location zero.
		if (ndxSaveEdit < 0)
			ndxSaveEdit = 0;

		// Fetch the selected items
		
		T d = (T) editDefinitionList.get(ndxSaveEdit);
		selectedEntry=(DictEntry<String,String>) d;

		// Extract the word or phrase being defined from the item
		fld_2aWord.setText(((DictEntry<String,String>) d).getWord());

		// Extract the definition from the item
		Scanner definitionScanner = new Scanner((((DictEntry<String,String>) d).getDefinition()).substring(1));
		String theDefinition = "";

		// Remove the lab from the beginning of line of the definition
		while (definitionScanner.hasNextLine()) {
			String line = definitionScanner.nextLine();
			if (line.length() > 0 && line.charAt(0) == '\t')
				theDefinition += line.substring(1);
			theDefinition += '\n';
		}

		// Save the definition
		txt_2bDefinition.setText(((DictEntry<String,String>) d).getDefinition());


		// Close the Scanner object
		definitionScanner.close();
	}
	/***************************************************
	 * Delete Data Deletes the DictEntry Object 
	 */
	private void deleteData() {
		int theIndex=theDictionary.getIndex(selectedEntry);
		theDictionary.remove(theIndex);
		sortAndWriteDictionary();
		searchDictionary();
		deleteDialog.hide();
		deleteDialog.close();

	}

	/**********
	 * This method populates the word/phrase to be defined and the definition fields to be
	 * edited based on the comboBox selection the user has performed
	 */
	private void saveTheEditDefinitionData () {


		String saveThisFormattedDefinition ="";
		Scanner definitionScanner = new Scanner(txt_2bDefinition.getText());
		while (definitionScanner.hasNextLine()) {
			String line = definitionScanner.nextLine();
			if (line.length()>1) saveThisFormattedDefinition+='\t'+line;
			saveThisFormattedDefinition+='\n';
		}

		definitionScanner.close();
		DictEntry<String,String> d = new DictEntry<String,String>(fld_2aWord.getText()+"\n",saveThisFormattedDefinition);
		int theIndex=theDictionary.getIndex(selectedEntry);
		theDictionary.setDictEntry(theIndex, d);
		sortAndWriteDictionary();
		searchDictionary();

		// Hide and close the pop-up window
		editDialog.hide();
		editDialog.close();

	}
	/********
	 * This routine firstly sort the dictionary according to alphabetical order of words and
	 * then writes the dictionary to the repository file
	 */
	private void sortAndWriteDictionary() {


		for (int ndxOfSmallest=0;ndxOfSmallest<theDictionary.getNumEntries()-1;ndxOfSmallest++)
			for (int theRest = ndxOfSmallest+1;theRest<theDictionary.getNumEntries();theRest++)
				if (theDictionary.getDictEntry(ndxOfSmallest).getWord().compareTo(
						theDictionary.getDictEntry(theRest).getWord()
						)>=0) {
					DictEntry<String,String> temp = theDictionary.getDictEntry(ndxOfSmallest);
					theDictionary.setDictEntry(ndxOfSmallest, theDictionary.getDictEntry(theRest));
					theDictionary.setDictEntry(theRest, temp);
				}
		File theDirectory = new File("RepositoryOut");
		if (!theDirectory.exists()) {
			theDirectory.mkdir();
			theDirectory.setReadable(true);
			theDirectory.setWritable(true);

		}
		File theDataFile = new File("RepositoryOut/Dictionary.txt");

		try (FileWriter writer = new FileWriter(theDataFile)) {
			writer.write("Dictionary\n");
			for (int ndx=0;ndx<theDictionary.getNumEntries();ndx++) {
				DictEntry<?,?> de = theDictionary.getDictEntry(ndx);
				writer.write(de.getWord()+"\n");
				writer.write((String) de.getDefinition());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fld_SearchText.clear();
	}


	/**********
	 * When changes are made to the search text field, we only show the Search button when there is
	 * at least one character in the text field and something has changed since the last time the 
	 * search button has been pressed.
	 */
	private void checkSearchText() {
		// Any change to the search text field means we do not show the number of items found
		// You can call this remove even if the item is not in the list.
		lbl_NumberOfSearchItemsFound.setText("");

		numberOfSearchItemsFound = -1;

		// After the change to the text field, we enable the button if there is at least one
		// character there
		if(fld_SearchText.getText().length() <= 0 )
			btn_Search.setDisable(true);
		else 
			btn_Search.setDisable(false);

		// We only enable the edit and the delete buttons when a search has been performed
		if (editDeletePopupDisplayed) {
			editDeletePopupDisplayed = false;
			dictionaryControls.getChildren().remove(btn_EditPopup);
			dictionaryControls.getChildren().remove(btn_DeletePopup);
		}

	}

	/**********
	 * This method is called when entering or leaving the Notes/To-Do tab
	 * @throws IOException 
	 */


	private void ntdChanged() throws IOException {
		if (!ntdTab.isSelected()) {
			ntdControls.setVisible(false);
			return;
		}
		ntdControls.setVisible(true);
		rb_Notes.setVisible(true);
		rb_todo.setVisible(true);
		todo_Add.setVisible(false);
		todo_Delete.setVisible(false);

		File theDirectory = new File("RepositoryOut");
		if (!theDirectory.exists()) {
			theDirectory.mkdir();
			theDirectory.setReadable(true);
			theDirectory.setWritable(true);

		}
		File theDataFile = new File("RepositoryOut/Notes.txt");
		if (!theDataFile.exists()) theDataFile.createNewFile();
		theDataFile = new File("RepositoryOut/To-Do.txt");
		if (!theDataFile.exists()) theDataFile.createNewFile();
		rb_Notes.setOnAction((event)->{try {
			setupNotesControl();
			todo_Add.setVisible(false);
			todo_Delete.setVisible(false);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}});
		rb_todo.setOnAction((event)->{
			try {
				setupToDoControl();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			todo_Add.setVisible(true);
			todo_Delete.setVisible(true);	
		});
		if (rb_Notes.isSelected()) {
			setupNotesControl();
			todo_Add.setVisible(false);
			todo_Delete.setVisible(false);	
		}
		if (rb_todo.isSelected()){
			setupToDoControl();
			todo_Add.setVisible(true);
			todo_Delete.setVisible(true);
		}


	}

	/******************
	 * This method is called when To-Do Section is needed
	 * @throws FileNotFoundException
	 */
	private void setupToDoControl() throws FileNotFoundException {
		getToDoFromRepository();
		TextArea toDoContent = appendToDo();
		toDoContent.setEditable(false);
		toDoContent.textProperty().addListener((observable,oldValue,newValue)->{try {
			saveToDoList();
		} catch (IOException e) {
			e.printStackTrace();
		}});
		ntdTab.setContent(toDoContent);

	}

	/************
	 * This method saves notes from text-area to repository
	 * @param t TextArea
	 * @param f  File Name
	 * @throws IOException
	 */
	private void saveNotes(TextArea t,File f) throws IOException {
		FileWriter writer = new FileWriter(f);
		String toBeSaved = t.getText();
		writer.write(toBeSaved);
		writer.close();
	}
	/***
	 * This method is called when notes section is needed
	 * @throws FileNotFoundException
	 */
	private void setupNotesControl() throws FileNotFoundException {
		TextArea notesContent = new TextArea();
		notesContent.textProperty().addListener((observable,oldValue,newValue)->{try {
			saveNotes(notesContent,new File("RepositoryOut/Notes.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}});
		ntdTab.setContent(notesContent);
		getNotesFromRepository(notesContent, new File("RepositoryOut/Notes.txt"));	
	}
	/******
	 * This routine launches the dialog required for adding a to-do entry
	 */
	private void setupAddToDoDialog() {
		addToDoDialog.setTitle("Add a To-Do Entry");
		Group todoAddControls = new Group();


		// Set up the pop-up window
		Scene dialogScene = new Scene(todoAddControls, 500, 200);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_tdEntry, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 30);
		lbl_tdEntry.setText("Add New Entry");
		setupTextUI(fld_tdEntry, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 80, true);



		// Set the screen so we can see it
		addToDoDialog.setScene(dialogScene);
		addToDoDialog.sizeToScene();


		setupButtonUI(btn_AddToDoEntry, "Arial", 12, 50, Pos.BASELINE_LEFT, 210, 120);
		btn_AddToDoEntry.setOnAction((event) -> { try {
			addToDoEntry();
		} catch (IOException e) {

			e.printStackTrace();
		} });

		// Populate the pop-up window with the GUI elements
		todoAddControls.getChildren().addAll(  
				lbl_tdEntry,fld_tdEntry,btn_AddToDoEntry);

		// Show the pop-up window
		addToDoDialog.show();
	}
	/***************************************
	 * This routine launched dialog required for deleting a to-do entry
	 */
	private void setupDeleteToDoDialog() {
		// Set up the pop-up modal dialogue window
		deleteToDoDialog.setTitle("Delete To-Do Entry");
		Group todoDeleteControls = new Group();

		// Fetch the search string that user had specified.  We know that since the number of
		// search items must be greater than zero for the code to call this method, so there 
		// had to be a search string, it had to be greater than zero characters, and at least 
		// one dictionary item matched it.


		// Reset the comboBox list and the list of definitions back to empty
		todoComboBox.getItems().clear();

		// Fetch the matching items and place them into a list
		for (int ndx = 0; ndx < toDoList.size(); ndx++) {
			String c = toDoList.get(ndx);
			todoComboBox.getItems().add(c);
		}

		// Set up the pop-up window
		Scene dialogScene = new Scene(todoDeleteControls, 500, 200);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_SelectEntry, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
		lbl_SelectEntry.setText("Select the Entry to be Deleted");
		setupTextUI(fld_delEntry, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 80, false);


		// Set up the comboBox to select one of the dictionary items that matched
		todoComboBox.setLayoutX(25);
		todoComboBox.setLayoutY(35);
		todoComboBox.getSelectionModel().selectFirst();
		todoComboBox.setOnAction((event) -> { loadTheEntryForDeletion(); });
		loadTheEntryForDeletion();

		// Set the screen so we can see it
		deleteToDoDialog.setScene(dialogScene);

		// Load in the current word and definition data into the edit fields

		setupButtonUI(btn_DeleteEntry, "Arial", 12, 50, Pos.BASELINE_LEFT, 210,120 );
		btn_DeleteEntry.setOnAction((event) -> { try {
			deleteToDoEntry();
		} catch (IOException e) {

			e.printStackTrace();
		} });

		// Populate the pop-up window with the GUI elements
		todoDeleteControls.getChildren().addAll(todoComboBox, lbl_SelectEntry, 
				fld_delEntry, btn_DeleteEntry);

		// Show the pop-up window
		deleteToDoDialog.show();
	}
	/******
	 * Helper Method For Deletion of To-Do Entry
	 */
	private void loadTheEntryForDeletion() {
		String selectedItem = todoComboBox.getSelectionModel().getSelectedItem();
		fld_delEntry.setText(selectedItem);
	}

	/*******
	 * This routine adds a to-do entry to the tool
	 * @throws IOException
	 */
	private void addToDoEntry() throws IOException {
		toDoList.add(fld_tdEntry.getText());
		saveToDoList();
		getToDoFromRepository();
		appendToDo();
		addToDoDialog.hide();
		addToDoDialog.close();
		setupToDoControl();
	}

	/*******
	 * This routine deletes a to-do entry to the tool
	 * @throws IOException
	 */
	private void deleteToDoEntry() throws IOException {
		toDoList.remove(fld_delEntry.getText());
		saveToDoList();
		getToDoFromRepository();
		appendToDo();
		deleteToDoDialog.hide();
		deleteToDoDialog.close();
		setupToDoControl();
	}
	/***
	 * This routine saves the to-do list to the repository file
	 * @throws IOException
	 */
	private void saveToDoList() throws IOException {
		try (FileWriter writer = new FileWriter("RepositoryOut/To-Do.txt")) {
			for (int ndx=0;ndx<toDoList.size();ndx++) {
				writer.write(toDoList.get(ndx));
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/****
	 * This routine fetches the to-do list from the repository
	 * @throws FileNotFoundException
	 */
	private void getToDoFromRepository() throws FileNotFoundException {
		Scanner notesScanner = new Scanner(new File("RepositoryOut/To-Do.txt"));
		toDoList.clear();
		while(notesScanner.hasNextLine()) {
			String entry = notesScanner.nextLine();
			toDoList.add(entry);
		}
		notesScanner.close();
	}

	/****
	 * This routine returns a TextArea containing the To-Do List to the Caller Method
	 * @return TextArea containing To-Do List
	 */
	private TextArea appendToDo() {
		String str= "";
		for (int ndx=0;ndx<toDoList.size();ndx++) {
			str+=toDoList.get(ndx);
			str+="\n";
		}
		TextArea newContent = new TextArea(str);
		newContent.setEditable(false);
		return newContent;
	}

	/*****
	 * This routine fetches notes from repository and display them directly to the text-area content
	 * @param t The Text Area
	 * @param f Repository File
	 * @throws FileNotFoundException
	 */

	private void getNotesFromRepository(TextArea t, File f) throws FileNotFoundException {
		Scanner notesScanner = new Scanner(f);
		while (notesScanner.hasNextLine())
		{
			t.appendText(notesScanner.nextLine()+"\n");
		}
		notesScanner.close();
	}
}