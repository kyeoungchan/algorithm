package swexpertacademy.하나로;

import java.util.*;
import java.io.*;

public class Solution_d4_1251_하나로_서울_20반_우경찬 {

    static int[][] graph;
    static double E;

    static int findParent(int x, int[] parents) {
        if (parents[x] != x) {
            parents[x] = findParent(parents[x], parents);
        }
        return parents[x];
    }

    static void unionParent(int a, int b, int[] parents) {
        a = findParent(a, parents);
        b = findParent(b, parents);

        if (a < b) {
            parents[b] = a;
        } else {
            parents[a] = b;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_1251.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            graph = new int[N + 1][2];
            for (int j = 0; j < 2; j++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int i = 1; i < N + 1; i++) {
                    graph[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            E = Double.parseDouble(br.readLine());

            int[] parents = new int[N + 1];
            for (int i = 1; i < N + 1; i++) {
                parents[i] = i;
            }

            // PriorityQueue를 사용해야한다. 그래야 모든 섬들에 대해서 가장 비용이 적게 드는 다른 섬과의 연결이 가능해지기 때문이다.
            // 그전에 나는 1번 섬과 연결될 수 있는 다른 섬들 중에서 가장 비용이 작은 섬이면 연결을 시켰지만, 그렇게 하면 안 되는 이유가 예를 들어 1번 섬과 3번 섬이 연결이 되었다고 치자. 그런데 1번 섬과 2번 섬을 나중에 연결시키고 보니 2번 섬과 3번 섬이 연결되는 것이 더 유리해지는 경우를 계산하지 못하기 때문이다.
            PriorityQueue<Bridge> q = new PriorityQueue<>(Comparator.comparing(Bridge::getCost));
            double totalCost = 0.0;
            for (int i = 1; i < N; i++) {
                for (int j = i + 1; j < N + 1; j++) {
                    q.offer(new Bridge(i, j, getCost(i, j)));
                }
            }

            while (!q.isEmpty()) {
                Bridge bridge = q.poll();
                if (findParent(bridge.getIsland1(), parents) != findParent(bridge.getIsland2(), parents)) {
                    unionParent(bridge.getIsland1(), bridge.getIsland2(), parents);
                    totalCost += bridge.getCost();
                }
            }
            sb.append("#").append(tc).append(" ").append(Math.round(totalCost)).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static double getCost(int i, int j) {
        long si = graph[i][0];
        long sj = graph[i][1];
        long ei = graph[j][0];
        long ej = graph[j][1];
        return (Math.pow((si - ei), 2) + Math.pow((sj - ej), 2)) * E;
    }
}

class Bridge {
    // 비용에 관한 데이터는 int형으로 적용할 수 없으므로 클래스 생성
    private int island1;
    private int island2;
    private double cost;

    public Bridge(int island1, int island2, double cost) {
        this.island1 = island1;
        this.island2 = island2;
        this.cost = cost;
    }

    public int getIsland1() {
        return island1;
    }

    public int getIsland2() {
        return island2;
    }

    public double getCost() {
        return cost;
    }
}
