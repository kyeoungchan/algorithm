package baekjoon.최솟값;

import java.io.*;
import java.util.*;

public class Solution_bj_10868_최솟값 {

    static final int MAX_DATA = 1_000_000_001;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        /*
        * N =1 -> 2
        * N=2 -> 4
        * N=3 -> 5~8
        * N=4 -> 7~8*/
        int height = (int) Math.ceil((Math.log(N) / Math.log(2))) + 1;
        int treeSize = (int) Math.pow(2, height);
        int[] tree = new int[treeSize];
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(br.readLine());

        init(0, N - 1, arr, 1, tree);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(query(a - 1, b - 1, 0, N - 1, tree, 1)).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void init(int start, int end, int[] arr, int treeIdx, int[] tree) {
        if (start == end) {
            tree[treeIdx] = arr[start];
            return;
        }
        // start = 0, end = 2
        // mid = 1
        int mid = start  + ((end - start) / 2);
        init(start, mid, arr, treeIdx * 2, tree);
        init(mid + 1, end, arr, treeIdx * 2 + 1, tree);
        tree[treeIdx] = Math.min(tree[treeIdx * 2], tree[treeIdx * 2 + 1]);
    }

    static int query(int left, int right, int start, int end, int[] tree, int treeIdx) {

        if (left <= start && end <= right) {
            return tree[treeIdx];
        }
        if (end < left || start > right) {
            return MAX_DATA;
        }

        int mid = start + ((end - start) / 2);
        int leftMin = query(left, right, start, mid, tree, treeIdx * 2);
        int rightMin = query(left, right, mid + 1, end, tree, treeIdx * 2 + 1);
        return Math.min(leftMin, rightMin);
    }
}
