package baekjoon.색종이_3;

import java.io.*;

public class Solution_bj_2571_색종이3_다른사람코드2_백준상위 {
    public static void main(String[] args) throws IOException {
        final int size = 100;
        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(re.readLine());
        int[][] paper = new int[size][size];

        for (int p = 0; p < n; p++) {
            String[] input = re.readLine().split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            for (int i = x; i < x + 10; i++) {
                for (int j = y; j < y + 10; j++) {
                    paper[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (paper[i][j] == 1) {
                    paper[i][j] += paper[i - 1][j];
                }
            }
        }

        debug(paper);

        int maxArea = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // size는 100이므로 height는 그 이하일 수밖에 없다.
                int height = size;
                for (int k = j; k < size && paper[i][k] != 0; k++) {
                    // paper에는 현재 0이 아닌 곳에는 높이들이 메모돼있다.
                    // height를 최소값으로 갱신을 계속 해줌으로써, 빈칸까지 포함한 넓이를 구하는 경우를 계속 피해주고 있다.
                    height = Math.min(height, paper[i][k]);
                    // k는 j가 1인 시점부터 계속 오른쪽으로 한 칸씩 움직인 값이므로 가로의 길이가 될 수 있다.
                    maxArea = Math.max(maxArea, (k - j + 1) * height);
//                    debug(paper, i, j, k, height, maxArea);
                }
            }
        }
        System.out.println(maxArea);
    }

    static void debug(int[][] paper) {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (paper[i][j] == 0)
                    System.out.print("-  ");
                else
                    System.out.printf("%2d ", paper[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static void debug(int[][] paper, int startI, int startJ, int k, int height, int maxArea) {
        System.out.println("height = " + height);
        System.out.println("maxArea = " + maxArea);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (i == startI && j == startJ)
                    System.out.printf("[%2d] ", paper[i][j]);
                else if (i == startI && j == k)
                    System.out.printf("{%2d} ", paper[i][j]);
                else if (paper[i][j] == 0)
                    System.out.print(" -   ");
                else
                    System.out.printf(" %2d  ", paper[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}

