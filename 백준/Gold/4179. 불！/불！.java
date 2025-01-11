import java.io.*;
import java.util.*;

public class Main {

    static int R, C;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];
        int jR = 0, jC = 0;
        ArrayDeque<int[]> fireQ = new ArrayDeque<>();
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'J') {
                    jR = i;
                    jC = j;
                } else if (map[i][j] == 'F') {
                    fireQ.offer(new int[]{i, j});
                }
            }
        }

        ArrayDeque<int[]> jihunQ = new ArrayDeque<>();
        boolean[][] visited = new boolean[R][C];
        visited[jR][jC] = true;
        jihunQ.offer(new int[]{jR, jC});

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
        int time = 0;
        boolean escaped = false;
        end: while (true) {
            int fireRage = fireQ.size();
            time++;
            for (int i = 0; i < fireRage; i++) {
                int[] cur = fireQ.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1 || map[nr][nc] == '#' || map[nr][nc] == 'F')
                        continue;
                    map[nr][nc] = 'F';
                    fireQ.offer(new int[]{nr, nc});
                }
            }
/*
            System.out.println("time = " + time);
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
*/
            int jihunRage = jihunQ.size();
            for (int i = 0; i < jihunRage; i++) {
                int[] cur = jihunQ.poll();
                if (hasEscaped(cur)) {
                    escaped = true;
                    break end;
                }
                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1 || map[nr][nc] == '#' || map[nr][nc] == 'F' || visited[nr][nc]) continue;
                    visited[nr][nc] = true;
                    jihunQ.offer(new int[] {nr, nc});
                }
            }
            if (jihunQ.isEmpty()) break;
        }
        if (escaped) System.out.println(time);
        else System.out.println("IMPOSSIBLE");
        br.close();
    }

    static boolean hasEscaped(int[] cur) {
        return cur[0] == 0 || cur[0] == R - 1 || cur[1] == 0 || cur[1] == C - 1;
    }

}