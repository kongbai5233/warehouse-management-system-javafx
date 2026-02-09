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
import java.sql.SQLException;

import static java.lang.Integer.parseInt;


public class Alert_iteam extends Application {
//    Manager_Item alter_iteam;
    private String materialItem_t;
    private String materialDes_t;
    private String unit_t;
    private int amount_t;
    //    开局内全局方便里面函数获取判断
    private TextField unit_d;
    private TextField materialDes_d;
    private TextField amount_d;

    public Alert_iteam(String materialItem, String materialDes, String unit, int amount) {
        materialItem_t=materialItem;
        materialDes_t=materialDes;
        unit_t=unit;
        amount_t=amount;

    }
//    Alert_iteam(Manager_Item alter_iteam){
//        this.alter_iteam=alter_iteam;
//    }

    @Override
    public void start(Stage stage) {
        GridPane rootNode = new GridPane();
        rootNode.setHgap(10);
        rootNode.setVgap(10);

        String background_pic = "images/bg5.png";
        rootNode.setStyle("-fx-background-image: url(" + background_pic + "); -fx-background-size: cover; ");


        Label label2 = new Label("物料描述：");
        materialDes_d = new TextField(materialDes_t);
        Label label3 = new Label("单位：");
        unit_d = new TextField(unit_t);
        Label label4 = new Label("数量：");
        amount_d = new TextField(String.valueOf(amount_t) );




        rootNode.add(label2, 0, 1);
        rootNode.add(materialDes_d, 1, 1);

        rootNode.add(label3, 0, 2);
        rootNode.add(unit_d, 1, 2);

        rootNode.add(label4, 0, 3);
        rootNode.add(amount_d, 1, 3);

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
        stage.setTitle("修改项目");
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
                    String s1 = materialItem_t;
                    String s3 = unit_d.getText();
                    String s2 = materialDes_d.getText();
                    String s4 = amount_d.getText();
                    String sql = "UPDATE storehouse SET 物料描述 = '"+s3+"', 单位 = '"+s2+"', 数量="+s4+" WHERE 物料号='"+s1+"'";
                    System.out.println(sql);
                    try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                         var pstmt = conn.prepareStatement(sql);
                    ) {
                        int rowsInserted = pstmt.executeUpdate(); // 执行更新操作

                        if (rowsInserted > 0) {//判断插入成功
                            int alert_num=parseInt(s4)-amount_t;
                            new Insert_history(new Manager_Item(s1,s2,s3,alert_num),"改").insert();


                            new ShowAlert(Alert.AlertType.INFORMATION, "更新成功！！", "您已经成功更新物料码为"+s1+"的商品！！！！").show();
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
        String s1 = materialItem_t;
        String s2 = unit_d.getText();
        String s3 = materialDes_d.getText();
        String s4 = amount_d.getText();

        if ( s2.length() == 0 || s3.length() == 0 || s4.length() == 0) flag = false;
        return flag;
    }



}