# Page snapshot

```yaml
- generic [active] [ref=e1]:
  - navigation [ref=e2]:
    - generic [ref=e3]:
      - link "게시판 서비스" [ref=e4] [cursor=pointer]:
        - /url: /board
      - generic [ref=e5]:
        - list [ref=e6]:
          - listitem [ref=e7]:
            - link "게시글" [ref=e8] [cursor=pointer]:
              - /url: /board
        - generic [ref=e9]:
          - link "로그인" [ref=e10] [cursor=pointer]:
            - /url: /login
          - link "회원가입" [ref=e11] [cursor=pointer]:
            - /url: /signup
  - generic [ref=e15]:
    - heading "로그인" [level=3] [ref=e16]
    - generic [ref=e17]:
      - generic [ref=e18]:
        - generic [ref=e19]: 사용자 ID (숫자)
        - spinbutton "사용자 ID (숫자)" [ref=e20]
      - generic [ref=e21]:
        - generic [ref=e22]: 비밀번호
        - textbox "비밀번호" [ref=e23]:
          - /placeholder: 비밀번호를 입력하세요
      - button "로그인" [ref=e25] [cursor=pointer]
    - generic [ref=e27]:
      - text: 계정이 없으신가요?
      - link "회원가입" [ref=e28] [cursor=pointer]:
        - /url: /signup
  - contentinfo [ref=e29]:
    - generic [ref=e30]: © 2025 Board Service. All rights reserved.
```