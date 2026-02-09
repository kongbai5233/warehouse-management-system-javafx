import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class AboutMe extends Application {

    @Override
    public void start(Stage stage)  {

            GridPane rootNode=new GridPane();
            rootNode.setHgap(10);
            rootNode.setVgap(10);
            stage.initStyle(StageStyle.UNDECORATED);
            String background_pic="images/bg3.jpg";
            rootNode.setStyle("-fx-background-image: url("+background_pic+"); -fx-background-size: cover; ");

            Label about = new Label("   关于本系统\n"+
                    "一款仓储管理系统！");
            about.setStyle("-fx-text-fill: RED");
            about.setFont(Font.font(20));


            Label bottom_about = new Label("Copyright @ 1999-2024 PremiumSoft Kong. 保留所有权");
                bottom_about.setStyle("-fx-text-fill: Green");
            bottom_about.setFont(Font.font(15));

            // 设置列约束，使得列扩展以填满空间
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100);
            rootNode.getColumnConstraints().add(columnConstraints);

            // 设置行约束，使得行扩展以填满空间
            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(30);
            RowConstraints row2 = new RowConstraints();
            row2.setPercentHeight(20);
            RowConstraints row3 = new RowConstraints();
            row3.setPercentHeight(33.33);
            rootNode.getRowConstraints().addAll(row1, row2, row3);

            // 将第一个标签添加到第一行，并设置其居中对齐
            GridPane.setHalignment(about, HPos.CENTER);
            GridPane.setValignment(about, VPos.CENTER);
            rootNode.add(about, 0, 0);




            ImageView logo=new ImageView("logo.png");
            logo.setFitWidth(50);
            logo.setFitHeight(50);
            // 将第图片添加到第二行，并设置其居中对齐
            GridPane.setHalignment(logo,HPos.CENTER);
            GridPane.setValignment(logo,VPos.CENTER);
            rootNode.add(logo, 0, 2);

            // 将第二个标签添加到第三行，并设置其居中对齐
            GridPane.setHalignment(bottom_about, HPos.CENTER);
            GridPane.setValignment(bottom_about, VPos.BOTTOM);
            rootNode.add(bottom_about, 0, 3);

            rootNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                            stage.close();
                    }
            }
            );

            Scene scene=new Scene(rootNode,600,300);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);//强制聚焦
            stage.show();
        }


}
