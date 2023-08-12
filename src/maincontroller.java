import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class maincontroller  {
    Stage stage;
    Scene scene;
    Parent root;
    Button mainexitbtn;
     
    @FXML
    void adde_btnOnAction(ActionEvent e) {
        // Changing scene to the add expense
        try{
            root = FXMLLoader.load(getClass().getResource("addExpense.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex)
        {
            System.out.println("Can't load");
        }
    }

     @FXML
    void addi_btnOnAction(ActionEvent e) {
        try{
            root = FXMLLoader.load(getClass().getResource("addIncome.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex)
        {
            System.out.println("Can't load");
        }
    }

    public void historybtnOnAction(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("show.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load");
        }
    }

    public void showexpenseOnAction(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("showexpense.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load");
        }
        
    }

    public void showincomeOnAction(ActionEvent e){
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

    public void mainexitbtnOnAction(ActionEvent e){
       System.exit(0);
    }
}
