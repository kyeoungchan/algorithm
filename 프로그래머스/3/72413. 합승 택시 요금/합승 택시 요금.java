import java.util.*;

class Solution {
    
    static class City implements Comparable<City> {
        int num, dist;
        
        public City(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }
        
        @Override
        public int compareTo(City o) {
            return Integer.compare(dist, o.dist);
        }
    }
    
    
    List<City>[] graph;
    int[][] dist;
        
    public int solution(int n, int s, int a, int b, int[][] fares) {
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        
        int answer = Integer.MAX_VALUE;
        
        dist = new int[3][n + 1];
        for (int i = 0; i < 3; i++) Arrays.fill(dist[i], answer);
        
        for (int[] fare: fares) {
            int c = fare[0];
            int d = fare[1];
            int f = fare[2];
            graph[c].add(new City(d, f));
            graph[d].add(new City(c, f));
        }
        
        dijkstra(0, s);
        dijkstra(1, a);
        dijkstra(2, b);
        
        for (int i = 1; i <= n; i++) {
            
            int hap = dist[0][i];
            answer = Math.min(answer, hap + dist[1][i] + dist[2][i]);      
        }
        
        return answer;
    }
    
    private void dijkstra(int idx, int start) {
        dist[idx][start] = 0;
        
        PriorityQueue<City> pq = new PriorityQueue<>();
        pq.offer(new City(start, 0));
        
        while(!pq.isEmpty()) {
            City cur = pq.poll();
            if (cur.dist > dist[idx][cur.num]) continue;
            
            for (City next: graph[cur.num]) {
                int nextDist = cur.dist + next.dist;
                if (nextDist >= dist[idx][next.num]) continue;
                dist[idx][next.num] = nextDist;
                pq.offer(new City(next.num, nextDist));
            }
        }
    }
}