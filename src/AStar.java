
public class AStar {

    Node start;
    Node end;
    Node[][] gridArea;

    class Node{
        int x;
        int y;
        double distance;
        Node parent=null;
        boolean visited;
        boolean blocked;

        public Node(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
}
