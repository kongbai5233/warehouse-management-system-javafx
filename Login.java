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
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends Application {
    private boolean success;
    public static String return_name;//用户名设置静态方便其他类获取
    public static String return_user;//账号设置静态方便其他类获取
    private CallbackHandler callbackHandler = new CallbackHandler();

    public CallbackHandler get_callbackHandler(){
        return callbackHandler;
    }
    public boolean is_login(){
        return success;
    }

    public String get_name(){
        return return_name;
    }


    // 显示弹窗消息



    @Override
    public void start(Stage stage){
        GridPane rootNode=new GridPane();
        rootNode.setHgap(50);
        rootNode.setVgap(50);
        Label label_name=new Label("账号：");
        label_name.setStyle("-fx-font-size: 20;");
        Label label_pass=new Label("密码：");
        label_pass.setStyle("-fx-font-size: 20;");
        TextField name=new TextField();
        PasswordField pass=new PasswordField();
        rootNode.add(label_name,0,0);
        rootNode.add(name,1,0);
        rootNode.add(label_pass,0,1);
        rootNode.add(pass,1,1);
        String background_pic="images/nav1.jpg";
        rootNode.setStyle("-fx-background-image: url("+background_pic+"); -fx-background-size: cover; ");


        stage.initModality(Modality.APPLICATION_MODAL);//强制聚焦

        Button button_login=new Button("登录");
        button_login.setStyle("-fx-opacity: 70%; -fx-background-insets: 0; -fx-padding: 0;-fx-padding: 0;-fx-font-size: 30;");
        button_login.setPrefWidth(200);
        button_login.setPrefHeight(100);

        rootNode.add(button_login,0,2, 2,1);
        button_login.setTranslateX(30);

        rootNode.setAlignment(Pos.CENTER);
        Scene scene=new Scene(rootNode,800,500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("登录");
        stage.show();

        button_login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String give_name=name.getText();
                String give_pass=pass.getText();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException cne) {
                    cne.printStackTrace();
                }
                var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";

                String sql = "select * from manager";
                try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                     var pstmt = conn.prepareStatement(sql);
                ) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String get_login_name = rs.getString("账号");
                        String get_pass = rs.getString("密码");
                        String get_now_name = rs.getString("用户名");
                        System.out.println(get_now_name+ " " + get_login_name + ' ' + get_pass);
                        if(get_login_name.equals(give_name)&&get_pass.equals(give_pass)){
                            success=true;
                            callbackHandler.notifyLoginResult(true);
                            return_name=get_now_name;
                            return_user=get_login_name;
                            new ShowAlert(Alert.AlertType.INFORMATION,"成功登录","欢迎你的到来！\n"+"\t当前登录账号用户名为："+get_now_name+"").show();
                            conn.close();//关闭数据库连接
                            stage.hide();
                            return;
                        }
                    }
                    if(!success){//登录失败处理
                        new ShowAlert(Alert.AlertType.WARNING,"登录失败","账户错误或者密码错误，请重新尝试！！").show();
                    }


                } catch (SQLException ex) {
                    System.out.println("数据库连接或者是数据库操作失败");
                }

            }
        });

    }



}
