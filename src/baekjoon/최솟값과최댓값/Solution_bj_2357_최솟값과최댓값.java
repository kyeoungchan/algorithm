package baekjoon.최솟값과최댓값;

import java.util.*;
import java.io.*;

public class Solution_bj_2357_최솟값과최댓값 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
        }
        int h = (int) Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = 1 << (h + 1);
        int[] minTree = new int[treeSize];
        int[] maxTree = new int[treeSize];

        init(numbers, minTree, maxTree, 1, 0, N - 1);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int min = queryMin(minTree, 1, 0, N - 1, left - 1, right - 1);
            int max = queryMax(maxTree, 1, 0, N - 1, left - 1, right - 1);
            sb.append(min).append(" ").append(max).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void init(int[] numbers, int[] minTree, int[] maxTree, int node, int start, int end) {
        if (start == end) {
            minTree[node] = maxTree[node] = numbers[start];
            return;
        }
        init(numbers, minTree, maxTree, node * 2, start, (start + end) / 2);
        init(numbers, minTree, maxTree, node * 2 + 1, (start + end) / 2 + 1, end);
        minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
        maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
    }

    static int queryMin(int[] minTree, int node, int start, int end, int left, int right) {
        if (end < left || start > right) {
            return Integer.MAX_VALUE;
        }
        if (left <= start && end <= right) {
            return minTree[node];
        }
        int lMin = queryMin(minTree, node * 2, start, (start + end) / 2, left, right);
        int rMin = queryMin(minTree, node * 2 + 1, (start + end) / 2 + 1, end, left, right);
        return Math.min(lMin, rMin);
    }

    static int queryMax(int[] maxTree, int node, int start, int end, int left, int right) {
        if (end < left || start > right) {
            return 0;
        }
        if (left <= start && end <= right) {
            return maxTree[node];
        }
        int lMax = queryMax(maxTree, node * 2, start, (start + end) / 2, left, right);
        int rMax = queryMax(maxTree, node * 2 + 1, (start + end) / 2 + 1, end, left, right);
        return Math.max(lMax, rMax);

    }
}
