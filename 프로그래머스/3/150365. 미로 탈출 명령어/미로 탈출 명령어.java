import java.util.*;

class Solution {
    
    static class Node {
        int r, c;
        StringBuilder sb = new StringBuilder();
        
        Node(int r, int c, String str) {
            this.r = r;
            this.c = c;
            sb.append(str);
        }
        
        int getLength() {
            return sb.toString().length();
        }
        
    }
    
    int[] dr = new int[] {1, 0, 0, -1}, dc = new int[] {0, -1, 1, 0};
    char[] command = new char[] {'d', 'l', 'r', 'u'};
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        Deque<Node> q = new ArrayDeque<>();
        q.offer(new Node(x, y, ""));
        boolean[][][] visited = new boolean[n + 1][m + 1][k + 1];
        visited[x][y][0] = true;
        
        String answer = null;
        while (!q.isEmpty()) {
            Node cur = q.poll();
            
            if (!canGo(cur, r, c, k)) continue;
            
            if (cur.getLength() == k) {
                if (cur.r == r && cur.c == c) {
                    answer = cur.sb.toString();
                    break;
                }
                continue;
            }
            
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                int nCnt = cur.getLength() + 1;
                if (nr < 1 || nr > n || nc < 1 || nc > m || visited[nr][nc][nCnt]) continue;
                visited[nr][nc][nCnt] = true;
                Node nNode = new Node(nr, nc, cur.sb.toString());
                nNode.sb.append(command[d]);
                q.offer(nNode);
            }
        }
        
        return answer == null ? "impossible" : answer;
    }
    
    boolean canGo(Node cur, int r, int c, int k) {
        int dist = Math.abs(cur.r - r) + Math.abs(cur.c - c);
        return k - cur.getLength() >= dist;
    }
}