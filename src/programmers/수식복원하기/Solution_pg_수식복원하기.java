package programmers.수식복원하기;

import java.util.*;

public class Solution_pg_수식복원하기 {
    public static void main(String[] args) throws Exception {
    }

    class Solution {
        public String[] solution(String[] expressions) {
            int minNumeralSystem = 9;// 최대 9진법이고, 점차 깎아나가는 방식으로 접근한다.
            List<String[]> newExpressions = new ArrayList<>();
            List<String[]> answerExpressions = new ArrayList<>();


            for (String expression : expressions) {
                String[] tokens = expression.split(" ");
                if (tokens[4].equals("X")) {
                    answerExpressions.add(tokens);
                } else {
                    newExpressions.add(tokens);
                }
            }

            for (String[] expression : newExpressions) {
                int numeralSystem = getNumeralSystem(expression, minNumeralSystem);
                minNumeralSystem = Math.min(minNumeralSystem, numeralSystem);
            }

            String[] answer = new String[answerExpressions.size()];
            int i = 0;
            for (String[] expression : answerExpressions) {
                int value = calculateNumeralValue(expression[0], expression[2], expression[1], minNumeralSystem);
                String valueInNumeralSystem = getValueInNumeralSystem(value, minNumeralSystem);
                answer[i] = expression[0] + " " + expression[1] + " " + expression[2] + " = " + valueInNumeralSystem;
            }

            return answer;
        }
        int getNumeralSystem(String[] expression, int maxNumeralSystem) {
            System.out.println("***getNumeralSystem***");
            System.out.println("expression = " + Arrays.toString(expression));
            System.out.println("maxNumeralSystem = " + maxNumeralSystem);
            while (true) {

                int value = calculateNumeralValue(expression[0], expression[2], expression[1], maxNumeralSystem);
                int n3 = generateNumeralValue(expression[4], maxNumeralSystem);
                System.out.println("n3 = " + n3);
                System.out.println("maxNumeralSystem = " + maxNumeralSystem);
                if (value == n3) {
                    return maxNumeralSystem;
                }
                maxNumeralSystem--;
            }
        }

        String getValueInNumeralSystem(int value, int numeralSystem) {
            StringBuilder sb = new StringBuilder();
            while (value > 0) {
                sb.append(value % numeralSystem);
                value /= numeralSystem;
            }
            sb.reverse();
            return sb.toString();
        }

        int calculateNumeralValue(String nStr1, String nStr2, String operand, int numeralSystem) {
            System.out.println("***calculateNumeralValue***");
            int n1 = generateNumeralValue(nStr1, numeralSystem);
            System.out.println("n1 = " + n1);
            int n2 = generateNumeralValue(nStr2, numeralSystem);
            System.out.println("n2 = " + n2);

            int value = 0;
            if (operand.equals("+")) {
                value = n1 + n2;
            } else {
                value = n1 - n2;
            }
            System.out.println("operand = " + operand);
            System.out.println("value = " + value);
            return value;
        }

        int generateNumeralValue(String numberStr, int numeralSystem) {
            System.out.println("**generateNumeralValue**");
            System.out.println("numberStr = " + numberStr);
            System.out.println("numeralSystem = " + numeralSystem);
            int value = 0;
            int multiple = 1;
            for (int i = numberStr.length() - 1; i >= 0; i--) {
                int digit = numberStr.charAt(i) - '0';
                value += multiple * digit;
                multiple *= numeralSystem;
            }
            System.out.println("value = " + value);
            System.out.println();
            return value;
        }
    }
}
