import java.util.*;

class Solution {
    
    static class Tube {
        int r, c, dist;
        long talk;
        
        Tube(int r, int c, int dist, long talk) {
            this.r = r;
            this.c = c;
            this.dist = dist;
            this.talk = talk;
        }
    }
    
    public int[] solution(int m, int n, int s, int[][] time_map) {
        
        int[] dr = new int[] {-1, 0, 1, 0};
        int[] dc = new int[] {0, 1, 0, -1};
        
        int moveDistance = Integer.MAX_VALUE;
        int sumOfTalkTime = Integer.MAX_VALUE;
        
        long[][] visited = new long[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);
        
        ArrayDeque<Tube> q = new ArrayDeque<>();
        q.offer(new Tube(0, 0, 0, 0));
        visited[0][0] = 0;
        
        while (!q.isEmpty()) {
            Tube cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                
                if (nr < 0 || nr >= m || nc < 0 || nc >= n || time_map[nr][nc] == -1) continue;
                int nDist = cur.dist + 1;
                long nTalk = cur.talk + time_map[nr][nc];
                if (nTalk > s || nTalk >= visited[nr][nc]) continue;
                
                if (nr == m - 1 && nc == n - 1) {
                    if (nDist < moveDistance) {
                        moveDistance = nDist;
                        sumOfTalkTime = (int) nTalk;
                    } else if (nDist == moveDistance && nTalk < sumOfTalkTime) {
                        moveDistance = nDist;
                        sumOfTalkTime = (int) nTalk;
                    }
                    continue;
                }
                
                visited[nr][nc] = nTalk;
                q.offer(new Tube(nr, nc, nDist, nTalk));
            }
        }
        
      
        int[] answer = new int[2];
        answer[0] = moveDistance;
        answer[1] = sumOfTalkTime;
      
        return answer;
    }
}