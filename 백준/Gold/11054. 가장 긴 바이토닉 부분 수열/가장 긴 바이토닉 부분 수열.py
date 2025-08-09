'''
가장 긴 바이토닉 부분 수열

수열 S가 오름차순 ~ 내림차순을 만족 -> 바이토닉 수열
오름차순 수열과 내림차순 수열도 바이토닉 수열에 포함된다.

이 문제는 LIS 문제 유형을 응용한 문제다.

DP를 활용하자.

입력
1. 수열 a의 크기 n
2. 수열 a

출력
가장 긴 바이토닉 수열의 길이
'''

n = int(input())
a = list(map(int, input().split()))

dpi = [1] * n  # 증가하는 부분 수열을 위한 리스트
dpd = [1] * n  # 감소하는 부분 수열을 위한 리스트

for i in range(1, n):
    for j in range(i):
        if a[j] < a[i]:
            dpi[i] = max(dpi[j] + 1, dpi[i])
# print(dpi)
a.reverse()  # 내림차순 부분 수열을 원소의 끝 부분을 기준으로 봐야하기 때문에, reverse 정렬시킨 후 오름차순으로 보는 것이 더 효율적이다.
# print(a)
for i in range(1, n):
    for j in range(i):
        if a[j] < a[i]:
            dpd[i] = max(dpd[j] + 1, dpd[i])

dpd.reverse()  # 다시 리버스 정렬을 시켜준다.
# print(dpd)
dpb = [1] * n  # 오름차순으로 갔다가 내림차순으로 가는 부분 수열의 길이를 보관할 리스트

for i in range(n):
    dpb[i] = dpi[i] + dpd[i] - 1  # 하나의 원소가 겹치므로 -1을 해준다.

print(max(dpb))
