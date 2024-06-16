package baekjoon.구간합구하기;

import java.io.*;
import java.util.*;

public class Solution_bj_2042_구간합구하기_연습 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        long[] arr = new long[N];
        int h = (int) Math.ceil(Math.log(N) / Math.log(2));
        // 1 + 2 + 4 + 8 = 15
        // 1 + 2 + 4 = 7
        int treeSize = 1 << (h + 1);
        long[] tree = new long[treeSize];

        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }
        init(tree, arr, 1, 0, N - 1);
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (a == 1) {
                long c = Long.parseLong(st.nextToken());
                update(tree, arr, 1, 0, N - 1, b - 1, c);
            } else if (a == 2) {
                int c = Integer.parseInt(st.nextToken());
                long result = query(tree, 1, 0, N - 1, b - 1, c - 1);
                sb.append(result).append("\n");
            }
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void init(long[] tree, long[] arr, int treeIdx, int start, int end) {
        if (start == end) {
            tree[treeIdx] = arr[start];
            return;
        }

        int mid = start + (end - start) / 2;
        init(tree, arr, treeIdx * 2, start, mid);
        init(tree, arr, treeIdx * 2 + 1, mid + 1, end);
        tree[treeIdx] = tree[treeIdx * 2] + tree[treeIdx * 2 + 1];
    }

    static void update(long[] tree, long[] arr, int treeIdx, int start, int end, int targetIdx, long val) {
        if (targetIdx < start || targetIdx > end) return;
        if (start == end) {
            tree[treeIdx] = arr[targetIdx] = val;
            return;
        }
        int mid = start + (end - start) / 2;
        update(tree, arr, treeIdx * 2, start, mid, targetIdx, val);
        update(tree, arr, treeIdx * 2 + 1, mid + 1, end, targetIdx, val);
        tree[treeIdx] = tree[treeIdx * 2] + tree[treeIdx * 2 + 1];
    }

    static long query(long[] tree, int treeIdx, int start, int end, int left, int right) {
        if (right < start || left > end) return 0;
        if (start >= left && end <= right) return tree[treeIdx];
        int mid = start + (end - start) / 2;
        long leftSum = query(tree, treeIdx * 2, start, mid, left, right);
        long rightSum = query(tree, treeIdx * 2 + 1, mid + 1, end, left, right);
        return leftSum + rightSum;
    }
}
