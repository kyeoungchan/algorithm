# 촛불 이벤트
[문제](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AXGBKzuaPOoDFAXR)

### 📝 오답노트
long 자료형으로 시작해도 범위 초과가 발생해서 적잖이 당황한 문제다.  
파라메트릭 서치를 사용할 때 right의 범위를 줄이는 것도 중요하다.  
나는 right=N으로 시작했더니 (1≤N≤10^18) long의 범위를 초과해서 문제가 발생하였다.  
```java
/*
* t는 totalCandles 줄임말이다.
* N = t/(t-1)/2
* 2N = t^2 -t
* 2N = (t - 1/2)^2 + 1/4
* 2N - 1/4 = (t-1/2)^2
* 2*Math.sqrt(2N-1/4)+1 = t*/
```
소스코드에서 계산한 결과 right는 최대 `2*Math.sqrt(2*N - 1/4) + 1`이 된다.  
따라서 right는 10^9보다는 클 수 있고, 여유분을 둬도 10^10보다는 작으므로 right를 10^9으로 제한두면 long의 범위를 초과하지 않고도 정답이 도출된다.
