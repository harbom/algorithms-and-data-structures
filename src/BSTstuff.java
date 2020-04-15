public class BSTstuff
{
    public static void main(String[] args)
    {
        BSTLibrary bst = new BSTLibrary();
        bst.root = bst.createNewNode(12);
        Node root = bst.root;
        int[] vals = {5,18,2,9,15,10,17};
        for (int i:vals)
            bst.insert(root,i);

        //System.out.println("before: ");
        bst.inorderWalk(root);
        //bst.preOrderWalk(root);
        //bst.postOrderWalk(root);
        //bst.treeSearch(root,2);
        //System.out.println(treeSucessor(root).key);
        //System.out.println("after: ");
        //bst.deleteKey(17);
        //bst.deleteKey(2);
        //bst.deleteKey(5);
        //bst.inorderWalk(root);
    }
}

class BSTLibrary
{
    public Node root;

    //creates/returns a node with default left/right/parent pointers
    public Node createNewNode(int val)
    {
        Node n = new Node();
        n.key = val;
        n.parent = null;
        n.left = null;
        n.right = null;
        return n;
    }

    //inserts a node with the given value to the tree
    public void insert(Node root, int val)
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

    //calls recursive function to delete the node
    public void deleteKey(int key)
    {
        root = deleteRec(root,key);
    }

    //does the deleting
    public Node deleteRec(Node root, int key)
    {
        //Base case: tree empty, return the root
        if (root==null) return root;

        //otherwise, recur down the tree
        if (key<root.key)
            root.left=deleteRec(root.left,key);
        else if (key>root.key)
            root.right=deleteRec(root.right,key);
        else //if the key is the same, needs to be deleted
        {
            /*at this point, there are 3 possibilities: since its not a leaf (above checks),
            either it has 2 children or one child. if the left is null, the right won't be, vice versa,
            OR it has 2 children.

            In the first two cases (1 child), just copy child data over to the node
            and you're good. Else, find the inorder successor and copy over that data to its right (again... due
            to structure of binary tree)
             */
            //node with 0 or 1 children
            if (root.left==null)
                return root.right; //then make the node become the value of the right subtree (circumvents/deletes curr node)
            else if (root.right==null)
                return root.left; //same thing but w left subtree

            //else, has multiple children
            root.key=treeSucessor(root).key; //makes val of the curr root the inorder successor
            root.right=deleteRec(root.right,root.key); //recurses down the right subtree so that when it meets the
            //node with the inorder successor, it deletes that node. aka the inorder successor node 'moved up' to the curr node
        }

        return root;
    }

    //walks through the tree 'in order' : 'bottom' edge while arrow traces from root counterclockwise, back to root
    public void inorderWalk(Node root)
    {
        if (root != null)
        {
            inorderWalk(root.left);
            printForNode(root);
            inorderWalk(root.right);
        }
    }

    // 'left' edge while arrow traces root counterclockwise, back to root
    public void preOrderWalk(Node root)
    {
        if (root != null)
        {
            printForNode(root);
            preOrderWalk(root.left);
            preOrderWalk(root.right);
        }
    }

    // 'left' edge while arrow traces root counterclockwise, back to root
    public void postOrderWalk(Node root)
    {
        if (root != null)
        {
            postOrderWalk(root.left);
            postOrderWalk(root.right);
            postOrderWalk(root);
        }
    }

    //searches a tree for a given value
    public Node treeSearch(Node root,int x)
    {
        if (root == null || root.key == x) //if values match up, return it. otherwise, if its null, the value isn't in the tree (prem convo remember)
            return root;

        if (root.key <= x) //if you need to move left, move to the left subtree
            return treeSearch(root.left, x);

        return treeSearch(root.right, x); //else, move to the right subtree
    }

    //iterative way of previous method
    public Node iterativeTreeSearch(Node root, int x)
    {
        while (root != null || root.key==x)
            root = (root.key<x) ? root.left:root.right;

        return root;
    }

    //returns max value of tree: keep going right from the root
    public Node treeMaximum(Node root)
    {
        Node temp = root;
        while (temp.right != null)
            temp = temp.right;
        return temp;
    }

    //returns min value of tree: keep going left from the root
    public Node treeMinimum(Node root)
    {
        Node temp = root;
        while (temp.left != null)
            temp = temp.left;
        return temp;
    }
    //returns the Node with the smallest value k such that k >= root.key
    public Node treeSucessor(Node root)
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
    private void printForNode(Node root)
    {
        if (root != null)
        {
            System.out.print("currNode: " + root.key);
            if (root.parent != null)
                System.out.print("   currParent: " + root.parent.key);
            if (root.left != null)
                System.out.print("   currLeft: " + root.left.key);
            if (root.right != null)
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


/* CLRS delete node below, doesn't seem to be working. I probably messed up pointer stuff near the end
//deletes a node from the tree
    public static Node delete(Node node)
    {
        //3 cases: (1) the node is a leaf, (2) the node has only one branch and can be spliced out
        // or (3) the node has two branches and tree_successor must be used to balance it out

        Node y = null;
        if (node.left == null || node.right == null)
            y = node;
        else
            y = treeSucessor(node);

        Node x = null;
        if (y.left != null)
            x=y.left;
        else
            x=y.right;

        if (x != null)
            x.parent = y.parent;
        if (y.parent == null)
        {
            Node rootNode = root(node);
            rootNode=x;
        } else if (y.key == y.parent.left.key)
            y.parent.left = x;
        else
            y.parent.right=x;

        if (y.key != node.key)
        {
            node.key=y.key;
            node.parent=y.parent;
            node.right=y.right;
            node.left=y.left;
        }
        return y;
    }
*/