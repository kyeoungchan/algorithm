package baekjoon.숨바꼭질2;

import java.io.*;
import java.util.*;

/**
 * 0 <= N, K <= 100_000
 * 수빈이는 걷거나 순간이동을 할 수 있다.
 * 걸으면 X+-1 위치
 * 순간이동하면 2*X의 위치
 * 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지
 * 그리고, 가장 빠른 시간으로 찾는 방법이 몇 가지인지 구하는 프로그램
 */
public class Solution_bj_12851_숨바꼭질2 {

    static class State {
        int pos, time;

        public State(int pos, int time) {
            this.pos = pos;
            this.time = time;
        }

        @Override
        public String toString() {
            return "State{" +
                    "pos=" + pos +
                    ", time=" + time +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken()); // 수빈이의 위치
        int K = Integer.parseInt(st.nextToken()); // 동생의 위치
        int range = K + Math.abs(K - N);
        int[] visited = new int[range + 1];
        Arrays.fill(visited, Integer.MAX_VALUE);

        ArrayDeque<State> q = new ArrayDeque<>();

        visited[N] = 0;
        q.offer(new State(N, 0));
        int minTime = Integer.MAX_VALUE;
        int methods = 0;

        while (!q.isEmpty()) {
            State cur = q.poll();

            if (cur.time > minTime) {
                break;
            }

            if (cur.pos == K) {
                minTime = cur.time;
                methods++;
            }

            int newTime = cur.time + 1;
            int newPos = cur.pos - 1;
            if (newPos >= 0 && visited[newPos] >= newTime) {
                visited[newPos] = newTime;
                q.offer(new State(newPos, newTime));
            }

            newPos = cur.pos + 1;
            if (newPos <= range && visited[newPos] >= newTime) {
                visited[newPos] = newTime;
                q.offer(new State(newPos, newTime));
            }

            newPos = 2 * cur.pos;
            if (newPos >= 0 && newPos <= range && visited[newPos] >= newTime) {
                visited[newPos] = newTime;
                q.offer(new State(newPos, newTime));
            }
        }

        System.out.println(minTime);
        System.out.println(methods);
        br.close();
    }
}
