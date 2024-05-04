package swexpertacademy.점심식사시간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution_d9_2383_점심식사시간_다른사람꺼2 {
    static int N, M, answer;
    static int[][] building;
    static int[] match;
    static ArrayList<PT> pl;
    static PT[] stair;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int z = 1; z <= T; z++) {
            answer = Integer.MAX_VALUE;
            N = Integer.parseInt(br.readLine());
            pl = new ArrayList<PT>(); // 사람정보
            stair = new PT[2]; // 계단정보
            building = new int[N][N];
            int idx = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    building[i][j] = Integer.parseInt(st.nextToken());
                    if (building[i][j] != 0) {
                        if (building[i][j] == 1) pl.add(new PT(i, j));
                        else stair[idx++] = new PT(i, j);
                    }
                }
            }
            M = pl.size();
            match = new int[M];
            dfs(0);
            System.out.println("#" + z + " " + answer);
        }
        br.close();
    }

    static int dit(int m, int s) {
        int dx = Math.abs(stair[s].x - pl.get(m).x);
        int dy = Math.abs(stair[s].y - pl.get(m).y);
        return dx + dy;
    }

    static void cal() {
        int totalTime = 0; // 모든 사람들이 내려가는 데 필요한 최소시간
        for (int sidx = 0; sidx < 2; sidx++) {
            PT temp = stair[sidx];
            int[] pq = new int[2 * N + 2];
            int[] pq2 = new int[2 * N + N * N]; // 시간 t일때 계단 내려가는 사람들
            int pe = Integer.MAX_VALUE;
            for (int midx = 0; midx < M; midx++) {
                if (match[midx] == sidx) {
                    int te = dit(midx, sidx) + 1;
                    pe = Math.min(pe, te);
                    pq[te]++; // 계단 도착뒤 1분후 아래로 내려갈 수 있음
                }
            }
            if (pe == Integer.MAX_VALUE) continue; // null case 제거
            int nowTime = pe; // sidx 계단을 내려가는 사람이 모두 작업을 마치는 데 필요한 최소시간
            int stairtime = building[temp.x][temp.y]; // 계단 내려가는 데 걸리는 시간
            for (int i = 1; i < 2 * N + 2; i++) {
                while (pq[i] > 0) {
                    pq[i]--;
                    int remaintime = stairtime;
                    for (int j = i; j < pq2.length; j++) { // 시간 흘려보내기 (1초씩)
                        if (pq2[j] < 3) {
                            pq2[j]++;
                            remaintime--;
                        }
                        if (remaintime == 0) {
                            nowTime = Math.max(nowTime, j + 1);
                            break;
                        }
                    }
                }
            }
            totalTime = Math.max(totalTime, nowTime); // 두계단 중 최대 시간
        }
        answer = Math.min(answer, totalTime);
    }

    static void dfs(int idx) {
        if (idx == M) {
            cal();
            return;
        }
        for (int sidx = 0; sidx < 2; sidx++) {
            match[idx] = sidx; // 계단 선택
            dfs(idx + 1); // 시계문제와 유사, 재귀에 익숙해지기
        }
    }

    static class PT {
        int x;
        int y;

        public PT(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
