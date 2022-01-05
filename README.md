# Huffman-Encoding

A program that uses **Huffman encoding** to compress a text file and write the output into bits. The program can also decompress the file with bits back into text.

## Implementation
First, I build a hash map mapping each character to its frequency in the file. Then, I create a binary tree where the highest-frequency characters are near the top of the tree, and the lowest frequency characters are deepest in the tree. This minimizes the size of our compressed file.

To retrieve the code of each letter, I traverse down the tree to the leaves. Traversing down a left child corresponds to a 0 while traversing down a right child corresponds to a 1. The code of each letter is stored in a hashmap. 

Finally, I loop through each character and use the hashmap to record the bit sequence of the text.

To decompress a file, we read each bit, traverse down the tree, and retrieve each letter.
