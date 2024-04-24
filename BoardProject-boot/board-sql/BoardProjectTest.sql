﻿CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_EMAIL"	VARCHAR2(50)		NOT NULL,
	"MEMBER_PW"	VARCHAR2(100)		NOT NULL,
	"MEMBER_NICKNAME"	VARCHAR2(100)		NOT NULL,
	"MEMBER_TEL"	CHAR(11)		NOT NULL,
	"MEMBER_ADDRESS"	VARCHAR2(200)		NULL,
	"PROFILE_IMG"	VARCHAR2(300)		NULL,
	"ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"AUTHORITY"	NUMBER	DEFAULT 1	NOT NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원번호 (PK)';

COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '회원이메일';

COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '회원비밀번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME" IS '회원닉네임';

COMMENT ON COLUMN "MEMBER"."MEMBER_TEL" IS '회원전화번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS" IS '회원주소';

COMMENT ON COLUMN "MEMBER"."PROFILE_IMG" IS '프로필이미지';

COMMENT ON COLUMN "MEMBER"."ENROLL_DATE" IS '가입일';

COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '탈퇴여부(Y/N)';

COMMENT ON COLUMN "MEMBER"."AUTHORITY" IS '권한(1 일반 2 관리자)';

CREATE TABLE "UPLOAD_FILE" (
	"FILE_NO"	NUMBER		NOT NULL,
	"FILE_PATH"	VARCHAR2(500)		NOT NULL,
	"FILE_ORIGINAL_NAME" VARCHAR2(300)	NOT NULL,
	"FILE_RENAME"	VARCHAR2(100)		NOT NULL,
	"FILE_UPLOAD_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "UPLOAD_FILE"."FILE_NO" IS 'File No (PK)';

COMMENT ON COLUMN "UPLOAD_FILE"."FILE_PATH" IS 'File path';

COMMENT ON COLUMN "UPLOAD_FILE"."FILE_ORIGINAL_NAME" IS 'Original Name';

COMMENT ON COLUMN "UPLOAD_FILE"."FILE_RENAME" IS 'File rename';

COMMENT ON COLUMN "UPLOAD_FILE"."FILE_UPLOAD_DATE" IS 'Upload date';

COMMENT ON COLUMN "UPLOAD_FILE"."MEMBER_NO" IS '회원번호 (FK)';

CREATE TABLE "BOARD" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	NVARCHAR2(100)		NOT NULL,
	"BOARD_CONTENT"	NVARCHAR2(400)		NOT NULL,
	"BOARD_CREATED_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"BOARD_UPDATE_DATE"	DATE		NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_CODE"	NUMBER		NOT NULL
);


COMMENT ON COLUMN "BOARD"."BOARD_NO" IS 'Board Number (PK)';

COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS 'Board Title';

COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS 'Board Content';

COMMENT ON COLUMN "BOARD"."BOARD_CREATED_DATE" IS 'created date';

COMMENT ON COLUMN "BOARD"."BOARD_UPDATE_DATE" IS 'updated date';

COMMENT ON COLUMN "BOARD"."READ_COUNT" IS 'views';

COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS 'deleted(Y/N)';

COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '회원번호 (FK)';

COMMENT ON COLUMN "BOARD"."BOARD_CODE" IS 'Board Code (FK)';

CREATE TABLE "BOARD_TYPE" (
	"BOARD_CODE"	NUMBER		NOT NULL,
	"BOARD_NAME"	NVARCHAR2(100)		NOT NULL
);

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_CODE" IS 'Board Code (PK)';

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_NAME" IS 'Board Type';

CREATE TABLE "BOARD_LIKE" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_LIKE"."MEMBER_NO" IS '회원번호 (PK)';

COMMENT ON COLUMN "BOARD_LIKE"."BOARD_NO" IS 'Board Number';

CREATE TABLE "BOARD_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NOT NULL,
	"IMG_ORIGINAL_NAME"	NVARCHAR2(100)		NOT NULL,
	"IMG_RENAME"	NVARCHAR2(100)		NOT NULL,
	"IMG_ORDER"	NUMBER		NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_IMG"."IMG_NO" IS 'image number';

COMMENT ON COLUMN "BOARD_IMG"."IMG_PATH" IS 'image path';

COMMENT ON COLUMN "BOARD_IMG"."IMG_ORIGINAL_NAME" IS 'image original name';

