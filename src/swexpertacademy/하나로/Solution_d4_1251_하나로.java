package swexpertacademy.하나로;

import java.util.*;
import java.io.*;

public class Solution_d4_1251_하나로 {

    static class Island implements Comparable<Island> {
        int number;
        double cost;

        public Island(int number, double cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Island o) {
            return Double.compare(this.cost, o.cost);
        }
    }

    static double E;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_1251.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            double[] minEdge = new double[N];
            Arrays.fill(minEdge, Double.MAX_VALUE);
            int[][] islands = new int[N][2];

            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    islands[j][i] = Integer.parseInt(st.nextToken());
                }
            }
            E = Double.parseDouble(br.readLine());
            double result = 0.0;
            PriorityQueue<Island> pq = new PriorityQueue<>();
            minEdge[0] = 0;
            pq.offer(new Island(0, 0));

            while (!pq.isEmpty()) {
                Island cur = pq.poll();
                int minVertex = cur.number;
                double minCost = cur.cost;
                if (minEdge[minVertex] < minCost) continue;
                result += minCost;

                for (int i = 0; i < N; i++) {
                    double cost = getCost(islands[minVertex][0], islands[minVertex][1], islands[i][0], islands[i][1]);
                    if (minEdge[i] > cost) {
                        minEdge[i] = cost;
                        pq.offer(new Island(i, minEdge[i]));
                    }
                }
            }
            long realResult = Math.round(result);
            sb.append("#").append(tc).append(" ").append(realResult).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static double getCost(int r, int c, int r2, int c2) {
        return E * (Math.pow(r - r2, 2) + Math.pow(c - c2, 2));
    }
}
