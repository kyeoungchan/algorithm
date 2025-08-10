package programmers.도둑질;

/*
* 이 마을에 있는 집은 3개 이상 1,000,000개 이하입니다.
* money 배열의 각 원소는 0 이상 1,000 이하인 정수입니다.
* */
public class Solution_pg_도둑질 {
    public int solution(int[] money) {
        int[] dpO = new int[money.length];
        int[] dpX = new int[money.length];
        dpO[0] = dpO[1] = money[0]; // 0번째 집을 털었다면 1번째 집에 대한 메모도 무조건 0번째 집의 가격
        dpX[1] = money[1]; // 1번째집부터 털기 시작했다면
        for (int i = 2; i < money.length; i++) {
            dpO[i] = Math.max(dpO[i - 1], dpO[i - 2] + money[i]);
            dpX[i] = Math.max(dpX[i - 1], dpX[i - 2] + money[i]);
        }
        // 결국에는 첫번째를 찍었다면 마지막보다 전의 애와 첫번째를 안 찍었을 때의 마지막 애를 비교해야한다!
        return Math.max(dpO[money.length - 2], dpX[money.length - 1]);
    }
}
