package swexpertacademy.사탕가방;

import java.io.*;
import java.util.*;

/**
 * N 종류의 사탕
 * 각 종류마다 A1, A2,...,AN개의 사탕이 있다.
 * 이 사탕을 가방에 잘 나눠담고 싶다.
 * - 가방 안에 정확히 M개의 사탕이 들어있어야 한다.
 * - 모든 가방에 들어있는 사탕 종류의 구성이 같아야 한다.
 * => 최대 몇 개의 사탕 가방을 만들 수 있는지 구하는 프로그램
 */
public class Solution_d4_11446_사탕가방 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_11446.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            long M = Long.parseLong(st.nextToken());
            long[] candies = new long[N];
            st = new StringTokenizer(br.readLine(), " ");
            long left = 1, right = 0;
            for (int i = 0; i < N; i++) {
                candies[i] = Long.parseLong(st.nextToken());
                right = Math.max(right, candies[i]);
            }

            while (left <= right) {
                long mid = left + (right - left) / 2;
                if (canMake(mid, N, M, candies)) left = mid + 1;
                else right = mid - 1;
            }
            sb.append("#").append(tc).append(" ").append(right).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static boolean canMake(long mid, int N, long M, long[] candies) {
        long result = 0;
        for (int i = 0; i < N; i++) {
            result += candies[i] / mid;
            if (result >= M) return true;
        }
        return false;
    }
}
