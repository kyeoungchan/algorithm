package baekjoon.마법사상어와블리자드;

import java.io.*;
import java.util.*;


public class Solution_bj_12611_마법사상어와블리자드_백준참고 {
    static int board[][], order[][], boardSize;
    static int blizzardNum, maxBeadSize;
    static int[][] dir = {{0, -1, 1, 0, 0},{0, 0, 0, -1, 1}};
    static boolean[][] visited;

    static int[] beads;
    static int explodeBeans[] = new int[4];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        boardSize = Integer.parseInt(st.nextToken());
        blizzardNum = Integer.parseInt(st.nextToken());

        maxBeadSize = boardSize * boardSize;
        board = new int[boardSize][boardSize];
        visited = new boolean[boardSize][boardSize];
        order = new int[boardSize][boardSize];

        beads = new int[maxBeadSize];

        for(int y = 0; y < boardSize; y++) {
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < boardSize; x++)
                board[y][x] = Integer.parseInt(st.nextToken());
        }

        makeOrder();

        for(int b = 0; b < blizzardNum; b++) {
            st = new StringTokenizer(br.readLine());
            int direction = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());

            // 구슬 블리자드
            for(int i = speed; 0 < i; i--) {
                int nextY = boardSize / 2 + i * dir[0][direction];
                int nextX = boardSize / 2 + i * dir[1][direction];

                if(nextY < 0 || nextX < 0 || boardSize <= nextY || boardSize <= nextX )
                    continue;
                if(beads.length < order[nextY][nextX])
                    continue;
                beads[order[nextY][nextX]] = 0;
            }

            // 구슬 폭팔
            beadExplode();

            // 구슬 변형
            beads = changeBeads();
        }

        System.out.println(explodeBeans[1] + 2 * explodeBeans[2] + 3 * explodeBeans[3]);

    }

    private static int[] changeBeads() {
        int[] newBeads = new int[maxBeadSize];
        int lastBead = -1;
        int len = 0;
        int idx = 1;

        for(int b = 0; b < maxBeadSize; b++) {
            int curBead = beads[b];
            if(curBead == 0)
                continue;

            if(curBead == lastBead) {
                len++;
                continue;
            }
            if(lastBead != -1 && idx < maxBeadSize) {
                // 변형
                newBeads[idx++] = len;
                newBeads[idx++] = lastBead;
            }
            len = 1;
            lastBead = curBead;
        }
        if(lastBead != -1 && idx < maxBeadSize) {
            newBeads[idx++] = len;
            newBeads[idx++] = lastBead;
        }

        return newBeads;
    }

    private static void beadExplode() {
        boolean isExplode = true;
        while(isExplode) {
            int lastBead = -1;
            int len = 0;
            int idx = -1;
            isExplode = false;
            for(int b = 0; b < maxBeadSize; b++) {
                //iter.next();
                int curBead = beads[b];
                if(curBead == 0)
                    continue;

                if(curBead == lastBead) {
                    len++;
                    continue;
                }
                //	removeList.add(new BeadExp(curIdx, lastBead, len));
                if(3 < len && 0 < lastBead) {// 폭8
                    for(int i = idx; i < b; i++)
                        beads[i] = 0;

                    explodeBeans[lastBead] += len;
                    isExplode = true;
                }
                idx = b;
                len = 1;
                lastBead = curBead;
            }
            if(3 < len && 0 < lastBead ) {// 폭8
                for(int i = idx; i < maxBeadSize; i++)
                    beads[i] = 0;
                explodeBeans[lastBead] += len;
                isExplode = true;
            }
        }

    }
    private static void makeOrder() {
        int[][] rotD = {{-1, 0, 1, 0},{0, -1, 0, 1}};
        int i = boardSize / 2;
        int j = boardSize / 2;
        int curDir = 0;
        // O(n ^ 2)
        for(int number = 0; number < boardSize * boardSize; number++) {
            // 방향 결정
            int nextDir =(curDir + 1) % 4;
            visited[i][j] = true;

            order[i][j] = number;

            beads[number] = board[i][j];

            if(!visited[i + rotD[0][nextDir]][j + rotD[1][nextDir]])
                curDir = nextDir;

            i = i + rotD[0][curDir];
            j = j + rotD[1][curDir];
        }
    }

    static void debug() {
        System.out.println("order");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(order[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}