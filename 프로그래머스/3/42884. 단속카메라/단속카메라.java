import java.util.*;

class Solution {
    
    static class EndPoint implements Comparable<EndPoint> {
        int end, idx;
        
        EndPoint(int end, int idx) {
            this.end = end;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(EndPoint o) {
            return end == o.end ? Integer.compare(idx, o.idx) :
            Integer.compare(end, o.end);
        }
        
        @Override
        public String toString() {
            return "end: " + end + ", idx: " + idx;
        }
    }
    
    public int solution(int[][] routes) {
        Arrays.sort(routes, (r1, r2) -> 
                   r1[0] == r2[0] ?
                   Integer.compare(r1[1], r2[1]) :
                   Integer.compare(r1[0], r2[0]));
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int answer = 0;
        
        for (int i = 0; i < routes.length; i++) {
            int start = routes[i][0];
            int end = routes[i][1];
            
            if (pq.isEmpty()) {
                pq.offer(end);
                continue;
            }
            
            if (pq.peek() < start) { 
                while (!pq.isEmpty()) {
                    pq.poll();
                }
                answer++;
            }
            
            pq.offer(end);
        }
        if (!pq.isEmpty()) {
            answer++;
        }
        return answer;
    }
}