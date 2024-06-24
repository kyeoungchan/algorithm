package swexpertacademy.중간값구하기;

import java.io.*;
import java.util.*;

public class Solution_d4_3000_중간값구하기2 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_3000.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        int mod = 20171109;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder()); // 좌측
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 우측
        for (int tc = 1; tc < T + 1; tc++) {
            maxHeap.clear();
            minHeap.clear();
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            maxHeap.add(Integer.parseInt(st.nextToken()));
            long sum = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                maxHeap.add(Math.min(x, y));
                minHeap.add(Math.max(x, y));
                if (maxHeap.peek() > minHeap.peek()) {
                    maxHeap.add(minHeap.poll());
                    minHeap.add(maxHeap.poll());
                }
                sum += maxHeap.peek() % mod;
            }
            sum %= mod;
            sb.append("#").append(tc).append(" ").append(sum).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
