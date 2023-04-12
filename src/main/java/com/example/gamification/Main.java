package com.example.gamification;

import controller.LoginController;
import repository.*;
import service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import test.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    private Service srv;

    @Override
    public void start(Stage primaryStage) throws IOException {
      Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        Properties propstest = new Properties();
        try {
            propstest.load(new FileReader("bdtest.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bdtest.config "+e);
        }
        IUserRepository userRepositoryt = new UserRepository(propstest);
        ICreatorBadgeRepository creatorBadgeRepositoryt = new CreatorBadgeRepository(propstest);
        IPlayerBadgeRepository playerBadgeRepositoryt = new PlayerBadgeRepository(propstest);
        IUserCreatorBadgesRepository userCreatorBadgesRepositoryt = new UserCreatorBadgesRepository(propstest);
        IUserPlayerBadgesRepository userPlayerBadgesRepositoryt = new UserPlayerBadgesRepository(propstest);
        IUserTriviaQuestRepository userTriviaQuestRepositoryt = new UserTriviaQuestRepository(propstest);
        IQuestTriviaRepository questTriviaRepositoryt = new QuestTriviaRepository(propstest);
        Test test = new Test(new Service(userRepositoryt,creatorBadgeRepositoryt,playerBadgeRepositoryt,userPlayerBadgesRepositoryt,userCreatorBadgesRepositoryt,questTriviaRepositoryt,userTriviaQuestRepositoryt));
        try{
            test.run();
            System.out.println("Succes");
        }catch(Exception ex){

        }

        IUserRepository userRepository = new UserRepository(props);
        ICreatorBadgeRepository creatorBadgeRepository = new CreatorBadgeRepository(props);
        IPlayerBadgeRepository playerBadgeRepository = new PlayerBadgeRepository(props);
        IUserCreatorBadgesRepository userCreatorBadgesRepository = new UserCreatorBadgesRepository(props);
        IUserPlayerBadgesRepository userPlayerBadgesRepository = new UserPlayerBadgesRepository(props);
        IUserTriviaQuestRepository userTriviaQuestRepository = new UserTriviaQuestRepository(props);
        IQuestTriviaRepository questTriviaRepository = new QuestTriviaRepository(props);
        srv = new Service(userRepository, creatorBadgeRepository, playerBadgeRepository, userPlayerBadgesRepository, userCreatorBadgesRepository, questTriviaRepository, userTriviaQuestRepository);
        initView(primaryStage);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Log in");

        LoginController loginController = loader.getController();
        loginController.setService(srv);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
