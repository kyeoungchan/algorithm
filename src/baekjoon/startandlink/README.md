# 스타트와 링크
https://www.acmicpc.net/problem/14889
입력1
```text
4
0 1 2 3
4 0 5 6
7 1 0 2
3 4 5 0
```
출력1
```text
0
```
입력2
```text
6
0 1 2 3 4 5
1 0 2 3 4 5
1 2 0 3 4 5
1 2 3 0 4 5
1 2 3 4 0 5
1 2 3 4 5 0
```
출력2
```text
2
```
입력3
```text
8
0 5 4 5 4 5 4 5
4 0 5 1 2 3 4 5
9 8 0 1 2 3 1 2
9 9 9 0 9 9 9 9
1 1 1 1 0 1 1 1
8 7 6 5 4 0 3 2
9 1 9 1 9 1 0 9
6 5 4 3 2 1 9 0
```
출력3
```text
1
```

### 배운점
나는 당연히 조합으로 팀을 나눈 후 순열로 점수를 계산하려고 했다. 하지만 2개를 뽑는 순열이라면 반복문을 활용하는 것이 훨씬 빠르다.
순열과 조합을 연습용으로 문제를 푼 것은 좋지만 성능을 더 높이고자한다면 순열을 반복문으로 활용하는 방안을 생각해보도록 하자.