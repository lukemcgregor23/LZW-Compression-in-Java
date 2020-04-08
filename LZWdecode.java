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
            //create a decoder and add the input from stdin to the encoded arraylist
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
        //create a new dictionary (just a arraylist this time)
        Dictionary dict = new Dictionary();
        //add the first char to c
        String c = "" + (char) (int) compressed.remove(0);
        //create stringbuffer with c (the first char of the compressesd file)
        StringBuffer result = new StringBuffer(c);
        // for every phrase number in the compressed file
        // see if the phrase number is smaller than the dictionary
        // if it is get the data for that phrase number,
        // append it to the result and add a new dictionary entry of  c + c.charAt(0)
        // otherwise add c + c.charAt(0) to the result and add a new dict entry of c + c.charAt(0) + c + c.charAt(0) + c.charAt(0)
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
        // create new arraylist to store everything
        // initilize the dictionary with the full 255 bytes posible with 8 bits
        private ArrayList<String> list = new ArrayList<String>();
        public Dictionary() {
            for (int i = 0; i < 256; i++) {
                this.add(""+(char) i);
            }
        }
        //helper functions to interact with the arraylist eaiser
        public String get(int p) {return list.get(p);}
        public void add(String p){
            this.list.add(p);
        }
        public int size(){
            return this.list.size();
        }
    }
}

