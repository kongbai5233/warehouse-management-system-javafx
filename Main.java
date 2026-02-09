import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    boolean isStageClosed = false;

    @Override
    public void start(Stage stage) {
        GridPane rootNode = new GridPane();
        rootNode.setHgap(15);
        rootNode.setVgap(50);
        Button button_login = new Button("管理员登录");
        Button button_register = new Button("管理员注册");

        rootNode.add(button_login, 0, 0);
        rootNode.add(button_register, 0, 1);
        button_login.setPrefWidth(200);
        button_login.setPrefHeight(80);

        button_register.setPrefHeight(80);
        button_register.setPrefWidth(200);
        rootNode.setAlignment(Pos.CENTER);

        String background_pic="images/bg2.jpg";
        rootNode.setStyle("-fx-background-image: url("+background_pic+"); -fx-background-size: cover; ");
        button_login.setStyle("-fx-opacity: 70%; -fx-background-insets: 0; -fx-font-size: 30;");
        button_register.setStyle("-fx-opacity: 70%;-fx-background-insets: 0; -fx-font-size: 30;");
        Scene scene = new Scene(rootNode, 800, 500);

        stage.setTitle("主页面");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();

        button_login.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                Login login = new Login();
                Stage loginStage = new Stage();

                login.start(loginStage);//进入登录页面

                loginStage.setOnHidden(new EventHandler<WindowEvent>() { // 监听登录页面是否被关闭
                    @Override
                    public void handle(WindowEvent event) {
                        isStageClosed = true;
//                        System.out.println("页面被关闭了");
                        System.out.println(login.is_login());
                        if (login.is_login()) {
                            loginStage.close();
                            Manager manager = new Manager();
                            try {
                                manager.start(new Stage());
                                stage.close();
                            } catch (Exception e) {
                                System.out.println("程序出错！！");
                                e.printStackTrace();
                            }
                        }
                        isStageClosed = false;
                    }
                });
            }
        });




//        isStageClosed = true;
//        System.out.println("页面被关闭了");
//        System.out.println(login.is_login());
//        if (login.is_login()) {
//            Manager manager = new Manager();
//            try {
//                manager.start(new Stage());
//            } catch (Exception e) {
//                System.out.println("程序出错！！");
//            }
//
//        }

        button_register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Register register = new Register();
                Stage registerStage = new Stage();
                register.start(registerStage);//进入注册页面

                registerStage.setOnHidden(new EventHandler<WindowEvent>() {//监听登录页面是否被关闭
                    @Override
                    public void handle(WindowEvent event) {
                        registerStage.close();

                    }
                });

            }
        });

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}