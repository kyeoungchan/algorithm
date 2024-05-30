import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        if (N == 1) System.out.println(1);
        else if (N == 2) System.out.println(2);
        else {
            int mod = 10_007;
            int[] nums = new int[N + 1];
            nums[1] = 1;
            nums[2] = 2;
            for (int i = 3; i < N + 1; i++) {
                nums[i] = (nums[i - 1] + nums[i - 2]) % mod;
            }
            System.out.println(nums[N]);
        }
        br.close();
    }
}