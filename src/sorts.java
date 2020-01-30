import com.sun.source.tree.Tree;

import java.util.Arrays;

//sorting algorithms such as insertion sort and merge sort
public class sorts
{
    private static int arrSize = 10;
    private static int[] arr = new int[arrSize];
    public static void main(String[] args)
    {
        initializeArray();
        //insertionSort_iterative();
        mergeSort_recursive(arr,0,arrSize-1);
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


    //takes two presorted arrays and merges them
    // A is the array, p is start of subarray, r is end of subarray, p <= q < r
    private static void merge(int[] A, int p, int q, int r)
    {
        int n1 = q-p+1; //length of left subarray
        int n2 = r-q; //length of right subarray

        //create arrays L[1..n1 + 1], R[1..n2+1]
        int[] L = new int[n1+1];
        int[] R = new int[n2+1];
        for (int i = 0; i < n1; i++) //add elements to left array
        {
            L[i] = A[p+i];
        }
        for (int j = 0; j < n2; j++) //add elements to right array
        {
            R[j] = A[q+j+1];
        }

        //create sentinels: once method reaches these, the other one will always be looked at
        // as integer.max value > anything else
        L[L.length-1] = Integer.MAX_VALUE;
        R[R.length-1] = Integer.MAX_VALUE;

        int i = 0; int j = 0; //i is left iterator, j is right iterator
        for (int k = p; k <= r; k++) //k = start index to end index (r)
        {
            if (L[i] <= R[j]) //if left at i < right at j
            {
                A[k] = L[i]; //assign the least element to A at position k
                i++; //move up on the left array
            } else
            {
                A[k] = R[j]; //assign the least element to A at position k
                j++; //move up on the right array
            }
        }
    }

    /*should take O(n*log(n))
    think recursion tree, splits an array into halves of the previous half of the array until
     the length of each array is 1, then combines them back up to a sorted array
    downfall is the space needed to create so many arrays*/
    private static void mergeSort_recursive(int[] A, int p, int r)
    {
        if (p<r) //will terminate once each p = r = 1
        {
            int q = (p+r)/2; //cut array into half
            mergeSort_recursive(A,p,q); //sort the left half at this point
            mergeSort_recursive(A,q+1,r); //sort the right half at this point
            merge(A,p,q,r); //take the product of the recursion from both sides and merge them
        }

        System.out.println("current: " + Arrays.toString(A));
    }
}

class HeapBST
{
    public static int[] arr = {};
    public static int heapSize = 0;
    public static int arraySize = arr.length;

    public HeapBST(int[] arr)
    {
        this.arr = arr;
        heapSize = arr.length;
        arraySize=arr.length;
    }

    /*maintains max heapify property: for all index i, parent[i] >= arr[i]
    does this by starting at i, comparing its left/right children, and exchanging
    array values and having the least value 'float' down to the leaves through recursion*/
    public static void maxHeapify(int i)
    {
        int largest;
        int l = left(i);
        int r = right(i);
        if (l <= heapSize && arr[i] > arr[l]) //pick largest of r, l, and i
            largest = i;
        else
            largest = (arr[r] > arr[l]) ? r : l;

        if (largest != i) //if the max-heap condition is not satisfied
        {
            //exchange arr[i] and arr[largest]
            int i_val = arr[i];
            arr[i] = arr[largest];
            arr[largest] = i_val;

            maxHeapify(largest); //recurse down to next subtree to put things in order
        }
    }

    //returns index of parent node in heap BST
    public static int parent(int i) { return (int)Math.floor(i/2); }
    //returns left child of node in heap BST
    public static int left(int i) { return (int)Math.floor(2*i); }
    //returns right child of node in heap BST
    public static int right (int i) { return (int)Math.floor(2*i +1); }
}

class Node
{
    public static int num;
    public static Node left;
    public static Node right;

    public Node(int num)
    {
        this.num = num;
        left = null;
        right = null;
    }
}