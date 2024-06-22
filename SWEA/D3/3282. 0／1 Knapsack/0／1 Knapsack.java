import java.io.*;
import java.util.*;

public class Solution {

    static class Item {
        int volume;
        int cost;

        public Item(int volume, int cost) {
            this.volume = volume;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken()); // 가방의 부피
            Item[] items = new Item[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int v = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                items[i] = new Item(v, c);
            }
            int answer = 0;
            int[] dp = new int[K + 1];
            for (int i = 0; i < N; i++) {
                Item item = items[i];
                for (int j = K; j > 0; j--) {
                    if (item.volume > j) break;
                    dp[j] = Math.max(dp[j], dp[j - item.volume] + item.cost);
                    answer = Math.max(answer, dp[j]);
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}