import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Hashing
{
    public static void main(String[] args)
    {
        HashingLibrary tester = new HashingLibrary();
        tester.initializeObjectList();
        tester.initializeDirectAddressTable();
        tester.initializeHashTableWithChaining();
    }
}

class HashingLibrary
{
    private int maxIndex = 50;
    private int numObjects = 10;
    private List<exampleObject> objectList = new ArrayList();
    private exampleObject[] arr = new exampleObject[maxIndex];

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

    //solves collisions by having a linked list at every index
    private void initializeHashTableWithChaining()
    {

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
    public exampleObject curr;
    public exampleObjectNode next;

    public exampleObjectNode(exampleObject x)
    {
        curr = x;
    }
}