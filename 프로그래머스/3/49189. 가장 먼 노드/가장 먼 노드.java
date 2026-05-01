import java.util.*;

class Solution {
    
    static class Node implements Comparable<Node> {
        int num, dist;
        
        public Node(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }
        
        @Override
        public int compareTo(Node o) {
            return Integer.compare(dist, o.dist);
        }
    }
    
    public int solution(int n, int[][] edge) {
        int answer = 0;
        
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        List<Integer>[] graph = new List[n+1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] e: edge) {
            int a = e[0];
            int b = e[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        
        PriorityQueue<Node> pq = new PriorityQueue();
        
        pq.offer(new Node(1, 0));
        dist[1] = 0;
        
        int maxDist = 0;
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.dist > dist[cur.num]) continue;
            
            for (int nextNum: graph[cur.num]) {
                int nextDist = cur.dist + 1;
                if (dist[nextNum] <= nextDist) continue;
                dist[nextNum] = nextDist;
                pq.offer(new Node(nextNum, nextDist));
                if (maxDist < nextDist) {
                    maxDist = nextDist;
                    answer = 1;
                } else if (maxDist == nextDist) {
                    answer++;
                }
            }
        }
        
        return answer;
    }
}