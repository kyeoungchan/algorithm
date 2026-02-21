package baekjoon.전깃줄;

import java.io.*;
import java.util.*;

/**
 * 두 전봇대 A와 B 사이에 하나 둘씩 전깃줄을 추가하다보니 교차하는 경우가 발생 -> 몇 개의 전깃줄을 없애 전깃줄이 교차하지 않도록
 * 없애야 하는 전깃줄의 최소 개수 구하기
 */
public class Solution_bj_2565_전깃줄 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int max = 0;
        int[][] wires = new int[n][2];

        // 설치 가능한 최대 개수로 설정
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int numA = Integer.parseInt(st.nextToken());
            int numB = Integer.parseInt(st.nextToken());
            wires[i][0] = numA;
            wires[i][1] = numB;
        }

        // a번 전봇대 위치 기준으로 오름차순 정렬
        Arrays.sort(wires, Comparator.comparingInt(o -> o[0]));

        for (int i = 0; i < n; i++) {
            dp[i]++;

            for (int j = 0; j < i; j++) {
                if (wires[i][1] > wires[j][1]) {
                    // i번째 전깃줄을 그을 때 최고로 많이 설치할 수 있는 경우의 수 메모
                    // 가장 긴 증가하는 부분 수열과 같은 메커니즘이다.
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 각 위치마다 전깃줄을 그을 때 최고로 많이 설치할 수 있는 경우의 수를 메모했으니 각 위치들 모두 비교해서 최고 값을 구하면 최대 설치 가능 개수가 나온다.
                    max = Math.max(max, dp[i]);
                }
            }
        }

        System.out.println(n - max);

        br.close();
    }
}
