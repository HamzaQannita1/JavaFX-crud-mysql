/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AlertManager;
import Model.Db;
import Model.user;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author hamza qannita
 */
public class AdminController implements Initializable {

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<user, Integer> id_col;

    @FXML
    private TableColumn<user, String> email_col;
    @FXML
    private TableColumn<user, String> name_col;

    @FXML
    private TableColumn<user, String> pass_col;

    @FXML
    private TableColumn<user, String> salary_col;

    @FXML
    private TableView<user> tabel_view;

    @FXML
    private TextField txt_email;

    @FXML
    private PasswordField txt_pass;
    @FXML
    private TextField txt_salary;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_usrname;
    AlertManager alert = new AlertManager();
    int index = -1;
    Connection conn;
    ResultSet rs = null;
    PreparedStatement pst = null;
    LocalDate date = LocalDate.MIN;

    @FXML
    void Add(ActionEvent event) throws SQLException, ClassNotFoundException {
        //validation input 
        if (txt_email.getText().isEmpty()
                || txt_salary.getText().isEmpty()
                || txt_usrname.getText().isEmpty()) {
            alert.errorMessage("All Fields are necessary to be filled ??");
            System.out.println("Enter Fill filrdes");

        } else if (txt_pass.getText().length() < 8) {
            alert.errorMessage("Invalid Password , al least 8 characters needs ??");
            System.out.println("password >8 ??");
        } else {
            // get data from all text fields 
            int id = Integer.valueOf(txt_id.getText());
            String email = txt_email.getText().trim();
            String salary = txt_salary.getText();
            String user = this.txt_usrname.getText();
            String pass = this.txt_pass.getText();

            // make an user object having this data
            user users = new user(user, salary, email, pass, id);
            // save the user in database by save method
            users.save();
            alert.successMessage("SuccessFully inserted Data ^_^" + "\n User name : " + user);
            System.out.println("SuccessFully inserted Data ^_^");
        }

    }

    @FXML
    void Delete(ActionEvent event) throws SQLException, ClassNotFoundException {
        conn = Db.ConnectioDB();
        String sql = "delete from user where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.valueOf(txt_id.getText()));
            pst.execute();
            alert.confirmatinMessage("Can you Delete Data Users");
        } catch (SQLException e) {
            alert.errorMessage("Error Delete");
        }
    }

    @FXML
    void reset(ActionEvent event) {
        txt_email.setText("");
        txt_pass.setText("");
        txt_usrname.setText("");
        txt_salary.setText("");
        txt_id.setText("");

    }

    @FXML
    void update(ActionEvent event) throws ClassNotFoundException, SQLException {
        conn = Db.ConnectioDB();

        try {
            String sql = "UPDATE `user` SET `name`=?, `email`=?, `password`=?, `salary`=? WHERE id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_usrname.getText());
            pst.setString(2, txt_email.getText());
            pst.setString(3, txt_pass.getText());
            pst.setString(4, txt_salary.getText());
            pst.setInt(5, Integer.valueOf(txt_id.getText()));
            pst.executeUpdate();
            alert.successMessage("Successfully Updated Data");
        } catch (SQLException e) {
            alert.errorMessage("Error Updating Data");
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        id_col.setCellValueFactory(new PropertyValueFactory("id"));
        name_col.setCellValueFactory(new PropertyValueFactory("name"));
        salary_col.setCellValueFactory(new PropertyValueFactory("salary"));
        email_col.setCellValueFactory(new PropertyValueFactory("email"));
        pass_col.setCellValueFactory(new PropertyValueFactory("password"));
        ObservableList<user> userList;

        tabel_view.getSelectionModel().selectedIndexProperty().addListener(event -> showSelectesUser());
        try {
            userList = FXCollections.observableArrayList(user.getAlluser());
            tabel_view.setItems(userList);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void showSelectesUser() {
        user user = tabel_view.getSelectionModel().getSelectedItem();
        txt_id.setText(String.valueOf(user.getId()));
        txt_email.setText(user.getEmail());
        txt_pass.setText(user.getPassword());
        txt_salary.setText(user.getSalary());
        txt_usrname.setText(user.getName());

    }

}
