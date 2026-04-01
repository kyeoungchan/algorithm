import java.util.*;
import java.io.*;

/**
 * 소설을 여러 장으로 나누어 쓰는데 각 장은 각각 다른 파일에 저장
 * 소설의 모든 장을 쓰고 나서는 각 장이 쓰여진 파일을 합쳐서 소설의 완성본이 들어있는 한개의 파일을 만든다.
 * 임시 파일 + 임시파일
 * 파일을 합칠 때 비용: 두 파일 크기의 합
 * -> 최종적인 한 개의 파일을 완성하는 데 필요한 비용의 총 합의 최소값
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 0; tc < T; tc++) {
            int K = Integer.parseInt(br.readLine());
            int[] files = new int[K + 1];
            int[][] dp = new int[K + 1][K + 1];

            st = new StringTokenizer(br.readLine());
            files[1] = Integer.parseInt(st.nextToken());
            for (int i = 2; i <= K; i++) {
                files[i] = Integer.parseInt(st.nextToken()) + files[i - 1];
            }

            for (int len = 1; len < K; len++) {
                for (int i = 1; i + len <= K; i++) {
                    int j = i + len;
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + files[j] - files[i - 1]);
                    }
                }
            }
            sb.append(dp[1][K]).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}