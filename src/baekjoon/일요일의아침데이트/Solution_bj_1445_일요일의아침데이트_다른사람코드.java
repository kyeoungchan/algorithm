package baekjoon.일요일의아침데이트;

import java.io.*;
import java.util.*;

public class Solution_bj_1445_일요일의아침데이트_다른사람코드 {
    static byte N, M;
    static char[][] map;
    static boolean[][] visit;

    static class Node implements Comparable<Node> {
        byte x, y;
        int cnt1, cnt2;

        public Node(byte x, byte y, int cnt1, int cnt2) {
            // TODO Auto-generated constructor stub
            this.x = x;
            this.y = y;
            this.cnt1 = cnt1;
            this.cnt2 = cnt2;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            if (this.cnt1 == o.cnt1)
                return this.cnt2 - o.cnt2;
            return this.cnt1 - o.cnt1;
        }

        @Override
        public String toString() {
            return "[ " + this.x + " , " + this.y + " , " + this.cnt1 + " , " + this.cnt2 + " ] ";
        }
    }

    static byte dxy[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Byte.parseByte(st.nextToken());
        M = Byte.parseByte(st.nextToken());

        map = new char[N][M];
        visit = new boolean[N][M];
        boolean[][] choice = new boolean[N][M];
        byte[] start = new byte[2];
        for (byte i = 0; i < N; ++i) {
            map[i] = br.readLine().toCharArray();
            for (byte j = 0; j < M; ++j) {
                if (map[i][j] == 'g') {
                    for (byte d = 0; d < 4; ++d) {
                        byte nx = (byte) (i + dxy[d][0]);
                        byte ny = (byte) (j + dxy[d][1]);

                        if (!mapChk(nx, ny))
                            continue;
                        choice[nx][ny] = true;
                    }
                } else if (map[i][j] == 'S')
                    start = new byte[]{i, j};
            }
        }

        PriorityQueue<Node> q = new PriorityQueue<>();
        visit[start[0]][start[1]] = true;
        q.add(new Node(start[0], start[1], 0, 0));
        Node answer = null;
        int cnt1 = 0;
        int cnt2 = 0;
        while (!q.isEmpty()) {
            Node now = q.poll();
            if (map[now.x][now.y] == 'F') {
                answer = now;
                break;
            }

            for (byte d = 0; d < 4; ++d) {
                byte nx = (byte) (now.x + dxy[d][0]);
                byte ny = (byte) (now.y + dxy[d][1]);

                if (!mapChk(nx, ny) || visit[nx][ny])
                    continue;

                visit[nx][ny] = true;
                cnt1 = now.cnt1;
                cnt2 = now.cnt2;
                if (map[nx][ny] == 'g')
                    cnt1++;
                else if (map[nx][ny] == '.' && choice[nx][ny])
                    cnt2++;
                q.add(new Node(nx, ny, cnt1, cnt2));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(answer.cnt1).append(" ").append(answer.cnt2);
        System.out.println(sb);
    }

    static boolean mapChk(byte x, byte y) {
        if (x < 0 || y < 0 || x >= N || y >= M)
            return false;
        return true;
    }
}
