package swexpertacademy.프로세서연결하기;

import java.util.*;
import java.io.*;

public class Solution_d9_1767_프로세서연결하기_강의설명 {

    static int N, max, totalCnt, min, map[][]; // 멕시노스 크기, 최대 코어 수, 비가장자리코어 수, 최소전선길이합, 멕시노스셀정보
    static int[] di = {-1, 1, 0, 0}, dj = {0, 0, -1, 1};
    static List<int[]> list; // 비가장자리 코어 리스트

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_1767.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            list = new ArrayList<>();
            max = 0;
            totalCnt = 0;
            min = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    // 비가장자리 코어 리스트에 담기
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (i > 0 && i < N - 1 && j > 0 && j < N - 1 && map[i][j] == 1) {
                        list.add(new int[]{i, j});
                        totalCnt++;
                    }
                }
            } // 멕시노스 셀 정보 입력 및 비가장자리 코어리스트 생성

            go(0, 0, 0);
            sb.append("#").append(tc).append(" ").append(min).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void go(int index, int cCnt, int lCnt) { // 현재 코어로 전선처리 시도, cCnt: 코어 갯수, lCnt: 전선 길이의 합

        if (cCnt + (totalCnt - index) < max) return; // 가지치기

        if (index == totalCnt) {
            if (max < cCnt) {
                max = cCnt;
                min = lCnt;
            } else if (max == cCnt) {
                if (min > lCnt) {
                    min = lCnt;
                }
            }
            return;
        }

        int[] cur = list.get(index);
        int i = cur[0];
        int j = cur[1];

        // 4방으로 시도
        for (int d = 0; d < 4; d++) {
            if (isAvailable(i, j, d)) {
                int len = setStatus(i, j, d, 2);// 전선 놓기
                go(index + 1, cCnt + 1, lCnt + len); // 다음 코어 가기
                setStatus(i, j, d, 0); // 전선 지우기
            }
        }

        // 전선 놓지 않기
        go(index + 1, cCnt, lCnt);
    }

    static boolean isAvailable(int i, int j, int d) { // i, j 위치에서 d 방향으로 전선 놓기가 가능한지 체크
        int ni = i;
        int nj = j;
        while (true) {
            ni += di[d];
            nj += dj[d];
            if (ni < 0 || ni >= N || nj < 0 || nj >= N) return true;
            if (map[ni][nj] > 0) return false;
        }

    }

    static int setStatus(int i, int j, int d, int s) { // i, j 위치에서 d 방향으로 s(2:전선, 0:빈칸)로 상태 set
        int ni = i;
        int nj = j;
        int cnt = 0;
        while (true) {
            ni += di[d];
            nj += dj[d];
            if (ni < 0 || ni >= N || nj < 0 || nj >= N) break;
            map[ni][nj] = s;
            cnt++; // 처리한 빈칸의 개수
        }
        return cnt;
    }
}
