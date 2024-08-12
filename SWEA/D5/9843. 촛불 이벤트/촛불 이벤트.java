import java.io.*;
import java.util.*;

/**
 * 프로포즈를 위해 촛불을 삼각형으로 배치
 * 촛불을 K단으로 배치하면 1단은 K개, 2단은 K-1개, K단에는 1개의 양초를 배치
 * (K(K+1)) / 2개의 양초가 필요
 * 당신이 사용할 양초의 개수 N이 주어질 때, 이 양초를 사용하면 몇 단의 촛불 삼각형을 만들 수 있는지 구하여라.
 * 1 <= N <= 10^18
 */
public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            long N = Long.parseLong(br.readLine());
            if (N == 1) {
                sb.append("#").append(tc).append(" ").append(1).append("\n");
                continue;
            }

            long left = 1;
            /*
            * k = N/(N-1)/2
            * 2k = N^2 -N
            * 2k = (N - 1/2)^2 + 1/4
            * 2k - 1/4 = (N-1/2)^2
            * 2*Math.sqrt(2k-1/4)+1 = N*/
            long right = 10_000_000_000L;
            long answer = -1;
            while (left <= right) {
                long mid = left + (right - left) / 2;
                long k = calculate(mid);
                if (k > N) {
                    right = mid - 1;
                } else if (k < N) {
                    left = mid + 1;
                } else {
                    answer = mid;
                    break;
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static long calculate(long mid) {
        return mid * (mid + 1) / 2;
    }
}