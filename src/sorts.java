import java.util.Arrays;

//sorting algorithms such as insertion sort and merge sort
public class sorts
{
    private static int arrSize = 10;
    private static int[] arr = new int[arrSize];
    public static void main(String[] args)
    {
        initializeArray();
    }

    private static void initializeArray()
    {
        for (int i = 0; i < arrSize; i++)
        {
            int newRand = (int)(Math.random()*10);
            arr[i] = newRand;
        }

        //System.out.println(Arrays.toString(arr));
    }
}
