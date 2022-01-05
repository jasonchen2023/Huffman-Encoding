/**
 * PSET 3
 * May 6th, 2020
 *
 * @author Jason Chen, Zi Lan Zhang
 **/


import java.io.*;
import java.util.*;

public class HuffmanEncoding {

    // creates a table with the frequency of each character in the file
    public static Map createFrequencyTable(File file) throws IOException {
        Map<Character, Integer> frequencyMap = new TreeMap<>();
        BufferedReader input = new BufferedReader(new FileReader(file));
        try {
            int n = input.read();
            Character c;
            // while the file is not empty, read each character
            while (n != -1) {

                // casts the character into a Character object
                c = (char) n;
                // if key is present in map, increment its value, else, add key into map with frequency 1
                if (frequencyMap.containsKey(c))
                    frequencyMap.put(c, frequencyMap.get(c) + 1);
                else
                    frequencyMap.put(c, 1);
                n = input.read();

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            input.close();
        }
        return frequencyMap;
    }

    // creates a priority queue storing Binary Trees with the character frequencies in ascending order
    public static PriorityQueue createQueue(Map map) {

        int i = map.size();
        PriorityQueue<BinaryTree<Elem>> queue;
        // if the map size is zero, create a queue with size 0
        if (i == 0){
            queue = new PriorityQueue<>();
        }else {
            // create priority queue with same size as the map
            queue = new PriorityQueue<>(map.size(), new TreeComparator());
            // iterate through key and value of map
            Iterator<Map.Entry<Character, Integer>> itr = map.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Character, Integer> entry = itr.next();
                Elem e = new Elem(entry.getKey(), entry.getValue()); // create element storing key and value
                BinaryTree<Elem> tree = new BinaryTree<Elem>(e); // create a new binary tree whose data is the element
                queue.add(tree);
            }

        }
        return queue;

    }

    // returns a map with the codes of each character
    public static Map codeRetrieval(BinaryTree<Elem> root) {
        Map<Character, String> map = new TreeMap<>();
        // return empty map if the character and frequency are zero (empty file)
        if (root.getData().getChar() == null && root.getData().getF() == null);
        else {
            // if there is only one character in the file
            if (root.isLeaf()) {
                map.put(root.getData().getChar(), "0");
            } else {
                traverseTree(map, root, "");
            }
        }
        return map;
    }

    // retrieves all the codes in one traversal
    public static void traverseTree(Map map, BinaryTree<Elem> node, String str) {
        // if the node is a leaf, add its code into the map
        if (node != null) {
            if (node.isLeaf()) {
                map.put(node.getData().getChar(), str);
            }
            // if the node has a left child, add a "0" to the string and call recursively on its left child
            if (node.hasLeft()) {
                traverseTree(map, node.getLeft(), str + "0");
            }
            // if a node has a right child, add a "1" to the string and call recursively on its right child
            if (node.hasRight()) {
                traverseTree(map, node.getRight(), str + "1");
            }
        }
    }

    // creates the root of the Binary Tree with all of the characters
    public static BinaryTree<Elem> createTree(PriorityQueue<BinaryTree<Elem>> q) {
        // more than one type of character
        while (q.size() > 1) {
            BinaryTree<Elem> e1 = q.poll();
            BinaryTree<Elem> e2 = q.poll();
            // create a new Binary tree connecting the two Binary Trees with smallest frequencies in the queue
            BinaryTree<Elem> newTree = new BinaryTree<>(new Elem(null, e1.getData().getF() + e2.getData().getF()), e1, e2);
            q.add(newTree);
        }
        // returns the root of the Binary tree
        if (q.size() > 0) {
            return q.poll();
        }else{
            // return new binary tree with Elem whose character and frequency are null (for empty file)
            return new BinaryTree<>(new Elem(null, null));
        }
    }

    // compresses the file
    public static void compressFile(Map<Character, String> codeMap, String fileName) throws IOException {

        BufferedReader input = new BufferedReader(new FileReader(fileName + ".txt"));
        BufferedBitWriter writer = new BufferedBitWriter(fileName + "_compressed.txt");
        try {
            int n = input.read();
            Character c;
            // while the file is not empty, read each character
            while (n != -1) {
                c = (char) n;
                // retrieve the Huffman code of the character
                String s = codeMap.get(c);
                // write out the bit for each character in the code
                for (int i = 0; i < s.length(); i++) {
                    Boolean b;
                    if (s.charAt(i) == '0') {
                        b = false;
                    } else {
                        b = true;
                    }
                    writer.writeBit(b);
                }
                // read the next character
                n = input.read();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            input.close();
            writer.close();
        }
    }

    // decompresses the compressed file
    public static void decompressFile(BinaryTree root, String filename) throws IOException{
        BufferedBitReader reader = new BufferedBitReader(filename + "_compressed.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(filename + "_decompressed.txt"));

        try {
            // pointer points at root initially
            BinaryTree<Elem> pointer = root;
            while (reader.hasNext()) {
                boolean bit = reader.readBit();
                // if the pointer is not a leaf, traverse down the appropriate edge of the tree
                if (!pointer.isLeaf()) {
                    if (!bit) {
                        pointer = pointer.getLeft();
                    } else {
                        pointer = pointer.getRight();
                    }
                }
                // if the pointer is a leaf, write the character of that node
                if (pointer.isLeaf()){
                    output.write(pointer.getData().getChar());
                    // resets the pointer as the root
                    pointer = root;
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            output.close();
        }
    }

    public static void main(String[] args) throws IOException {

        // boolean to print map and binary trees in console
        Boolean debugging = false;

        // create an arraylist storing the file names
        ArrayList<String> fileList = new ArrayList<>();
        for (int i = 1; i < 4; i++){
            fileList.add("inputs/Test" + i);
        }
        fileList.add("inputs/USConstitution");
        fileList.add("inputs/WarAndPeace");

        // process each file in the list
        for (String filename: fileList){
            // creates a map containing characters as keys and frequency as values
            Map<Character, Integer> frequencyTable = createFrequencyTable(new File(filename + ".txt"));

            // creates a priority queue storing Binary Trees in ascending order based on frequency
            PriorityQueue<BinaryTree<Elem>> q = createQueue(frequencyTable);

            // returns the root of the completed Binary Tree
            BinaryTree<Elem> root = createTree(q);

            if (debugging) {
                System.out.println(root.toString());
            }

            // creates a map with characters as keys and Huffman codes as values
            Map<Character, String> codeMap = codeRetrieval(root);

            if (debugging) {
                System.out.println(codeMap);
            }

            // compresses file
            compressFile(codeMap, filename);

            // decompresses file
            decompressFile(root, filename);
        }
    }
}


