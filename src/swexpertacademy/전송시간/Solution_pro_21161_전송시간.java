package swexpertacademy.전송시간;

import java.util.*;

/**
 * 네트워크의 루트 노드 3개로 외부와 통신하는 네트워크가 있다. => 최단 전송 시간을 측정하려고 한다.
 * 네트워크는 루트 노드 3개와 최대 300개의 소규모 그룹으로 구성된다.
 * 소규모 그룹은 각각 대표 노드 3개와 최대 27개로 구성되어 있고, 대표 노드 3개는 루트노드나 다른 소규모 그룹의 대표 노드와 연결 가능하다.
 * 각각의 라인에는 전송 시간이 있다.
 * 각각의 노드는 도느 번호를 가진다.
 * 1번 소규모 그룹의 대표번호: 101, 102, 103
 * 대표노드 번호: xxx01, xxx02, xxx03
 * 1. 소규모 그룹마다 최대 한 번 연결된다.
 * 2. 임의의 노드 A와 임의의 노드 B는 최대 한 번 연결된다.
 * 3. 임의의 소규모 그룹 A와 소규모 그룹 B의 연결은 각각 대표 노드를 통해서만 한 번 연결된다.
 * 네트워크 초기화 이후 노드의 추가나 제거는 없고, 라인이 추가되거나 제거될 수는 있다.
 */
public class Solution_pro_21161_전송시간 {

    static class Node implements Comparable<Node> {
        int number;
        int cost;

        public Node(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost, o.cost);
        }

        @Override
        public boolean equals(Object o) {
            Node node = (Node) o;
            return number == node.number;
        }
    }

    private final int MAX_NUMBER = 30_030;
    private List<Node>[] graph;

    /**
     * @param N      소규모 그룹의 개수
     * @param K      라인의 개수
     * @param mNodeA 서로 같은 인덱스의 노드는 연결되어 있다.
     * @param mNodeB 각각 원소는 노드 번호를 의미하고, 1~N*100 + 30까지로 구성되어 있다.
     * @param mTime  라인의 전송시간 30~1,000 범위
     */
    public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[]) {
        graph = new List[MAX_NUMBER + 1];
        for (int i = 1; i < 4; i++) {
            // 루트 노드는 무조건 있다.
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < K; i++) {
            if (graph[mNodeA[i]] == null) graph[mNodeA[i]] = new ArrayList<>();
            graph[mNodeA[i]].add(new Node(mNodeB[i], mTime[i]));

            if (graph[mNodeB[i]] == null) graph[mNodeB[i]] = new ArrayList<>();
            graph[mNodeB[i]].add(new Node(mNodeA[i], mTime[i]));
        }
    }

    /**
     * 두 노드 사이에 mTime인 새로운 라인 추가
     * 서로 다르고, 연결되어있지 않은 상태 보장
     * 두 노드는 루트 노드가 아님을 보장
     * 호출 최대 200
     */
    public void addLine(int mNodeA, int mNodeB, int mTime) {
        if (graph[mNodeA] == null) graph[mNodeA] = new ArrayList<>();
        graph[mNodeA].add(new Node(mNodeB, mTime));
        if (graph[mNodeB] == null) graph[mNodeB] = new ArrayList<>();
        graph[mNodeB].add(new Node(mNodeA, mTime));
    }

    /**
     * 두 노드 사이에 연결된 라인 제거
     * 연결된 라인이 없을 경우 아무것도 하지 않는다.
     * 두 노드는 루트 노드가 아님을 보장
     * 호출 최대 200
     */
    public void removeLine(int mNodeA, int mNodeB) {
        if (graph[mNodeA] == null) return;
        graph[mNodeA].remove(new Node(mNodeB, -1));
        if (graph[mNodeB] == null) return;
        graph[mNodeB].remove(new Node(mNodeA, -1));
    }

    /**
     * 호출 최대 700
     */
    public int checkTime(int mNodeA, int mNodeB) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[MAX_NUMBER + 1];
        for (int i = 1; i < MAX_NUMBER + 1; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        pq.offer(new Node(mNodeA, 0));
        dist[mNodeA] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.cost > dist[cur.number]) continue;
            if (cur.number == mNodeB) break;

            for (Node next : graph[cur.number]) {
                if (dist[next.number] > cur.cost + next.cost) {
                    dist[next.number] = cur.cost + next.cost;
                    pq.offer(new Node(next.number, dist[next.number]));
                }
            }
        }
        return dist[mNodeB];
    }
}
