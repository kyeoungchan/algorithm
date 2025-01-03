import java.io.*;
import java.util.*;

/**
 * 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임
 * 파란 구슬이 구멍에 들어가면 안 된다.
 * 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패다.
 * 굴리는 동작: 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기
 * 더이상 구슬이 움직여지지 않을 때까지 기울이는 동작을 한다.
 * 3 <= N, M <= 10
 * .: 빈칸
 * #: 공이 이동할 수 없는 장애물
 * 0: 구멍의 위치
 * R: 빨간 구슬
 * B: 파란 구슬
 * 보드의 가장자리는 모두 장애물
 * 구멍, 빨간 구슬, 파란 구슬은 모두 각각 1개
 * 출력
 * 1. 최소 몇 번만에 빨간 구슬을 빼낼 수 있는지
 * 2. 10번 이하로 빨간 구슬을 구멍을 통해 빼낼 수 없으면 -1 출력
 */
public class Main {

    static class State {
        int redR, redC, blueR, blueC, moveCnt;

        public State(int redR, int redC, int blueR, int blueC, int moveCnt) {
            this.redR = redR;
            this.redC = redC;
            this.blueR = blueR;
            this.blueC = blueC;
            this.moveCnt = moveCnt;
        }

        public State(int[] redPos, int[] bluePos, int moveCnt) {
            redR = redPos[0];
            redC = redPos[1];
            blueR = bluePos[0];
            blueC = bluePos[1];
            this.moveCnt = moveCnt;
        }

        @Override
        public String toString() {
            return "State{" +
                    "redR=" + redR +
                    ", redC=" + redC +
                    ", blueR=" + blueR +
                    ", blueC=" + blueC +
                    ", moveCnt=" + moveCnt +
                    '}';
        }
    }

    static boolean inHole;
    static char[][] board;
    static int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static int holeR, holeC;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        board = new char[N][M];
        int redR = 0, redC = 0, blueR = 0, blueC = 0;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                char c = s.charAt(j);

                if (c == 'R') {
                    redR = i;
                    redC = j;
                } else if (c == 'B') {
                    blueR = i;
                    blueC = j;
                } else if (c == 'O') {
                    holeR = i;
                    holeC = j;
                }

                if (c != '#') c = '.';
                board[i][j] = c;
            }
        }

        ArrayDeque<State> q = new ArrayDeque<>();
        boolean[][][][] visited = new boolean[N][M][N][M];
        q.offer(new State(redR, redC, blueR, blueC, 0));
        visited[redR][redC][blueR][blueC] = true;

        int answer = -1;
        boolean redHoled = false;
        end: while (!q.isEmpty()) {
            State cur = q.poll();
            int newMoveCnt = cur.moveCnt + 1;
            if (newMoveCnt > 10) break;
            for (int d = 0; d < 4; d++) {
                if (isBlueFirst(cur, d)) {
                    // 파란 공이 먼저 가는 경우
                    int[] newPosBlue = goStraight(cur.blueR, cur.blueC, d, cur.redR, cur.redC);
                    if (inHole) {
//                        System.out.println("1");
                        inHole = false;
                        continue;
                    }
                    int[] newPosRed = goStraight(cur.redR, cur.redC, d, newPosBlue[0], newPosBlue[1]);
                    if (inHole) {
//                        System.out.println(2);
                        answer = newMoveCnt;
                        break end;
                    }
                    if (visited[newPosRed[0]][newPosRed[1]][newPosBlue[0]][newPosBlue[1]]) continue;
                    visited[newPosRed[0]][newPosRed[1]][newPosBlue[0]][newPosBlue[1]] = true;
                    q.offer(new State(newPosRed, newPosBlue, newMoveCnt));
                } else {
                    // 빨간 공이 먼저 가는 경우
                    int[] newPosRed = goStraight(cur.redR, cur.redC, d, cur.blueR, cur.blueC);
//                    System.out.println("newPosRed = " + Arrays.toString(newPosRed));

                    if (inHole) {
//                        System.out.println(3);
                        redHoled = true;
                        inHole = false;
                    }
                    int[] newPosBlue = goStraight(cur.blueR, cur.blueC, d, newPosRed[0], newPosRed[1]);
                    if (inHole) {
//                        System.out.println(4);
                        inHole = false;
                        redHoled = false;
                        continue;
                    }
                    if (redHoled) {
//                        System.out.println(5);
                        answer = newMoveCnt;
                        break end;
                    }
                    if (visited[newPosRed[0]][newPosRed[1]][newPosBlue[0]][newPosBlue[1]]) continue;
                    visited[newPosRed[0]][newPosRed[1]][newPosBlue[0]][newPosBlue[1]] = true;
                    q.offer(new State(newPosRed, newPosBlue, newMoveCnt));

                }
            }
        }
        System.out.println(answer);
        br.close();
    }

    static int[] goStraight(int r, int c, int d, int oR, int oC) {
        if (board[r][c] == '#') {
            System.out.println("Error!");
            return null;
        }
        while (true) {
            int tempR = r + dr[d];
            int tempC = c + dc[d];
            if (board[tempR][tempC] == '#') break;
            if (tempR == oR && tempC == oC) break;
            if (isInHole(tempR, tempC)) {
                inHole = true;
                return new int[] {0, 0};
            }
            r = tempR;
            c = tempC;
        }
        return new int[]{r, c};
    }

    static boolean isInHole(int r, int c) {
        return holeR == r && holeC == c;
    }

    static boolean isInHole(int[] pos) {
        return holeR == pos[0] && holeC == pos[1];
    }

    static boolean isBlueFirst(State cur, int d) {
        return (d == 0 && cur.blueR <= cur.redR) || (d == 1 && cur.blueC >= cur.redC) || (d == 2 && cur.blueR >= cur.redR) || (d == 3 && cur.blueC <= cur.redC);
    }
}