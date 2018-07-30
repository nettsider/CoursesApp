package app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.database.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserController {

	@FXML
	private MenuItem m_clear;
	@FXML
	private MenuItem m_logout;
	@FXML
	private MenuItem m_help;
	@FXML
	private TextField tf_name;
	@FXML
	private TextField tf_last;
	@FXML
	private CheckBox cb_java;
	@FXML
	private CheckBox cb_python;
	@FXML
	private CheckBox cb_other;
	@FXML
	private TextField tf_other;
	@FXML
	private RadioButton rb_basic;
	@FXML
	private ToggleGroup eng;
	@FXML
	private RadioButton rb_med;
	@FXML
	private RadioButton rb_high;
	@FXML
	private ComboBox<String> c_kursy;

	@FXML
	private Button btn_submit;

	// COMBO SELECTION LIST:
	ObservableList<String> kursy = FXCollections.observableArrayList("Back-end Dev", "Front-end Dev");

	public void initialize() {
		c_kursy.setItems(kursy);
		c_kursy.setValue(null);
	}

	@FXML
	void ClearAction(ActionEvent event) {
		tf_name.clear();
		tf_last.clear();
		tf_other.clear();
		cb_java.setSelected(false);
		cb_other.setSelected(false);
		rb_basic.setSelected(false);
		rb_med.setSelected(false);
		rb_high.setSelected(false);
		c_kursy.setValue(null);

	}

	@FXML
	void HelpAction(ActionEvent event) {
		Alert help = new Alert(AlertType.INFORMATION);
		help.setTitle("Instrukcja");
		help.setHeaderText("Instrukcja Wypełaniania Ankiety");
		help.setContentText("Opis...");
		help.showAndWait();

	}

	@FXML
	void LogoutAction(ActionEvent event) {
	    logoutMethod();
        //((Node) (event.getSource())).getScene().getWindow().hide();

	}

	@FXML
	void OtherAction(ActionEvent event) {
		if (cb_other.isSelected()) {
			tf_other.setDisable(false);
		} else {
			tf_other.setDisable(true);
			tf_other.clear();
		}

	}

	@FXML
	void submitAction(MouseEvent event) {
		if (tf_name.getText().equals("") || tf_last.getText().equals("")
				|| (eng.selectedToggleProperty().getValue() == null) || c_kursy.getValue() ==null) {

			Alert loginError = new Alert(AlertType.ERROR);
			loginError.setTitle("Data Error");
			loginError.setHeaderText("***************");
			loginError.setContentText("All fields must be filled properly");
			loginError.showAndWait();

		} else

		{

			// WRITE TO A FILE

			String progLang = "";
			if (cb_java.isSelected()) {
				progLang = cb_java.getText();
			}
			if (cb_python.isSelected()) {
				progLang += cb_python.getText();
			}
			if (!tf_other.getText().equals("")) {
				progLang += tf_other.getText();
			}

			String english = " ";
			if (rb_basic.isSelected()) {
				english = rb_basic.getText() + " ";
			} else if (rb_med.isSelected()) {
				english = rb_med.getText() + " ";
			} else if (rb_high.isSelected()) {
				english = rb_high.getText() + " ";

			}

			try {
				String dkontakt = "Dane Kontaktowe:\n" + tf_name.getText() + "\n" + tf_last.getText() + "\n";
				String dkomp = "Dane kompetencji \n" + progLang + "\n" + english + "\n";
				String dkurs = "Dane Kursu: \n" + c_kursy.getValue();
				String ankietaRes = dkontakt + dkomp + dkurs;

				PrintWriter pw = new PrintWriter("result.txt");
				pw.println(ankietaRes);
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// CONNECTING TO DB
			DBConnector db = new DBConnector();
			Connection conn = db.connInit();
			try {
				PreparedStatement setname = conn.prepareStatement("SET NAMES utf8");
				setname.executeQuery();

				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO ankieta (name,last,java,python,other,english,kurs,data) "
								+ "VALUES(?,?,?,?,?,?,?,now())");
				ps.setString(1, tf_name.getText());
				ps.setString(2, tf_last.getText());
				ps.setBoolean(3, cb_java.isSelected());
				ps.setBoolean(4, cb_python.isSelected());
				ps.setString(5, cb_other.getText());
				ps.setString(6, english);
				ps.setString(7, c_kursy.getValue());

				ps.executeUpdate();
				// czyszczenie okna po wysłaniu
				ClearAction(null);
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void logoutMethod() {

	    Stage stage = new Stage();
        Parent parent;
        try {
            parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/LoginView.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("LogIn");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // MouseEvent me = new MouseEvent(null, 0, 0, 0, 0, null, 0, false, false,
        // false, false, false, false, false, false, false, false, null);

    }

}
