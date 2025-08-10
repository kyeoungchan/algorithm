package baekjoon.가장긴바이토닉부분수열;

import java.io.*;
import java.util.*;

public class Solution_bj_11054_가장긴바이토닉수열2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        int[] increDp = new int[N];
        for (int i = 0; i < N; i++) {
            increDp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    increDp[i] = Math.max(increDp[i], increDp[j] + 1);
                }
            }
        }

        int[] decreDp = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            decreDp[i] = 1;
            for (int j = N - 1; j > i; j--) {
                if (nums[i] > nums[j]) {
                    decreDp[i] = Math.max(decreDp[i], decreDp[j] + 1);
                }
            }
        }
        int answer = 0;
        for (int i = 0; i < N; i++) {
            answer = Math.max(answer, increDp[i] + decreDp[i] - 1);
        }
        System.out.println(answer);
        br.close();
    }
}
