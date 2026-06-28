import java.util.*;

class Solution {
    
    static class Soldier {
        int position, cost;
        
        Soldier(int position, int cost) {
            this.position = position;
            this.cost = cost;
        }
    }
    
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        
        List<Integer>[] roadList = new List[n+1];
        for (int i = 1; i <= n; i++) roadList[i] = new ArrayList<>();
        
        for (int[] road: roads) {
            int a = road[0];
            int b = road[1];
            roadList[a].add(b);
            roadList[b].add(a);
        }
        
        int[] answer = new int[sources.length];
        int[] visited = new int[n+1];
        
        ArrayDeque<Soldier> q = new ArrayDeque<>();
        for (int i = 0; i < sources.length; i++) {
            int s = sources[i];
            if (s == destination) continue;
            
            visited[s] = s;
            q.clear();
            q.offer(new Soldier(s, 0));
            
            flag: while (!q.isEmpty()) {
                Soldier cur = q.poll();
                int nextCost = cur.cost + 1;
                for (int next: roadList[cur.position]) {
                    if (visited[next] == s) continue;
                    if (next == destination) {
                        answer[i] = nextCost;
                        break flag;
                    }
                    visited[next] = s;
                    q.offer(new Soldier(next, nextCost));
                }
            }
            if (answer[i] == 0) answer[i]--;
        }
        
        return answer;
    }
}