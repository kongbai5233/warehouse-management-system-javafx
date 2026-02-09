import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Register extends Application {
    private String Now_name;
    private String Now_pass;
    private String Now_UserName;

    @Override
    public void start(Stage stage){
        GridPane rootNode=new GridPane();
        rootNode.setHgap(50);
        rootNode.setVgap(50);
        Label label_name=new Label("账号：");

        label_name.setStyle("-fx-text-fill: white");
        Label label_pass=new Label("密码：");
        label_pass.setStyle("-fx-text-fill: white");
        Label label_UserName=new Label("用户名：");
        label_UserName.setStyle("-fx-text-fill: white");
        TextField name=new TextField();
        PasswordField pass=new PasswordField();
        TextField UserName=new TextField();
        rootNode.add(label_name,0,0);
        rootNode.add(name,1,0);
        rootNode.add(label_pass,0,1);
        rootNode.add(pass,1,1);
        rootNode.add(label_UserName,0,2);
        rootNode.add(UserName,1,2);
        String background_pic="images/bg1.png";
        rootNode.setStyle("-fx-background-image: url("+background_pic+"); -fx-background-size: cover; ");

        stage.initModality(Modality.APPLICATION_MODAL);//强制聚焦

        Button button_register=new Button("注册");
        button_register.setStyle("-fx-opacity: 70%; -fx-background-insets: 0; -fx-padding: 0;-fx-padding: 0;-fx-font-size: 30;");
        button_register.setPrefWidth(200);
        button_register.setPrefHeight(100);

        rootNode.add(button_register,0,3, 2,1);
        button_register.setTranslateX(30);

        rootNode.setAlignment(Pos.CENTER);
        Scene scene=new Scene(rootNode,800,500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("注册");
        stage.show();


        button_register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Now_name=name.getText();
                Now_pass=pass.getText();
                Now_UserName=UserName.getText();
                if(Now_name.length()<11&&Now_pass.length()<11&&Now_UserName.length()<11) {
//                数据库连接
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException cne) {
                        cne.printStackTrace();
                    }
                    var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";


//               数据库操作
                    String sql = "INSERT INTO manager(账号, 密码,用户名) " + "VALUES('" + Now_name + "','" + Now_pass + "','" + Now_UserName + "')";
                    try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                         var pstmt = conn.prepareStatement(sql);
                    ) {
                        int rowsInserted = pstmt.executeUpdate(); // 执行插入操作

                        if (rowsInserted > 0) {//判断插入成功
                            new ShowAlert(Alert.AlertType.INFORMATION, "注册成功", "您已经成功注册！").show();
                            conn.close();//关闭数据库
                            stage.hide();//隐藏窗口然后被检测后进入登录页面
                        }

                    } catch (SQLException ex) {

                            new ShowAlert(Alert.AlertType.WARNING, "注册失败", "可能原因：\n"+
                                    "\t1.改账号可能已经存在！\n"+
                                    "\t2.服务器异常导致插入失败！"   ).show();

                    }

                }else{
                    new ShowAlert(Alert.AlertType.ERROR,"注册失败","请勿取超过10位长度的账号、密码或者用户名！！\n"+"请重新尝试！").show();
                }


            }
        });
    }


}
