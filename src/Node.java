public class Node implements Comparable<Node>{
    Node left, right;
    char value;
    int freq;
    boolean isLink;

    public Node(Node l, Node r){
        left = l;
        right = r;
        freq = l.freq + r.freq;
        isLink = true;
    }

    public Node(int f, char v){
        freq = f;
        value = v;
        isLink = false;
        left = null;
        right = null;
    }

    public int compareTo(Node other){
        return freq - other.freq;
    }

    public String toString(){
        return (isLink ? "link" : Character.toString(value)) + "=" + freq;
    }
}
