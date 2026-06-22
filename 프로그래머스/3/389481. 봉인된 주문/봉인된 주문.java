import java.util.*;

/*
a-1 ~ z-26     -> 1 + 0~25
aa-27 ~ az-52 -> 1+26*(1+0) + 0~25
ba-53 ~ bz-78 -> 1+26*(1+1) + 0~25
ca ~ cz       -> 1+26*(1+2) + 0~25
...
za ~ zz       -> 1+26*(1+25) + 0~25
aaa ~ aaz        -> 1+26*26 + 0~25
aba ~ abz     -> 1+26*26+26 + 0~25
*/
class Solution {
    
    public String solution(long n, String[] bans) {
        List<Long> unhasheds = new ArrayList<>(bans.length);
        for (String b: bans) {
            long order = unhash(b);
            unhasheds.add(order);
        }
        Collections.sort(unhasheds);
        // System.out.println(unhasheds);
        
        long code = getNum(unhasheds, n, 0);
        // System.out.println(code);
        
        return hash(code);
    }
    
    long unhash(String spell) {
        int len = spell.length();
        long result = spell.charAt(len-1) - 'a' + 1;
        long digit = 1;
        for (int i = len - 2; i >= 0; i--) {
            digit *= 26;
            result += (spell.charAt(i) - 'a' + 1) * digit;
        }
        return result;
    }
    
    String hash(long code) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append((char)('a' + (code + 25) % 26));
            if ((code - 1) / 26 == 0) break;
            code = (code - 1) / 26;
        }
        sb.reverse();
        return sb.toString();
    }
    
    long getNum(List<Long> list, long target, int start) {
        // System.out.println("target: " + target);
        // System.out.println("start: " + start);
        int left = start;
        int right = list.size() - 1;
        int idx = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                right = mid - 1;
            } else {
                idx = mid;
                left = mid + 1;
            }
        }
        
        // 모든 삭제된 주문이 n번째보다 뒷 순서라면 n
        if (idx == -1) return target;
        
        // 모든 삭제된 주문이 n번째보다 앞 순서라면 list 사이즈만큼 뒤로 댕겨서 확인하면 된다.
        if (idx == list.size() - 1) return target + list.size() - start;
        
        // 중간에 위치하면 idx만큼 뒤로 댕겨서 이분탐색 또 시작해봐야한다.
        return getNum(list, target + idx + 1 - start, idx + 1);
    }
}