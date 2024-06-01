package baekjoon.일1로만들기;

import java.io.*;
import java.util.*;

public class Solution_bj_1463_1로만들기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            nums[i] = Math.min(i - 1, nums[i - 1] + 1);
            if (i % 2 == 0) {
                nums[i] = Math.min(nums[i], nums[i / 2] + 1);
            }
            if (i % 3 == 0) {
                nums[i] = Math.min(nums[i], nums[i / 3] + 1);
            }
//            System.out.println(i + " : " + nums[i]);
        }
        System.out.println(nums[N]);
        br.close();
    }
}
