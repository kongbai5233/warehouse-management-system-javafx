import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Controller_manager_list {
    @FXML
    private TableView<Manager_Item> table_list;

    @FXML
    private TextField material_text;

    @FXML
    private TableColumn<Manager_Item, String> material_iteam;

    @FXML
    private TableColumn<Manager_Item, String> material_des;

    @FXML
    private TableColumn<Manager_Item, String> unit;

    @FXML
    private TableColumn<Manager_Item, Integer> amount;

    @FXML
    private Button search_button;

    @FXML
    private Button add_button;

    @FXML
    private Button alert_button;

    @FXML
    private Button delete_button;

    @FXML
    private Button clear_button;

    private ObservableList<Manager_Item> data;

    private LocalDate currentDate ;


    @FXML
    void  initialize(){//页面初始化就更新第一次数据库
        currentDate = LocalDate.now();

//      存放数据
        data= FXCollections.observableArrayList();


        // 设置列与数据模型的对应关系
        material_iteam.setCellValueFactory(new PropertyValueFactory<>("materialItem"));
        material_des.setCellValueFactory(new PropertyValueFactory<>("materialDes"));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        update();

        clear_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(update()){
                    new ShowAlert(Alert.AlertType.WARNING,"更新成功！！！","正在从数据库君手里偷取数据！！！QAQ").show();
                }
            }
        });


        delete_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               Manager_Item mouse_now= table_list.getSelectionModel().getSelectedItem();
               if(mouse_now==null){//初始鼠标没有选中列表
                   new ShowAlert(Alert.AlertType.WARNING,"警告！！！","未选择目标").show();
                   return;
               }
               String delete_iteam=mouse_now.getMaterialItem();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException cne) {
                    cne.printStackTrace();
                }
                var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";

                String sql = "DELETE FROM storehouse WHERE 物料号 = '"+delete_iteam+"'";
                try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                     var pstmt = conn.prepareStatement(sql);
                ) {
                    int success=pstmt.executeUpdate();


                    if(success>0){

                        new Insert_history(mouse_now,"删").insert();


                        new ShowAlert(Alert.AlertType.INFORMATION,"删除成功！","正在刷新列表！！").show();
                        update();
                    }

                    conn.close();
                } catch (SQLException ex) {
                    new ShowAlert(Alert.AlertType.WARNING,"删除出错！","服务器异常！！").show();
                }

            }
        });

        alert_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Manager_Item mouse_now= table_list.getSelectionModel().getSelectedItem();
                if(mouse_now==null){//初始鼠标没有选中列表
                    new ShowAlert(Alert.AlertType.WARNING,"警告！！！","未选择目标").show();
                    return;
                }

                Alert_iteam alertIteam=new Alert_iteam(mouse_now.getMaterialItem(),mouse_now.getMaterialDes(),mouse_now.getUnit(),mouse_now.getAmount());

                Stage alert_state=new Stage();

                alertIteam.start(alert_state);

                alert_state.setOnHidden(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        alert_state.close();
                        update();
                    }
                });

            }
        });

        search_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String get_materialItem = material_text.getText();
                if (get_materialItem.length() > 0 && get_materialItem.length() < 20) {

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException cne) {
                        cne.printStackTrace();
                    }
                    var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";
                    String sql = "SELECT * FROM storehouse WHERE 物料号 = '"+get_materialItem+ "'";
                    try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
                         var pstmt = conn.prepareStatement(sql);
                    ) {
                        data.clear();//数据清除,避免数据重复
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            String materialItem=rs.getString("物料号");
                            String materialDes=rs.getString("物料描述");;
                            String unit=rs.getString("单位");;
                            int amount=rs.getInt("数量");;
                            data.add(new Manager_Item(materialItem, materialDes, unit, amount));
                        }
                        if(data.size()>0){
                            new ShowAlert(Alert.AlertType.INFORMATION, "搜索成功！", "正在刷新列表！！").show();
                        }else{
                            new ShowAlert(Alert.AlertType.WARNING, "搜索失败！", "不存在改物料码！！").show();
                            update();
                        }
                        table_list.setItems(data);
                        conn.close();
                    } catch (SQLException ex) {
                        new ShowAlert(Alert.AlertType.WARNING, "程序出错！", "服务器异常！！").show();
                    }
                }else{
                    new ShowAlert(Alert.AlertType.WARNING, "不符合搜索标准！", "物料码为空或者过长！！").show();
                }
            }

        });


        add_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Add_iteam add_iteam=new Add_iteam();
                Stage add_iteam_stage = new Stage();
                add_iteam.start(add_iteam_stage);//进入注册页面

                add_iteam_stage.setOnHidden(new EventHandler<WindowEvent>() {//监听登录页面是否被关闭
                    @Override
                    public void handle(WindowEvent event) {
                        add_iteam_stage.close();
                        update();
                    }
                });


            }
        });

    }
//上面是end标识符，别搞错了

//更新数据库列表
    private boolean update(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";

        String sql = "select * from storehouse";
        try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");
             var pstmt = conn.prepareStatement(sql);
        ) {
            data.clear();//数据清除,避免数据重复
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                 String materialItem=rs.getString("物料号");
                 String materialDes=rs.getString("物料描述");;
                 String unit=rs.getString("单位");;
                 int amount=rs.getInt("数量");;
//                System.out.println(rs);
//                System.out.println(materialItem);
//                System.out.println(materialDes);
//                System.out.println(unit);
//                System.out.println(amount);
                data.add(new Manager_Item(materialItem, materialDes, unit, amount));
            }
            table_list.setItems(data);
            conn.close();
            return true;
        } catch (SQLException ex) {
             new ShowAlert(Alert.AlertType.WARNING,"程序出错！","服务器异常！！").show();
            return false;
        }
    }





}
