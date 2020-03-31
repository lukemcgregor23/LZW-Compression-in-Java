import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;


public class LZWencode {


    public static void main(String[] args) {
        LZWencode encoder = new LZWencode();
        ArrayList encoded = encoder.encode(args[0]);
//        LZWdecode decoder = new LZWdecode();
//        ArrayList decoded = decoder.decode(encoded);
        System.out.println(encoded);
//        System.out.println(decoded);
    }

    private ArrayList encode(String input) {
        Dictionary dict = new Dictionary();

        byte[] data = read(input);
        ArrayList<String> chararray = new ArrayList<String>();

        for(byte d : data){
            chararray.add(stringOf(d));}

        String a = "";
        ArrayList result = new ArrayList();
        for (String b : chararray) {
            String ab = a + b;
            if (dict.contains(ab))
                a = ab;
            else {
                result.add(dict.get(a));
                dict.add(ab);
                a = "" + b;
            }
        }

        if (!a.equals(""))
            result.add(dict.get(a));
        return result;

       // dict.print();


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
        private ArrayList list = new ArrayList();
        public Dictionary() {
            for (int i = 0; i < 256; i++) {
                this.add(""+(char) i);
            }
        }
        public int get(String p) {
            return list.indexOf(p);
        }
        public void add(String p){
            list.add(p);
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

