public class Letter implements Comparable<Letter>{
    char character;
    int frequency;
    boolean hasLetter;

    public Letter(int freq){
        frequency = freq;
        hasLetter = false;
    }

    public Letter(char c, int freq){
        character = c;
        frequency = freq;
        hasLetter = true;
    }

    public int compareTo(Letter other){
        return Integer.compare(frequency, other.frequency);
    }

    public String toString(){
        return (hasLetter ? Character.toString(character) : "no_letter") + "=" + frequency;
    }
}
