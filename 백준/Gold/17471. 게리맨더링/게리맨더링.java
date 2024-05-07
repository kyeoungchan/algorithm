import java.util.*;
import java.io.*;

/**
 * 백준시는 N개의 구역. 1~N번까지 번호가 매겨져있다.
 * 각 구역은 두 선거구 중 하나에 포함된다.
 * 선거구는 적어도 하나 포함해야한다.
 * 같은 선거구는 연결되어야한다.
 * 두 선거구에 포함된 인구의 차이를 최소로 할 때 최솟값을 구해보자.
 */
public class Main {

    static int N, min;
    static int[] populations;
    static int[][] g;
    static boolean[] isA;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        populations = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i < N + 1; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
        }
        g = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++) {
                int to = Integer.parseInt(st.nextToken());
                g[i][to] = 1;
            }
        }


        min = Integer.MAX_VALUE;
        isA = new boolean[N + 1];
        subSet(1, 0);
        if (min == Integer.MAX_VALUE) min = -1;
        System.out.println(min);
        br.close();
    }

    static void subSet(int cnt, int aCnt) {
        if (cnt == N + 1) {
            if (aCnt == 0 || aCnt == N) return;
            // a와 b가 연결되었는지 확인
            bfs();
            return;
        }

        isA[cnt] = true;
        subSet(cnt + 1, aCnt + 1);
        isA[cnt] = false;
        subSet(cnt + 1, aCnt);
    }

    static void bfs() {
        boolean[] v = new boolean[N + 1];
        int cnt = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int aCnt = 0;
        int bCnt = 0;
        for (int i = 1; i < N + 1; i++) {
            // bfs를 두 번만에 모두 다 도는지 확인해보고 cnt가 3이상이 뜨면 두 번만에 다 못 돈다는 뜻이다.
            if (v[i]) continue;
            v[i] = true;
            cnt++;
            q.offer(i);
            while (!q.isEmpty()) {
                int cur = q.poll();
                if (isA[cur]) aCnt += populations[cur];
                else bCnt += populations[cur];
                for (int j = 1; j < N + 1; j++) {
                    // XOR연산 => true와 false가 만나면 true
                    if (g[cur][j] == 0 || v[j] || isA[cur]^isA[j]) continue;
                    v[j] = true;
                    q.offer(j);
                }
            }
            if (cnt > 2) return;
        }

        min = Math.min(min, Math.abs(aCnt-bCnt));
    }
}