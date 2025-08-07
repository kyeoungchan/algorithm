package mincoding.두개의점;

import java.io.*;
import java.util.*;

public class Solution_min_두개의점 {
    static class Point implements Comparable<Point> {
        int x, y;

        Point(int x, int y) {
            this.x = x; this.y = y;
        }

        public int compareTo(Point o) {
            return Integer.compare(this.x, o.x);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int xi = Integer.parseInt(st.nextToken());
            int yi = Integer.parseInt(st.nextToken());
            points.add(new Point(xi, yi));
        }

        Collections.sort(points);

        int l = 0;
        int answer = Integer.MIN_VALUE;
        TreeMap<Integer, Integer> yMap = new TreeMap<>();

        for (int r = 0; r < N; r++) {
            int xr = points.get(r).x;
            int yr = points.get(r).y;

            // 윈도우 유지
            while (xr - points.get(l).x > K) {
                int yl = points.get(l).y;

                yMap.put(yl, yMap.get(yl) - 1);
                if (yMap.get(yl) == 0) yMap.remove(yl);
                l++;
            }

            // 현재 윈도우에서 가장 큰 y와 조합
            if (!yMap.isEmpty()) {
                answer = Math.max(answer, yr + yMap.lastKey());
            }

            // 현재 y 삽입
            yMap.put(yr, yMap.getOrDefault(yr, 0) + 1);
        }

        System.out.println(answer);
    }
}