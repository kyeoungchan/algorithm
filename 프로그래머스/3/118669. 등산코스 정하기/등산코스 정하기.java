import java.util.*;

class Solution {
    
    static class Path implements Comparable<Path> {
        int num, cost;
        
        Path(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Path o) {
            return Integer.compare(cost, o.cost);
        }
    }
    
    int n, INF = 123456789;
    int[] answer;
    List<Path>[] graph;
    Set<Integer> gateSet, summitSet;
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        gateSet = new HashSet<>();
        for (int gate: gates) {
            gateSet.add(gate);
        }
        
        summitSet = new HashSet<>();
        for (int summit: summits) {
            summitSet.add(summit);
        }
        
        this.n = n;
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        
        for (int[] path: paths) {
            int s = path[0];
            int e = path[1];
            int w = path[2];
            
            if (gateSet.contains(s)) {
                // s가 출입구
                // s->e 단방향
                if (!gateSet.contains(e)) {
                    graph[s].add(new Path(e, w));
                }
            } else if (gateSet.contains(e)) {
                // e가 출입구
                // e->s 단방향
                graph[e].add(new Path(s, w));
            } else if (summitSet.contains(s)) {
                // s가 산봉우리
                if (!summitSet.contains(e)) {
                    // e->s 단방향
                    graph[e].add(new Path(s, w));
                }
            } else if (summitSet.contains(e)) {
                // e가 산봉우리
                graph[s].add(new Path(e, w));
            } else {
                // 그 외 코스
                graph[s].add(new Path(e, w));
                graph[e].add(new Path(s, w));
            }
        }
        
        
        answer = new int[] {-1, INF};
        
        dijkstra();
        
        return answer;
    }
    
    void dijkstra() {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        
        PriorityQueue<Path> pq = new PriorityQueue<>();
        for (int gate: gateSet) {
            dist[gate] = 0;
            pq.offer(new Path(gate, 0));    
        }
        
        
        while (!pq.isEmpty()) {
            Path cur = pq.poll();
            if (dist[cur.num] < cur.cost) continue;
            
            for (Path next: graph[cur.num]) {
                int nCost = Math.max(cur.cost, next.cost);
                if (dist[next.num] <= nCost) continue;
                dist[next.num] = nCost;
                pq.offer(new Path(next.num, nCost));
            }
        }
        
        for (int summit: summitSet) {
            if (dist[summit] < answer[1]) {
                answer[0] = summit;
                answer[1] = dist[summit];
            } else if (dist[summit] == answer[1]) {
                if (summit < answer[0]) {
                    answer[0] = summit;
                    answer[1] = dist[summit];
                }
            }
        }
    }
}