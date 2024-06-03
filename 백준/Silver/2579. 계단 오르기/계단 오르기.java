import java.io.*;
import java.util.*;

public class Main {

    static int[] memo, stairScores;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int stairCnt = Integer.parseInt(br.readLine());
        memo = new int[stairCnt + 1];
        stairScores = new int[stairCnt + 1];
        for (int i = 1; i < stairCnt + 1; i++) {
            stairScores[i] = Integer.parseInt(br.readLine());
            memo[i] = -1;
        }

        memo[1] = stairScores[1];
        if (stairCnt > 1)
            memo[2] = stairScores[1] + stairScores[2];
        System.out.println(dp(stairCnt));
        br.close();
    }

    static int dp(int cnt) {
        if (memo[cnt] != -1) return memo[cnt];
        // 3단계 전까지 올라온 최대 점수 + 한 단계 전의 개별 점수와 2단계 전까지 올라온 최대점수 중 최댓값과 현재의 점수를 더한값을 메모한다.
        // 한 단계 전의 최대점수(memo[cnt - 1])을 사용하지 않는 이유는 한 단계 전은 연속으로 온 상황인지, 아닌지 여부에 따라서 점수를 사용할 수도 못 할수도 있기 때문이다.
        // 하지만 3단계 전에서 1단계 전으로 두칸 움직인 경우에는 3계단 연속 규칙으로부터 자유롭다.
        // 마찬가지로 2단계 전도 3연속 규칙으로부터 자유롭다.
        memo[cnt] = Math.max(dp(cnt - 3) + stairScores[cnt - 1], dp(cnt - 2)) + stairScores[cnt];
        return memo[cnt];
    }
}