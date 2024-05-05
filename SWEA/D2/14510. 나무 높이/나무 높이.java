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
                // 짝수의 날짜와 홀수의 날짜를 먼저 홀수의 날짜가 최소로 가도록 계산을 한다.
                // 2를 채워야한다면 홀수의 날짜로 계산할 때보다 하루 기다렸다가 짝수의 날짜에 채우는 것이 유리하기 때문이다.
                odd += temp % 2;
                even += temp / 2;
            }
            while (even >= odd + 2) {
                // 4만큼의 높이를 채울 때를 보면 2, 4일에 물을 주는 것보다 1, 2, 3일에 물을 주는 것이 낫다. 즉, 짝수 하루로 홀수 이틀을 채워주는 것이 유리하다.
                // 하지만 1, 3일에 이미 1을 채워야하는 상황이라면 2, 4일에 4를 채울 수밖에 없으므로 짝수가 홀수보다 이틀 더 많을 때만 짝수 하루를 홀수 이틀로 변환해준다.
                even--;
                odd += 2;
            }

            int result;
            if (even > odd) {
                // 위의 반복문으로 인해서 even이 odd보다 1만큼 많은 경우밖에 남지 않았다.
                // 만약 1, 4를 채운다고 가정해보자. 그러면 odd=1, even=2
                // 1일에 1 채우고, 2일에 2, 4일에 2를 채운다. 즉 1, 2, x, 4
                // odd 하루가 비게 되므로 +1만큼 해주어야한다.
                result = even + odd + 1;
            } else if (even == odd) {
                // 만약 3 하나만 채운다고 가정해보자.
                // 1, 2
                // 1, 1, 4를 채운다고 가정해보자.
                // 1, 2, 3, 4
                // 정해진 날짜만큼 틈틈히 물을 주게 되므로 그냥 even + odd가 된다.
                result = even + odd;
            } else {
                // odd가 더 많은 경우는 1 1 1 1 씩 채워야 하는 경우를 예를 들 수 있다.
                // 그러면 1, x, 3, x, 5, x, 7 이렇게 주면 된다.
                // 만약 저기에 2만큼 채워야 하는 나무가 더 있다고 가정해보자. 1, 1, 1, 1, 2
                // 그러면 1, 2, 3, x, 5, x, 7 이렇게 주면 된다.
                // 결국 홀수를 기준으로 2 * odd - 1로 계산이 끝나게 된다.
                result = 2 * odd - 1;
                // x만큼 더 채운다는 느낌으로 (odd - even)를 더해주고 -1을 하는 방법도 된다.
                // result = odd + even + (odd - even) - 1;
            }
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}