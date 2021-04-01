# pbl5_team7 2021


# 개발 환경 설정 방법

```shell
git clone https://github.com/yeongyeongkim/pbl5_team7.git
git config user.name "YOUR NAME"
git config user.email "YOUR EMAIL"
git config credential.helper store

git checkout -b <#<issue number>>
```

# commit 하는 방법
```shell
git add .
git commit -m "#<issue number> meaningful commit message"

# 초기 푸쉬일 경우
git push --set-upstream origin <#<issue number>>


# 이후 푸쉬
git push

# 기존 remote commit을 덮어씌울 경우
git push -f
```

# 개발 컨밴션

## 단일 이슈, 단일 브랜치, 단일 커밋
특정한 이슈가 생길 경우,
1. Issues에 issue 생성
2. issue number로 브랜치 생성
ex)
```shell
git checkout -b "#12345"
# push example
git push --set-upstream origin "#12345"
```
3. 커밋 메시지의 최선두에 이슈 번호 마킹
```
#12345 이것은 commit 예제입니다.
```

4. 커밋에 변경사항이 필요해서 수정이 필요한 경우
수정사항을 만들고, 기존 커밋을 amend 한다.
```shell
# 커밋을 수정해야 할 경우
git commit --amend
```

이미 remote에 push를 했을 경우 force을 주어서 덮어씌운다.

```shell
# 기존 remote commit을 덮어씌울 경우
git push -f
```


## 질문은 이슈 생성 후 question label을 붙여서 할 것
중복되는 질문을 방지하고, 어떠한 concern이 있었는지 명확하게 하기 위함이다.

## 자료조사
특정 영역에 대해서 자료조사가 진행된 경우, 조사한 자료를 (그것이 rough한 것이라고 할 지라도) 본 repository wiki에 반드시 업데이트 할 것
