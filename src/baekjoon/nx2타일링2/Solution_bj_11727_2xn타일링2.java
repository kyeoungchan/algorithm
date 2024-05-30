package baekjoon.nx2타일링2;

import java.io.*;

public class Solution_bj_11727_2xn타일링2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //f(1) = 1, f(2) = 3, f(3) = 5, f(n) = f(n-1) + 2 * f(n-2)
        int N = Integer.parseInt(br.readLine());
        if (N == 1) System.out.println(1);
        else if (N == 2) System.out.println(3);
        else {
            int mod = 10_007;
            int[] nums = new int[N + 1];
            nums[1] = 1; nums[2] = 3;
            for (int i = 3; i < N + 1; i++) nums[i] = (nums[i - 1] + 2 * nums[i - 2]) % mod;
            System.out.println(nums[N]);
        }
        br.close();
    }
}
