public class Result {

    private Node node;
    private float result;

    public Result(Node node, float result) {
        this.node = node;
        this.result = result;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }


    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}