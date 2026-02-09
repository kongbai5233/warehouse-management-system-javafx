import javafx.scene.control.Alert;
//弹窗类
public class ShowAlert {
//    弹窗类型
    Alert.AlertType alertType;

//    弹窗标题
    String title;

//    弹窗信息
    String message;

    ShowAlert(Alert.AlertType alertType, String title, String message){
        this.alertType=alertType;
        this.title=title;
        this.message=message;
    }

    void show() {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
