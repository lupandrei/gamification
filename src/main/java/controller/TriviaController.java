package controller;

import domain.QuestTrivia;
import domain.User;
import domain.dtos.QuestDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Service;

public class TriviaController {
    private Service srv;
    private User user;
    private QuestTrivia quest;
    private int reward;
    public Text question;
    public Text textQuestion;
    public Button buttonHint;
    public Text hint;
    public Text textHint;
    public Button buttonSubmit;
    public TextField textField;

    public void handleButtonSubmit(ActionEvent actionEvent) {
        String answer = textField.getText().toString();
        if(!answer.isEmpty())
        {
            if(answer.equalsIgnoreCase(quest.getAnswer())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("CONGRATS");
                alert.setContentText("THAT'S RIGHT!");
                alert.showAndWait();
                srv.updateTokens(user.getId(),user.getScore()+reward);
                srv.updateQuestsTaken(user.getId(),user.getQuestsTaken()+1);
                srv.deleteQuest(quest.getId());
                Stage currentStage =(Stage) textHint.getScene().getWindow();
                currentStage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("You lost");
                alert.setContentText("The answer was: " + quest.getAnswer());
                alert.showAndWait();
                Stage currentStage =(Stage) textHint.getScene().getWindow();
                currentStage.close();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Your answer is empty");
            alert.showAndWait();
        }
    }

    public void handleButtonHint(ActionEvent actionEvent) {
        textHint.setVisible(true);
        hint.setVisible(true);
        textHint.setText(quest.getHint());
        buttonHint.setDisable(true);
        reward=reward/2;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("OPS...");
        alert.setContentText("You got the hint, but your max reward is now "+ String.valueOf(reward));
        alert.showAndWait();
    }

    public void setService(Service srv, User user, QuestDTO questDTO){
        this.srv=srv;
        this.user=user;
        this.quest=srv.findQuestById(questDTO.getId());
        this.reward = questDTO.getReward();
        hint.setVisible(false);
        textHint.setVisible(false);
        textQuestion.setText(quest.getQuestion());
    }
}
