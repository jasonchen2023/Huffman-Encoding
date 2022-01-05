/**
 * PSET 3
 * May 6th, 2020
 *
 * @author Jason Chen, Zi Lan Zhang
 **/

import java.util.Comparator;

// compares the frequencies of the tree
public class TreeComparator implements Comparator<BinaryTree<Elem>> {

    // ascending order of frequency
    @Override
    public int compare(BinaryTree<Elem> b1, BinaryTree<Elem> b2) {
        Elem e1 = b1.getData();
        Elem e2 = b2.getData();
        if (e1.getF() < e2.getF())
            return -1;
        else if (e1.getF() > e2.getF())
            return 1;
        else
            return 0;
    }
}