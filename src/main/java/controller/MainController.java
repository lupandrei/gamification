package controller;

import com.example.gamification.Main;
import domain.Badge;
import domain.User;
import domain.dtos.BadgeDTO;
import domain.dtos.QuestDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Service;
import observer.observer;
import java.io.IOException;
import java.util.List;

public class MainController implements observer{
    public TextField textFieldReward;
    public Button buttonCreateQuest;
    public TableView<QuestDTO> tableViewQuests;
    public TableColumn<QuestDTO,String> tableColumnType;
    public TableColumn<QuestDTO,String> tableColumnCreator;
    public TableColumn<QuestDTO,Integer> tableColumnReward;
    public Button buttonStart;

    public Text textUsername;
    public Text textWelcome;
    public Text textTokens;
    public Text textBadges;
    public TableView<BadgeDTO> tableViewBadges;
    public TableColumn<BadgeDTO,String> tableColumnBadgeType;
    public TableColumn<BadgeDTO,String> tableColumnBadgeName;
    public TextField textFieldQuestion;
    public TextField textFieldAnswer;
    public TextField textFieldHint;
    public TableColumn<QuestDTO,Integer> tableColumnId;
    private ObservableList<User> modelUser = FXCollections.observableArrayList();
    private ObservableList<BadgeDTO> modelBadges = FXCollections.observableArrayList();
    private ObservableList<QuestDTO> modelQuests = FXCollections.observableArrayList();
    public TableColumn<User,Integer> tableColumnTokens;
    public TableColumn<User,String> tableColumnUsername;
    public TableView<User> tableViewTop;
    public javafx.scene.control.TabPane TabPane;
    public Text textNrTokens;
    private Service srv;
    private User user;

    public void handleButtonStart(ActionEvent actionEvent) {
        int index = tableViewQuests.getSelectionModel().getSelectedItem().getId();
        if(index!=0){
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/trivia-view.fxml"));
            try{
                Scene scene = new Scene(fxmlLoader.load(), 600, 500);
                stage.setTitle(user.getId());
                stage.setScene(scene);
                String creator = tableViewQuests.getSelectionModel().getSelectedItem().getCreator();
                int reward = tableViewQuests.getSelectionModel().getSelectedItem().getReward();
                QuestDTO questDTO = new QuestDTO(index,"trivia",creator,reward);
                TriviaController triviaController =fxmlLoader.getController();
                triviaController.setService(srv,user,questDTO);

                stage.show();
            }
            catch(IOException ex){

            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("First you should select one quest from the table!");
            alert.showAndWait();
        }
    }


    public void handleButtonCreateQuest(ActionEvent actionEvent) {
        String answer = textFieldAnswer.getText().toString();
        String question = textFieldQuestion.getText().toString();
        String hint = textFieldHint.getText().toString();
        int reward = Integer.valueOf(textFieldReward.getText().toString());
        if(!answer.isEmpty() && !question.isEmpty() && !hint.isEmpty() && reward!=0){
            if(reward>user.getScore()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("You don't have enough tokens!");
                alert.showAndWait();
            }
            else{
                srv.addQuestTrivia(question,answer,hint,user.getId(),reward);
                srv.updateQuestsCreated(user.getId(),srv.findUserById(user.getId()).getQuestsCreated()+1);
                srv.updateTokens(user.getId(),user.getScore()-reward);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Succes!");
                alert.setContentText("Your quest has been added!");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Complete all the fields!");
            alert.showAndWait();
        }
    }

    public void setService(Service srv, User user) {
        this.srv=srv;
        srv.addObserver(this);
        this.user=user;
        srv.updateBadges();
        textNrTokens.setText(String.valueOf(user.getScore()));
        textUsername.setText(String.valueOf(user.getId()));

        initModelUser(srv.getUsersTop());
        initModelBadges(srv.getAllBadges(user.getId()));
        initModelQuests(srv.getAllTriviaQuests(user.getId()));
    }

    private void initModelQuests(List<QuestDTO> allTriviaQuests) {
        modelQuests.setAll(allTriviaQuests);
    }

    private void initModelBadges(List<BadgeDTO> allBadges) {
        modelBadges.setAll(allBadges);
    }

    private void initModelUser(List<User> usersTop) {
        modelUser.setAll(usersTop);
    }
    @FXML
    public void initialize(){
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
        tableColumnTokens.setCellValueFactory(new PropertyValueFactory<User,Integer>("score"));
        tableViewTop.setItems(modelUser);

        tableColumnBadgeName.setCellValueFactory(new PropertyValueFactory<BadgeDTO,String>("name"));
        tableColumnBadgeType.setCellValueFactory(new PropertyValueFactory<BadgeDTO,String>("type"));
        tableViewBadges.setItems(modelBadges);

        tableColumnType.setCellValueFactory(new PropertyValueFactory<QuestDTO,String>("type"));
        tableColumnCreator.setCellValueFactory(new PropertyValueFactory<QuestDTO,String>("creator"));
        tableColumnReward.setCellValueFactory(new PropertyValueFactory<QuestDTO,Integer>("reward"));
        tableColumnId.setCellValueFactory(new PropertyValueFactory<QuestDTO,Integer>("id"));
        tableViewQuests.setItems(modelQuests);
    }

    @Override
    public void update() {
        user = srv.findUserById(user.getId());
        textNrTokens.setText(String.valueOf(user.getScore()));
        srv.updateBadges();
        initModelUser(srv.getUsersTop());
        initModelBadges(srv.getAllBadges(user.getId()));
        initModelQuests(srv.getAllTriviaQuests(user.getId()));
    }
}
