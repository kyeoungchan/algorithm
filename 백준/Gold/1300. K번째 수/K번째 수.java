import java.io.*;
import java.util.*;

/**
 * 1 2 3  4
 * 2 4 6  8
 * 3 6 9  12
 * 4 8 12 16
 *
 * 1 2  4  6
 * 3 8  9  11
 * 5 10 13 14
 * 7 12 15 16
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        int left = 1, right = k;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long midIdx = getIdx(mid, N);
            if (midIdx < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(left);
        br.close();
    }

    static long getIdx(int mid, int N) {
        long result = 0;
        for (int i = 1; i < N + 1; i++) {
            result += Math.min(mid / i, N);
        }
        return result;
    }
}