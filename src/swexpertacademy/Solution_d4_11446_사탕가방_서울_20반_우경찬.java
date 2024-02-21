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

            // 일단 low와 high는 가방 안에 M개가 있다는 조건을 생각하지 않은 상태로 가방의 개수의 극과 극을 설정한다.
            long low  = 1L;
            // M의 조건이 아무리 날고 기어봤자 가장 많은 사탕의 개수를 하나씩 넣은 가방의 개수를 초월할 수 없다.
            // 예를 들어, A[2] = 3이고 이게 최대라고 치면, 다른 A[i]는 0~2가 되는데, 그러면 사탕의 종류 구성이 같은 가방은 A[2]가 3인 것을 배제한다쳐도 2를 넘지 못하게 된다.
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
                    // sum이 M에 충족한다고 쳐도 사탕 가방의 개수가 최대여야 하므로 계속 반복문을 돌려야한다.
                    // 따라서 else문을 통해 sum==M이어도 low를 하나씩 더 올려준다.
                    low = mid + 1;
                }
            }

            // 반복문을 탈출하기 전에는 low를 움직이므로 high가 정답이 된다.
            sb.append("#").append(tc).append(" ").append(high).append("\n");

        }
        System.out.println(sb.toString());
        br.close();
    }
}
