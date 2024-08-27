package baekjoon.꼬인전깃줄;

import java.io.*;
import java.util.*;

public class Solution_bj_1365_꼬인전깃줄 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] rights = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i < N + 1; i++) rights[i] = Integer.parseInt(st.nextToken());

        int[] lis = new int[N + 1];
        int len = 0;
        for (int i = 1; i < N + 1; i++) {
            if (rights[i] > lis[len]) {
                lis[++len] = rights[i];
                continue;
            }
            int left = 1, right = len;
//            int idx = len;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (lis[mid] < rights[i]) {
                    left = mid + 1;
//                    idx = mid;
                } else {
                    right = mid - 1;
                }
            }
            lis[left] = rights[i];
        }

        System.out.println(N - len);
        br.close();
    }
}
