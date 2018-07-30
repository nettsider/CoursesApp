package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.database.DBConnector;
import app.model.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminController {
	@FXML
	private TableView<Admin> t;
	
	// TableColumn <Model, pole>
	@FXML
	private TableColumn<Admin, Integer> t_id;
	@FXML
	private TableColumn<Admin, String> t_name;
	@FXML
	private TableColumn<Admin, String> t_last;
	@FXML
	private TableColumn<Admin, Boolean> t_java;
	@FXML
	private TableColumn<Admin, Boolean> t_python;
	@FXML
	private TableColumn<Admin, String> t_other;
	@FXML
	private TableColumn<Admin, String> t_english;
	@FXML
	private TableColumn<Admin, String> t_kurs;
	@FXML
	private TableColumn<Admin, String> t_data;
	@FXML
	private Button btn_refresh;
	@FXML
	private Button btn_delete;

	// OBIEKT PRZECHOWUJĄCY DANE POBIERANE Z BAZY DANYCH
	private ObservableList<Admin> dane = FXCollections.observableArrayList();

	// KLASA CONNECTION DO POŁĄCZENIA Z DB
	private void connection() {
		db = new DBConnector();
		conn = db.connInit();
	}

	// ZMIENNE GLOBALNE W TEJ KLASIE
	PreparedStatement ps;
	Connection conn;
	DBConnector db;
	int id_selected;

	@FXML
	void delete_Action(MouseEvent event) {

		id_selected = t.getSelectionModel().getSelectedItem().getId();

		connection();

		try {
			ps = conn.prepareStatement("DELETE FROM ankieta WHERE id = ?");
			ps.setInt(1, id_selected);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		select();

	/*	Alert alercik = new Alert(AlertType.ERROR);
		alercik.setTitle("Alercik!");
		alercik.setHeaderText("************************");
		alercik.setContentText("Zaznacz coś, inaczej nici z wciskania guziczków! ");
		alercik.showAndWait();*/

	}

	@FXML
	void refreshAction(MouseEvent event) {
		select();

	}

	private void select() {
		connection();
		dane.clear();
		t.setItems(dane);
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ankieta");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				dane.add(new Admin(rs.getInt("id"), rs.getString("name"), rs.getString("last"), rs.getBoolean("java"),
						rs.getBoolean("python"), rs.getString("other"), rs.getString("english"), rs.getString("kurs"),
						rs.getString("data")));
			}

			t_id.setCellValueFactory(new PropertyValueFactory<Admin, Integer>("id"));
			t_name.setCellValueFactory(new PropertyValueFactory<Admin, String>("name"));
			t_last.setCellValueFactory(new PropertyValueFactory<Admin, String>("last"));
			t_java.setCellValueFactory(new PropertyValueFactory<Admin, Boolean>("java"));
			t_python.setCellValueFactory(new PropertyValueFactory<Admin, Boolean>("python"));
			t_other.setCellValueFactory(new PropertyValueFactory<Admin, String>("other"));
			t_english.setCellValueFactory(new PropertyValueFactory<Admin, String>("english"));
			t_kurs.setCellValueFactory(new PropertyValueFactory<Admin, String>("kurs"));
			t_data.setCellValueFactory(new PropertyValueFactory<Admin, String>("data"));
			System.out.println(dane);
			t.setItems(dane);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {

	    select();
	}



}
