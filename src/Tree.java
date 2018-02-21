import java.util.HashMap;

public class Tree implements Comparable<Tree>{
    int frequency;
    public Tree (int f){
        frequency = f;
    }

    public int compareTo(Tree other){
        return frequency - other.frequency;
    }

    public String toString(){
        return Integer.toString(frequency);
    }
}

class Leaf extends Tree{
    char letter;
    public Leaf(int f, char c){
        super(f);
        letter = c;
    }
}

class Link extends Tree {
    Tree left, right;
    public Link(Tree l, Tree r){
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
