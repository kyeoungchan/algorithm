import java.util.*;

class Solution {
    
    public int[] solution(String[] operations) {
        
        TreeSet<Integer> ts = new TreeSet<>();
        StringTokenizer st;
        
        for (String op: operations) {
            st = new StringTokenizer(op);
            String op1 = st.nextToken();
            String op2 = st.nextToken();
            int num = Integer.parseInt(op2);
            
            if (op1.charAt(0) == 'I') {    
                ts.add(num);
            } else if (!ts.isEmpty()) {
                if (num == 1)
                    ts.pollLast();
                else
                    ts.pollFirst();    
            }
            // System.out.println(ts);
        }
        if (ts.isEmpty()) return new int[] {0, 0};
        
        int min = ts.first();
        int max = ts.last();
        return new int[] {max, min};
    }
}