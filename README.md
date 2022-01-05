# Huffman-Encoding

A program that uses Huffman encoding to compress a text file and write the output into bits. The program can also decompress the file with bits back into text.

First, I first map each character to its frequency in the file. Then, I create a binary tree where the highest-frequency characters are near the top of the tree, and the lowest frequency characters are deepest in the tree.

To find the bit sequence of the text file, I first traverse the tree to build a hash map, mapping each letter to its code. Traversing down a left child corresponds to a 0 while traversing down a right child corresponds to a 1. Then, we loop through each character and use this hashmap to convert the text into its bit sequence, which is written in the compressed file.

To decompress a file, we traverse the tree based on the bit sequence to retrieve each letter.

### Usage
