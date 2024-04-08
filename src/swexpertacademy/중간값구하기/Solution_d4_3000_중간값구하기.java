package swexpertacademy.중간값구하기;

import java.util.*;
import java.io.*;

public class Solution_d4_3000_중간값구하기 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_3000.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int first = Integer.parseInt(st.nextToken());
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            maxHeap.offer(first);
            long answer = 0L;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                if (a < b) {
                    maxHeap.offer(a);
                    minHeap.offer(b);
                } else {
                    maxHeap.offer(b);
                    minHeap.offer(a);
                }
                if (maxHeap.peek() > minHeap.peek()) {
                    maxHeap.offer(minHeap.poll());
                    minHeap.offer(maxHeap.poll());
                }
                answer += maxHeap.peek() % 20171109;
            }
            answer %= 20171109;
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
