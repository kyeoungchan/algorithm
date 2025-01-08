import java.io.*;
import java.util.*;

/**
 * 매일 물공간과 접촉한 빙판 공간은 녹는다. 대각선은 고려하지 않는다.
 * 백조는 오직 물 공간에서 상하좌우로 움직일 수 있다.
 * 며칠이 지나야 백조들이 만날 수 있는지
 */
public class Main {

    static class Node {
        int r, c;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static char[][] map;
    static int R, C;
    static int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static boolean[][] visited;
    static ArrayDeque<Node> q;
    static ArrayDeque<Node> waterQ;
    static Node[] swans;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];
        visited = new boolean[R][C];
        swans = new Node[2];
        q = new ArrayDeque<>();
        waterQ = new ArrayDeque<>();

        int swanIdx = 0;
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                char c = s.charAt(j);
                if (c == 'L') {
                    swans[swanIdx++] = new Node(i, j);
                }
                if (c != 'X') {
                    waterQ.offer(new Node(i, j));
                }
                map[i][j] = c;
            }
        }

        q.offer(swans[0]);
        visited[swans[0].r][swans[0].c] = true;

        int day = 0;
        boolean meet = false;


        while (true) {
            ArrayDeque<Node> nextQ = new ArrayDeque<>();
            while (!q.isEmpty()) {
                Node cur = q.poll();

                // 백조를 만날 시에 종료
                if (cur.r == swans[1].r && cur.c == swans[1].c) {
                    meet = true;
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];

                    if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1 || visited[nr][nc]) continue;
                    visited[nr][nc] = true;

                    // 물에 인접한 얼음으로 다음날 백조가 탐색할 지역
                    if (map[nr][nc] == 'X') {
                        nextQ.offer(new Node(nr, nc));
                        continue;
                    }
                    // 현재 탐색 가능 지역
                    q.offer(new Node(nr, nc));
                }
            }

            // 백조가 만났으면 종료
            if (meet) break;
            // q를 다음날 탐색할 지역이 담긴 nextQ로 바꾼다.
            q = nextQ;

            // 얼음을 녹인다.
            int size = waterQ.size();
            for (int i = 0; i < size; i++) {
                Node cur = waterQ.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];

                    if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1) continue;

                    // 물에 인접한 얼음을 발견하면 녹이고 다시 큐에 넣는다.
                    if (map[nr][nc] == 'X') {
                        map[nr][nc] = '.';
                        waterQ.offer(new Node(nr, nc));
                    }
                }
            }
            day++;
        }
        System.out.println(day);
        br.close();
    }
}