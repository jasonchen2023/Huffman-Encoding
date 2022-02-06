# Huffman-Encoding

A program that uses **Huffman Encoding** to compress a text file and write the output into bits. The program can also decompress the file with bits back into text.

To minimize the size of the file, characters with higher frequency will have shorter binary codes. Characters with lower frequency will have longer codes.

To avoid ambiguity in the decoding process, Huffman Encoding uses the concept of prefix code – the code of any character will not be a prefix of any other code.

Huffman Encoding has a compression ratio of about 55%. Its time complexity is O(nlogn), where n is the number of unique characters.

## Implementation
First, I build a hash map mapping each character to its frequency in the file. 

Then, I create a binary tree where the highest-frequency characters are near the top of the tree, and the lowest frequency characters are deepest in the tree. This minimizes the size of our compressed file.

To retrieve the code of each letter, I traverse down the tree to the leaves. Traversing down a left child corresponds to a 0 while traversing down a right child corresponds to a 1. The code of each letter is stored in a hashmap. 

Finally, I loop through each character and use the hashmap to record the bit sequence of the text.

To decompress a file, we read each bit, traverse down the tree, and retrieve each letter.
