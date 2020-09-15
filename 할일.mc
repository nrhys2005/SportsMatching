매칭 찾기 -> 매칭
팀 찾기 -> 팀
팀 정보 
	- 내 팀 정보 확인(team/myteam/id), 안쓰
		 - 팀원 리스트(team/myteam_list)
			- 내 팀원 추방(팀장)(보류)
		- 내 팀 탈퇴(team/myteam_drop)
		- 팀 정보 수정(팀장)(team/myteam/id)
매칭 정보
	- 매칭 리스트 -서버(match/mymatching_list)/:id), 안쓰
		- 매칭 정보 확인 (match/mymatching/:match_id), 안쓰
		- 매칭 취소(match/mymatching_cancel/:id,match_id),안쓰

결제 API

* 매치는 겹치면 안된다.			- 서버, 안쓰
* 매치는 정해진 인원수를 넘으면 안된다.	- 서버, 안쓰
* 매치 기간이 만료되면 삭제			- 서버?

매치 등록할때 matching_user에 추가 -상훈
매치 참가할때 matching_user에 추가 -상훈

*DB
매치 참가가능인원 수 추가(max_user)	-현석
팀장 추가	(master_id)		-현석


관리자모드?
