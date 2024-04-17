package baekjoon.보석도둑;

import java.util.*;
import java.io.*;

/**
 * 일단은 가방은 제일 가벼운 애부터 꺼낸다.
 * 보석들을 제일 가벼운 애부터, 그리고 같은 무게라면 더 비싼애부터 꺼낸 가방에 담을 수 있는 보석들을 모두 꺼내서 PQ에 가격을 담는다.
 * PQ는 가격이 큰 애부터 꺼낼 수 있게 한다.
 * 매 가방마다 PQ에 담긴 가격을 꺼내면서 정답 변수에 누적시켜 더해준다.
 */
public class Solution_bj_1202_보석도둑 {

    static class Jewerly implements Comparable<Jewerly>{
        int value;
        int weight;

        public Jewerly(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public int compareTo(Jewerly o) {
            return weight == o.weight ? Integer.compare(o.value, value) : Integer.compare(weight, o.weight);
        }

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        List<Jewerly> js = new ArrayList<>();
//        Jewerly[] js = new Jewerly[N]; // 이게 더 느림
        int[] knapsacks = new int[K];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int w = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            js.add(new Jewerly(v, w));
        }
        Collections.sort(js);

        for (int i = 0; i < K; i++) {
            knapsacks[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(knapsacks);

        int jsIdx = 0;
        long answer = 0L;
        for (int w: knapsacks) {
            while (jsIdx < N && js.get(jsIdx).weight <= w) {
                pq.offer(js.get(jsIdx++).value);
            }

            if (!pq.isEmpty()) {
                answer += pq.poll();
            }
        }

        System.out.println(answer);
        br.close();
    }

}