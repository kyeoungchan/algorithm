package swexpertacademy.벌꿀채취;

import java.util.*;
import java.io.*;

public class Solution_d9_2115_벌꿀채취_서울_20반_우경찬 {

    static int N, M, C, result;
    static int[][] honeyBoard, gottenHoney;


    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2115.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            honeyBoard = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    honeyBoard[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            result = Integer.MIN_VALUE;
            gottenHoney = new int[2][M]; // 각 일꾼마다 가진 벌꿀
            comb(0, 0, 0);
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void comb(int cnt, int startI, int startJ) {
        if (cnt == 2) {
            int worker1 = calcValue(gottenHoney[0]);
            int worker2 = calcValue(gottenHoney[1]);
            result = Math.max(worker1 + worker2, result);
            return;
        }

        for (int i = startI; i < N; i++) {
            for (int j = 0; j <= N - M; j++) {
                if (i == startI && j < startJ) continue;

                for (int k = j; k < j + M; k++) {
                    gottenHoney[cnt][k - j] = honeyBoard[i][k];
                }

                if (j + M == N) {
                    if (i < N - 1)
                        comb(cnt + 1, i + 1, 0);
                    else if (cnt == 1) {
                        comb(cnt + 1, 0, 0);
                    }
                } else {
                    comb(cnt + 1, i, j + M);
                }
            }
        }
    }

    static int calcValue(int[] honeys) {
        Arrays.sort(honeys);
        int totalHoney = 0;
        for (int honey : honeys) {
            totalHoney += honey;
        }

        if (totalHoney <= C) {
            int value = 0;
            for (int honey : honeys) {
                value += honey * honey;
            }
            return value;
        }

        return setSubSet(0, honeys, 0, 0);
    }

    static int setSubSet(int cnt, int[] honeys, int honey, int price) {
        if (cnt == M) {
            return price;
        }

        int a = 0;
        if (honey + honeys[cnt] <= C)
            a = setSubSet(cnt + 1, honeys, honey + honeys[cnt], price + honeys[cnt] * honeys[cnt]);
        int b = setSubSet(cnt + 1, honeys, honey, price);
        return Math.max(a, b);
    }
}


