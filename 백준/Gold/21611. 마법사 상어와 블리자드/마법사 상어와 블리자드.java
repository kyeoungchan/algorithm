import java.util.*;
import java.io.*;

public class Main {

    static int N, beads[], grid[][], bombCnt[], di[] = {-1, 1 , 0, 0}, dj[] = {0, 0, -1, 1}, si, sj, endIdx;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        si = N / 2;
        sj = N / 2;
        setGrid();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                int beadNum = Integer.parseInt(st.nextToken());
                beads[grid[i][j]] = beadNum;
                if (beadNum != 0 && grid[i][j] > endIdx) endIdx = grid[i][j];
            }
        }
        bombCnt = new int[4];
        for (int time = 0; time < M; time++) {
            st = new StringTokenizer(br.readLine(), " ");
            int d = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());
            spell(d, s);
            do {
                moveBeads();
            } while (bomb());
            changeBeads();
        }

        int answer = 0;
        for (int i = 1; i < 4; i++) {
            answer += bombCnt[i] * i;
        }
        System.out.println(answer);
        br.close();
    }

    static void changeBeads() {
        if (endIdx == 0) return;
        int[] newBeads = new int[N * N];
        int newEndIdx = 0;
        int cnt = 1;
        for (int number = 1; number < endIdx; number++) {
            if (beads[number] == beads[number + 1]) {
                cnt++;
            } else {
                newBeads[++newEndIdx] = cnt;
                if (newEndIdx == N * N - 1) break;
                newBeads[++newEndIdx] = beads[number];
                if (newEndIdx == N * N - 1) break;
                cnt = 1;
            }
        }
        if (newEndIdx < N * N - 1) {
            newBeads[++newEndIdx] = cnt;
            if (newEndIdx < N * N - 1) {
                newBeads[++newEndIdx] = beads[endIdx];
            }
        }
        beads = newBeads;
        endIdx = newEndIdx;
    }

    static void moveBeads() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int number = 1; number < endIdx + 1; number++) {
            if (beads[number] == 0) {
                q.offer(number);
            } else if (!q.isEmpty()) {
                int emptySpace = q.poll();
                beads[emptySpace] = beads[number];
                beads[number] = 0;
                q.offer(number);
            }
        }
        if (!q.isEmpty()) {
            int emptySpace = q.poll();
            endIdx = emptySpace - 1;
        }
    }

    static boolean bomb() {
        if (endIdx == 0) return false;
        boolean hasBombed = false;
        int cnt = 1;
        for (int number = 1; number < endIdx; number++) {
            if (beads[number] == beads[number + 1]) {
                cnt++;
            } else {
                if (cnt > 3) {
                    hasBombed = true;
                    bombCnt[beads[number]] += cnt;
                    for (int i = 0; i < cnt; i++) {
                        beads[number - i] = 0;
                    }
                }
                cnt = 1;
            }
        }
        if (cnt > 3) {
            hasBombed = true;
            bombCnt[beads[endIdx]] += cnt;
            for (int i = 0; i < cnt; i++) {
                beads[endIdx - i] = 0;
            }
        }

        return hasBombed;
    }

    static void spell(int d, int s) {
        for (int k = 1; k < s + 1; k++) {
            int ni = si + di[d] * k;
            int nj = sj + dj[d] * k;
            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1) return;
            beads[grid[ni][nj]] = 0;
        }
    }

    static void setGrid() {
        beads = new int[N * N];
        grid = new int[N][N];
        int[] di = {-1, 0, 1, 0}, dj = {0, -1, 0, 1};
        boolean[][] v = new boolean[N][N];
        int curD = 0;
        int i = si;
        int j = sj;
        for (int number = 1; number < N * N; number++) {
            v[i][j] = true;

            int nexD = (curD + 1) % 4;
            int viewI = i + di[nexD];
            int viewJ = j + dj[nexD];
            if (!v[viewI][viewJ]) curD = nexD;
            i += di[curD];
            j += dj[curD];
            grid[i][j] = number;
        }
    }
}