import java.util.*;

class Solution {
    
    /*
    노드: in-0,             out-2이상
    도넛: in-1,             out-1
    막대: in-0 하나, 1 여러개, out-0 하나, 1 여러개
    8자:  in-2 하나, 1 여러개, out-2 하나, 1 여러개
    */
    
    public int[] solution(int[][] edges) {
        Map<Integer, Integer> out = new HashMap<>();
        Map<Integer, Integer> in = new HashMap<>();
        int[] answer = new int[4];
        
        for (int[] edge: edges) {
            out.put(edge[0], out.getOrDefault(edge[0], 0) + 1);
            in.put(edge[1], out.getOrDefault(edge[1], 0) + 1);
        }
        
        for (int node: out.keySet()) {
            if (out.get(node) >= 2) {
                if (!in.containsKey(node)) {
                    answer[0] = node;
                } else {
                    answer[3]++;
                }
            }            
        }
        
        for (int node: in.keySet()) {
            if (!out.containsKey(node)) answer[2]++;
        }
        
        answer[1] = out.get(answer[0]) - answer[2] - answer[3];
        return answer;
        
    }
    
    
}