# 문제 추천 시스템 Version 1
https://www.acmicpc.net/problem/21939

입력
```text
5
1000 1
1001 2
19998 78
2667 37
2042 55
8
add 1402 59
recommend 1
solved 1000
solved 19998
recommend 1
recommend -1
solved 1001
recommend -1
```
출력
```text
19998
1402
1001
2667
```

### 오답노트
1. 문제가 난이도가 다르고 같은 번호가 주어지는 경우를 읽었지만, 처음에는 Node 객체가 아닌 이차원 배열로 관리를 했는데, 문제 번호로써 `boolean[] solved`를 관리했더니 문제 번호의 범위가 너무 커서 공간 낭비가 심하고, PQ에 담겼지만 풀었다는 것을 solved 배열로 관리가 가능하다고 생각했지만, PQ에서 아직 값이 안 빠져나간 상태에서 새로운 같은 번호의 문제가 들어왔을 때 PQ에 같은 번호의 두 문제가 들어가게 되는 경우 처리할 수 없게 되었다.
   -> 그래서 결국 Node로 `equals()` 메서드를 오버라이딩하여 HashMap을 사용하였다.
2. recommend 명령어를 받았을 때 PQ에서 꺼내서 다시 넣어야하는데 그 과정을 깜빡하였다.