package programmers.수식복원하기;

import java.util.*;

public class Solution_pg_수식복원하기 {
    private String[] expressions;
    private List<Integer> answerIndexes;
    private boolean possibleSystem;

    public static void main(String[] args) throws Exception {

        Solution_pg_수식복원하기 sol = new Solution_pg_수식복원하기();
//        String[] res = sol.solution(new String[]{"14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"});
        String[] res = sol.solution(new String[]{"1 + 1 = 2", "1 + 3 = 4", "1 + 5 = X", "1 + 2 = X"});
        System.out.println(Arrays.toString(res));
    }

    public String[] solution(String[] expressions) throws Exception {
        this.expressions = expressions;
        answerIndexes = new ArrayList<>(); // X가 주어진 수식들의 인덱스를 담는 리스트
        setIndexesList();
        String[] answer = new String[answerIndexes.size()];

        List<Integer> numeralSystems = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            numeralSystems.add(i);
        }
        List<Integer> firstDigits = new ArrayList<>();
        List<Integer> secondDigits = new ArrayList<>();
        List<Integer> resultDigits = new ArrayList<>();
        for (int i = 0; i < expressions.length; i++) {
            char operator = parseExpression(firstDigits, secondDigits, resultDigits, expressions[i]);
            for (int j = numeralSystems.size() - 1; j >= 0; j--) {
                int numeralSystem = numeralSystems.get(j);
                possibleSystem = true;
                int firstValueInTenSystem = getValueInTenSystem(firstDigits, numeralSystem);
                if (!possibleSystem) {
                    numeralSystems.remove(j);
                    continue;
                }
                int secondValueInTenSystem = getValueInTenSystem(secondDigits, numeralSystem);
                if (!possibleSystem) {
                    numeralSystems.remove(j);
                    continue;
                }
                if (answerIndexes.contains(i)) {
                    continue;
                }
                int resultValueInTenSystem = getValueInTenSystem(resultDigits, numeralSystem);
                if (!possibleSystem) {
                    numeralSystems.remove(j);
                    continue;
                }
                int calculateVal = calculate(firstValueInTenSystem, operator, secondValueInTenSystem);
                if (calculateVal != resultValueInTenSystem) {
                    numeralSystems.remove(j);
                }
            }
        }

        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (int index: answerIndexes) {
            String expression = expressions[index];
            sb = new StringBuilder();
            int result = -1;
            boolean calculated = false;
            boolean questioned = false;
            sb.append(expression, 0, expression.length() - 1);
            for (int numeralSystem: numeralSystems) {
                char operator = parseExpression(firstDigits, secondDigits, resultDigits, expression);
                int firstValueInTenSystem = getValueInTenSystem(firstDigits, numeralSystem);
                int secondValueInTenSystem = getValueInTenSystem(secondDigits, numeralSystem);
                int calculateVal = calculate(firstValueInTenSystem, operator, secondValueInTenSystem);
                calculateVal = getValueInNumeralSystem(calculateVal, numeralSystem);
                if (!calculated) {
                    calculated = true;
                    result = calculateVal;
                } else if (calculateVal != result) {
                    sb.append("?");
                    questioned = true;
                    break;
                }
            }
            if (!questioned) {
                sb.append(result);
            }
            answer[i++] = sb.toString();
        }
        return answer;
    }

    private void setIndexesList() {
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].endsWith("X")) {
                answerIndexes.add(i);
            }
        }
    }

    private int getValueInNumeralSystem(int valueInTenSystem, int numeralSystem) {
        int result = 0;
        int i = 1;
        while (valueInTenSystem > 0) {
            result += (valueInTenSystem % numeralSystem) * i;
            valueInTenSystem /= numeralSystem;
            i *= 10;
        }
        return result;
    }

    private int getValueInTenSystem(List<Integer> digits, int numeralSystem) {
        int result = 0;
        int multi = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            int digit = digits.get(i);
            if (digit >= numeralSystem) {
                possibleSystem = false;
                break;
            }
            result += digit * multi;
            multi *= numeralSystem;
        }
        return result;
    }

    private int calculate(int firstOperand, char operator, int secondOperand) {
        if (operator == '+') {
            return firstOperand + secondOperand;
        } else if (operator == '-') {
            return firstOperand - secondOperand;
        }
        return firstOperand + secondOperand;
    }

    private char parseExpression(List<Integer> firstDigits, List<Integer> secondDigits, List<Integer> resultDigits, String expression) {
        firstDigits.clear();
        boolean firstEnd = false;
        secondDigits.clear();
        boolean secondEnd = false;
        resultDigits.clear();
        char operator = '?';
        for (int i = 0; i < expression.length(); i++) {
            char parsedChar = expression.charAt(i);
            if (parsedChar == ' ') {
                if (!firstEnd) firstEnd = true;
                else if (!secondEnd && !secondDigits.isEmpty()) secondEnd = true;
            } else if (isNumber(parsedChar)) {
                if (!firstEnd) {
                    firstDigits.add(parsedChar - '0');
                } else if (!secondEnd) {
                    secondDigits.add(parsedChar - '0');
                } else {
                    resultDigits.add(parsedChar - '0');
                }
            } else if (parsedChar == '+' || parsedChar == '-') {
                operator = parsedChar;
            }
        }
        return operator;
    }

    private boolean isNumber(char parsed) {
        int value = parsed - '0';
        return value >= 0 && value <= 9;
    }
}
