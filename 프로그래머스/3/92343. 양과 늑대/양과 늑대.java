import java.util.*;

class Solution {
    
    int answer;
    int[] info;
    List<Integer>[] graph;
    boolean[][] visited;
    
    public int solution(int[] info, int[][] edges) {
        this.info = info;
        int n = info.length;
        graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        
        for (int[] edge: edges) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        
        answer = 0;
        int sheepCnt = 0;
        for (int i: info) {
            if (i == 0) sheepCnt++;
        }
        visited = new boolean[n][sheepCnt+1];
        visited[0][1] = true;
        // -1은 이미 양이든 늑대든 사람을 따라가고 있다는 표시
        info[0] = -1;
        go(0, 1, 0);
        return answer;
    }
    
    void go(int node, int sheepCnt, int wolfCnt) {
        // System.out.println("node: " + node + ", sheepCnt: " + sheepCnt + ", wolfCnt: " + wolfCnt);
        // System.out.println();
        boolean canMove = false;
        for (int next: graph[node]) {
            int nSheepCnt = sheepCnt;
            int nWolfCnt = wolfCnt;
            if (info[next] == 0) {
                nSheepCnt++;
                if (visited[next][nSheepCnt]) continue;
                
                info[next] = -1;
                visited[next][nSheepCnt] = true;
                go(next, nSheepCnt, nWolfCnt);
                visited[next][nSheepCnt] = false;
                info[next] = 0;
            } else if (info[next] == 1) {
                if (visited[next][nSheepCnt]) continue;
                nWolfCnt++;
                if (nSheepCnt <= nWolfCnt) {
                    continue;
                }
                info[next] = -1;
                visited[next][nSheepCnt] = true;
                go(next, nSheepCnt, nWolfCnt);
                visited[next][nSheepCnt] = false;
                info[next] = 1;
            } else {
                if (visited[next][nSheepCnt]) continue;
                visited[next][nSheepCnt] = true;
                go(next, nSheepCnt, nWolfCnt);
                visited[next][nSheepCnt] = false;
            }
            canMove = true;
        }
        if (!canMove) {
            answer = Math.max(answer, sheepCnt);
        }
    }
}