package baekjoon.소수구하기;

import java.io.*;
import java.util.*;

public class Solution_bj_1929_소수구하기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        boolean[] notPrime = new boolean[N + 1];

        // 에라토스테네스의 체
        notPrime[0] = notPrime[1] = true;
        for (int i = 2; i <= Math.sqrt(N); i++) {
            if (notPrime[i]) continue;
            // j = i * i에서 시작하는 것은 올바른 최적화 (i*2 ~ i*(i-1)은 이미 이전 단계에서 마킹됨)
            for (int j = i * i; j <= N; j += i) {
                notPrime[j] = true;
            }
        }

        for (int i = M; i <= N; i++) {
            if (!notPrime[i]) sb.append(i).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
