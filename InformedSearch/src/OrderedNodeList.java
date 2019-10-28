import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OrderedNodeList {

    private List<Node> nodeList;
    public OrderedNodeList () {
        nodeList = new LinkedList<>();
    }

    public int size() {
        return nodeList.size();
    }

    public boolean add(Node node){
        boolean ret = nodeList.add(node);
        Collections.sort(nodeList, (x,y) -> Float.compare(x.getValue(), y.getValue()));
        return ret;
    }

    public boolean remove(Node node){
        boolean ret = nodeList.remove(node);
        Collections.sort(nodeList, (x,y) -> Float.compare(x.getValue(), y.getValue()));
        return ret;
    }

    public Node getRemove(int i){
        Node val = nodeList.get(i);
        nodeList.remove(val);
        Collections.sort(nodeList, (x,y) -> Float.compare(x.getValue(), y.getValue()));
        return val;
    }

    public boolean isEmpty() {
        return nodeList.size()==0;
    }

    public boolean contains(Node node){
        for (Node n : nodeList) {
            if (n.getX()==node.getX() && node.getY()==n.getY()) return true;
        }
        return false;
    }

    public Iterator<Node> iterator(){
        return nodeList.iterator();
    }

}
