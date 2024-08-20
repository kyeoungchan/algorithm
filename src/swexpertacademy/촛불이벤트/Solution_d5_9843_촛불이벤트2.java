package swexpertacademy.촛불이벤트;

import java.io.*;
import java.util.*;

public class Solution_d5_9843_촛불이벤트2 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d5_9843.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            long N = Long.parseLong(br.readLine());
            sb.append("#").append(tc).append(" ");
            if (N == 1) {
                sb.append(1).append("\n");
                continue;
            }
            int left = 1;
            /* N = k(k+1)/2
            * 2N = k^2 + k
            * 2N + 1/4 = (k + 1/2)^2
            * k + 1/2 = sqrt(2N+1/4)
            * k = sqrt(2N + 1/4) - 1/2
            * 1 <= k <= sqrt(2 * 10^18 + 1/4) - 1/2*/

            int right = 2000000000;
            int answer = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                long total = (long) mid * (mid + 1) / 2;
                if (total == N) {
                    answer = mid;
                    break;
                } else if (total < N) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            sb.append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
