# ⚙ Route-In Backend

Route-In 서비스의 백엔드 서버입니다.

Spring Boot 기반 REST API + WebSocket을 통해  
채팅, 알림, 게시글, 루틴, AI 추천 기능을 제공합니다.

---

## 🛠 Tech Stack

- Spring Boot
- MyBatis
- MySQL
- WebSocket (STOMP)
- JWT

---

## ⭐ 주요 기능

### 💬 실시간 채팅
- WebSocket 메시지 처리
- room_read 테이블 기반 읽음 관리
- unread 메시지 계산

### 🔔 알림
- Notification DB 저장
- 사용자별 WebSocket Push

### 📰 게시글
- CRUD
- 댓글 / 대댓글
- 추천 (1인 1추천 DB 제약)

### 🗺 러닝 코스
- 코스 저장
- 즐겨찾기
- 게시글 코스 복사

### 📅 주간 루틴
- 요일별 루틴 관리
- 체크 상태 저장

### 🤖 AI 운동 추천
- 날씨 + 운동 기록 기반 프롬프트
- 질문 / 답변 DB 저장

### 📊 인바디
- 날짜별 체중 / 근육량 / 체지방 저장

### ✅ 출석
- 하루 1회 UNIQUE 제약

---

## 🗄 Database

주요 테이블:

- user_tb
- message_tb
- room_tb
- room_participant_tb
- room_read_tb
- post_tb
- comment_tb
- notification_tb
- course_tb

---

## 🧨 트러블슈팅

### unread 계산 문제

- 원인: 읽음 기준 컬럼 부재
- 해결: room_read + last_read_message_id 도입

---
