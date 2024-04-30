package swexpertacademy.디저트카페;

import java.io.*;

public class Solution_d9_2105_디저트카페_참고 {
    private static int N, result;
    private static int[][] map;
    private static boolean[] eatNumber;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int test = Integer.parseInt(br.readLine());

        for (int t = 1; t <= test; t++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            eatNumber = new boolean[101];
            result = -1;
            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(line[j]);
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    action(i, j);
                }
            }

            System.out.println("#" + t + " " + result);

        }
    }

    private static void action(int i, int j) {
        for (int a = 1; a < N; a++) {
            for (int b = 1; b < N; b++) {
                if (j + a < N && i + a + b < N && j - b >= 0 && ((a + b) * 2) > result) {

                    eatNumber = new boolean[101];
                    eatNumber[map[i][j]] = true;
                    boolean isAble = false;
                    int curI = i;
                    int curJ = j;

                    for (int n = 0; n < a; n++) {
                        curI++;
                        curJ++;
                        if (eatNumber[map[curI][curJ]]) {
                            isAble = false;
                            break;
                        } else {
                            isAble = true;
                            eatNumber[map[curI][curJ]] = true;
                        }
                    }

                    if (!isAble) continue;

                    for (int n = 0; n < b; n++) {
                        curI++;
                        curJ--;
                        if (eatNumber[map[curI][curJ]]) {
                            isAble = false;
                            break;
                        } else {
                            isAble = true;
                            eatNumber[map[curI][curJ]] = true;
                        }
                    }

                    if (!isAble) continue;

                    for (int n = 0; n < a; n++) {
                        curI--;
                        curJ--;
                        if (eatNumber[map[curI][curJ]]) {
                            isAble = false;
                            break;
                        } else {
                            isAble = true;
                            eatNumber[map[curI][curJ]] = true;
                        }
                    }

                    if (!isAble) continue;

                    for (int n = 0; n < b; n++) {
                        curI--;
                        curJ++;
                        if (!(curI == i && curJ == j)) {
                            if (eatNumber[map[curI][curJ]]) {
                                isAble = false;
                                break;
                            } else {
                                isAble = true;
                                eatNumber[map[curI][curJ]] = true;
                            }
                        }
                    }

                    if (!isAble) continue;

                    result = (a + b) * 2;

                }
            }
        }
    }
}
