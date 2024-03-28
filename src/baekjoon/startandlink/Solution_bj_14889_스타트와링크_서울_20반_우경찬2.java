package baekjoon.startandlink;

import java.util.*;
import java.io.*;

public class Solution_bj_14889_스타트와링크_서울_20반_우경찬2 {

    static int N, S[][], ANS, temp;
    static boolean[] isStartTeam;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        S = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        isStartTeam = new boolean[N];
        ANS = Integer.MAX_VALUE;
        comb(0, 0);
        System.out.println(ANS);
        br.close();
    }

    static void comb(int cnt, int start) {
        if (cnt == N/2) {
            temp = 0;
            int[] selected = new int[2];
            comb2(0, 0, true, selected);
            comb2(0, 0, false, selected);
            ANS = Math.min(ANS, Math.abs(temp));
            return;
        }

        for (int i = start; i < N; i++) {
            isStartTeam[i] = true;
            comb(cnt + 1, i + 1);
            isStartTeam[i] = false;
        }
    }

    static void comb2(int cnt, int start, boolean aboutStartTeam, int[] selected) {
        if (cnt == 2) {
            int i = selected[0];
            int j = selected[1];
            if (aboutStartTeam) {
                temp += (S[i][j] + S[j][i]);
            } else {
                temp -= (S[i][j] + S[j][i]);
            }
            return;
        }

        if (aboutStartTeam) {
            for (int i = start; i < N; i++) {
                if (isStartTeam[i]) {
                    selected[cnt] = i;
                    comb2(cnt + 1, i + 1, aboutStartTeam, selected);
                }
            }
        } else {
            for (int i = start; i < N; i++) {
                if (!isStartTeam[i]) {
                    selected[cnt] = i;
                    comb2(cnt + 1, i + 1, aboutStartTeam, selected);
                }
            }
        }
    }
}
