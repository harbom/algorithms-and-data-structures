public class BSTstuff {
    public static void main(String[] args)
    {
        Node root = createNewNode(12);
        int[] vals = {5,18,2,9,15,10,17};
        for (int i:vals)
            insert(root,i);

        //inorderWalk(root);
        //preOrderWalk(root);
        //postOrderWalk(root);
        //treeSearch(root,2);
        //System.out.println(treeSucessor(root).key);
    }

    //creates/returns a node with default left/right/parent pointers
    public static Node createNewNode(int val)
    {
        Node n = new Node();
        n.key = val;
        n.parent = null;
        n.left = null;
        n.right = null;
        return n;
    }

    //inserts a node with the given value to the tree
    public static void insert(Node root, int val)
    {
        Node newNode = createNewNode(val);
        Node tempParent=null;
        Node x=root;
        while (x != null) //while the left or right subtree is not null
        {
            tempParent=x; //set the parent node to that x
            if (val<x.key) //set x to left or right subtree, depending on which side key needs to go
                x=x.left;
            else
                x=x.right;
        }

        newNode.parent=tempParent;
        if (tempParent == null) //the tree was empty
            root=newNode;
        else if (val<tempParent.key) //val needs to go on left
            tempParent.left=newNode;
        else //val needs to go on right
            tempParent.right=newNode;
    }

    //walks through the tree 'in order' : 'bottom' edge while arrow traces from root counterclockwise, back to root
    public static void inorderWalk(Node root)
    {
        if (root != null)
        {
            inorderWalk(root.left);
            printForNode(root);
            inorderWalk(root.right);
        }
    }

    // 'left' edge while arrow traces root counterclockwise, back to root
    public static void preOrderWalk(Node root)
    {
        if (root != null)
        {
            printForNode(root);
            preOrderWalk(root.left);
            preOrderWalk(root.right);
        }
    }

    // 'left' edge while arrow traces root counterclockwise, back to root
    public static void postOrderWalk(Node root)
    {
        if (root != null)
        {
            postOrderWalk(root.left);
            postOrderWalk(root.right);
            postOrderWalk(root);
        }
    }

    //searches a tree for a given value
    public static Node treeSearch(Node root,int x)
    {
        if (root == null || root.key == x) //if values match up, return it. otherwise, if its null, the value isn't in the tree (prem convo remember)
            return root;

        if (root.key <= x) //if you need to move left, move to the left subtree
            return treeSearch(root.left, x);

        return treeSearch(root.right, x); //else, move to the right subtree
    }

    //iterative way of previous method
    public static Node iterativeTreeSearch(Node root, int x)
    {
        while (root != null || root.key==x)
            root = (root.key<x) ? root.left:root.right;

        return root;
    }

    //returns max value of tree: keep going right from the root
    public static Node treeMaximum(Node root)
    {
        Node temp = root;
        while (temp.right != null)
            temp = temp.right;
        return temp;
    }

    //returns min value of tree: keep going left from the root
    public static Node treeMinimum(Node root)
    {
        Node temp = root;
        while (temp.left != null)
            temp = temp.left;
        return temp;
    }
    //returns the Node with the smallest value k such that k >= root.key
    public static Node treeSucessor(Node root)
    {
        //2 cases: right subtree is not null, and right subtree is null (its a leaf, bit more complicated)

        //case 1: right subtree is null, just return min of that subtree
        if (root.right != null)
            return treeMinimum(root.right);

        //else, case 2: its a leaf. walk up the tree, setting x to its prev parent and parentX to its parent,    /^\
        //until either parentX is null or x is to the left of parentX. due to the nature of a BST, once x       o
        //is to the left, the parent will be the next largest                                                 /  \
        Node parentX = root.parent;
        Node tempX = root;
        while (parentX != null && parentX.right.key==tempX.key) //while the parent node isn't null and the
        {
            tempX=parentX;
            parentX=parentX.parent;
        }

        return parentX;
    }

    //just prints out current node, parent
    private static void printForNode(Node root)
    {
        if (root != null)
        {
            System.out.print("currNode: " + root.key);
            System.out.print("   currParent: " + root.parent.key);
            System.out.print("   currLeft: " + root.left.key);
            System.out.print("   currRight: " + root.right.key);
        }
        System.out.println();
    }
}

class Node
{
    int key;
    Node left,right,parent;
}