package baekjoon.구간합구하기;

import java.util.*;
import java.io.*;

public class Solution_bj_2042_구간합구하기 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        long[] a = new long[N];
        int h = (int) Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = 1 << (h + 1);
        long[] tree = new long[treeSize];
        for (int i = 0; i < N; i++) {
            a[i] = Long.parseLong(br.readLine());
        }
        init(tree, a, 1, 0, N - 1);

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int command = Integer.parseInt(st.nextToken());
            if (command == 1) {
                int index = Integer.parseInt(st.nextToken());
                long val = Long.parseLong(st.nextToken());
                update(tree, a, 1, 0, N - 1, index - 1, val);
            } else {
                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                sb.append(query(tree, 1, 0, N - 1, left - 1, right - 1)).append("\n");
            }
        }
        System.out.print(sb.toString());
        br.close();
    }

    static long query(long[] tree, int node, int start, int end, int left, int right) {
        if (end < left || start > right) {
            return 0;
        }
        if (left <= start && end <= right) {
            return tree[node];
        }
        long lSum = query(tree, node * 2, start, (start + end) / 2, left, right);
        long rSum = query(tree, node * 2 + 1, (start + end) / 2 + 1, end, left, right);
        return lSum + rSum;
    }

    static void update(long[] tree, long[] a, int node, int start, int end, int index, long val) {
        if (index < start || index > end) {
            return;
        }
        if (start == end) {
            a[index] = val;
            tree[node] = val;
            return;
        }
        int mid = (start + end) / 2;
        update(tree, a, node * 2, start, mid, index, val);
        update(tree, a, node * 2 + 1, mid + 1, end, index, val);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    static void init(long[] tree, long[] a, int node, int start, int end) {
        if (start == end) {
            tree[node] = a[start];
            return;
        }
        int mid = (start + end) / 2;
        init(tree, a, node * 2, start, mid);
        init(tree, a, node * 2 + 1, mid + 1, end);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }
}