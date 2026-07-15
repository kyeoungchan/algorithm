import java.math.BigInteger;

class Solution {
    public int[] solution(long[] numbers) {
        int len = numbers.length;
        int[] answer = new int[len];
        for (int i = 0; i < len; i++) {
            // 노드의 개수: 2의 제곱수 - 1, 단, 앞에 0을 추가해서 개수가 성립하면 가능
            // 1010 -> 0001010
            // 실제 노드의 부모가 더미노드가 될 수 없음
            String base = to2Base(numbers[i]);
            int baseLen = base.length();
            if (baseLen == 1) {
                if (Integer.parseInt(base) == 1) answer[i]++;
                continue;    
            }
            
            int rootIdx = baseLen / 2;
            if (base.charAt(rootIdx) == '0') {
                // 루트 인덱스는 0일 수 없다.
                continue;
            }
            
            if (checkSubTree(base.substring(0, rootIdx)) && checkSubTree(base.substring(rootIdx + 1, baseLen))) {
                answer[i]++;
            }
        }
        return answer;
    }
    
    String to2Base(long n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(n % 2);
            n /= 2;
        }
        
        long len = sb.length() + 1;
        
        double a = Math.log(len) / Math.log(2);
        // System.out.println(a);
        long b = (long) a;
        if (a % 1 > 0) b++;
        
        // System.out.println("b: " + b);
        long targetLen = (long) Math.pow(2, b);
        for (int i = 0; i < targetLen - len; i++) sb.append(0);
        
        sb.reverse();
        // System.out.println(sb);
        return sb.toString();
    }
    
    boolean checkSubTree(String tree) {
        // System.out.println(tree);
        int len = tree.length();
        if (len == 1) return true;
        int rootIdx = len / 2;
        if (tree.charAt(rootIdx) == '0') {
            // 루트 자리가 0이라면 좌우 모두 0이어야 한다.
            return BigInteger.valueOf(0).equals(new BigInteger(tree));
        }
        return checkSubTree(tree.substring(0, rootIdx)) && checkSubTree(tree.substring(rootIdx + 1, len));
    }
}