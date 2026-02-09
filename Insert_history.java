import javafx.scene.control.Alert;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class Insert_history {
    LocalDate currentDate ;
    Manager_Item in_item;
    String op_user;
    String now_time;
    String cz;
    Insert_history(Manager_Item in_item,String cz){
        this.in_item=in_item;
        this.cz=cz;
    }
   public void insert(){
       LocalDate currentDate = LocalDate.now();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        var dburl = "jdbc:mysql://127.0.0.1:3306/javasql?serverTimezone=UTC";
        String materialItem=in_item.getMaterialItem();
        String materialDes=in_item.getMaterialDes();
        String unit=in_item.getUnit();
       int amount=in_item.getAmount();
       op_user=Login.return_user;
       now_time=currentDate.toString();


       String sql= "INSERT INTO history(物料号,物料描述,单位,出入数量,操作人员,操作时间,操作) VALUES('"+materialItem+"','"+materialDes+"','"+unit+"',"+String.valueOf(amount)+",'"+op_user+"','"+now_time+"','"+cz+"')";
       System.out.println(sql);
       try (var conn = DriverManager.getConnection(dburl, "root", "kongbai520..");

            var pstmt = conn.prepareStatement(sql);
        ) {
            int success=pstmt.executeUpdate();


            conn.close();
        } catch (SQLException ex) {
            new ShowAlert(Alert.AlertType.WARNING,"出错！","历史操作添加出错！！！服务器异常！！").show();
        }
    }

}