COMMENT ON COLUMN "BOARD_IMG"."IMG_RENAME" IS 'image rename';

COMMENT ON COLUMN "BOARD_IMG"."IMG_ORDER" IS 'image order';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_NO" IS 'Board Number (FK)';

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	VARCHAR2(500)		NOT NULL,
	"COMMENT_CREATE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS 'comment number (PK)';

COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS 'comment content';

COMMENT ON COLUMN "COMMENT"."COMMENT_CREATE_DATE" IS 'comment created Date';

COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS 'comment deleted';

COMMENT ON COLUMN "COMMENT"."BOARD_NO" IS 'Board Number (FK)';

COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호 (FK)';

COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS 'parent comment number (FK)';

ALTER TABLE "MEMBER" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"MEMBER_NO"
);

ALTER TABLE "UPLOAD_FILE" ADD CONSTRAINT "PK_UPLOAD_FILE" PRIMARY KEY (
	"FILE_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "BOARD_TYPE" ADD CONSTRAINT "PK_BOARD_TYPE" PRIMARY KEY (
	"BOARD_CODE"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "PK_BOARD_LIKE" PRIMARY KEY (
	"MEMBER_NO",
	"BOARD_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "PK_BOARD_IMG" PRIMARY KEY (
	"IMG_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "UPLOAD_FILE" ADD CONSTRAINT "FK_MEMBER_TO_UPLOAD_FILE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_BOARD_TYPE_TO_BOARD_1" FOREIGN KEY (
	"BOARD_CODE"
)
REFERENCES "BOARD_TYPE" (
	"BOARD_CODE"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_LIKE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "FK_BOARD_TO_BOARD_LIKE_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "FK_BOARD_TO_BOARD_IMG_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_BOARD_TO_COMMENT_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_COMMENT_TO_COMMENT_1" FOREIGN KEY (
	"PARENT_COMMENT_NO"
)
REFERENCES "COMMENT" (
	"COMMENT_NO"
);

DROP TABLE "BOARD";
DROP TABLE "COMMENT";
COMMIT;
DROP TABLE "BOARD_LIKE";
DROP TABLE BOARD_TYPE;
DROP TABLE BOARD_IMG;

SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, BOARD_CODE, READ_COUNT,
	MEMBER_NO, MEMBER_NICKNAME, PROFILE_IMG,
	
	TO_CHAR(BOARD_WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') BOARD_WRITE_DATE, 
	TO_CHAR(BOARD_UPDATE_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') BOARD_UPDATE_DATE,
	
	(SELECT COUNT(*)
	 FROM "BOARD_LIKE"
	 WHERE BOARD_NO = 1960) LIKE_COUNT,
	
	(SELECT IMG_PATH || IMG_RENAME
	 FROM "BOARD_IMG"
	 WHERE BOARD_NO = 1960
	 AND   IMG_ORDER = 0) THUMBNAIL


FROM "BOARD"
JOIN "MEMBER" USING(MEMBER_NO)
WHERE BOARD_DEL_FL = 'N'
AND BOARD_CODE = 1
AND BOARD_NO = 1960;


---------------------------------------------


/* 상세조회 되는 게시글의 모든 이미지 조회 */
SELECT *
FROM "BOARD_IMG"
WHERE BOARD_NO = 1960
ORDER BY IMG_ORDER;


---------------------------------------------


/* 상세조회 되는 게시글의 모든 댓글 조회 */
/*계층형 쿼리*/
SELECT LEVEL, C.* FROM
	(SELECT COMMENT_NO, COMMENT_CONTENT,
	    TO_CHAR(COMMENT_WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') COMMENT_WRITE_DATE,
	    BOARD_NO, MEMBER_NO, MEMBER_NICKNAME, PROFILE_IMG, PARENT_COMMENT_NO, COMMENT_DEL_FL
	FROM "COMMENT"
	JOIN MEMBER USING(MEMBER_NO)
	WHERE BOARD_NO = 1960) C
WHERE COMMENT_DEL_FL = 'N'
OR 0 != (SELECT COUNT(*) FROM "COMMENT" SUB
				WHERE SUB.PARENT_COMMENT_NO = C.COMMENT_NO
				AND COMMENT_DEL_FL = 'N')
START WITH PARENT_COMMENT_NO IS NULL
CONNECT BY PRIOR COMMENT_NO = PARENT_COMMENT_NO
ORDER SIBLINGS BY COMMENT_NO
;
