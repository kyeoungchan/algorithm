import java.util.*;

class Solution {
    
    static class Road implements Comparable<Road> {
        int num, intensity;
        
        Road(int num, int intensity) {
            this.num = num;
            this.intensity = intensity;
        }
        
        @Override
        public int compareTo(Road o) {
            return Integer.compare(intensity, o.intensity);
        }
    }
    
    int INF = 123456789, n;
    int[] answer;
    Set<Integer> gateSet, summitSet;
    List<Road>[] graph;
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        this.n = n;
        gateSet = new HashSet<>();
        summitSet = new HashSet<>();
        
        for (int gate: gates) {
            gateSet.add(gate);
        }
        
        for (int summit: summits) {
            summitSet.add(summit);
        }
        
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        
        for (int[] path: paths) {
            int i = path[0];
            int j = path[1];
            int w = path[2];
            if (gateSet.contains(i)) {
                // i가 gate라면
                if (!gateSet.contains(j)) {
                    // 단방향 그래프
                    graph[i].add(new Road(j, w));
                }
            } else if (gateSet.contains(j)) {
                graph[j].add(new Road(i, w));
            } else if (summitSet.contains(i)) {
                // i가 산봉우리라면
                if (!summitSet.contains(j)) {
                    graph[j].add(new Road(i, w));
                }
            } else if (summitSet.contains(j)) {
                graph[i].add(new Road(j, w));
            } else {
                graph[i].add(new Road(j, w));
                graph[j].add(new Road(i, w));
            }
        }
        
        answer = new int[] {-1, INF};
        dijkstra();
        return answer;
    }
    
    void dijkstra() {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        PriorityQueue<Road> pq = new PriorityQueue<>();

        for (int gate: gateSet) {
            dist[gate] = 0;
            pq.offer(new Road(gate, 0));
        }
        
        while (!pq.isEmpty()) {
            Road cur = pq.poll();
            
            if (dist[cur.num] < cur.intensity) continue;
            
            for (Road next: graph[cur.num]) {
                int nIntensity = Math.max(cur.intensity, next.intensity);
                if (dist[next.num] > nIntensity) {
                    dist[next.num] = nIntensity;
                    pq.offer(new Road(next.num, nIntensity));
                }
            }
        }
        
        for (int summit: summitSet) {
            if (dist[summit] < answer[1]) {
                answer[0] = summit;
                answer[1] = dist[summit];
            } else if (dist[summit] == answer[1]) {
                answer[0] = Math.min(answer[0], summit);
            }
        }
    }
}