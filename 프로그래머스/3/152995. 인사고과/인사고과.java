import java.util.*;

class Solution {
    
    static class Employee implements Comparable<Employee> {
        int attitude, peer, idx;
        
        Employee(int attitude, int peer, int idx) {
            this.attitude = attitude;
            this.peer = peer;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(Employee o) {
            return attitude == o.attitude ? 
                Integer.compare(o.peer, peer) :
                Integer.compare(o.attitude, attitude);
        }
        
        @Override
        public String toString() {
            return "idx: " + idx + ", attitude: " + attitude + ", peer: " + peer;
        }
    }
    
    public int solution(int[][] scores) {
        
        if (scores.length == 1) {
            return 1;
        }
        
        List<Employee> scoreList = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            scoreList.add(new Employee(scores[i][0], scores[i][1], i));
        }
        Collections.sort(scoreList);
        
        Deque<Employee> q = new ArrayDeque<>();
        Set<Integer> noIncentive = new HashSet<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        q.offer(scoreList.get(0));
        
        for (int i = 1; i < scores.length; i++) {
            Employee cur = scoreList.get(i);
            while (!q.isEmpty() && q.peek().attitude > cur.attitude) {
                pq.offer(q.poll().peer);
            }
            if (!pq.isEmpty() && pq.peek() > cur.peer) {
                if (cur.idx == 0) return -1;
                noIncentive.add(cur.idx);
            }
            q.offer(cur);
        }
        
        int targetScore = scores[0][0] + scores[0][1];
        int answer = 1;
        for (int i = 0; i < scoreList.size(); i++) {
            Employee cur = scoreList.get(i);
            int idx = cur.idx;
            if (noIncentive.contains(idx)) continue;
            if (cur.attitude + cur.peer > targetScore) answer++;
        }
        return answer;
    }
}