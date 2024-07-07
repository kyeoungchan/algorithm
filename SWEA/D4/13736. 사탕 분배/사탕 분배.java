import java.io.*;
import java.util.*;

/**
 * 나연이는 A개의 사탕
 * 다현이는 B개의 사탕
 * 작업을 K번 반복
 * 사탕의 개수가 더 적은 사람: X, 더 많은 사람: Y
 * 같은 개수라면 나연이가 X, 다현이가 Y
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int T2 = A + B;
            long a = A * getPowerOf2(K, T2) % T2;
            sb.append("#").append(tc).append(" ").append(Math.min(a, T2 - a)).append("\n");
            /*
             * P + Q = T
             * P => 2 * P
             * Q => Q - P = Q - (T - Q) = 2Q - T
             * 0 <= 2Q - T <= T
             * 2Q - T = (2Q - T) % T = 2Q % T*/
        }
        System.out.print(sb.toString());
        br.close();
    }

    static long getPowerOf2(int n, int T) {
        long result = 1;
        long num = 2;
        while (n > 0) {
            if (n % 2 == 1) {
                result *= num;
                result %= T;
            }
            num *= num;
            num %= T;
            n /= 2;
        }
        return result;
    }
}