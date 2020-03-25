/*AVL trees are an effort to make more balanced binary trees: the max difference between the height (max length from that
node to a leaf) between the left and right children is +- 1. height(node) = height(left)
*/

class AVLNode
{
    int key, height;
    AVLNode left,right,parent;
}

public class AVLtrees
{
    private static AVLNode root;

    public static void main(String[] args)
    {
        AVLtrees tree = new AVLtrees();
        root.parent=null;
    }

    //returns the height (max len of the path from that node to a leaf) of the node
    int height(AVLNode n)
    {
        if (n==null) return 0;
        return n.height;
    }

    //rotates a node and its position in the tree to the left
    private void CLRSleftRotate(AVLNode x)
    {
        /*for example, use this graphic:
              y                *x*
         *x*     r  -->     a       y
        a   b                     b   r
        */

        AVLNode y = x.right; //y is x's right subtree (in pic above, its b)
        //assign y's left subtree to x.right: assign the left subtree of x.right to x.right
        x.right = y.left;

        if (y.left != null) //if b.left is not null aka it has children
            y.left.parent = x; //set y.left.parent (y itself) to x

        y.parent = x.parent; //link x's parent to y.parent aka x.right now has x.parent as its parent, instead of x
        if (x.parent == null) //if the parent is null, y is now null (y.p = null too so it works)
            root=y;
        else if (x == x.parent.left) //if x is a left child
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left=x;
        x.parent=y;
    }
}
