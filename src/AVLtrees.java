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
        //testRotate();

        //testing insertion/maintenance of AVL properties
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);

        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        inorderTransversal(tree.root); //worked, in order transversal was preserved
    }

    //returns the height (max len of the path from that node to a leaf) of the node
    //O(1): simply returning an attribute of the AVLNode
    private static int height(AVLNode n)
    {
        if (n==null) return 0;
        return n.height;
    }

    //rotates a node and its position in the tree to the left
    //O(1): constant # of pointer changes
    private static AVLNode CLRSleftRotate(AVLNode x)
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

        return y;
    }

    //basically, same code as above function but switched the .left's and .right's
    //O(1): constant # of pointer changes
    private static AVLNode CLRSRightRotate(AVLNode x)
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

        return y;
    }

    //a method to test the CLRS rotation methods
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
        //CLRSRightRotate(two);
        System.out.println("after: ");
        inorderTransversal(root);
        //it worked for this example, the inorder property was preserved
    }

    //should always start out at root
    //O(n): goes through all nodes
    private static void inorderTransversal(AVLNode r)
    {
        if (r != null)
        {
            inorderTransversal(r.left);
            System.out.println(r.key);
            inorderTransversal(r.right);
        }
    }

    //getting the balance of any node: should always be -1, 0 or 1. if it's not,
    //AVL property is violated
    //O(height(n)): goes from that node to a leaf on the left and on the right
    private static int getBalance(AVLNode n)
    {
        if (n == null) //return 0's for leaves
            return 0;

        //else, height(node) = height(node.left) - height(node.right)
        return height(n.left) - height(n.right);
    }

    //inserts a node with the given key starting at the root
    //O(lgn): look at notes why
    private static AVLNode insert(AVLNode node, int key)
    {
        //if the root is null
        if (node == null)
            return (new AVLNode(key));

        //1) find right position for the node
        //normal insertion
        if (key < node.key)
            node.left = insert(node.left,key); //recurse to left
        else if (key > node.key)
            node.right = insert(node.right,key); //recurse to right

        //2) check whether root is balanced
        int balance = getBalance(node);

        //remember, for each node, the height.left and height.right must be +- 1 in height
        //if root is unbalanced, 4 cases

        //case 1: 'left left', simple rotate right once if the new node is on the root's left
        /*like this:
                /
             /
          /
          , just requires 1 rotate and that'll be good
        */
        if (balance > 1 && key < node.left.key)
            return CLRSRightRotate(node);

        //case 2: 'right right', simple left rotate once if the new node is on the root's right
        /*like this:
                \
                    \
                        \
            , just requires 1 rotate and balance is restored
         */
        if (balance < -1 && key > node.right.key)
            return CLRSleftRotate(node);

        //case 3: 'left right': need to rotate twice (see notes)
        /*like this:
                 /
               /
                   \
            , need to rotate twice
         */
        if (balance > 1 && key > node.left.key)
        {
            node.left = CLRSleftRotate(node.left);
            return CLRSRightRotate(node);
        }

        //case 4: 'right left': need to rotate twice
        /* like this:
               \
                    \
                /
            , need to rotate twice
         */
        if (balance < -1 && key < node.right.key)
        {
            node.right = CLRSRightRotate(node.right);
            return CLRSleftRotate(node);
        }

        //else, if it's balanced
        return node;
    }
}
