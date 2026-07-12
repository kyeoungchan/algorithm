import java.util.*;

class Solution {
    
    static class Node {
        int r, c, cost;
        
        Node(int r, int c, int cost) {
            this.r = r;
            this.c = c;
            this.cost = cost;
        }
    }
    
    int INF = 123456789;
    int[] dr = new int[] {-1, 0, 1, 0},
          dc = new int[] {0, 1, 0, -1},
          dr2 = new int[] {-1, 1, 1, -1},
          dc2 = new int[] {1, 1, -1, -1};
    
    public int solution(String numbers) {
        int answer = INF;
        int numLen = numbers.length();
        // 왼손 위치 숫자, 오른손 위치 숫자, 타겟 숫자
        int[][][] dp = new int[10][10][numLen + 1];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        
        dp[4][6][0] = 0;
        
        for (int i = 0; i < numLen; i++) {
            int target = numbers.charAt(i) - '0';
            for (int l = 0; l < 10; l++) {
                for (int r = 0; r < 10; r++) {
                    if (dp[l][r][i] == INF) continue;
                    
                    // 왼손을 움직인 경우 오른손이 target에 있으면 안된다.
                    if (r != target) {
                        int leftMove = getCost(l, target);
                        leftMove += dp[l][r][i];
                        dp[target][r][i + 1] = Math.min(dp[target][r][i + 1], leftMove);    
                        // System.out.println("dp["+target+"]["+r+"]["+(i + 1)+"]: " + dp[target][r][i + 1]);
                    }
                    
                    // 오른손을 움직인 경우 왼손이 target에 있으면 안 된다.
                    if (l != target) {
                        int rightMove = getCost(r, target);
                        rightMove += dp[l][r][i];
                        dp[l][target][i + 1] = Math.min(dp[l][target][i + 1], rightMove);
                        // System.out.println("dp["+l+"]["+target+"]["+(i+1) +"]: " + dp[l][target][i+1]);
                    }
                    // System.out.println();
                }
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                answer = Math.min(dp[i][j][numLen], answer);
            }
        }
        return answer;
    }
    
    int getCost(int n1, int n2) {
        int[] n1Pos = getPos(n1);
        int[] n2Pos = getPos(n2);
        int startR = n1Pos[0];
        int startC = n1Pos[1];
        int endR = n2Pos[0];
        int endC = n2Pos[1];
        
        if (startR == endR && startC == endC) return 1;
        int[][] visited = new int[4][3];
        for (int i = 0; i < 4; i++) Arrays.fill(visited[i], INF);
        
        visited[startR][startC] = 0;
        Deque<Node> q = new ArrayDeque<>();
        q.offer(new Node(startR, startC, 0));
        
        // int cost = INF;
        while (!q.isEmpty()) {
            Node cur = q.poll();
            
            // 상하좌우의 경우
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr >= 4 || nc < 0 || nc >= 3) continue;
                int nCost = cur.cost + 2;
                if (visited[nr][nc] <= nCost) continue;
                visited[nr][nc] = nCost;
                if (nr == endR && nc == endC) {
                    // cost = Math.min(cost, nCost);
                } else {
                    q.offer(new Node(nr, nc, nCost));
                }
            }
            
            // 대각선 이동의 경우
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr2[d];
                int nc = cur.c + dc2[d];
                if (nr < 0 || nr >= 4 || nc < 0 || nc >= 3) continue;
                int nCost = cur.cost + 3;
                if (visited[nr][nc] <= nCost) continue;
                visited[nr][nc] = nCost;
                if (nr == endR && nc == endC) {
                    // cost = Math.min(cost, nCost);
                } else {
                    q.offer(new Node(nr, nc, nCost));
                }
            }
        }
        return visited[endR][endC];
    }
    
    int[] getPos(int n) {
        if (1 <= n && n <= 9) {
            int r = (n - 1) / 3;
            int c = (n - 1) % 3;
            return new int[] {r, c};
        } else {
            return new int[] {3, 1};
        }
    }
}