import java.util.*;

public class Solution {
    private String[] expressions;
    private List<Integer> answerIndexes, completeIndexes;
    private boolean possbileSystem;

    public String[] solution(String[] expressions) throws Exception {
        this.expressions = expressions;
        answerIndexes = new ArrayList<>(); // X가 주어진 수식들의 인덱스를 담는 리스트
        completeIndexes = new ArrayList<>(); // 완성된 수식들의 인덱스를 담는 리스트
        setIndexesList();
        String[] answer = new String[answerIndexes.size()];

        List<Integer> numeralSystems = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            numeralSystems.add(i);
        }
        List<Integer> firstDigits = new ArrayList<>();
        List<Integer> secondDigits = new ArrayList<>();
        List<Integer> resultDigits = new ArrayList<>();
        for (int j = 0; j < expressions.length; j++) {
            char operator = parseExpression(firstDigits, secondDigits, resultDigits, expressions[j]);
            for (int i = numeralSystems.size() - 1; i >= 0; i--) {
                int numeralSystem = numeralSystems.get(i);
                possbileSystem = true;
                int firstValueInTenSystem = getValueInTenSystem(firstDigits, numeralSystem);
                if (!possbileSystem) {
                    numeralSystems.remove(i);
                    continue;
                }
                int secondValueInTenSystem = getValueInTenSystem(secondDigits, numeralSystem);
                if (!possbileSystem) {
                    numeralSystems.remove(i);
                    continue;
                }
                if (answerIndexes.contains(j)) continue;
                int resultValueInTenSystem = getValueInTenSystem(resultDigits, numeralSystem);
                if (!possbileSystem) {
                    numeralSystems.remove(i);
                    continue;
                }
                int calculateVal = calculate(firstValueInTenSystem, operator, secondValueInTenSystem);
                if (calculateVal != resultValueInTenSystem) {
                    numeralSystems.remove(i);
//                    System.out.println("numeralSystems = " + numeralSystems);
                }
            }
        }
//        System.out.println(numeralSystems);

        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (int index: answerIndexes) {
            String expression = expressions[index];
            sb = new StringBuilder();
            int result = -1;
            boolean calculated = false;
            boolean questioned = false;
//            sb.append(expression.substring(0, expression.length() - 1));
            sb.append(expression, 0, expression.length() - 1);
//            System.out.println("sb = " + sb);
            for (int j = numeralSystems.size() - 1; j >= 0; j--) {
                int numeralSystem = numeralSystems.get(j);
/*
                System.out.println("numeralSystem = " + numeralSystem);
                System.out.println("result = " + result);
*/
                char operator = parseExpression(firstDigits, secondDigits, resultDigits, expression);
                possbileSystem = true;
                int firstValueInTenSystem = getValueInTenSystem(firstDigits, numeralSystem);
                if (!possbileSystem) {
                    numeralSystems.remove(j);
                    continue;
                }
                int secondValueInTenSystem = getValueInTenSystem(secondDigits, numeralSystem);
                if (!possbileSystem) {
                    numeralSystems.remove(j);
                    continue;
                }
                int calculateVal = calculate(firstValueInTenSystem, operator, secondValueInTenSystem);
//                System.out.println("calculateVal = " + calculateVal);
                calculateVal = getValueInNumeralSystem(calculateVal, numeralSystem);
/*
                System.out.println("calculateVal = " + calculateVal);
                System.out.println();
*/
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
//            System.out.println("sb = " + sb);
        }
//        System.out.println(sb.toString());
        return answer;
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
/*
        System.out.println("digits = " + digits);
        System.out.println("numeralSystem = " + numeralSystem);
*/
        int result = 0;
        int multi = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            int digit = digits.get(i);
            if (digit >= numeralSystem) {
                possbileSystem = false;
                break;
            }
            result += digit * multi;
            multi *= numeralSystem;
        }
/*
        System.out.println("result = " + result);
        System.out.println();
*/
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
/*
        System.out.println("firstDigits = " + firstDigits);
        System.out.println("operator = " + operator);
        System.out.println("secondDigits = " + secondDigits);
        System.out.println("resultDigits = " + resultDigits);
*/
        return operator;
    }

    private void setIndexesList() {
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].endsWith("X")) {
                answerIndexes.add(i);
            } else {
                completeIndexes.add(i);
            }
        }
    }

    private boolean isNumber(char parsed) {
        int value = parsed - '0';
        return value >= 0 && value <= 9;
    }
}
