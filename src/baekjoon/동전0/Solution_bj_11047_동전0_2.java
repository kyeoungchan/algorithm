package baekjoon.동전0;

import java.io.*;
import java.util.*;

public class Solution_bj_11047_동전0_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(br.readLine());
        }
        int cnt = 0;
        for (int i = N - 1; i > -1; i--) {
            int price = A[i];
            cnt += K / price;
            K %= price;
            if (K == 0) break;
        }
        System.out.println(cnt);
        br.close();
    }
}
