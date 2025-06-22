package mincoding.두개의점;

import java.io.*;
import java.util.*;

public class Solution_min_두개의점 {
    static class Point implements Comparable<Point> {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return x == o.x ? Integer.compare(o.y, y) : Integer.compare(x, o.x);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private static int K;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        List<Point> points = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points.add(new Point(x, y));
        }
        Collections.sort(points);
//        System.out.println("points = " + points);

        int answer = closestPair(points, 0, N - 1);

        System.out.println(answer);

        br.close();
    }

    private static int closestPair(List<Point> points, int start, int end) {
        int n = end - start + 1;

        if (n <= 3) {
            // n이 3이하면 완전탐색으로 가장 가까운 두 점 사이의 거리를 찾음.
            return bruteForce(points, start, end);
        }

        int mid = start + (end - start) / 2;

        int yY = Math.max(closestPair(points, start, mid), closestPair(points, mid + 1, end));

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = start; i <= end; i++) {
            if (i != mid && Math.abs(points.get(mid).x - points.get(i).x) <= K && points.get(mid).y + points.get(i).y > yY) {
                pq.add(points.get(mid).y + points.get(i).y);
            }
        }

        if (pq.isEmpty()) return yY;

        return pq.poll();
    }

    // 완전 탐색으로 가장 가까운 점들 중 K 이내의 y 값 덧셈이 가장 큰 애들 찾기
    private static int bruteForce(List<Point> points, int start, int end) {
/*
        System.out.println("start = " + start);
        System.out.println("end = " + end);
*/
        int maxYs = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            for (int j = i + 1; j <= end; j++) {
/*
                System.out.println("points = " + points);
                System.out.println("points.get(i) = " + points.get(i));
                System.out.println("points.get(j) = " + points.get(j));
*/
                if (points.get(j).x - points.get(i).x > K) {
//                    System.out.println("continue! \n");
                    break;
                }

                maxYs = Math.max(maxYs, points.get(i).y + points.get(j).y);
/*
                System.out.println("maxYs = " + maxYs);
                System.out.println();
*/
            }
        }
        return maxYs;
    }
}
