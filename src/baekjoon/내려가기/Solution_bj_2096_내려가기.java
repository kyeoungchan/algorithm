package baekjoon.내려가기;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/2096
 * N줄에 0 이상 9 이하의 숫자가 3개씩 적혀 있다.
 * 이 게임은 첫 줄에서 시작해서 마지막 줄에 끝나게 되는 놀이다.
 * 내려갈 때 제약: 바로 아래의 수로 넘어가거나, 대각선으로만 이동 가능하다.
 * 얻을 수 있는 최대 점수, 최소 점술르 구하는 프로그램을 작성하라.
 * 점수는 원룡이가 위치한 곳의 합니다.
 */
public class Solution_bj_2096_내려가기 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());

        int[] maxDp = new int[3];
        int[] minDp = new int[3];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int n3 = Integer.parseInt(st.nextToken());

            if (i == 0) {
                maxDp[0] = minDp[0] = n1;
                maxDp[1] = minDp[1] = n2;
                maxDp[2] = minDp[2] = n3;
            } else {
                int before1 = maxDp[0];
                int before2 = maxDp[1];
                int before3 = maxDp[2];
                maxDp[0] = Math.max(before1, before2) + n1;
                maxDp[1] = Math.max(Math.max(before1, before2), before3) + n2;
                maxDp[2] = Math.max(before2, before3) + n3;

                before1 = minDp[0];
                before2 = minDp[1];
                before3 = minDp[2];
                minDp[0] = Math.min(before1, before2) + n1;
                minDp[1] = Math.min(Math.min(before1, before2), before3) + n2;
                minDp[2] = Math.min(before2, before3) + n3;
            }
        }

        int max = Math.max(maxDp[0], Math.max(maxDp[1], maxDp[2]));
        int min = Math.min(minDp[0], Math.min(minDp[1], minDp[2]));
        System.out.println(max + " " + min);
        br.close();
    }


}
