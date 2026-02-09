import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;


public class Add_iteam extends Application {

//    开局内全局方便里面函数获取判断
    private TextField material_iteam;
    private TextField unit ;
    private TextField materialDes;
    private TextField amount;
    @Override
    public void start(Stage stage) {
        GridPane rootNode = new GridPane();
        rootNode.setHgap(10);
        rootNode.setVgap(10);

        String background_pic = "images/bg7.jpg";
        rootNode.setStyle("-fx-background-image: url(" + background_pic + "); -fx-background-size: cover; ");

        Label label1 = new Label("物料号：");
        material_iteam = new TextField();
        Label label2 = new Label("物料描述：");
        materialDes = new TextField();
        Label label3 = new Label("单位：");
        unit = new TextField();
        Label label4 = new Label("数量：");
        amount = new TextField();




        rootNode.add(label1, 0, 0);
        rootNode.add(material_iteam, 1, 0);

        rootNode.add(label2, 0, 1);
        rootNode.add(materialDes, 1, 1);

        rootNode.add(label3, 0, 2);
        rootNode.add(unit, 1, 2);

        rootNode.add(label4, 0, 3);
        rootNode.add(amount, 1, 3);

        Button sure_button = new Button("确认");

        sure_button.setPrefWidth(100);
        sure_button.setPrefHeight(50);
        sure_button.setStyle("-fx-opacity: 70%; -fx-background-insets: 0; -fx-padding: 0;-fx-text-fill: red; -fx-font-size: 20");
        rootNode.add(sure_button, 0, 4, 3, 1);
        sure_button.setTranslateX(80);
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 500, 300);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);//强制聚焦
        stage.setTitle("添加项目");
        stage.show();


        sure_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (legal()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException cne) {
                        cne.printStackTrace();
                    }
                    var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";
                    String s1 = material_iteam.getText();
                    String s3 = unit.getText();
                    String s2 = materialDes.getText();
                    String s4 = amount.getText();
                    String sql = "INSERT INTO storehouse(物料号, 物料描述,单位,数量) " + "VALUES('" + s1 + "','" + s2 + "','" + s3 + "',"+parseInt(s4)+")";
                    System.out.println(sql);
                    try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                         var pstmt = conn.prepareStatement(sql);
                    ) {
                        int rowsInserted = pstmt.executeUpdate(); // 执行插入操作

                        if (rowsInserted > 0) {//判断插入成功

                            new Insert_history(new Manager_Item(s1,s2,s3,parseInt(s4)),"增").insert();

                            new ShowAlert(Alert.AlertType.INFORMATION, "添加成功！！", "您已经成功添加物料码为"+s1+"的商品！！！！").show();
                            conn.close();//关闭数据库
                            stage.hide();//隐藏窗口然后被检测后回到管理页面
                        }

                    } catch (SQLException ex) {
                        new ShowAlert(Alert.AlertType.WARNING, "程序出错！", "服务器异常！！").show();
                    }
                } else {
                    new ShowAlert(Alert.AlertType.WARNING, "错误！！！", "已存在物料号!!!!!").show();
                }
            }
        });



//    下面是start结束
    }

        private boolean legal () {
            boolean flag = true;
            String s1 = material_iteam.getText();
            String s2 = unit.getText();
            String s3 = materialDes.getText();
            String s4 = amount.getText();

            if (s1.length() == 0 || s2.length() == 0 || s3.length() == 0 || s4.length() == 0) flag = false;
            else {


                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException cne) {
                    cne.printStackTrace();
                }
                var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";
                String sql = "SELECT * FROM storehouse WHERE 物料号 = '" + s1 + "'";
                try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                     var pstmt = conn.prepareStatement(sql);
                ) {
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {//存在即找到，不满足唯一性
                        flag = false;
                    }

                    conn.close();
                } catch (SQLException ex) {
                    new ShowAlert(Alert.AlertType.WARNING, "程序出错！", "服务器异常！！").show();
                }
            }

            return flag;
        }


}
