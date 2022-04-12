import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(new FileInputStream("Image.png"));
        //Image image = new Image(new FileInputStream("Image2.png"));
        //Image image = new Image(new FileInputStream("Image3.png"));
        //Image image = new Image(new FileInputStream("Image4.png"));
        //Image image = new Image(new FileInputStream("Image5.png"));
        //Image image = new Image(new FileInputStream("Image6.png"));

        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        Group group = new Group(imageView);

        Canvas canvas = new Canvas();
        canvas.setHeight(400);
        canvas.setWidth(400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        QuadTree quadTree = new QuadTree(image,group,canvas,gc);
        quadTree.makeTree();
        quadTree.draw();

        //System.out.println("END");

        stage.setTitle("QuadTree - Dawid Gabryś");
        stage.setScene(new Scene(group, 400, 400));
        stage.setResizable(false);
        stage.show();
    }



}
