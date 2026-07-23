import java.util.*;

class Solution {
    
    static class Point implements Comparable<Point> {
        int r, c, d, dist;
        
        Point(int r, int c, int d, int dist) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.dist = dist;
        }
        
        @Override
        public int compareTo(Point o) {
            return Integer.compare(dist, o.dist);
        }
    }
    
    int INF = 123456789;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    
    public int solution(int[][] board) {
        int n = board.length;
        int[][][] dist = new int[n][n][4];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dist[i][j], INF);    
            }
        }
        
        PriorityQueue<Point> pq = new PriorityQueue<>();
        pq.offer(new Point(0, 0, -1, 0));
        dist[0][0][0] = dist[0][0][1] = dist[0][0][2] = dist[0][0][3] = 0;
        
        while (!pq.isEmpty()) {
            Point cur = pq.poll();
            if (cur.d != -1 && dist[cur.r][cur.c][cur.d] < cur.dist) continue;
            
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr > n - 1 || nc < 0 || nc > n - 1 || board[nr][nc] == 1) continue;
                int plusCost = (d + cur.d) % 2 == 0 ? 100 : 600;
                if (cur.d == -1) plusCost = 100;
                int nDist = cur.dist + plusCost;
                if (dist[nr][nc][d] <= nDist) continue;
                dist[nr][nc][d] = nDist;
                pq.offer(new Point(nr, nc, d, nDist));
            }
        }
        
        int answer = INF;
        for (int i = 0; i < 4; i++) {
            answer = Math.min(answer, dist[n-1][n-1][i]);
        }
        return answer;
    }
}