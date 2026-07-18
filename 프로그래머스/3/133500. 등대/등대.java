import java.util.*;

class Solution {
    
    boolean[] checked, isBright;
    List<Integer>[] graph;
    int answer;
    
    public int solution(int n, int[][] lighthouse) {
        graph = new List[n + 1];
        checked = new boolean[n + 1];
        isBright = new boolean[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        for (int[] lh: lighthouse) {
            int a = lh[0];
            int b = lh[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        
        answer = 0;
        
        // checked[1] = true;
        search(1);
        // System.out.println(Arrays.toString(isBright));
        return answer;
    }
    
    boolean search(int num) {
        checked[num] = true;
        
        if (graph[num].size() == 1 && checked[graph[num].get(0)]) {
            // 여기는 리프노드다.
            return false;
        }
        
        for (int next: graph[num]) {
            // checked된 애들은 부모 노드
            if (checked[next]) {
                continue;
            }
            if (!search(next)) {   
                isBright[num] = true;
            }
        }
        if (isBright[num]) {
            answer++;
        }
        return isBright[num];
    }
}