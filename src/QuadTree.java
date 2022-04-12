import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class QuadTree {
    private Node root;
    private Image image;
    private Group group;
    private Canvas canvas;
    private GraphicsContext gc;

    final int BLUE_R = 3;
    final int BLUE_G = 104;
    final int BLUE_B = 228;

    final int YELLOW_R = 255;
    final int YELLOW_G = 242;
    final int YELLOW_B = 0;

    final double TOLERANCE_COLOR = 0.0001;


    Rectangle rectangle;

    public QuadTree(Image image, Group group, Canvas canvas, GraphicsContext gc) {
        this.image = image;
        this.group = group;
        this.canvas = canvas;
        this.gc = gc;

        rectangle = new Rectangle(0,0,400,400);
        root = new Node(null,rectangle);

    }

    public void makeTree() {
        subDivide(root,image);
    }


    public boolean isMixed(Rectangle rectangle, Image image) {
        // Niebieski: R - 3, G - 104, B - 228
        // Żółty: R - 255, G - 242, B - 0

        boolean isTwoColor = false;
        boolean isYellow = false;
        boolean isBlue = false;
        PixelReader pixelReader = image.getPixelReader();

        for (int i = (int)rectangle.getX(); i < (int)(rectangle.getX()+rectangle.getWidth()); i++) {
            for (int j = (int)rectangle.getY(); j < (int)(rectangle.getY()+rectangle.getHeight()); j++) {
               Color color = pixelReader.getColor(i,j);
               if (color.getRed() * 255 > BLUE_R - TOLERANCE_COLOR && color.getRed() * 255 < BLUE_R + TOLERANCE_COLOR && color.getGreen() * 255 > BLUE_G - TOLERANCE_COLOR && color.getGreen() * 255 < BLUE_G + TOLERANCE_COLOR && color.getBlue() * 255 > BLUE_B - TOLERANCE_COLOR && color.getBlue() * 255 < BLUE_B + TOLERANCE_COLOR) {
                   isBlue = true;
               }
               if (color.getRed() * 255 > YELLOW_R - TOLERANCE_COLOR && color.getRed() * 255 < YELLOW_R + TOLERANCE_COLOR && color.getGreen() * 255 > YELLOW_G - TOLERANCE_COLOR && color.getGreen() * 255 < YELLOW_G + TOLERANCE_COLOR && color.getBlue() * 255 > YELLOW_B - TOLERANCE_COLOR && color.getBlue() * 255 < YELLOW_B + TOLERANCE_COLOR) {
                   isYellow = true;
               }

                if (isBlue && isYellow) {
                    isTwoColor = true;
                }

            }
        }
        //System.out.println(isBlue + " " + isYellow);
        return isTwoColor;
    }

    public void subDivide(Node node, Image image) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(node.rect.getX(),node.rect.getY(),node.rect.getWidth(),node.rect.getHeight());

        if (isMixed(node.rect, image)) {
            int width = (int) (node.rect.getWidth() / 2);
            int height = (int) (node.rect.getHeight() / 2);

            if (width > 0 && height > 0) {


                Node node1 = new Node(node,new Rectangle(node.rect.getX(), node.rect.getY(), width, height));
                Node node2 = new Node(node,new Rectangle(node.rect.getX() + width, node.rect.getY(), width, height));
                Node node3 = new Node(node,new Rectangle(node.rect.getX(), node.rect.getY() + height, width, height));
                Node node4 = new Node(node,new Rectangle(node.rect.getX() + width, node.rect.getY() + height, width, height));

                node.ch1 = node1;
                node.ch2 = node2;
                node.ch3 = node3;
                node.ch4 = node4;

                if (isMixed(node1.rect,image)) {
                    subDivide(node1,image);
                }
                else {
                    node1 = null;
                }

                if (isMixed(node2.rect,image)) {
                    subDivide(node2,image);
                }
                else {
                    node2 = null;
                }

                if (isMixed(node3.rect,image)) {
                    subDivide(node3,image);
                }
                else {
                    node3 = null;
                }

                if (isMixed(node4.rect,image)) {
                    subDivide(node4,image);
                }
                else {
                    node4 = null;
                }

            }
        }
    }

    public void draw(){
        group.getChildren().add(canvas);
    }
}
