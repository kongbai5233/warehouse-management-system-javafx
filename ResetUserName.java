import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ResetUserName extends Application {
    String user_name=Login.return_user;
    @Override
    public void start(Stage stage)  {
        GridPane rootNode=new GridPane();
        rootNode.setHgap(10);
        rootNode.setVgap(10);

        String background_pic="123.jpg";
        rootNode.setStyle("-fx-background-image: url("+background_pic+"); -fx-background-size: cover; ");
        Label now=new Label("当前用户名");

        Label now_username=new Label(Login.return_name);
        now_username.setFont(Font.font(15));
        now_username.setStyle("-fx-text-fill: red");

        Label after=new Label("修改名称");
        Button sure_button=new Button("确认");
        TextField after_text=new TextField();

        rootNode.add(now,0,0);
        rootNode.add(now_username,1,0);

        rootNode.add(after,0,2);
        rootNode.add(after_text,1,2);

        rootNode.add(sure_button,1,3);
        sure_button.setTranslateX(60);

        sure_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String Data_name=after_text.getText();
                if(Data_name.length()<11) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException cne) {
                        cne.printStackTrace();
                    }
                    var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";


                    String sql = "UPDATE manager SET 用户名 = '" + Data_name + "' WHERE 账号 = '" + user_name + "'";
                    System.out.println(sql);
                    try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                         var pstmt = conn.prepareStatement(sql);
                    ) {
                        int rowsAffected = pstmt.executeUpdate();
                        System.out.println(rowsAffected);
                        if (rowsAffected > 0) {
                            new ShowAlert(Alert.AlertType.INFORMATION, "修改成功！", "成功修改名称为 " + Data_name + "!!!!").show();
                            Login.return_name=Data_name;
                            stage.close();
                        }
                    } catch (SQLException ex) {
                        new ShowAlert(Alert.AlertType.WARNING, "修改失败！", "可能因素：服务器异常！").show();
                    }
                }else{
                    new ShowAlert(Alert.AlertType.WARNING, "修改失败！", "用户名禁止过长！").show();
                }

            }


        });

        Scene scene=new Scene(rootNode,300,100);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);//强制聚焦
        stage.setTitle("重置用户名");
        stage.show();
    }
}
