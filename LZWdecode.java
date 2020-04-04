import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.util.*;

public class LZWdecode {

    public String decode(ArrayList<Integer> compressed) {
        System.out.print("("); for (int k : compressed) { System.out.print("["+k+"], ");}System.out.println(")");
        Dictionary dictionary = new Dictionary();
        String w = "" + (char)(int)compressed.remove(0);
        System.out.print(w);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {

            String entry;
            if (k < dictionary.size())
                entry = dictionary.get(k);
            else if (k == dictionary.size())
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);

            result.append(entry);
            System.out.print(entry);

            // Add w+entry[0] to the dictionary.
            dictionary.add(w + entry.charAt(0));
            w = entry;
        }
        return result.toString();


    }
    public class Dictionary {
        private ArrayList<String> list = new ArrayList<String>();
        public Dictionary() {
            for (int i = 0; i < 256; i++) {
                this.add(""+(char) i);
            }
        }
        public int index(String p) {
            return this.list.indexOf(p);
        }
        public String get(int p) {return list.get(p);}
        public void add(String p){
            this.list.add(p);
        }
        public int size(){
            return this.list.size();
        }
        public boolean contains(String p){
            return this.list.contains(p);
        }
        public void print(){
            String result = "{";
            int i = 0;
            while(i < list.size()){
                result += "(" + i + ", {"+ this.list.get(i) +"})";

                if(i+1 < list.size()){
                    result += ", ";
                }
                i++;
            }
            System.out.println("dictionary: " + result + "}");
        }
    }
}