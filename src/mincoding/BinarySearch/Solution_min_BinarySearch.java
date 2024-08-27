package mincoding.BinarySearch;

import java.io.*;
import java.util.*;

public class Solution_min_BinarySearch {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(arr);
        int k = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < k; i++) {
            int value = Integer.parseInt(st.nextToken());
            if (exists(arr, value, n)) sb.append('O');
            else sb.append('X');
        }
        System.out.println(sb.toString());
        br.close();
    }

    static boolean exists(int[] arr, int value, int n) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == value) return true;
            else if (arr[mid] < value) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}
