import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.IOException;

public class History_manager extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("history_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("历史操作查询系统");

        stage.show();
    }

}

