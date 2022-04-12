public class Node {
    Rectangle rect;
    Node parent;
    Node ch1;
    Node ch2;
    Node ch3;
    Node ch4;



    public Node(Node parent, Rectangle rect) {
        this.parent = parent;
        this.rect = rect;
        this.ch1 = null;
        this.ch2 = null;
        this.ch3 = null;
        this.ch4 = null;
    }



}
