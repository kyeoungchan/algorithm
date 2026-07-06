import java.util.*;


class Solution {
    class Food implements Comparable<Food> {
        int num;
        int sec;
        
        public Food(int num, int sec){
            this.num = num;
            this.sec = sec;
        }
        
        @Override
        public int compareTo(Food f){
            return this.sec == f.sec? Integer.compare(this.num, f.num) : Integer.compare(this.sec, f.sec);
        }
        
    }
    public int solution(int[] food_times, long k) {
        PriorityQueue<Food> pq = new PriorityQueue<>();
        long sumTime = 0;
        
        for(int i=0;i<food_times.length;i++){
            pq.offer(new Food(i + 1, food_times[i]));
            sumTime += food_times[i];
        }
        
        if(sumTime <= k){
            return -1;
        }
        
        sumTime = 0;
        long previous = 0; 
        long length = food_times.length; 
        
        while(sumTime + ((pq.peek().sec - previous) * length) <= k){
            int now = pq.poll().sec;
            sumTime += (now - previous) * length;
            previous = now;
            length--;
        }
        
        ArrayList<Food> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(pq.poll());
        }
        
        result.sort(Comparator.comparing(f -> f.num));
        
        return result.get((int) ((k-sumTime) % length)).num;
    }
}