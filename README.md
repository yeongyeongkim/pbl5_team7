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

- 단일 이슈, 단일 브랜치, 단일 커밋

```shell
# 커밋을 수정해야 할 경우
git commit --amend
```
