package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

	@FXML
	TextField usernametf, passwordTf;
	@FXML
	Button loginBtn, closeBtn;

	@FXML
	Button addBtn, editBtn, addUserBtn, issueBtn, exitBtn;

	@FXML
	Button savebtn, backBtn;
	@FXML
	TextField studentIDtf, studentNametf, fatherNametf, courseTf, branchTf;

	@FXML
	Button saveBookButton, backBookButton;
	@FXML
	TextField bookIDTf, nameTf, publisherTf, priceTf, yearTf;

	@FXML
	Button issueSaveButton, backIssueButton;
	@FXML
	TextField issueBookIDTf, issueStudentIDTf;
	@FXML
	DatePicker issueDatePicker, dueDatePicker;

	@FXML
	TextField removeBookIdTf;
	@FXML
	Button removeButton, removeBackButton;
	@FXML
	Button searchButton, requestButton, returnBookButtton, studentLogoutButton;
	@FXML
	TextField returnBookIdTf, returnnameTf, returnpublisherTf, returnyearTf, returnpriceTf;
	@FXML
	Button ReturnBookButton, backReturnBookButton;

	@FXML
	Button searchBookButton, allBooksButton, searchCloseButton;
	@FXML
	TextField searchByBookIdTf, searchByBookNameTf;
	@FXML
	Button requestaBookButton, requestBackButton;
	@FXML
	TextField requestBookIdTf, requestNameTf;
	@FXML
	Label displayBooksLabel;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void login(ActionEvent event) throws IOException {
		if (usernametf.getText().equals("admin") && passwordTf.getText().equals("admin")) {

//			root = FXMLLoader.load(getClass().getResource("Home.fxml"));
//			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//			scene = new Scene(root);
//			stage.setScene(scene);
//			stage.show();
//			stage.centerOnScreen();

		} else if (usernametf.getText().equals("student") && passwordTf.getText().equals("student")) {
			root = FXMLLoader.load(getClass().getResource("StudentHome.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			stage.centerOnScreen();
		} else {
			JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
		}
		usernametf.setText(null);
		passwordTf.setText(null);
	}

	// Button Add Student
	public void addUser(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();

	}

	public void saveUser(ActionEvent event) {
		String studentID = studentIDtf.getText();
		String studentName = studentNametf.getText();
		String fatherName = fatherNametf.getText();
		String courseName = courseTf.getText();
		String branchName = branchTf.getText();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO students VALUES (?,?,?,?,?)");
			preparedStatement.setString(1, studentID);
			preparedStatement.setString(2, studentName);
			preparedStatement.setString(3, fatherName);
			preparedStatement.setString(4, courseName);
			preparedStatement.setString(5, branchName);
			preparedStatement.addBatch();
			preparedStatement.executeBatch();

			JOptionPane.showMessageDialog(null, "Successfully Updated");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Unexpected error!");
		}
		reset(studentIDtf);
		reset(studentNametf);
		reset(fatherNametf);
		reset(courseTf);
		reset(branchTf);
	}

	// Button Add Book
	public void add(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();

	}

	public void saveBook(ActionEvent event) {
		String bookID = bookIDTf.getText();
		String name = nameTf.getText();
		String publisher = publisherTf.getText();
		String price = priceTf.getText();
		String year = yearTf.getText();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO books VALUES (?,?,?,?,?)");
			preparedStatement.setString(1, bookID);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, publisher);
			preparedStatement.setString(4, price);
			preparedStatement.setString(5, year);
			preparedStatement.addBatch();
			preparedStatement.executeBatch();

			JOptionPane.showMessageDialog(null, "Successfully Updated");

		} catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Unexpected error!");

		}
		reset(bookIDTf);
		reset(nameTf);
		reset(yearTf);
		reset(publisherTf);
		reset(priceTf);
	}

	// Remove Book
	public void edit(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("EditBook.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public void delete(ActionEvent event) {
		String sql = "delete from books where BookId = ?";
		String BookId = removeBookIdTf.getText();
		int x = Integer.parseInt(BookId);
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, x);
			st.addBatch();
			st.executeBatch();
			JOptionPane.showMessageDialog(null, "Book Successfully Removed!");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unexpected error!");
			System.out.println(e);
		}
	}

	// Issue Book
	public void IssueBook(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("IssueBook.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public void Issue(ActionEvent event) {
		int bookID = Integer.parseInt(issueBookIDTf.getText());
		int studentID = Integer.parseInt(issueStudentIDTf.getText());

		LocalDate issueDate = issueDatePicker.getValue();
		LocalDate dueDate = dueDatePicker.getValue();
		String idate = issueDate.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
		String rdate = dueDate.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
		dueDatePicker.setPromptText("Enter Due Date");

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			PreparedStatement preparedStatement = con.prepareStatement("delete from books where BookId = ?");
			preparedStatement.setInt(1, bookID);
			preparedStatement.addBatch();
			preparedStatement.executeBatch();

			PreparedStatement preparedStatement2 = con.prepareStatement("INSERT INTO issuedBooks VALUES(?,?,?,?)");
			preparedStatement2.setInt(1, bookID);
			preparedStatement2.setInt(2, studentID);
			preparedStatement2.setString(3, idate);
			preparedStatement2.setString(4, rdate);
			preparedStatement2.addBatch();
			preparedStatement2.executeBatch();

			JOptionPane.showMessageDialog(null, "Book Issued!");

		} catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Connection Error!");

		}
		reset(issueBookIDTf);
		reset(issueStudentIDTf);
		reset(dueDatePicker);
		reset(issueDatePicker);
	}

	// Start for student login

	// request book

	// search
	public void search(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SearchBook.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public void DisplayBooks(ActionEvent event) throws SQLException {
		System.out.println("CHeck 1");
		String BookId = searchByBookIdTf.getText();
		// String BookName = searchByBookNameTf.getText();// currently not working
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("");

			ResultSet rs = pst.executeQuery(BookId);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			System.out.println("CHeck 1");
			String table = null;
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					System.out.print(rs.getString(i) + " ");
					table += rs.getString(i) + " ";
				}
				System.out.println();
				table += "\n";
			}
			displayBooksLabel.setText(table);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}

	}

	public void searchBook(ActionEvent event) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			String sql = "Search *from books where BookId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, (searchByBookIdTf.getText()));
			ResultSet rs = ps.executeQuery();
			// ResultSetMetaData rsmt = rs.getMetaData();

			String Name = null;
			while (rs.next()) {
				Name += rs.getString("Name") + "\n";
			}
			displayBooksLabel.setText(Name);
			// st.execute(sql + searchByBookIdTf.getText());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void bookReturn(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ReturnBook.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public void returnBook(ActionEvent event) {
		;
		String name = returnnameTf.getText();
		String bookID = returnBookIdTf.getText();
		String publisher = returnpublisherTf.getText();
		String price = returnpriceTf.getText();
		String year = returnyearTf.getText();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			PreparedStatement pst = con.prepareStatement("insert into books values(?,?,?,?,?)");

			pst.setString(1, bookID);
			pst.setString(2, name);
			pst.setString(3, publisher);
			pst.setString(4, price);
			pst.setString(5, year);

			pst.addBatch();
			pst.executeBatch();
			JOptionPane.showMessageDialog(null, "Book is requested to be issued!");

		} catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Unexpected Error!");

		}
		reset(returnBookIdTf);
		reset(returnnameTf);
		reset(returnyearTf);
		reset(returnpublisherTf);
		reset(returnpriceTf);
	}

	public void requestBook(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("RequestBook.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public void requestaBook(ActionEvent event) {

		String BookId = requestBookIdTf.getText();
		String Name = requestNameTf.getText();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			PreparedStatement preparedStatement = con.prepareStatement("insert into requestedBooks values(?,?)");
			preparedStatement.setString(1, BookId);
			preparedStatement.setString(2, Name);
			preparedStatement.addBatch();
			preparedStatement.executeBatch();
			JOptionPane.showMessageDialog(null, "Book is requested to be issued!");

		} catch (Exception e) {

			System.out.println(e);
		}
		reset(requestBookIdTf);
		reset(requestNameTf);

	}

	// Reusable Code
	public void backtoAdminHome(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public void backtoAdminHome2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("StudentHome.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();

	}

	public void close(ActionEvent event) {
		System.exit(0);
	}

	public void reset(TextField tf) {
		tf.setText("");
	}

	public void reset(DatePicker DP) {
		DP.setPromptText("");
	}
}
