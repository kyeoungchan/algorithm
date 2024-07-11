package baekjoon.수열과쿼리16;

import java.util.*;
import java.io.*;

public class Solution_bj_14428_수열과쿼리16 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        int h = (int) Math.ceil(Math.log(N) / Math.log(2));
        int size = 1 << (h + 1);
        int[][] tree = new int[size][2];
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
        init(tree, 1, arr, 0, N - 1);
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int command = Integer.parseInt(st.nextToken());
            if (command == 1) {
                int idx = Integer.parseInt(st.nextToken()) - 1;
                int value = Integer.parseInt(st.nextToken());
                update(tree, 1, arr, 0, N - 1, idx, value);
            } else {
                int left = Integer.parseInt(st.nextToken()) - 1;
                int right = Integer.parseInt(st.nextToken()) - 1;
                int result = query(tree, 1, 0, N - 1, left, right)[1];
                sb.append(result + 1).append("\n");
            }
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void init(int[][] tree, int treeIdx, int[] arr, int start, int end) {
        if (start == end) {
            tree[treeIdx][0] = arr[start];
            tree[treeIdx][1] = start;
            return;
        }
        int mid = start + (end - start) / 2;
        init(tree, treeIdx * 2, arr, start, mid);
        init(tree, treeIdx * 2 + 1, arr, mid + 1, end);
        if (tree[treeIdx * 2][0] == tree[treeIdx * 2 + 1][0]) {
            if (tree[treeIdx * 2][1] < tree[treeIdx * 2 + 1][1]) {
                tree[treeIdx][0] = tree[treeIdx * 2][0];
                tree[treeIdx][1] = tree[treeIdx * 2][1];
            } else {
                tree[treeIdx][0] = tree[treeIdx * 2 + 1][0];
                tree[treeIdx][1] = tree[treeIdx * 2 + 1][1];
            }
        } else if (tree[treeIdx * 2][0] < tree[treeIdx * 2 + 1][0]) {
            tree[treeIdx][0] = tree[treeIdx * 2][0];
            tree[treeIdx][1] = tree[treeIdx * 2][1];
        } else {
            tree[treeIdx][0] = tree[treeIdx * 2 + 1][0];
            tree[treeIdx][1] = tree[treeIdx * 2 + 1][1];
        }
    }

    static void update(int[][] tree, int treeIdx, int[] arr, int start, int end, int idx, int value) {
        if (start == end) {
            tree[treeIdx][0] = value;
            tree[treeIdx][1] = idx;
            arr[idx] = value;
            return;
        }
        if (idx < start || idx > end) return;
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(tree, treeIdx * 2, arr, start, mid, idx, value);
        } else {
            update(tree, treeIdx * 2 + 1, arr, mid + 1, end, idx, value);
        }

        if (tree[treeIdx * 2][0] == tree[treeIdx * 2 + 1][0]) {
            if (tree[treeIdx * 2][1] < tree[treeIdx * 2 + 1][1]) {
                tree[treeIdx][0] = tree[treeIdx * 2][0];
                tree[treeIdx][1] = tree[treeIdx * 2][1];
            } else {
                tree[treeIdx][0] = tree[treeIdx * 2 + 1][0];
                tree[treeIdx][1] = tree[treeIdx * 2 + 1][1];
            }
        } else if (tree[treeIdx * 2][0] < tree[treeIdx * 2 + 1][0]) {
            tree[treeIdx][0] = tree[treeIdx * 2][0];
            tree[treeIdx][1] = tree[treeIdx * 2][1];
        } else {
            tree[treeIdx][0] = tree[treeIdx * 2 + 1][0];
            tree[treeIdx][1] = tree[treeIdx * 2 + 1][1];
        }
    }

    static int[] query(int[][] tree, int treeIdx, int start, int end, int left, int right) {
        if (end < left || start > right) return null;
        if (left <= start && end <= right) return tree[treeIdx];
        int mid = start + (end - start) / 2;
        int[] leftResult = query(tree, treeIdx * 2, start, mid, left, right);
        int[] rightResult = query(tree, treeIdx * 2 + 1, mid + 1, end, left, right);
        if (leftResult != null && rightResult != null) {
            if (leftResult[0] == rightResult[0]) {
                if (leftResult[1] < rightResult[1]) {
                    return leftResult;
                } else {
                    return rightResult;
                }
            } else if (leftResult[0] > rightResult[0]) {
                return rightResult;
            } else {
                return leftResult;
            }
        } else if (leftResult == null) return rightResult;
        else return leftResult;
    }
}
