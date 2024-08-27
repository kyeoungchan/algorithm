package mincoding.츄러스;

import java.io.*;
import java.util.*;

/**
 * N개의 츄러스로, 가장 길고 일정한 K개의 크기로 만들어서 고객에게 전달한다.
 * 하나의 츄러스가 가질 수 있는 최대 길이
 */
public class Solution_min_츄러스 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] churros = new int[N];
        int right = 1;
        for (int i = 0; i < N; i++) {
            churros[i] = Integer.parseInt(br.readLine());
            right = Math.max(right, churros[i]);
        }

        int left = 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canMake(mid, N, K, churros)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(right);
        br.close();
    }

    static boolean canMake(int mid, int N, int K, int[] churros) {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            cnt += churros[i] / mid;
        }
        return cnt >= K;
    }
}
