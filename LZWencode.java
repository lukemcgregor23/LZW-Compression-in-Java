import java.io.*;
import java.lang.*;
import java.util.*;


public class LZWencode {


    public static void main(String[] args) {
        try{
            LZWencode encoder = new LZWencode();
            LZWdecode decoder = new LZWdecode();
            LZWpack packer = new LZWpack();

            ArrayList encoded = encoder.encode(args[0]);



            String decoded = decoder.decode(encoded);
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
            writer.write(decoded);
            writer.close();
        }catch(Exception e){System.out.println(e);}
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

