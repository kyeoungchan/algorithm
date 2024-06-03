import java.util.*;
import java.io.*;

/**
 * 1~n번호가 매겨져있다.
 * 한 도시에서 출발해 n개의 도시를 거쳐 다시 원래의 도시로 돌아온다.
 * 가장 적은 비용을 들이려고 한다.
 */
public class Main {
    static final int INF = 987654321;
    static int N;
    static int[][] W, costs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        W = new int[N][N];
        costs = new int[N][1 << N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(costs[i], -1);
        }

        System.out.println(travel(0, 1));
        br.close();
    }

    static int travel(int idx, int status) {
        if (status == (1 << N) - 1) {
            if (W[idx][0] == 0) return INF;
            return W[idx][0];
        }

        if (costs[idx][status] != -1) return costs[idx][status];
        costs[idx][status] = INF;

        for (int i = 0; i < N; i++) {
            if (W[idx][i] == 0 || (status & (1 << i)) > 0) continue;
            costs[idx][status] = Math.min(travel(i, status | (1 << i)) + W[idx][i], costs[idx][status]);
        }
        return costs[idx][status];
    }
}