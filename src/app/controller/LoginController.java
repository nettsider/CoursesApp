package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import app.database.DBConnector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField tf_login;

	@FXML
	private PasswordField pf_login;

	@FXML
	private Button bt_login;

	DBConnector db;
	PreparedStatement ps;

	private void loginMethod() {

		db = new DBConnector();
		Connection conn = db.connInit();
		try {
			
			ps = conn.prepareStatement("SELECT permission FROM logowanko WHERE login = ? AND pass = ?");
			ps.setString(1, tf_login.getText());
			ps.setString(2, pf_login.getText());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("permission").equals("0")) {
					System.out.println("you are a user ");

					Stage stageUser = new Stage();
					Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/UserView.fxml"));
					Scene scene = new Scene(parent);
					stageUser.setScene(scene);
					stageUser.setTitle("User View");
					stageUser.show();

				} else {
					System.out.println("you are the admin");

					Stage stageAdmin = new Stage();
					Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/AdminView.fxml"));
					Scene scene = new Scene(parent);
					stageAdmin.setScene(scene);
					stageAdmin.setTitle("The Admin View");
					stageAdmin.show();

				}
			} else {
				Alert loginError = new Alert(AlertType.ERROR);
				loginError.setTitle("Login Error!");
				loginError.setHeaderText("******************************************");
				loginError.setContentText("Access Denied! Bad login and/or password.");
				loginError.showAndWait();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void keyLoginAction(KeyEvent event) {
		loginMethod();
		((Node) (event.getSource())).getScene().getWindow().hide();

	}

	@FXML
	void loginAction(MouseEvent event) {
		loginMethod();
		((Node) (event.getSource())).getScene().getWindow().hide();

	}

}
