import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hashing
{
    public static void main(String[] args)
    {
        HashingLibrary tester = new HashingLibrary();
        tester.initializeObjectList();
    }
}

class HashingLibrary
{
    private int maxIndex = 50;
    private int numObjects = 10;
    private List<exampleObject> objectList = new ArrayList<exampleObject>();

    public void initializeObjectList()
    {
        for (int i = 0; i < numObjects; i++)
            objectList.add(new exampleObject((int)(maxIndex*Math.random())));

        confirmObjList();
    }

    private void confirmObjList()
    {
        System.out.println("confirmation of object list:");
        for (exampleObject i:objectList)
            System.out.println(i + " key:" + i.key);
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