import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Hashing
{
    public static void main(String[] args)
    {
        HashingLibrary hasher = new HashingLibrary();
        hasher.initializeObjectList();

        hasher.initializeDirectAddressTable();

        hasher.initializeHashTableWithChaining();
        hasher.insertAllObjectsIntoHashTableWithChaining();
    }
}

class HashingLibrary
{
    private int maxIndex = 50;
    private int numObjects = 10;
    private List<exampleObject> objectList = new ArrayList();
    private exampleObject[] arr = new exampleObject[maxIndex];
    private exampleObjectNode[] hashTableChaining = new exampleObjectNode[maxIndex];

    public void initializeObjectList()
    {
        for (int i = 0; i < numObjects; i++)
            objectList.add(new exampleObject((int)(maxIndex*Math.random())));

        confirmObjList();
    }

    //builds a list of objects, as a prestage to direct address table or hashtable
    private void confirmObjList()
    {
        System.out.println("confirmation of object list:");
        for (exampleObject i:objectList)
            System.out.println(i + " key:" + i.key);
    }

    //adds all elements in list into an array so that search will be O(1)
    //this doesn't solve collisions so the most recent duplicates will only be stored
    public void initializeDirectAddressTable()
    {
        for (exampleObject i: objectList)
        {
            arr[i.key] = i;
        }

        confirmDirectAddressTable();
    }

    private void confirmDirectAddressTable()
    {
        System.out.println("Confirmation of direct address table");
        for (int i=0; i<arr.length; i++)
        {
            System.out.print("index: " + i + " ");
            if (arr[i] == null)
                System.out.print("no object here");
            else
                System.out.print("object key: " + arr[i].key + "\tobject: " + arr[i]);
            System.out.println();
        }
    }

    //initializes all linked lists in the hash table with a null curr object, null prev and next pointers
    public void initializeHashTableWithChaining()
    {
        for (int i = 0; i < maxIndex; i++)
        {
            hashTableChaining[i] = new exampleObjectNode();
        }
    }

    public void insertAllObjectsIntoHashTableWithChaining()
    {
        for (exampleObject i:objectList)
            insert(i);
    }

    //inserts a given example object into the hash table with chaining
    //solves collisions by having a linked list at every index
    //average (not worst case) search time is O(1) now
    public void insert(exampleObject x)
    {
        //need to insert each object at the head of the linked list at that index in the hash table
        int key = x.key;

        exampleObjectNode currLL = hashTableChaining[key];
        //currLL either has (prev,object,null) = (null,null,null) (initial state) or (something,object,null) (need to append it to the head).
        if (currLL.object == null)
        {
            //nothing is in this LL yet, assign the object and continue
            currLL.object = x;
        } else
        {
            currLL = new exampleObjectNode(x,currLL); //newLLNode.object = x, newLLNode.prev = currLL, currLL.next = newLLNode
        }

        hashTableChaining[key] = currLL;
    }
}

class exampleObject
{
    public int key;
    public Object data;

    public exampleObject(int key)
    {
        this.key = key;
    }
}

class exampleObjectNode
{
    public exampleObjectNode prev;
    public exampleObject object;
    public exampleObjectNode next;

    public exampleObjectNode(exampleObject x, exampleObjectNode prev)
    {
        object = x;
        next = null;
        this.prev = prev;
        prev.next = this;
    }

    public exampleObjectNode()
    {
        prev= null;
        object = null;
        next = null;
    }
}