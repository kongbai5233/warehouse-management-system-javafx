import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Controller_history_list {
    @FXML
    private TableView<Manager_Item> tbaleview;

    @FXML
    private TableColumn<Manager_Item, Integer> NO;

    @FXML
    private TableColumn<Manager_Item, String> materialItem;

    @FXML
    private TableColumn<Manager_Item,String > materialDes;

    @FXML
    private TableColumn<Manager_Item, String> unit;

    @FXML
    private TableColumn<Manager_Item, String> amount;

    @FXML
    private TableColumn<Manager_Item, String> op_user;

    @FXML
    private TableColumn<Manager_Item, String> alert_time;

    @FXML
    private TableColumn<Manager_Item, String> op_cz;

    @FXML
    private TextField get_op_user;

    @FXML
    private Button search_button;

    @FXML
    private Button claer_button;

    private ObservableList<Manager_Item> data;

    @FXML
     void initialize(){
        data= FXCollections.observableArrayList();

        // 设置列与数据模型的对应关系
        //fxml模型   --------连接------->内容类的属性名称
        NO.setCellValueFactory(new PropertyValueFactory<>("NO"));
        materialItem.setCellValueFactory(new PropertyValueFactory<>("materialItem"));
        materialDes.setCellValueFactory(new PropertyValueFactory<>("materialDes"));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        op_user.setCellValueFactory(new PropertyValueFactory<>("op_user"));
        alert_time.setCellValueFactory(new PropertyValueFactory<>("alert_time"));
        op_cz.setCellValueFactory(new PropertyValueFactory<>("op_cz"));


        if(!updata()){
            new ShowAlert(Alert.AlertType.WARNING,"出错！！","列表更新异常！！").show();
        }

        claer_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                data.clear();
                if(!updata()){
                    new ShowAlert(Alert.AlertType.WARNING,"出错！！","列表更新异常！！").show();
                }
            }
        });

        search_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String search_op=get_op_user.getText();
                if(search_op.length()==0||search_op.length()>15){
                    new ShowAlert(Alert.AlertType.WARNING,"警告！！","操作人员名字字数为空或超过最大限制！！！请重新尝试！！");
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException cne) {
                    cne.printStackTrace();
                }
                var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";

                String sql = "SELECT * FROM history WHERE 操作人员 = '"+search_op+ "'";
                try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                     var pstmt = conn.prepareStatement(sql);
                ) {
                    ResultSet rs = pstmt.executeQuery();
                    data.clear();
                    while (rs.next()) {
                        int get_NO=rs.getInt("NO");
                        String materialItem=rs.getString("物料号");
                        String materialDes=rs.getString("物料描述");;
                        String unit=rs.getString("单位");;
                        int amount=rs.getInt("出入数量");;
                        String op_user=rs.getString("操作人员");
                        String alert_time=rs.getString("操作时间");
                        String opcz=rs.getString("操作");
                        System.out.println(opcz);
                        data.add(new Manager_Item(get_NO,materialItem,materialDes,unit,amount,op_user,alert_time,opcz));
                    }
                    if(data.size()>0){
                        tbaleview.setItems(data);
                    }else{
                        new ShowAlert(Alert.AlertType.WARNING,"查询失败！","不存在该操作人员的操作历史！！").show();
                    }

                    conn.close();
                } catch (SQLException ex) {
                    new ShowAlert(Alert.AlertType.WARNING,"程序出错！","服务器异常！！").show();
                }



            }
        });
    }


    private boolean updata(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";

        String sql = "select * from history";
        System.out.println(sql);
        try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
             var pstmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int get_NO=rs.getInt("NO");
                String materialItem=rs.getString("物料号");
                String materialDes=rs.getString("物料描述");;
                String unit=rs.getString("单位");;
                int amount=rs.getInt("出入数量");;
                String op_user=rs.getString("操作人员");
                String alert_time=rs.getString("操作时间");
                String opcz=rs.getString("操作");
                System.out.println("____"+opcz);
              data.add(new Manager_Item(get_NO,materialItem,materialDes,unit,amount,op_user,alert_time,opcz));
            }
            tbaleview.setItems(data);
            conn.close();
            return true;
        } catch (SQLException ex) {
            new ShowAlert(Alert.AlertType.WARNING,"程序出错！","服务器异常！！").show();
            return false;
        }

    }



}
