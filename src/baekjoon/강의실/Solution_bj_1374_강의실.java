package baekjoon.강의실;

import java.io.*;
import java.util.*;

public class Solution_bj_1374_강의실 {

    static class Course implements Comparable<Course>{
        int startTime, endTime;

        public Course(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(Course o) {
            return Integer.compare(startTime, o.startTime);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Course> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int number = Integer.parseInt(st.nextToken()); // 번호는 굳이 필요없다.
            pq.offer(new Course(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
//        int result = 1;
        PriorityQueue<Integer> finish = new PriorityQueue<>(); // 각 강의마다 끝나는 시간을 담는 PQ
        finish.offer(pq.poll().endTime);
        while (!pq.isEmpty()) {
            Course cur = pq.poll();
            if (finish.peek() <= cur.startTime) {
                // 이전 강의가 끝나고 다음 강의가 들어온다면
                finish.poll(); // 이전 강의는 없애고
                finish.offer(cur.endTime); // 다음 강의를 넣는다.
            } else {
                // 이전 강의가 덜 끝났는데 다음 강의가 들어온다면
                // 이전 강의는 남기고 개수를 하나 더 늘린다.
//                result++;
                finish.offer(cur.endTime); // 다음 강의를 넣는다.
            }
        }
//        System.out.println(result);
        System.out.println(finish.size());
        br.close();
    }
}
