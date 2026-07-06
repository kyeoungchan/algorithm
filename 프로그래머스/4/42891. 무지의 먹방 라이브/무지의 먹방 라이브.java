import java.util.*;

class Solution {
    
    static class Food implements Comparable<Food> {
        int time, idx;
        
        Food(int time, int idx) {
            this.time = time;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(Food o) {
            return time == o.time ? Integer.compare(idx, o.idx) : Integer.compare(time, o.time);
        }
    }
    
    public int solution(int[] food_times, long k) {
        int answer = 0;
        PriorityQueue<Food> pq = new PriorityQueue<>();
        
        long sumTime = 0;
        int len = food_times.length;
        
        for (int i = 0; i < len; i++) {
            pq.offer(new Food(food_times[i], i));
            sumTime += food_times[i];
        }
        
        if (sumTime <= k) return -1;
        
        long prevTotalTime = 0;
        long totalTime = 0;
        int prevTime = 0;
        
        int lastIdx = -1;
        while (!pq.isEmpty()) {
            
            Food cur = pq.poll();
            totalTime += (cur.time - prevTime) * len;
            lastIdx = cur.idx;
            
            if (totalTime > k) {
                List<Integer> idxList = new ArrayList<>();
                idxList.add(cur.idx);
                while (!pq.isEmpty()) {
                    idxList.add(pq.poll().idx);
                }
                Collections.sort(idxList);
                
                answer = idxList.get((int) (k - prevTotalTime) % len) + 1;
                
            }
            
            prevTime = cur.time;
            prevTotalTime = totalTime;
            len--;
        }
        
        if (answer == 0) answer = lastIdx + 1;
        return answer;
    }
}