package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private DataSource dataSource;

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// DB 연결
	private void getConnection() {
		try {
			conn = dataSource.getConnection();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원 정리
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 방명록 리스트
	public List<GuestbookVo> getGuestbookList() {

		// DB 값을 가져와서 List로 저장
		List<GuestbookVo> guestList = new ArrayList<GuestbookVo>();

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String select = "";
			select += " SELECT ";
			select += "     no, ";
			select += "     name, ";
			select += "     password, ";
			select += "     content, ";
			select += "     reg_date ";
			select += " FROM ";
			select += "     guestbook ";
			select += " ORDER BY ";
			select += "     no ASC ";

			pstmt = conn.prepareStatement(select);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, reg_date);
				guestList.add(guestbookVo);
			}

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
		return guestList;
	}

	// 방명록 등록
	public void insert(GuestbookVo guestbookVo) {

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String insert = "";
			insert += " INSERT INTO guestbook VALUES ( ";
			insert += "     seq_guestbook_no.NEXTVAL, ";
			insert += "     ?, ";
			insert += "     ?, ";
			insert += "     ?, ";
			insert += "     sysdate ";
			insert += " ) ";

			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getContent());

			pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[" + guestbookVo.getName() + "님이 등록되었습니다.]");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
	}

	// 방명록 삭제
	public int delete(int no, String pw) {

		int count = -1;

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String delete = "";
			delete += " DELETE FROM guestbook ";
			delete += " WHERE ";
			delete += "         password = ? ";
			delete += "     AND no = ? ";

			pstmt = conn.prepareStatement(delete);
			pstmt.setString(1, pw);
			pstmt.setInt(2, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			if (count == 1) {
				System.out.println("[" + no + "번 글이 삭제되었습니다.]");
			} else if (count == 0) {
				System.out.println("비밀번호가 틀렸습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();

		return count;
	}
}
