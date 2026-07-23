import java.util.*;

class Solution {
    
    static class Food implements Comparable<Food> {
        int num, quantity;
        
        Food(int num, int quantity) {
            this.num = num;
            this.quantity = quantity;
        }
        
        @Override
        public int compareTo(Food o) {
            return Integer.compare(quantity, o.quantity);
        }
    }
        
    
    public int solution(int[] food_times, long k) {
        PriorityQueue<Food> pq = new PriorityQueue<>();
        for (int i = 0; i < food_times.length; i++) {
            pq.offer(new Food(i + 1, food_times[i]));
        }
        // Collections.sort(foodTimes);
        int answer = -1;
        // long time = 0L;
        long minus = 0L;
        List<Integer> remain = new ArrayList<>();
        while (!pq.isEmpty()) {
            int pqSize = pq.size();
            Food cur = pq.peek();
            long thisMinus = cur.quantity-minus;
            
            if (k < thisMinus * pqSize) {
                while (!pq.isEmpty()) {
                    remain.add(pq.poll().num);
                }
                Collections.sort(remain);
                int idx = (int)(k % remain.size());
                answer = remain.get(idx);
                break;
            }
            k -= thisMinus * pqSize;
            minus += thisMinus;
            pq.poll();
            
        }
        return answer;
    }
}