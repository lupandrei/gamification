package controller;

import com.example.gamification.Main;
import domain.User;
import exception.RepositoryException;
import exception.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class LoginController {
        private Service srv;

        @FXML
        Button login;

        @FXML
        TextField textField;

        @FXML
        PasswordField passwordField;

        public void setService(Service srv){
            this.srv=srv;
        }


        public void handleLoginButton(ActionEvent actionEvent) {
            String username = textField.getText();
            String password = passwordField.getText();
            if(!username.isEmpty() && !password.isEmpty())
            {
                try{
                    User user = srv.loginUser(username,password);
                    connect(user);
                }
                catch(ServiceException|RepositoryException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }

            }


        }

        private void connect(User user) {
            Stage currentStage = (Stage) passwordField.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/main-view.fxml"));
            try{
                Scene scene = new Scene(fxmlLoader.load(), 1200, 500);
                stage.setTitle(user.getId());
                stage.setScene(scene);

                MainController mainController =fxmlLoader.getController();
                mainController.setService(srv,user);

                stage.show();
            }
            catch(IOException ex){

            }
        }

    public void handleSignInRestaurant(ActionEvent actionEvent) {
    }
}
