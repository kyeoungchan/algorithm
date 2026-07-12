import java.util.*;

class Solution {
    public String[] solution(String[] expressions) {
        Deque<String[]> resultQ = new ArrayDeque<>();
        
        boolean[] possible = new boolean[10];
        for (int i = 2; i <= 9; i++) possible[i] = true;
        
        for (String expression: expressions) {
            
            String[] arr = expression.split(" ");
            // System.out.println(Arrays.toString(arr));
            int calcAnswer = "X".equals(arr[4]) ? -1 : Integer.parseInt(arr[4]);
            for (int i = 2; i <= 9; i++) {
                if (!possible[i]) continue;
                Integer operand1 = genDecimal(arr[0], i);
                // System.out.println("base: " + i);
                // System.out.println("operand1: " + operand1);
                if (operand1 == null) {
                    possible[i] = false;
                    continue;
                }
                Integer operand2 = genDecimal(arr[2], i);
                // System.out.println("operand2: " + operand2);
                if (operand2 == null) {
                    possible[i] = false;
                    continue;
                }
                
                if (calcAnswer != -1) {
                    int calcDecimal = calc(operand1, arr[1], operand2);
                    // System.out.println("calcDecimal: " + calcDecimal);
                    int calcResult = genBase(calcDecimal, i);
                    // System.out.println("calcResult: " + calcResult);
                    // System.out.println();
                    if (calcResult != calcAnswer) {
                        possible[i] = false;
                    }    
                }
            }
             
            if (calcAnswer == -1) {
                resultQ.offer(arr);
            }
        }
        // System.out.println(Arrays.toString(possible));
        
        String[] answer = new String[resultQ.size()];
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        
        while(!resultQ.isEmpty()) {
            String[] cur = resultQ.poll();
            int result = -1;
            sb.setLength(0);
            
            for (int i = 2; i <= 9; i++) {
                if (!possible[i]) continue;
                int operand1 = genDecimal(cur[0], i);
                int operand2 = genDecimal(cur[2], i);
                int resultDecimal = calc(operand1, cur[1], operand2);
                int resultBase = genBase(resultDecimal, i);
                if (result == -1) {
                    result = resultBase;
                } else if (result != resultBase) {
                    result = -1;
                    break;
                }
            }
            sb.append(cur[0]).append(" ").append(cur[1]).append(" ").append(cur[2]).append(" ").append("=").append(" ");
            if (result == -1) sb.append("?");
            else sb.append(result);
            answer[idx++] = sb.toString();
        }
        
        return answer;
    }
    
    Integer genDecimal(String target, int base) {
        int result = 0;
        for (int i = 0; i < target.length(); i++) {
            int digit = target.charAt(i) - '0';
            // 예를 들어 2진수인데 한자릿수가 2이상일 수가 없다..
            if (digit >= base) return null;
            result *= base;
            result += digit;
        }
        return result;
    }
    
    int calc(int operand1, String operator, int operand2) {
        if ("+".equals(operator)) {
            return operand1 + operand2;
        } else {
            return operand1 - operand2;
        }
    }
    
    int genBase(int decimal, int base) {
        if (decimal == 0) return 0;
        StringBuilder sb = new StringBuilder();
        while (decimal > 0) {
            sb.append(decimal % base);
            decimal /= base;
        }
        sb.reverse();
        // System.out.println(sb);
        return Integer.parseInt(sb.toString());
    }
}