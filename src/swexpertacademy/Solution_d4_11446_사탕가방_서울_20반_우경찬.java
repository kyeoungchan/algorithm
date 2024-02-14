package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d4_11446_사탕가방_서울_20반_우경찬 {


    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_11446.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            long M = Long.parseLong(st.nextToken());
            long[] A = new long[N];
            long max = Long.MIN_VALUE;
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                A[i] = Long.parseLong(st.nextToken());
                max = Math.max(max, A[i]);
            }

            long low  = 1L;
            long high = max;

            while (low <= high) {
                long mid = (low + high) / 2;
                long sum = 0L;
                for (int i = 0; i < N; i++) {
                    sum += (A[i] / mid);
                }
                if (sum < M) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            sb.append("#").append(tc).append(" ").append(high).append("\n");

        }
        System.out.println(sb.toString());
        br.close();
    }

/*
    A = new long[N + 1][4];

    st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i < N + 1; i++) {
        A[i][0] = Long.parseLong(st.nextToken()); // 남은 사탕의 수
        A[i][1] = 0; // 뺀 사탕의 수
        A[i][2] = A[i][0]; // 현재에서 사탕 하나를 빼면 몇 봉지 만들 수 있는지
        A[i][3] = i;
    }
    long ans = 0;
    boolean canNotPack = false;
            for (long i = 0; i < M; i++) {
        Arrays.sort(A, Comparator.comparingLong(a -> a[2]));
        if (A[N][0] != 0) {
            if (i == M - 1) {
                ans = A[N][2];
            }
            A[N][1]++;
            A[N][2] = A[N][0] / (A[N][1] + 1);
        } else {
            canNotPack = true;
            break;
        }
    }
            if (canNotPack)
    ans = 0;
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
*/


}
