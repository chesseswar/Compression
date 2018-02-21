import java.io.*;
import java.util.*;

public class Main {
    static HashMap<Character, String> codes = new HashMap<>();
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        //get list of text files to read from and compress
        ArrayList<String> files = new ArrayList<>();
        while (in.hasNext()){
            files.add(in.nextLine());
        }


        for (String s : files) {
            in = new Scanner(new File(s));

            //array with frequencies of 256 different characters
            int[] freqs = new int[256];

            //concatenate file into huge string to account for spaces
            String bigString = "";
            while(in.hasNext()){
                bigString += in.nextLine();
            }

            //each letter takes up 8 bits
            int startSize = bigString.length() * 8;

            //fill freq array with various character frequencies
            char[] input = bigString.toCharArray();
            for (char c : input){
                freqs[(int)c]++;
            }

            //fill initial priority queue with leaves of every character that appears at least once
            PriorityQueue<Node> frequencies = new PriorityQueue<>();
            for (int i = 0; i < freqs.length; i++){
                if (freqs[i] != 0){
                    Node temp = new Node(freqs[i], (char)i);
                    frequencies.add(temp);
                }
            }

            //build huffman tree until there is only one tree remaining
            while (frequencies.size() > 1){
                frequencies.add(new Node(frequencies.poll(), frequencies.poll()));
            }

            int endSize = 0;

            //fill "codes" hashmap with codes for every character that appears at least once
            getCodes(frequencies.peek(), "");

            //output
            //System.out.println(codes);
            for (char c : codes.keySet()){
                //System.out.println(c + "  " + codes.get(c) + "  " + freqs[(int)c] + " occurrences");
                endSize += codes.get(c).length();
            }
            System.out.println(s);
            System.out.println("start size (bits): " + startSize + "\nend size (bits): " + endSize);
            System.out.println();

            PrintWriter writer = new PrintWriter(new FileWriter(s.substring(0, s.length()-4) + "COMPRESSED.txt"));
            for (char c : input){
                writer.write(codes.get(c));
            }
            writer.close();
        }
    }

    public static void getCodes(Node root, String code){
        if (root.left == null && root.right == null){
            codes.put(root.value, code);
            return;
        }
        getCodes(root.left, code + "0");
        getCodes(root.right, code + "1");
    }
}
