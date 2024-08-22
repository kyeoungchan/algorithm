package mincoding.대나무수확;

import java.io.*;
import java.util.*;

/**
 * 최대한 많은 대나무를 수확하려고 한다.
 * 각 맵에는 대나무의 크기가 적혀있다.
 * 인부는 총 D일에 걸쳐 작업을 하며, 인부의 작업 할당량은 날짜 별로 다르다.
 * 할당량만큼 칸을 차지한다고 봐도 될듯하다.
 * 작업을 했을 때는 전부 수확하지 않고, 작업 칸의 대나무에 대하여 1 크기만 남겨둔 채 수확을 한다.
 * 대나무 크기가 처음부터 1인 경우, 수확을 하지는 않지만 작업을 했다고 가정한다.
 * 하루가 지나면 대나무들은 크기가 1씩 성장한다.
 * D일에 걸쳐 수확한 양을 합했을 때 최대값을 출력해라.
 */
public class Solution_min_대나무수확 {

    static class Bamboo implements Comparable<Bamboo> {
        int initHeight, changeDay;
        int factor;

        public Bamboo(int initHeight, int changeDay) {
            this.initHeight = initHeight;
            this.changeDay = changeDay;
            // 요인은 초기에 심어진 나무의 크기가 클수록, 그리고 심은 날짜가 적을수록 크다.
            factor = initHeight - changeDay;
        }

        @Override
        public int compareTo(Bamboo o) {
            // 내림차순 정렬
            return Integer.compare(o.factor, factor);
        }

        @Override
        public String toString() {
            return "Bamboo{" +
                    "initHeight=" + initHeight +
                    ", changeDay=" + changeDay +
                    ", factor=" + factor +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        long result = 0L;
        // 가로, 세로 길이가 최대 1,000이므로 이차원 배열 모두 탐색하는 건 좋지 못한 전략
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        PriorityQueue<Bamboo> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++)
                pq.offer(new Bamboo(Integer.parseInt(st.nextToken()), 0)); // 첫째날은 0일로 심는다.
        }
        st = new StringTokenizer(br.readLine(), " ");
        for (int d = 0; d < D; d++) {
            int allocation = Integer.parseInt(st.nextToken()); // 할당량
            for (int i = 0; i < Math.min(allocation, pq.size()); i++) {
                // 할당량을 다 못 채워도 PQ를 한 바퀴 다 돌았으면 그만둬야하므로 둘 중 작은 것을 기준으로 반복문 실행
                Bamboo target = pq.poll();
                int yield = target.initHeight - 1 + d - target.changeDay;
                // 처음 심어놓은 대나무의 크기 + 날짜가 지난 만큼 => 1만큼 남겨놔야 하므로 -1까지 하면 수확량이 된다.
                result += yield;
                if (yield == 0) pq.offer(target); // 이 분기점을 사용하지 않으면 아무 대나무도 베지 않았을 때에도 다음날 대나무가 1로 초기화가 되게 된다.
                else pq.offer(new Bamboo(1, d));
            }
        }
        System.out.println(result);
        br.close();
    }
}
