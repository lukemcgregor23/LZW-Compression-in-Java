import java.io.*;
import java.lang.*;
import java.util.*;


public class LZWencode {


    public static void main(String[] args) {
        try{
            LZWencode encoder = new LZWencode();
            ArrayList encoded = encoder.encode();
            for (int counter = 0; counter < encoded.size(); counter++) {
                System.out.println(encoded.get(counter));
            }
        }catch(Exception e){System.out.println(e);}
    }

    private ArrayList encode() {
        Dictionary dict = new Dictionary();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> chararray = new ArrayList<String>();
        try {
            String input = br.readLine();
            while (input != null) {
                for (int i = 0; i <= input.length(); i++) {
                    chararray.add(stringOf(i));
                }
                input = br.readLine();
            }
        }catch(Exception e){System.out.println(e);}

        String a = "";
        ArrayList result = new ArrayList();
        for (String b : chararray) {
            String ab = a + b;
            if (dict.contains(ab))
                a = ab;
            else {
                result.add(dict.index(a));
                dict.add(ab);
                a = "" + b;
            }
        }

        if (!a.equals(""))
            result.add(dict.index(a));
        //dict.print();
        return result;



    }


    private String stringOf(byte data){
        return String.valueOf((char) data);
    }
    private String stringOf(int data){
        return String.valueOf(data);
    }
    private byte[] read(String input){
        try {
            File file = new File(input);
            byte[] bytesArray = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray);
            fis.close();
            return bytesArray;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }



    public class Dictionary {
        int size = 255;
        TrieMap trieMap = new TrieMap();
        public Dictionary() {
            for (int i = 0; i <= 255; i++) {
                trieMap.put(""+(char)i, i);
            }
        }
        public int index(String p) {
            return Integer.parseInt(trieMap.get(p).toString());
        }
        public void add(String p){
            size++;
            trieMap.put(p, size);
        }
        public int size(){
            return size;
        }
        public boolean contains(String p){
            if(trieMap.get(p) != null) { return true; } else { return false; }
        }
    }
}

