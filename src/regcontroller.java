import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class regcontroller {
    static int id;

    @FXML
    public Label regmsglabel;
    @FXML
    public Button logincancel;
    @FXML
    public Button regcancel;
    @FXML
    public TextField usernamefield;
    @FXML
    public PasswordField passwordfield;
    @FXML
    public Label l_msglabel;
    @FXML
    public TextField l_usernamefield;
    @FXML
    public PasswordField l_passwordfield;
    Stage stage;
    Scene scene;
    Parent root;
    

    // For changing scene to the register page
    public void r_ButtonOnAction(ActionEvent e) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("reg.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load");
        }
    }

    // For register button
    public void regButtonOnAction(ActionEvent e) {
        if (!usernamefield.getText().trim().isEmpty() && !passwordfield.getText().trim().isEmpty()) 
        {
            registerUser(e, usernamefield.getText(), passwordfield.getText());
        } 
        else {
            System.out.println("Please fill in all information!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill all information to register");
            alert.show();
        }
    }

    public void logincancelOnAction(ActionEvent e){
        System.exit(0);
    }

    public void loginButtonOnAction(ActionEvent e) throws Exception {
        loginUser(e, l_usernamefield.getText(), l_passwordfield.getText());
    }

    // Used for connectivity
    Connection connection = null;

    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
                    "");
            return connection;
        } catch (Exception ex) {
            System.out.println(ex);
            return connection;
        }
    }

    public void regcancelOnAction(ActionEvent e){
        System.exit(0);
    }
    public void registerUser(ActionEvent e, String username, String password) {
        connection = connection();
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUser = null; // For checking if already this user exists or not
        ResultSet resultSet = null; // For storing query's result

        try {
            psCheckUser = connection.prepareStatement("SELECT * FROM expense_tracker.register WHERE username = ?");
            psCheckUser.setString(1, username);
            resultSet = psCheckUser.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this usename");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO expense_tracker.register (username,password) VALUES (?,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setContentText("You register successfully!!");
                 alert.show();
                try {
                    root = FXMLLoader.load(getClass().getResource("login.fxml"));
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ex) {
                    System.out.println("Can't load");
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void loginUser(ActionEvent e, String username, String password) throws IOException{
        connection = connection();
        PreparedStatement psCheckUser = null;
        ResultSet resultSet = null;

        try {
            psCheckUser = connection.prepareStatement("SELECT * FROM expense_tracker.register WHERE username = ? and password = ?");
            psCheckUser.setString(1, username);
            psCheckUser.setString(2, password);
            resultSet = psCheckUser.executeQuery();

            if (resultSet.next()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setContentText("You logged in successfully!!");
                 alert.show();
                regcontroller.id = resultSet.getInt("userID");
                //Changing scene to main page
                try {
                    root = FXMLLoader.load(getClass().getResource("main.fxml"));
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } 
                catch (Exception ex) {
                    System.out.println("Can't load");
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid username or password");
                alert.show();
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}