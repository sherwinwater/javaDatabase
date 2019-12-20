package dbproject1;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewUpdateStaffTable extends Application {

	private PreparedStatement preparedStatement;
	private PreparedStatement preparedStatementInsert;
	private PreparedStatement preparedStatementUpdate;
	private PreparedStatement preparedStatementDelete;

	private TextField tfssn = new TextField();
	private TextField tfLastName = new TextField();
	private TextField tfFirstName = new TextField();
	private TextField tfCourseID = new TextField();
	private TextField tfAddress = new TextField();
	private TextField tfDeptID = new TextField();
	private TextField tfMi = new TextField();
	private TextField tfPhone = new TextField();
	private TextField tfZipcode = new TextField();
	private TextField tfBirthdate = new TextField();

	private TextArea outputData = new TextArea();

	private Label labelssn = new Label("ssn");
	private Label labelCourseID = new Label("CourseID");
	private Label labelLastName = new Label("Last Name");
	private Label labelFirstName = new Label("First Name");
	private Label labelAddress = new Label("Address");
	private Label labelDeptID = new Label("DeptID");
	private Label labelMi = new Label("Mi");
	private Label labelPhone = new Label("Phone");
	private Label labelZipcode = new Label("Zipcode");
	private Label labelBirthdate = new Label("Birthdate");
	private Label labelStatus = new Label();

	@Override
	public void start(Stage primaryStage) {
		initializeDB();

		Button btView = new Button("view");
		Button btInsert = new Button("insert");
		Button btUpdate = new Button("update");
		Button btDelete = new Button("delete");

		HBox h1Box = new HBox(5);
		h1Box.getChildren().addAll(labelssn, tfssn, labelCourseID, tfCourseID);

		HBox h2Box = new HBox(5);
		h2Box.getChildren().addAll(labelLastName, tfLastName, labelFirstName, tfFirstName);

		HBox h3Box = new HBox(5);
		h3Box.getChildren().addAll(labelAddress, tfAddress, labelDeptID, tfDeptID);

		HBox h4Box = new HBox(5);
		h4Box.getChildren().addAll(labelBirthdate, tfBirthdate, labelZipcode, tfZipcode);

		HBox h5Box = new HBox(5);
		h5Box.getChildren().addAll(labelPhone, tfPhone, labelMi, tfMi);

		HBox h6Box = new HBox(5);
		h6Box.getChildren().addAll(btView, btInsert, btUpdate, btDelete);
		h6Box.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(5);
		vBox.getChildren().addAll(h1Box, h2Box, h3Box, h4Box, h5Box, h6Box);
		vBox.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(new ScrollPane(outputData));
		pane.setTop(vBox);
		pane.setBottom(labelStatus);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 550, 350);
		primaryStage.setTitle("ViewStaff"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		//view
		btView.setOnAction(e -> viewStaff());
		//insert
		btInsert.setOnAction(e -> insertStudent());
		//update
		btUpdate.setOnAction(e -> updateStudent());
		//clear
		btDelete.setOnAction(e -> deleteStudent());

	}

	//view
	private void viewStaff() {
		String ssn = tfssn.getText();
		String courseID = tfCourseID.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();
		outputData.setText(null);

		try {
			preparedStatement.setString(1, ssn);
			preparedStatement.setString(2, _firstname);
			preparedStatement.setString(3, _lastname);

			ResultSet rset = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			int cols = rsmd.getColumnCount();
			int rows = rset.getRow();
			int count = 0;

			if (!rset.next()) {
				labelStatus.setText("Not found");
				System.out.println("Not found");
			} else {
				// output the title
				for (int i = 1; i <= cols; i++) {
					String outL = String.format("%-12s", rsmd.getColumnName(i));
					outputData.appendText(outL);
					System.out.printf(outL);
				}
				outputData.appendText("\n");
				System.out.println();

				rset.beforeFirst();// move the pointer to the default position (before first)
				// output the data content
				while (rset.next()) {
					for (int i = 1; i <= cols; i++) {
						String outF = String.format("%-12s", rset.getObject(i));
						outputData.appendText(outF);
						System.out.printf(outF);
					}
					outputData.appendText("\n");
					count++;
					System.out.println();
				}
				labelStatus.setText(count + " found");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	//insert student
	private void insertStudent() {
		String ssn = tfssn.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();
		String _birth = tfBirthdate.getText();
		String _address = tfAddress.getText();
		String _zipcode = tfZipcode.getText();
		String _mi = tfMi.getText();
		String _phone = tfPhone.getText();
		String _deptID = tfDeptID.getText();

		try {
			preparedStatementInsert.setString(1, ssn);
			preparedStatementInsert.setString(2, _firstname);
			preparedStatementInsert.setString(3, _mi);
			preparedStatementInsert.setString(4, _lastname);
			preparedStatementInsert.setString(5, _birth);
			preparedStatementInsert.setString(6, _address);
			preparedStatementInsert.setString(7, _phone);
			preparedStatementInsert.setString(8, _zipcode);
			preparedStatementInsert.setString(9, _deptID);
			// execute the query to populate data
			preparedStatementInsert.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	// update student
	private void updateStudent() {
		String ssn = tfssn.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();

		try {
			preparedStatementUpdate.setString(1, _firstname);
			preparedStatementUpdate.setString(2, ssn);
			preparedStatementUpdate.setString(3, _lastname);

			// execute the query to populate data
			preparedStatementUpdate.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	// delete student
	private void deleteStudent() {
		String ssn = tfssn.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();

		try {
			preparedStatementDelete.setString(1, ssn);
			preparedStatementDelete.setString(2, _firstname);
			preparedStatementDelete.setString(3, _lastname);

			// execute the query to populate data
			preparedStatementDelete.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// initialize
	private void initializeDB() {
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
//      Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded");

			// Establish a connection
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
//    ("jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl",
//     "scott", "tiger");
			System.out.println("Database connected");

			//creat a query
//			String querystring = "select firstName, mi, "
//					+ "lastName, title, grade from Student, Enrollment, Course "
//					+ "where Student.ssn = ? or Enrollment.courseId = ? or Student.firstName = ?";
//					+ "and Enrollment.courseId = Course.courseId";
			String querystring = "select * from Student where Student.ssn = ? "
					+ "or Student.firstName = ? or Student.lastName = ?";

			// Create a statement
//			stmt = connection.createStatement();
			preparedStatement = connection.prepareStatement(querystring);

			// Create a preparedstatement for populating data
			String querystringInsert = "insert into Student(ssn,firstName,mi,lastName,birthDate,street,phone,zipCode,deptId) "
					+ "values(?,?,?,?,?,?,?,?,?)";
			preparedStatementInsert = connection.prepareStatement(querystringInsert);

			// Update data
			String querystringUpdate = "update Student set firstName = ? where ssn = ? and lastName = ?";
			preparedStatementUpdate = connection.prepareStatement(querystringUpdate);

			// Delete data
			String querystringDelete = "delete from Student where ssn = ? or firstName = ? or lastName = ?";
			preparedStatementDelete = connection.prepareStatement(querystringDelete);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	//main
	public static void main(String[] args) {
		launch(args);
	}

}
