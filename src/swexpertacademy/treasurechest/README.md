# 보물상자 비밀번
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRUN9KfZ8DFAUo

## 배운 점
1. TreeSet이든, PQ든 역순정렬은 `Comparator.reverseOrder()`다!!
   - 직접 구현을 하면 `Comparator.reverseOrder()`에 비해서 성능이 훨씬 떨어진다.
2. String형도 기본적으로 오름차순으로 정렬할 수 있게 되어있다.
   - 심지어 1이 A보다 작은 수로도 java가 판별해준다.
   - 따라서 이 문제에서 `TreeSet<String> ts = new TreeSet<>(Comparator.reverseOrder());`에 `ts.add("F12"); ts.add("123");`으로 넣어주면 자동으로 "123", "F12" 순으로 넣어준다.