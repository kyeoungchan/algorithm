import java.util.*;

class Solution {
    
    static class Incentive implements Comparable<Incentive> {
        int attitude, peer, idx;
        
        Incentive(int attitude, int peer, int idx) {
            this.attitude = attitude;
            this.peer = peer;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(Incentive o) {
            return Integer.compare(o.attitude, attitude);
        }
        
        @Override
        public String toString() {
            return "attitude: " + attitude + ", peer: " + peer + ", idx: " + idx;
        }
    }
    
    public int solution(int[][] scores) {
        int N = scores.length;
        // 완호밖에 없음
        if (N == 1) return 1;
        else if (N == 2) {
            return scores[0][0] + scores[0][1] >= scores[1][0] + scores[1][1] ? 1 : 2;    
        }
        
        List<Incentive> incentives = new ArrayList<>();
        
        for (int i = 0; i < N; i++) {
            incentives.add(new Incentive(scores[i][0], scores[i][1], i));
        }
        
        Collections.sort(incentives);

        // System.out.println(incentives);
        
        
        Set<Integer> noIncentive = new HashSet<>();        
        
        Deque<Incentive> buffer = new ArrayDeque<>();
        buffer.offer(incentives.get(0));
        buffer.offer(incentives.get(1));
        
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        
        for (int i = 2; i < N; i++) {
            Incentive cur = incentives.get(i);
            // System.out.println("cur: " + cur);
            while (!buffer.isEmpty() && buffer.peek().attitude > cur.attitude) {
                pq.offer(buffer.poll().peer);
            }
            buffer.offer(cur);
            // System.out.println("buffer: " + buffer);
            // System.out.println("pq: " + pq);
            
            if (!pq.isEmpty()) {
                int n = pq.poll();
                if (n > cur.peer) {
                    // 원호가 인센티브를 못 받으면 -1 반환하고 끝
                    if (cur.idx == 0) return -1;
                    noIncentive.add(cur.idx);
                }
                pq.offer(n);
            }
            // System.out.println("noIncentive: " + noIncentive);
            // System.out.println();
            
        }
        // System.out.println(noIncentive);
        
        int targetScore = scores[0][0] + scores[0][1];
        // List<Integer> highScore = new ArrayList<>();
        
        int answer = 1;
        for (int i = 1; i < N; i++) {
            if (noIncentive.contains(i)) continue;
            if (scores[i][0] + scores[i][1] > targetScore) answer++;
        }
        
        
        
        return answer;
    }
}