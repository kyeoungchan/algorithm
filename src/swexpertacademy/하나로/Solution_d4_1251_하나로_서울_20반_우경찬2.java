package swexpertacademy.하나로;

import java.util.*;
import java.io.*;

/**
 * 간선의 수가 많으므로 프림으로 접근한다.
 */
public class Solution_d4_1251_하나로_서울_20반_우경찬2 {

    static int N, info[][];
    static long result;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_1251.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            info = new int[N][2];
            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    info[j][i] = Integer.parseInt(st.nextToken());
                }
            }
            double E = Double.parseDouble(br.readLine());
            primPq();
            long finalResult = Math.round(result * E);
            sb.append("#").append(tc).append(" ").append(finalResult).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static void primPq() {
        long[] minEdge = new long[N];
        Arrays.fill(minEdge, Long.MAX_VALUE);

        PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
        boolean[] v = new boolean[N];
        pq.offer(new long[]{0, 0});

        result = 0;
        int cnt = 0;
        while (!pq.isEmpty()){
            long[] cur = pq.poll();
            int minVertex = (int) cur[0];
            long min = cur[1];

            if(v[minVertex]) continue;
            v[minVertex] = true;

            result += min;
            if (++cnt == N) break;

            for (int j = 0; j < N; j++) {
                long dist = distance(minVertex, j);
                if (!v[j] && minEdge[j] > dist) {
                    minEdge[j] = dist;
                    pq.offer(new long[]{j, dist});
                }
            }
        }
    }

    private static long distance(int from, int to) {
        return (long) (Math.pow(info[from][0] - info[to][0], 2) + Math.pow(info[from][1] - info[to][1], 2));
    }
}
