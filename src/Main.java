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
        PrintWriter writerStat = new PrintWriter(new FileWriter("Compression Stats.txt"));
        for (String s : files) {
            BitInputStream bitInputStream = new BitInputStream(s);
            in = new Scanner(new File(s));

            //array with frequencies of 256 different characters
            int[] freqs = new int[256];

            //read the file, get frequencies of each set of 8 bits (each character)
            int read = 0;
            int length = 0;
            while (true){
                try {
                    read = bitInputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (read == -1) {
                    break;
                }

                freqs[read]++;
                length++;
            }

            int startSize = length * 8;

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

            //output compressed file
            BitOutputStream bitOutputStream = new BitOutputStream(s.substring(0, s.length()-4) + "COMPRESSED.xd");
            bitInputStream.reset();
            read = 0;
            while (true){
                try {
                    read = bitInputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (read == -1) {
                    break;
                }
                String code = codes.get((char)read);
                endSize += code.length();
                bitOutputStream.writeBits(code.length(), Integer.parseInt(code, 2));
            }

            bitOutputStream.close();


            //output stats (sizes of before/after files)
            System.out.println(s);
            System.out.println("start size (bits): " + startSize + "\nend size (bits): " + endSize);
            System.out.println(1 - ((double) endSize / startSize) * 100 + "% compressed");
            System.out.println();


            writerStat.write(s);
            writerStat.write("start size (bits): " + startSize + "\nend size (bits): " + endSize);
            writerStat.write((1 - ((double) endSize / startSize) * 100 + "% compressed"));
            writerStat.write("\n");


            //output decompressed file
            bitInputStream = new BitInputStream(s.substring(0, s.length()-4) + "COMPRESSED.xd");
            PrintWriter writer = new PrintWriter(new FileWriter(s.substring(0, s.length()-4) + "DECOMPRESSED.txt"));
            Node current = frequencies.peek();
            for (int i = 0; i < endSize; i++){
                current = (bitInputStream.readBits(1) == 0 ? current.left : current.right);
                if (current.left == null && current.right == null) {
                    writer.write(current.value);
                    current = frequencies.peek();
                }
            }

            writer.close();
        }
        writerStat.close();
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
