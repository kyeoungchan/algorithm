package programmers.N으로표현;

import java.util.*;

public class Solution_pg_N으로표현2 {
    public int solution(int N, int number) {
        if (N == number) return 1;

        int answer = -1;
        List<Set<Integer>> dp = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            dp.add(new HashSet<>());
        }
        dp.get(1).add(N);

        StringBuilder sb = new StringBuilder().append(N);
        for (int i = 2; i <= 8; i++) {
            sb.append(N);
            dp.get(i).add(Integer.parseInt(sb.toString()));

            for (int j = 1; j < i; j++) {
                int k = i - j;
                for (Integer num1 : dp.get(j)) {
                    for (Integer num2 : dp.get(k)) {
                        dp.get(i).add(num1 + num2);
                        dp.get(i).add(num1 - num2);
                        dp.get(i).add(num1 * num2);
                        if (num2 != 0) dp.get(i).add(num1 / num2);
                    }
                }
            }

            if (dp.get(i).contains(number)) {
                answer = i;
                break;
            }
        }

        return answer;
    }
}
