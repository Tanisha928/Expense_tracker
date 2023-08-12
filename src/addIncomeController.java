import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

public class addIncomeController implements Initializable{
    
    @FXML
    public ComboBox<String> i_category;
    @FXML
    public TextField i_amount;
    @FXML
    public DatePicker i_date;
   
    Stage stage;
    Scene scene;
    Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        i_category.setItems(FXCollections.observableArrayList("Salary","Bonus","Shares","Profit","Reward","Others"));

    }

    //Used for connectivity
    Connection connection=null;
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

     

     //For expense:type 1    income:type 2

     // For income save button
    public void i_saveButtonOnAction(ActionEvent e)
    {
        // amount and category field should not be empty
        if(!i_amount.getText().trim().isEmpty()) {
           int amount = Integer.parseInt(i_amount.getText());   //To convert getText's text into int
            if(amount <= 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Amount can't be less than 0");
                alert.show(); 
            }
            else{
                addData(e, 2, Integer.parseInt(i_amount.getText()), i_category.getValue(), i_date.getValue(), regcontroller.id);
                // For changing scene to the main page
                try {
                    root = FXMLLoader.load(getClass().getResource("main.fxml"));
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ex) {
                    System.out.println("Can't load");
                }
            }
        }
        else
        {
            // If all fields are not filled
            System.out.println("Please fill in all information!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill all information to register");
            alert.show();
        } 
    }


    public void i_cancelOnAction(ActionEvent e) throws Exception {
        try {
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load");
        }
    }
    
    public void addData(ActionEvent e,int type,int amount,String category,LocalDate date,int userID)
    {
        connection = connection();
        PreparedStatement psInsert=null;   

        try {
                psInsert = connection.prepareStatement("INSERT INTO expense_tracker.data (type,amount,category,date,userID) VALUES (?,?,?,?,?)");
                psInsert.setInt(1, type);
                psInsert.setInt(2, amount);
                psInsert.setString(3, category);
                psInsert.setString(4, date+"");
                psInsert.setInt(5, userID);
                psInsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Your data added successfully");
                alert.show();    

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    public void onIDateClicked()
    {
        i_date.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
    }
 
}
