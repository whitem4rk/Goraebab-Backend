-- 사용자 생성
CREATE USER root IDENTIFIED BY root;

-- 사용자에게 권한 부여 (테이블 생성 및 데이터 조작 가능)
GRANT CONNECT, RESOURCE TO root;
