package Project_212;

public class Merge {

    // mode:
    // 0 = sort by name
    // 1 = sort by ID
    public static void mergeSort(Customer[] arr, int left, int right, int mode) {
        if (left >= right)
            return;

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid, mode);
        mergeSort(arr, mid + 1, right, mode);

        merge(arr, left, mid, right, mode);
    }

    private static void merge(Customer[] arr, int left, int mid, int right, int mode) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        Customer[] L = new Customer[n1];
        Customer[] R = new Customer[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {

            boolean takeLeft;

            if (mode == 0) {
                // sort by name
                takeLeft = L[i].getName().compareToIgnoreCase(R[j].getName()) <= 0;

            } else {
                // sort by ID
                takeLeft = L[i].getCustomerId() <= R[j].getCustomerId();
            }

            if (takeLeft)
                arr[k++] = L[i++];
            else
                arr[k++] = R[j++];
        }

        while (i < n1)
            arr[k++] = L[i++];

        while (j < n2)
            arr[k++] = R[j++];
    }
}

