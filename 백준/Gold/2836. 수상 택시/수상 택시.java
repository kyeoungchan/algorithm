import java.io.*;
import java.util.*;

/**
 * 집은 0번부터 M번까지 번호가 매겨져있다 => M+1개
 * 인접한 집사이의 거리는 모두 1킬로
 * 상근이는 0번집에 산다.
 * M번 집으로 가는 길에 사람들을 태워준다.
 * 수상 택시를 타려는 사람은 총 N명 모두 탑승 가능
 */
public class Main {

    static class House implements Comparable<House> {
        int start, end;
        public House(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(House o) {
            return end == o.end ? Integer.compare(o.start, start) : Integer.compare(end, o.end);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        // 5 3, 8 2
        // 6 2, 8 4
        // 1 5, 4 12
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        PriorityQueue<House> info = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (a <= b) continue;
            info.offer(new House(a, b));
        }

        long cnt = 0;
        House cur = null;
        while (!info.isEmpty()) {
            if (cur == null)
                cur = info.poll();

//            while (!info.isEmpty() && info.peek().start <= cur.start)
//                info.poll();
            while (!info.isEmpty() && info.peek().end <= cur.start)
                cur.start = Math.max(info.poll().start, cur.start);

            if (info.isEmpty() || info.peek().end > cur.start) {
                cnt += (cur.start - cur.end) * 2L;
                cur = null;
            }
        }
        System.out.println(M + cnt);
        br.close();
    }
}