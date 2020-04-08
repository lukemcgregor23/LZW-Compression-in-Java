// Name: Luke McGregor
// ID: 1359403

// Name: Kiera Leahy
// ID: 1159999

import java.io.*;
import java.lang.*;
import java.util.*;


public class LZWencode {


    public static void main(String[] args) {
        LZWencode encoder = new LZWencode();
        ArrayList encoded = encoder.encode();
        //print phrase numbers
        for (int counter = 0; counter < encoded.size(); counter++) {
            System.out.println(encoded.get(counter));
        }
    }

    private ArrayList encode() {
        //create the dictionary
        Dictionary dict = new Dictionary();
        ArrayList<String> chararray = new ArrayList<String>();
        //get input from stdin
        try {
            int input = System.in.read();
            while (input != -1) {
                chararray.add(stringOf((byte)input));
                input = System.in.read();
            }
        }catch(Exception e){System.out.println(e);}

        // for each character run the lzw algorithim:
        // find longest matching string in dict and then add its idex number to the result.
        // then add the next char to it and add itinto the dictionary as a new entry
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

    //helper code to convery bytes to chars
    private String stringOf(byte data){
        return String.valueOf((char) data);
    }


    public class Dictionary {
        //initilize the dictionary with the full 255 bytes posible with 8 bits
        int size = 255;
        TrieMap trieMap = new TrieMap();
        public Dictionary() {
            for (int i = 0; i <= 255; i++) {
                trieMap.put(""+(char)i, i);
            }
        }

        // map trie functions sowe can remember what they do eiser
        public int index(String p) {
            return Integer.parseInt(trieMap.get(p).toString());
        }
        public void add(String p){
            size++;
            trieMap.put(p, size);
        }
        public boolean contains(String p){
            if(trieMap.get(p) != null) {
                return true;
            } else {
                return false;
            }
        }
        //if we ever need the size of the trie it is recorded in the size var
        public int size(){
            return size;
        }
    }
}
class TrieMap {
    private Object[] mChars = new Object[256];
    private Object mPrefixVal; // Used only for values of prefix keys.

    // Simple container for a string-value pair.
    private static class Leaf {
        public String mStr;
        public Object mVal;
        public Leaf(String str, Object val) {
            mStr = str;
            mVal = val;
        }
    }

    public TrieMap() {
    }

    public boolean isEmpty() {
        if(mPrefixVal != null) {
            return false;
        }
        for(Object o : mChars) {
            if(o != null) {
                return false;
            }
        }
        return true;
    }


    // Inserts a key/value pair.
    public void put(String key, Object val) {
        assert key != null;
        assert !(val instanceof TrieMap); // Only we get to store TrieMap nodes. TODO: Allow it.
        if(key.length() == 0) {
            // All of the original key's chars have been nibbled away
            // which means this node will store this key as a prefix of other keys.
            mPrefixVal = val; // Note: possibly removes or updates an item.
            return;
        }
        char c = key.charAt(0);
        Object cObj = mChars[c];
        if(cObj == null) { // Unused slot means no collision so just store and return;
            if(val == null) {
                return; // Don't create a leaf to store a null value.
            }
            mChars[c] = new Leaf(key, val);
            return;
        }
        if(cObj instanceof TrieMap) {
            // Collided with an existing sub-branch so nibble a char and recurse.
            TrieMap childTrie = (TrieMap)cObj;
            childTrie.put(key.substring(1), val);
            if(val == null && childTrie.isEmpty()) {
                mChars[c] = null; // put() must have erased final entry so prune branch.
            }
            return;
        }
        // Collided with a leaf
        if(val == null) {
            mChars[c] = null; // Null value means to remove any previously stored value.
            return;
        }
        assert cObj instanceof Leaf;
        // Sprout a new branch to hold the colliding items.
        Leaf cLeaf = (Leaf)cObj;
        TrieMap branch = new TrieMap();
        branch.put(key.substring(1), val); // Store new value in new subtree.
        branch.put(cLeaf.mStr.substring(1), cLeaf.mVal); // Plus the one we collided with.
        mChars[c] = branch;
    }


    // Retrieve a value for a given key or null if not found.
    public Object get(String key) {
        assert key != null;
        if(key.length() == 0) {
            // All of the original key's chars have been nibbled away
            // which means this key is a prefix of another.
            return mPrefixVal;
        }
        char c = key.charAt(0);
        Object cVal = mChars[c];
        if(cVal == null) {
            return null; // Not found.
        }
        assert cVal instanceof Leaf || cVal instanceof TrieMap;
        if(cVal instanceof TrieMap) { // Hash collision. Nibble first char, and recurse.
            return ((TrieMap)cVal).get(key.substring(1));
        }
        if(cVal instanceof Leaf) {
            // cVal contains a user datum, but does the key match its substring?
            Leaf cPair = (Leaf)cVal;
            if(key.equals(cPair.mStr)) {
                return cPair.mVal; // Return user's data value.
            }
        }
        return null; // Not found.
    }
}
