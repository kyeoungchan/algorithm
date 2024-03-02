# 차량 정비소
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV6c6bgaIuoDFAXy

### 배운 점
- 구현 문제에서 동시간대에 어떻게 처리하는지 예시를 잘 봐두는 게 좋다.
  - 접수 창구에서 손님이 떠나자마자 바로 다음 손님을 앉힌다.
  - 따라서 동시간대에 기다리는 손님을 앉히는 작업보다, 접수 중인 손님들 처리 작업을 먼저한 후 앉히는 작업을 해야했다.
- 그리고 접수 창구 개수 N과 정비 창구 개수 M을 헷갈려서 무한루프가 발생하기도 하였다.
  - 즉, `int[] occupyingRepair = new int[N + 1];` 이렇게 M이 아닌 N으로 작성했던 것이다.
  - 복붙을 사용한다면 특히 조심해야하는 것이 코딩이다.