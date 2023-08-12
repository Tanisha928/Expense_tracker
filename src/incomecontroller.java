import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class incomecontroller implements Initializable {


    @FXML
    ScrollPane historyscroll;
    @FXML
    RadioButton i_todayradio;
    @FXML
    RadioButton i_weekradio;
    @FXML
    RadioButton i_monthradio;
    @FXML
    RadioButton i_allradio;
    @FXML
    Button incomebackbtn;
    @FXML
    Label i_total;
    Connection connection;
    Stage stage;
    Scene scene;
    Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ToggleGroup tGroup=new ToggleGroup();
            i_todayradio.setToggleGroup(tGroup);
            i_weekradio.setToggleGroup(tGroup);
            i_monthradio.setToggleGroup(tGroup);
            i_allradio.setToggleGroup(tGroup);
             i_allradio.setSelected(true);
            showIncome();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public void showIncome() throws Exception{
        VBox mainBox = new VBox();
        Font text = new Font("Verdana", 16);
        mainBox.setPrefWidth(598);
        mainBox.setSpacing(10);
        connection = connection();
        ResultSet historySet;
        int temp=0;
        
         if(i_todayradio.isSelected())
        {
            PreparedStatement getHistory = connection.prepareStatement("SELECT * from expense_tracker.data where userID = ? and type = 2 and date = CURRENT_DATE()");
            getHistory.setInt(1, regcontroller.id);
            historySet = getHistory.executeQuery();
        }
        else if(i_weekradio.isSelected())
        {
            PreparedStatement getHistory = connection.prepareStatement("SELECT * from expense_tracker.data where userID = ? and type = 2 and date> CURRENT_DATE() - INTERVAL 7 day order by date desc");
            getHistory.setInt(1, regcontroller.id);
            historySet = getHistory.executeQuery();
        }
        else if(i_monthradio.isSelected())
        {
            PreparedStatement getHistory = connection.prepareStatement("SELECT * from expense_tracker.data where userID = ? and type = 2 and date> CURRENT_DATE() - INTERVAL 30 day order by date desc");
            getHistory.setInt(1, regcontroller.id);
            historySet = getHistory.executeQuery();
        }
        else
        {
            PreparedStatement getHistory = connection.prepareStatement("SELECT * from expense_tracker.data where userID = ? and type = 2 order by date desc");
             getHistory.setInt(1, regcontroller.id);
             historySet = getHistory.executeQuery();
        }
           
            while(historySet.next())
         {
            AnchorPane gp = new AnchorPane();
            Label dateLabel = new Label(historySet.getDate("date") + "");
            Label categoryLabel = new Label(historySet.getString("category"));
            Label amountLabel = new Label(historySet.getString("amount"));
            dateLabel.setFont(text);
            categoryLabel.setFont(text);
            amountLabel.setFont(text);
            gp.getChildren().add(dateLabel);
            gp.getChildren().add(categoryLabel);
            gp.getChildren().add(amountLabel);
            dateLabel.setLayoutX(10);
            amountLabel.setLayoutX(430);
            categoryLabel.setLayoutX(160);
            categoryLabel.setMinWidth(200);
            categoryLabel.setAlignment(Pos.CENTER);
            dateLabel.setMinHeight(40);
            categoryLabel.setMinHeight(40);
            amountLabel.setMinHeight(40);
             temp = temp + Integer.parseInt(amountLabel.getText());
           
                dateLabel.setStyle("-fx-text-fill: green");
                categoryLabel.setStyle("-fx-text-fill: green");
                amountLabel.setStyle("-fx-text-fill: green");
            gp.setStyle("-fx-background-color: #cbdcf2");
            mainBox.getChildren().add(gp);
         }
        
        i_total.setText(temp+"");
        historyscroll.setContent(mainBox);
    }

    public void incomebackbtnOnAction(ActionEvent e){
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

    public void charT(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("i_pieCharT.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load");
        }
    }
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
}