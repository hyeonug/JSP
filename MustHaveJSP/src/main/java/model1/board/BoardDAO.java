
package model1.board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

public class BoardDAO extends JDBConnect {

    public BoardDAO(ServletContext application) {
        super(application);
    }

    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM board ";

        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%'";
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("게시물 수를 구하는 중 예외 발생");
            e.printStackTrace();
        }
        return totalCount;
    }

    public List<BoardDTO> selectList(Map<String, Object> map) {
        List<BoardDTO> bbs = new Vector<BoardDTO>();
        String query = "SELECT * FROM board";

        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%'";
        }

        query += " ORDER BY num DESC";

        try {
            Statement stmt = con.createStatement(); // getCon() → con
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                BoardDTO dto = new BoardDTO();
                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));
                bbs.add(dto);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return bbs;
        
    }
    public List<BoardDTO> selectListPage(Map<String, Object> map) {
        List<BoardDTO> bbs = new Vector<BoardDTO>();

        String query = "SELECT * FROM board ";

        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " LIKE ?";
        }

        query += " ORDER BY num DESC LIMIT ?, ?";

        try {
            psmt = con.prepareStatement(query);

            int paramIndex = 1;

            if (map.get("searchWord") != null) {
                psmt.setString(paramIndex++, "%" + map.get("searchWord") + "%");
            }

            int start = Integer.parseInt(map.get("start").toString());
            int end = Integer.parseInt(map.get("end").toString());

            int offset = start - 1;
            int limit = end - offset;

            psmt.setInt(paramIndex++, offset);
            psmt.setInt(paramIndex++, limit);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                BoardDTO dto = new BoardDTO();
                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));
                bbs.add(dto);
            }
            rs.close();
            psmt.close();

        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return bbs;
    }

    public int insertWrite(BoardDTO dto) {
        int result = 0;
        try {
            String sql = "INSERT INTO board (title, content, id, postdate, visitcount) VALUES (?, ?, ?, NOW(), 0)";
            psmt = con.prepareStatement(sql);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getId());

            result = psmt.executeUpdate();

            System.out.println("insertWrite 성공 여부: " + result);  // 성공 시 1 출력됨
        } catch (Exception e) {
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }
    public BoardDTO selectView(String num) { 
        BoardDTO dto = new BoardDTO();

        String query = "SELECT B.*, M.name " 
                     + " FROM member M INNER JOIN board B " 
                     + " ON M.id=B.id "
                     + " WHERE num=?";

        try (
            PreparedStatement psmt = con.prepareStatement(query); // 지역 변수 선언
        ) {
            psmt.setString(1, num);    
            try (ResultSet rs = psmt.executeQuery()) {             // 지역 변수 선언 및 자동 close 처리
                if (rs.next()) {
                    dto.setNum(rs.getString(1)); 
                    dto.setTitle(rs.getString(2));
                    dto.setContent(rs.getString("content"));
                    dto.setPostdate(rs.getDate("postdate"));
                    dto.setId(rs.getString("id"));
                    dto.setVisitcount(rs.getString(6));
                    dto.setName(rs.getString("name")); 
                }
            }
        } catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }

        return dto; 
    }

    public void updateVisitCount(String num) { 
        // 쿼리문 준비 
        String query = "UPDATE board SET "
                     + " visitcount=visitcount+1 "
                     + " WHERE num=?";
        
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, num);  // 인파라미터를 일련번호로 설정 
            psmt.executeUpdate();     // 쿼리 실행 
        } 
        catch (Exception e) {
            System.out.println("게시물 조회수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }
    public int updateEdit(BoardDTO dto) { 
        int result = 0;
        
        try {
            // 쿼리문 템플릿 
            String query = "UPDATE board SET "
                         + " title=?, content=? "
                         + " WHERE num=?";
            
            // 쿼리문 완성
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getNum());
            
            // 쿼리문 실행 
            result = psmt.executeUpdate();
        } 
        catch (Exception e) {
            System.out.println("게시물 수정 중 예외 발생");
            e.printStackTrace();
        }
        
        return result; // 결과 반환 
    }
    public int deletePost(BoardDTO dto) { 
        int result = 0;

        try {
            // 쿼리문 템플릿
            String query = "DELETE FROM board WHERE num=?"; 

            // 쿼리문 완성
            psmt = con.prepareStatement(query); 
            psmt.setString(1, dto.getNum()); 

            // 쿼리문 실행
            result = psmt.executeUpdate(); 
        } 
        catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }
        
        return result; // 결과 반환
    }
}
