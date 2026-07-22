import java.util.*;

class Solution {
    
    static class Soldier {
        int num, dist;
        
        Soldier(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }
    }
    
    List<Integer>[] graph;
    
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        for (int[] road: roads) {
            int a = road[0];
            int b = road[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        
        int[] visited = new int[n + 1];
        
        Deque<Soldier> q = new ArrayDeque<>();
        int[] answer = new int[sources.length];
        Arrays.fill(answer, -1);
        for (int i = 0; i < sources.length; i++) {
            int s = sources[i];
            if (s == destination) {
                answer[i] = 0;
                continue;
            }
            q.clear();
            visited[s] = s;
            q.offer(new Soldier(s, 0));
            over: while(!q.isEmpty()) {
                Soldier cur = q.poll();
                for (int next: graph[cur.num]) {
                    
                    if (visited[next] == s) continue;
                    int nDist = cur.dist + 1;
                    if (next == destination) {
                        answer[i] = nDist;
                        break over;
                    }
                    visited[next] = s;
                    q.offer(new Soldier(next, nDist));
                }
            }
        }
        return answer;
    }
}