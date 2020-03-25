/*AVL trees are an effort to make more balanced binary trees: the max difference between the height (max length from that
node to a leaf) between the left and right children is +- 1. height(node) = height(left)
*/

class AVLNode
{
    int key, height;
    AVLNode left,right,parent;

    AVLNode(int key)
    {
        this.key = key;
        height=1;
    }
}

public class AVLtrees
{
    private static AVLNode root;

    public static void main(String[] args)
    {
        AVLtrees tree = new AVLtrees();

        //to test the left and right rotate functions
        testRotate();
    }

    //returns the height (max len of the path from that node to a leaf) of the node
    int height(AVLNode n)
    {
        if (n==null) return 0;
        return n.height;
    }

    //rotates a node and its position in the tree to the left
    private static void CLRSleftRotate(AVLNode x)
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

    //basically, same code as above function but switched the .left's and .right's
    private static void CLRSRightRotate(AVLNode x)
    {

        /*for example, use this graphic:
              y                *x*
         *x*     r  <--     a       y
        a   b                     b   r
        */

        AVLNode y = x.left; //y is x's left subtree (in pic above, its b)
        //assign y's right subtree to x.left
        x.left = y.right;

        if (y.right != null) //if b.left is not null aka it has children
            y.right.parent = x; //set y.left.parent (y itself) to x

        y.parent = x.parent; //link x's parent to y.parent aka x.right now has x.parent as its parent, instead of x
        if (x.parent == null) //if the parent is null, y is now null (y.p = null too so it works)
            root=y;
        else if (x == x.parent.left) //if x is a left child
            x.parent.left = y;
        else
            x.parent.right = y;

        y.right=x;
        x.parent=y;
    }

    private static void testRotate()
    {
        //test for the left rotate feature
        /*
                 4                                         2
               2   5  --> leftRotate(2) should go to    1     4
             1   3                                          3   5
         */

        AVLNode one = new AVLNode(1);
        AVLNode two = new AVLNode(2);
        AVLNode three = new AVLNode(3);
        AVLNode four = new AVLNode(4);
        AVLNode five = new AVLNode(5);

        root = four;
        root.left = two;
        root.right = five;
        two.parent = root;
        five.parent = root;
        one.parent=two;
        three.parent=two;
        two.left=one;
        two.right=three;

        System.out.println("before: ");
        inorderTransversal(root);
        //CLRSleftRotate(two);
        CLRSRightRotate(two);
        System.out.println("after: ");
        inorderTransversal(root);
        //it worked for this example, the inorder property was preserved
    }

    //should always start out at root
    private static void inorderTransversal(AVLNode r)
    {
        if (r != null)
        {
            inorderTransversal(r.left);
            System.out.println(r.key);
            inorderTransversal(r.right);
        }
    }
}
