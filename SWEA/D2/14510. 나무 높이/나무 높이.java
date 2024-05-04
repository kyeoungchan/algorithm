import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[] trees = new int[N];
            st = new StringTokenizer(br.readLine(), " ");
            int maxH = 0;
            for (int i = 0; i < N; i++) {
                trees[i] = Integer.parseInt(st.nextToken());
                maxH = Math.max(maxH, trees[i]);
            }
            int odd = 0;
            int even = 0;
            for (int i = 0; i < N; i++) {
                int temp = maxH - trees[i];
                // odd 날에 물을 주는 날짜의 횟수
                odd += temp % 2;
                // even 날에 물을 주는 날짜의 횟수
                even += temp / 2;
            }

            while (odd + 2 <= even) {
                // 4만큼 차이가 난다면, even날이 2가 생기는데, x, 2, x 4로 물을 주는 것 보다는 홀수의 날 이틀로 바꾸어 1, 2, 3으로 물을 주는 것이 더 빠르다.
                // 단, 홀수 날 이틀이 빈다는 전제 하다.
                even--;
                odd += 2;
            }

            /*
            1 -> even0, odd 1. 1
            2 -> even 1, odd 0. x, 1
            3 -> even 1, odd 1. 1, 2
            4 -> even 2, odd 0 -> even 1, odd 2. 1, 2, 3
            5 -> even 2, odd 1. 1, 2, x, 4
            6 -> even 3, odd 0 -> even 2, odd 2. 1, 2, 3, 4
            7 -> even 3, odd 1 -> even 2, odd 3. 1, 2, 3, 4, 5
            8 -> even 4, odd 0 -> even 3, odd 2. 1, 2, 3, 4, x, 6
            9 -> even 4, odd 1 -> even 3, odd 3. 1, 2, 3, 4, 5, 6
            */
            int result;
            if (even > odd) {
                // 위의 반복문으로 인해 even이 odd보다 1만큼 많은 정도가 된 상황이다.
                // 1일부터 시작되기 때문에 홀수 날짜 하루가 비게 된다.
                result = odd + even + 1;
            } else if (even == odd) {
                result = odd + even;
            } else {
                // 예를 들어 1, 2, 3, x, 5, x, 7, x, 9이렇게 물을 준다고 치자.
                // odd = 5, even = 1인 상황이다.
                // odd + even + (x의 개수만큼의 even만큼 날짜를 채움 4) => 이렇게 하면 홀짝홀짝이 합쳐져서 10일이 된다.
                // 그러므로 마지막으로 1만큼 빼주어서 9가 되어야 한다.
                result = odd + even + (odd - even) - 1;
            }
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}