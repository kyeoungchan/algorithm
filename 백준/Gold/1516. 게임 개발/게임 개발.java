import java.io.*;
import java.util.*;

public class Main {

    static int N;
    static int[] time, dp;
    static List<Integer>[] before;

    static int INF = 100_000 * 500 + 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        time = new int[N + 1];
        before = new List[N + 1];
        dp = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            before[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            time[i] = Integer.parseInt(st.nextToken());
            while (true) {
                int b =  Integer.parseInt(st.nextToken());
                if (b == -1) {
                    break;
                }
                before[i].add(b);
            }
            dp[i] = INF;
        }

        for (int i = 1; i < N + 1; i++) {
            if (dp[i] != INF) continue;
            dp[i] = calculate(i);
        }

        for (int i = 1; i <= N; i++) {
            sb.append(dp[i]).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    static int calculate(int num) {
        if (dp[num] != INF) return dp[num];

        int max = 0;
        for (int bNum : before[num]) {
            if (dp[bNum] == INF) {
                dp[bNum] = calculate(bNum);
            }
            max = Math.max(max, dp[bNum]);
        }
        return dp[num] = max + time[num];
    }
}