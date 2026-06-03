package mincoding.락다운시티;

import java.util.*;

class UserSolution {

    static class Node {

        int r, c, dist;

        Node(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    int N, M, visitedTime;
    int[] dr = new int[] {-1, 0, 1, 0} , dc = new int[]{0, 1, 0, -1};
    List<Integer> certifications;
    Set<Integer> set;
    int[][] grid;
    int[][] visited;
    ArrayDeque<Node> q;
    StringBuilder sb;

    int hash(String grade) {
        int result = 0;
        int len = grade.length();
        for (int i = 0; i < M; i++) {
            if (i >= len) result *= 27;
            else result = result * 27 + (grade.charAt(i) - 'A' + 1);
        }
        return result;
    }

    String unhash(int val) {
        sb.setLength(0);
        while (val > 0) {
            int mod = val % 27;
            if (mod > 0) sb.append((char) ('A' + mod - 1));
            val /= 27;
        }
        return sb.reverse().toString();
    }

    void init(int N, int M, String mGrade[][]) {
        sb = new StringBuilder();
        this.N = N;
        this.M = M;
//        this.grid = mGrade;
        grid = new int[N][N];
        visitedTime = 0;
        set = new HashSet<>();
        visited = new int[N][N];
        q = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
//                set.add(mGrade[i][j]);
                int hashed = hash(mGrade[i][j]);
                set.add(hashed);
                grid[i][j] = hashed;
            }
        }
        certifications = new ArrayList<>(set);
        Collections.sort(certifications);
    }

    void change(int mRow, int mCol, int mDir, int mLength, String mGrade) {
        int curR = mRow;
        int curC = mCol;
        int d = mDir == 0 ? 2 : 1;
        int hashed = hash(mGrade);
        for (int i = 0; i < mLength; i++) {
            grid[curR][curC] = hashed;
            curR += dr[d];
            curC += dc[d];
        }
        if (!set.contains(hashed)){
            set.add(hashed);
            // 정렬된 위치에 바로 삽입 → O(log K) 탐색 + O(K) 삽입
            int pos = Collections.binarySearch(certifications, hashed);
            if (pos < 0) certifications.add(-pos - 1, hashed);
        }
    }

    String calculate(int L, int sRow, int sCol, int eRow, int eCol) {
        int left = 0;
        int right = certifications.size() - 1;
        int result = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int target = certifications.get(mid);

            boolean canGo;
            if (grid[eRow][eCol] < target) {
                canGo = false;
            } else {
                canGo = travel(target, L, sRow, sCol, eRow, eCol);
            }

            if (canGo) {
                left = mid + 1;
                result = certifications.get(mid);
            } else {
                right = mid - 1;
            }
        }

        return unhash(result);
    }

    boolean travel(int certification, int L, int sr, int sc, int er, int ec) {
        visitedTime++;
        q.clear();

        q.offer(new Node(sr, sc, 0));
        visited[sr][sc] = visitedTime;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (grid[nr][nc] < certification) continue;
                int nDist = cur.dist + 1;
                if (visited[nr][nc] == visitedTime) continue;
                if (nDist > L) continue;
                if (nr == er && nc == ec) return true;
                visited[nr][nc] = visitedTime;
                q.offer(new Node(nr, nc, nDist));
            }
        }
        return false;
    }
}