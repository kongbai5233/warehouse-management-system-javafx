import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class Manager extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        GridPane rootNode=new GridPane();
        rootNode.setHgap(50);
        rootNode.setVgap(50);


        String background_pic="images/bg0.png";
        rootNode.setStyle("-fx-background-image: url("+background_pic+"); -fx-background-size: cover; ");



        MenuBar menuBar = new MenuBar();


        Menu list_user=new Menu("用户管理");
        MenuItem alert_name=new MenuItem("修改用户名");
        list_user.getItems().add(alert_name);
        alert_name.setOnAction(actionEvent -> {
            new ResetUserName().start(new Stage());
        });

        Menu list_control=new Menu("功能操作");
        MenuItem control=new MenuItem("存储操作");
        MenuItem history=new MenuItem("历史查看");
        list_control.getItems().add(control);
        control.setOnAction(actionEvent -> {

            try {
                Store_manager store_managee=new Store_manager();
                Stage store_managee_stage=new Stage();
                store_managee.start(store_managee_stage);
                stage.hide();
                store_managee_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        stage.show();
                    }
                });
            } catch (IOException e) {
                new ShowAlert(Alert.AlertType.WARNING,"系统错误！","系统出错，打开失败，请联系管理员！！");
                throw new RuntimeException(e);
            }

        });



        list_control.getItems().add(history);
        history.setOnAction(actionEvent -> {
            try{
            History_manager history_manager=new History_manager();
            Stage history_manager_stage=new Stage();
            history_manager.start(history_manager_stage);
            stage.hide();
            history_manager_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    stage.show();
                }
            });
        } catch (IOException e) {
            new ShowAlert(Alert.AlertType.WARNING,"系统错误！","系统出错，打开失败，请联系管理员！！");
            throw new RuntimeException(e);
        }
        });


        Menu list_other=new Menu("其他");
        MenuItem about_me=new MenuItem("关于");
        about_me.setOnAction(actionEvent -> {
            new AboutMe().start(new Stage());
        });
        list_other.getItems().add(about_me);

        menuBar.getMenus().addAll(list_user,list_control,list_other);

        rootNode.add(menuBar,0,0);

        // 设置列和行的约束，使菜单栏充满整个宽度
        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.ALWAYS);
        rootNode.getColumnConstraints().add(column);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.NEVER);
        rootNode.getRowConstraints().add(row1);

        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);
        rootNode.getRowConstraints().add(row2);

        GridPane.setHalignment(menuBar, HPos.CENTER);
        GridPane.setValignment(menuBar, VPos.TOP);

        Scene scene=new Scene(rootNode,800,500);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);//强制聚焦
        stage.setTitle("主页面");
        stage.show();

    }

}
