public class BSTstuff {
    public static void main(String[] args)
    {
        Node root = createNewNode(0);
        insert(root,1);
        insert(root,5);
        insert(root,4);
        insert(root,3);
        insert(root,2);
        inorderWalk(root);
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

    //walks through the tree 'in order'
    public static void inorderWalk(Node root)
    {
        if (root != null)
        {
            inorderWalk(root.left);
            System.out.println(root.key);
            inorderWalk(root.right);
        }
    }
}

class Node
{
    int key;
    Node left,right,parent;
}