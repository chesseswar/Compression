import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        int[] freqs = new int[256];
        String bigString = "";
        while(in.hasNext()){
            bigString += in.nextLine();
        }
        System.out.println(bigString);
        char[] input = bigString.toCharArray();
        for (char c : input){
            freqs[(int)c]++;
        }
        PriorityQueue<Tree> frequencies = new PriorityQueue<>();
        for (int i = 0; i < freqs.length; i++){
            Leaf temp = new Leaf(freqs[i], (char)i);
            frequencies.add(temp);
        }

        while (frequencies.size() > 1){
            frequencies.add(new Link(frequencies.poll(), frequencies.poll()));
        }
        System.out.println(frequencies);
    }
}
