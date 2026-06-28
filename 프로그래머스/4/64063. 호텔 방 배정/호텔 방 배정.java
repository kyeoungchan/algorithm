import java.util.*;

class Solution {
    
//     static class Room implements Comparable<Room> {
        
//     }
    
    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];
        TreeMap<Long,Long> tm = new TreeMap<>();
        for (int i = 0; i < room_number.length; i++) {
            long target = room_number[i];
            Long left = tm.floorKey(target);
            if (left == null) {
                answer[i] = target;
                
                if (tm.containsKey(target + 1)) {
                    // 오른쪽과 이어질 수 있으면
                    long right = tm.get(target + 1);
                    tm.remove(target + 1);
                    tm.put(target, right);
                } else {
                    tm.put(target, target);
                }
            } else {
                long right = tm.get(left);
                if (right < target) {
                    answer[i] = target;
                    long nRight = target;
                    
                    if (tm.containsKey(target+1)) {
                        // 오른쪽과 이어질 수 있다면
                        nRight = tm.get(target + 1);
                        tm.remove(target+1);
                    }
                    
                    if (right == target - 1) {
                        // 왼쪽과 겹치지는 않지만 이어질 수 있으면
                        tm.put(left, nRight);
                    } else {
                        // 이어질 수 없다면
                        tm.put(target, nRight);
                    }
                    
                } else {
                    // target이 기존에 있는 left~right에 겹쳐져 있다면
                    // right+1을 했을 때 없어야한다. 존재한다면 위에 잘못 체크한 게 있단거
                    long nRight = answer[i] = right + 1;
                    
                    if (tm.containsKey(nRight + 1)) {
                        // 새롭게 이어질 수 있다면
                        long tempKey = nRight + 1;
                        nRight = tm.get(tempKey);
                        tm.remove(tempKey);
                    }
                    
                    tm.put(left, nRight);
                }
            }
        }
        return answer;
    }
}