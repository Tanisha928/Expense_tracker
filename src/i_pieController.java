import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class i_pieController implements Initializable {
    @FXML
    public PieChart pieChart;
    Stage stage;
    Scene scene;
    Parent root;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
        ObservableList<PieChart.Data> pieChartData = fetchDataFromDatabase();
        pieChart.setData(pieChartData);
    }
        catch (Exception ex){
            System.out.println("Can't load");
        }

}
    private ObservableList<PieChart.Data> fetchDataFromDatabase() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            connection = connection();
           try{
              PreparedStatement statement = connection.prepareStatement("SELECT userID,category,SUM(amount) as amount FROM expense_tracker.data\n" +
                    "where type=\"2\" and (category=\"Bonus\" or category=\"Salary\"  or category=\"Shares\"  or  category=\"Reward\" or category=\"Profit\" or category=\"Others\")\n" +
                    "GROUP BY userID,category;");
            
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                 int userID = resultSet.getInt("userID");
                if(userID == regcontroller.id)
                {
                pieChartData.add(new PieChart.Data(category, amount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }

     public void i_chartbackbtnOnAction(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("showincome.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load");
        }
    }
}