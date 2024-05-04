package swexpertacademy.미생물격리;

import java.util.*;


public class Solution_d9_2382_미생물격리_다른사람꺼 {

    /*
     * 1) germList를 만든다.
     * 2) 각 지역에 몇 개의 군집이 있는지에 관한 지도인 map 을 만든다.
     *
     * */
    public static void makeGerm(Scanner scnr, int k, ArrayList<Germ> germList, int[][] map) {
        for (int germ = 0; germ < k; germ++) {
            int r = scnr.nextInt();
            int c = scnr.nextInt();
            int germSize = scnr.nextInt();
            int d = scnr.nextInt();
            Germ newGerm = new Germ(r, c, germSize, d);
            germList.add(newGerm);
            map[r][c]++;
        }
    }

    /*
     * 1) germList의 Germ들을 이동시킨다.
     * 2) 그에 맞춰 map 업데이트.
     * */
    public static void move(ArrayList<Germ> germList, int n, int[][] map) {

        for (int germ = 0; germ < germList.size(); germ++) {
            if (germList.get(germ).d == 1) {
                germList.get(germ).r -= 1;
                if (germList.get(germ).r == 0) {
                    germList.get(germ).d = 2;
                    germList.get(germ).s /= 2;
                }
            } else if (germList.get(germ).d == 2) {
                germList.get(germ).r += 1;
                if (germList.get(germ).r == n - 1) {
                    germList.get(germ).d = 1;
                    germList.get(germ).s /= 2;
                }
            } else if (germList.get(germ).d == 3) {
                germList.get(germ).c -= 1;
                if (germList.get(germ).c == 0) {
                    germList.get(germ).d = 4;
                    germList.get(germ).s /= 2;
                }
            } else if (germList.get(germ).d == 4) {
                germList.get(germ).c += 1;
                if (germList.get(germ).c == n - 1) {
                    germList.get(germ).d = 3;
                    germList.get(germ).s /= 2;
                }
            }

        }
    }

    public static boolean encounter(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > 1) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void merge(ArrayList<Germ> germList, int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > 1) {

                    int maxSize = 0;
                    int maxD = 0;
                    int totalSize = 0;

                    for (int germ = 0; germ < germList.size(); germ++) {
                        if (germList.get(germ).r == i && germList.get(germ).c == j) {
                            if (germList.get(germ).s > maxSize) {
                                maxSize = germList.get(germ).s;
                                maxD = germList.get(germ).d;
                            }
                        }
                    }

                    for (int germ = 0; germ < germList.size(); germ++) {
                        if (germList.get(germ).r == i && germList.get(germ).c == j) {
                            totalSize += germList.get(germ).s;
                        }
                    }


                    for (int germ = germList.size() - 1; germ >= 0; germ--) {
                        if (germList.get(germ).r == i && germList.get(germ).c == j) {
                            germList.remove(germ);
                        }
                    }
                    Germ maxG = new Germ(i, j, totalSize, maxD);
                    germList.add(maxG);

                }
            }
        }
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(" " + map[i][j]);
            }
            System.out.println();
        }
    }

    public static int[][] updateMap(int[][] map, int n, ArrayList<Germ> germList) {
        map = new int[n][n];
        for (int germ = 0; germ < germList.size(); germ++) {
            map[germList.get(germ).r][germList.get(germ).c] += 1;
        }
        return map;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        int testCase = scnr.nextInt();
        int[][] map;

        for (int test = 1; test <= testCase; test++) {
            int n = scnr.nextInt(); // 지도 크기
            int m = scnr.nextInt(); // m시간 후
            int k = scnr.nextInt(); // 군 수.
            map = new int[n][n];

            ArrayList<Germ> germList = new ArrayList<Germ>(); // 군집 리스트.
            // 각 셀마다 몇 군집이 모여있는지 숫자로 표시.
            // 예를 들어 map[1][2] = 3 이면 군집 3개가 모여있음.


            // 군집 만들기.
            makeGerm(scnr, k, germList, map);
            map = updateMap(map, n, germList);

            // m시간 동안 실시.
            for (int times = 0; times < m; times++) {
                move(germList, n, map);
                map = updateMap(map, n, germList);
                if (encounter(map)) {
                    merge(germList, map);
                    map = updateMap(map, n, germList);
                }
            }

            // 결과
            int result = 0;
            for (int germ = 0; germ < germList.size(); germ++) {
                result += germList.get(germ).s;
            }
            System.out.println("#" + test + " " + result);


        }
    }

}

class Germ {
    int r; //세로위치
    int c; //가로위치
    int s; //미생물 수
    int d;

    Germ(int r, int c, int s, int d) {
        this.c = c;
        this.r = r;
        this.s = s;
        this.d = d;
    }
}
