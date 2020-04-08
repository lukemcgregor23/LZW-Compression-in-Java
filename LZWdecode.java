// Name: Luke McGregor
// ID: 1359403

// Name: Kiera Leahy
// ID: 1159999

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.util.*;

public class LZWdecode {

    public static void main(String[] args) {
        try {
            LZWdecode decoder = new LZWdecode();
            ArrayList encoded = new ArrayList();
            Scanner in = new Scanner(System.in);
            while (in.hasNextInt()) {
                encoded.add(in.nextInt());
            }
            System.out.print(decoder.decode(encoded));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String decode(ArrayList<Integer> compressed) {
        Dictionary dict = new Dictionary();
        String c = "" + (char) (int) compressed.remove(0);
        StringBuffer result = new StringBuffer(c);
        for (int x : compressed) {

            String entry;
            if (x < dict.size())
                entry = dict.get(x);
            else if (x == dict.size())
                entry = c + c.charAt(0);
            else
                break;
            result.append(entry);

            dict.add(c + entry.charAt(0));
            c = entry;
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
        public String get(int p) {return list.get(p);}
        public void add(String p){
            this.list.add(p);
        }
        public int size(){
            return this.list.size();
        }
    }
//    public class Dictionary {
//        int size = 255;
//        TrieMap trieMap = new TrieMap();
//        public Dictionary() {
//            for (int i = 0; i <= 255; i++) {
//                trieMap.put(""+i, ""+(char)i);
//            }
//        }
//        public String get(int p) {
//            Object got = trieMap.get(""+p);
//            return got.toString();
//        }
//        public void add(String p){
//            size++;
//            trieMap.put(""+size, p);
//        }
//        public int size(){
//            return size;
//        }
//    }
}

