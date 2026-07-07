import java.util.*;

class Solution {
    
    static class Info {
        int num, idx;
        
        Info(int num, int idx) {
            this.num = num;
            this.idx = idx;
        }
        
        @Override
        public int hashCode() {
            return num;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof Info) {
                Info info = (Info) o;
                return num == info.num;
            }
            return false;
        }
        
        @Override
        public String toString() {
            return "[num: " + num + ", idx: " + idx + "]";
        }
    }
    
    public int[] solution(String[] gems) {
        Map<String, Integer> map = new HashMap<>();
        int n = 0;
        for (String gem: gems) {
            if (map.containsKey(gem)) continue;
            // 1부터
            map.put(gem, ++n);
        }

        if (n == 1) return new int[] {1, 1};
        
        // num을 담는 큐
        ArrayDeque<Integer> q = new ArrayDeque<>();
        // num이 다 모였는지 체크하는 셋
        Set<Integer> set = new HashSet<>();
        
        // num별로 가장 마지막 인덱스 값을 업데이트하는 맵
        Map<Integer, Integer> idxMap = new HashMap<>();
        
        int count = gems.length + 1;
        int[] answer = new int[2];
        int startIdx = 0;
        
        for (int i = 0; i < gems.length; i++) {
            int num = map.get(gems[i]);
            if (set.contains(num)) {
                // 이미 있는 num이라면
                idxMap.put(num, i);
                
                while (!q.isEmpty()) {
                    int frontNum = q.peek();
                    // q의 맨앞의 종류가 num이라면
                    if (frontNum == num) {
                        q.poll();
                        startIdx++;    
                        continue;
                    } else if (idxMap.get(frontNum) > startIdx) {
                        // 맨앞의 종류가 num이 아니더라도 이미 큐에 존재하는 다른 숫자가 있다면
                        q.poll();
                        startIdx++;
                        continue;
                    }
                    break;
                    
                }
            } else {
                // 없는 num이라면
                set.add(num);
                idxMap.put(num, i);
                    
                // 또 들어내기 작업..
                while (set.size() == n) {
                    int newSize = i - startIdx + 1;
                    if (newSize < count) {
                        count = newSize;
                        answer[0] = startIdx + 1;
                        answer[1] = i + 1;
                    }

                    int frontNum = q.poll();
                    if (idxMap.get(frontNum) == startIdx) {
                        set.remove(frontNum);
                        idxMap.remove(frontNum);
                    }
                    startIdx++; 
                }
                    
            }
            
            // 큐에 num 추가
            q.offer(num);

        }
        
        
        return answer;
    }
}