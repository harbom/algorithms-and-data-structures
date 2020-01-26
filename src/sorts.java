import java.util.Arrays;

//sorting algorithms such as insertion sort and merge sort
public class sorts
{
    private static int arrSize = 10;
    private static int[] arr = new int[arrSize];
    public static void main(String[] args)
    {
        initializeArray();
        insertionSort_iterative();
    }

    private static void initializeArray()
    {
        for (int i = 0; i < arrSize; i++)
        {
            int newRand = (int)(Math.random()*10);
            arr[i] = newRand;
        }

        System.out.println("initial: " + Arrays.toString(arr));
    }

    /*should take O(n^2)
    arr[0,key-1] is presorted, that's the invariant. iterate through k and perform pairwise swaps to maintain
    presorted array*/
    private static void insertionSort_iterative()
    {
        for (int key = 1; key < arrSize; key++) //iterate through key
        {
            for (int j = key-1; j>=0; j--) //start from first num before key
            {
                if (arr[j] > arr[j+1]) //if swap needs to be done with num before j and num after j, do swap
                {
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        System.out.println("insertion sort iterative: " + Arrays.toString(arr));
    }

    private static void insertionSort_recursive()
    {
    }
}
