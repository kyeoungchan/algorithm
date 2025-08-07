import java.util.*;

/*5
* 5+5 5-5 5*5 5/5 55*/
public class Solution {
    public int solution(int N, int number) {
        if (N == number) return 1;
        Set<Integer>[] dp = new Set[9]; // 최대 8
        StringBuilder sb = new StringBuilder();
        sb.append(N);
        dp[1] = new HashSet<>();
        dp[1].add(N);
        for (int i = 2; i < 9; i++) {
            dp[i] = new HashSet<>();
            sb.append(N);
            int numElement = Integer.parseInt(sb.toString());
            if (numElement == number) return i;
            dp[i].add(numElement);
            for (int j = 1; j <= i / 2; j++) {
                for (Integer d1 : dp[j]) {
                    for (Integer d2 : dp[i - j]) {
                        int plus = d1 + d2;
                        if (plus == number) return i;
                        dp[i].add(plus);
                        int minus = d1 - d2;
                        if (minus == number) return i;
                        dp[i].add(minus);
                        int minus2 = d2 - d1;
                        if (minus2 == number) return i;
                        dp[i].add(minus2);
                        int multiply = d1 * d2;
                        if (multiply == number) return i;
                        dp[i].add(multiply);
                        if (d2 != 0) {
                            int divide = d1 / d2;
                            if (divide == number) return i;
                            dp[i].add(divide);
                        }
                        if (d1 != 0) {
                            int divide = d2 / d1;
                            if (divide == number) return i;
                            dp[i].add(divide);
                        }
                    }
                }
            }
        }
        return -1;
    }
}
