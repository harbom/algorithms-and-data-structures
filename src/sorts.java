import java.util.Arrays;

public class sorts
{
    public static void main(String[] args)
    {
        SortsLibrary sorter = new SortsLibrary();
        sorter.initializeArray();
        //sorter.bubbleSort();
        //sorter.bogoSort();
        //sorter.insertionSort_iterative();
        //sorter.mergeSort_recursive(sorter.arr,0,sorter.arrSize-1);
        sorter.heapSort(sorter.arr);
        //sorter.countingSort(sorter.arr);
        //sorter.radixCountingSort(sorter.arr,1);
        int[] radixTestArr = {329,457,657,839,436,720,355,24};
        //sorter.radixSort(radixTestArr);
    }
}
//sorting algorithms such as insertion sort and merge sort
class SortsLibrary
{
    public int arrSize = 10;
    public int[] arr = new int[arrSize];

    public void initializeArray()
    {
        for (int i = 0; i < arrSize; i++)
        {
            int newRand = (int)(Math.random()*10);
            arr[i] = newRand;
        }

        System.out.println("initial: " + Arrays.toString(arr));
    }

    //very inneficient sorting, O(n^2)
    public void bubbleSort()
    {
        for (int i = 0; i < arrSize; i++)
        {
            for (int j = 0; j < arrSize && j != i; j++)
            {
                if (arr[j] > arr[i])
                {
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }

        System.out.println("after bubblesort: " + Arrays.toString(arr));
    }

    //VERY inneficient method of sorting
    public void bogoSort()
    {
        while(!isSorted(arr))
        {
            shuffle(arr);
        }

        System.out.println("after bogosort: " + Arrays.toString(arr));
    }

    private boolean isSorted(int[] array)
    {
        for(int i = 0; i < array.length-1; i++)
        {
            if(array[i+1] < array[i])
                return false;
        }
        return true;
    }

    private void shuffle(int[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            int randomIndex = (int)(arr.length*Math.random());
            int curr = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = curr;
        }
    }


    /*should take O(n^2)
    arr[0,key-1] is presorted, that's the invariant. iterate through k and perform pairwise swaps to maintain
    presorted array*/
    public void insertionSort_iterative()
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

    public void insertionSort_recursive()
    {
    }


    //takes two presorted arrays and merges them
    // A is the array, p is start of subarray, r is end of subarray, p <= q < r
    private void merge(int[] A, int p, int q, int r)
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
    public void mergeSort_recursive(int[] A, int p, int r)
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

    //builds a heap based off of the array
    private void buildMaxHeap(int[] array, HeapBST heap)
    {
        //elements A[ (floor)(n/2 )..n-1] are all leaves, so start from nodes before that and recurse down the tree to the leaves
        for (int i = (int)Math.floor(array.length/2)-1; i>= 0; i--)
        {
            heap.maxHeapify(arr,i);
        }

        System.out.println("after build: " + Arrays.toString(array));
    }

    /* should take O(nlgn)
    for all leaves until the second node (i--), the root is exchanged with A[i]. Then it cuts off that last node
    by decrementing heap size and does max heapify to arrange heap for each root exchanged. Leads to decreasing
    list as the root will be the highest
     */
    public void heapSort(int[] array)
    {
        HeapBST heap = new HeapBST(array);
        heap.heapSize = array.length;

        //build max heap first
        buildMaxHeap(array, heap);

        //exchange root with A[i] for each i
        for (int i = array.length-1; i >= 1; i--)
        {
            //exchange A[i] and A[0]
            int rootVal = array[0];
            array[0] = array[i];
            array[i] = rootVal;

            //decrement heap size so max heap condition is not broken
            heap.heapSize -= 1;

            //rearrange max heap to follow structure
            heap.maxHeapify(array,0);
        }

        System.out.println("after heapsort: " + Arrays.toString(array));
    }

    //stable(duplicate nums go in same order as they came in) sorting algo with Omega(n)
    public void countingSort(int[] array)
    {
        //find max of array to set k, the length of the array of the positions array
        int k = 0;
        for (int i:array)
            if (i>=k)
                k=i;
        //initialize positions array as a blank array of length k, going from 0 to k
        int[] positions = new int[k+1];
        //System.out.println("After initializing positions array: " + Arrays.toString(positions));

        //step 1: make positions a counter array, where index i appears in array positions[i] times
        for (int i:array)
            positions[i]++;
        //System.out.println("After making positions a counter array: " + Arrays.toString(positions));

        //step 2: positions[i] should now hold the number of elements <= i
        for (int i = 1; i < positions.length; i++)
            positions[i] += positions[i-1];
        //System.out.println("After the <= step: " + Arrays.toString(positions));

        //starting from the front of the input array, assign element j to output[position[j]]. then,
        //decrement position[j] so that if a duplicate occurs, the numbers are not overwritten, the next num goes backward
        int[] output = new int[array.length+1];
        for (int i = array.length-1; i >= 0; i--)
        {
            output[positions[array[i]]] = array[i];
            positions[array[i]]--;
        }

        System.out.println("After counting sort: " + Arrays.toString(output));
    }

    //look at above method for reference, created a diff method just for radix sort
    public int[] radixCountingSort(int[] array,int placevalue)
    {
        int range = 10;
        int[] positions = new int[10];

        //pigenholes each num into a bucket from 0-9, thats really the only modification from the above method

        //assigned frequencies
        for (int i = 0; i < array.length; i++)
        {
            int digit = (array[i] / placevalue) % range;
            positions[digit]++;
        }

        //assign nums until that index
        for (int i = 1; i < range; i++)
            positions[i] += positions[i-1];

        //assign to an output array
        int[] output = new int[array.length];
        for (int i = array.length-1; i >= 0; i--)
        {
            int digit = (array[i] / placevalue) % range;
            output[positions[digit]-1] = array[i];
            positions[digit]--;
        }

        return output;
    }

    private int getMax(int[] arr)
    {
        int max=0;
        for (int i:arr) if (i>=max) max= i;
        return max;
    }

    private int getNumDigits(int i)
    {
        return Integer.toString(i).length();
    }

    //sorts least significant digit to most significant digit
    //O(d(n+k)), where d is the max number of digits in the array. Can be O(n) if d and k are O(n)
    public void radixSort(int[] numbers)
    {
        int maxNum = getMax(numbers);
        int numDigits = getNumDigits(maxNum);
        int placeval = 1;
        while (numDigits-- > 0)
        {
            numbers = radixCountingSort(numbers,placeval);
            placeval *= 10;
            System.out.println("Iteration: " + Arrays.toString(numbers));
        }

        System.out.println("Final: " + Arrays.toString(numbers));
    }
}

class HeapBST
{
    public int heapSize = 0;

    public HeapBST(int[] arr)
    {
        heapSize = arr.length;
    }

    /*maintains max heapify property: for all index i, parent[i] >= arr[i]
    does this by starting at i, comparing its left/right children, and exchanging
    array values and having the least value 'float' down to the leaves through recursion*/
    public void maxHeapify(int[] array, int i)
    {
        //System.out.println("start of maxheapify: " + Arrays.toString(array) + "\ncurr i: " + i);
        int largest = i;
        int l = left(i);
        int r = right(i);

        //System.out.println("heapSize: " + heapSize);
        if (l < heapSize) //if its a leaf, will not do anything
        {
            //pick largest of l and i
            if (array[i] > array[l])
                largest = i;
            else
                largest = l;
            //System.out.println("i is greater: A[i] is " + currValue + " and A[l] is " + leftValue);
        }

        if (r < heapSize) //if its a leaf, will not do anything
        {
            //pick largest of r, l and i
            if (array[r] > array[largest])
                largest = r;
            //System.out.println("no, r is greater: : A[largest] is " + array[largest] + " and A[r] is " + rightValue);
        }

        //System.out.println("curr index: " + i + "\tlargest index: " + largest);
        if (largest != i) //if the max-heap condition is not satisfied
        {
            //System.out.println("time to exchange");
            //exchange arr[i] and arr[largest]
            int i_val = array[i];
            array[i] = array[largest];
            array[largest] = i_val;

            //System.out.println("on to the next recursion level\n");
            maxHeapify(array,largest); //recurse down to next subtree to put things in order
        }

        //System.out.println("end of maxHeapify: " + Arrays.toString(array) + "\n\n");
    }

    //returns index of parent node in heap BST
    public int parent(int i) { return (int)Math.floor(i/2); }
    //returns left child of node in heap BST
    public int left(int i) { return (int)Math.floor(2*i+1); }
    //returns right child of node in heap BST
    public int right (int i) { return (int)Math.floor(2*i +2); }
}