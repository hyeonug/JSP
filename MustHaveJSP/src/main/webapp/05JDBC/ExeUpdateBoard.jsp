<%@page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 등록 처리</title>
</head>
<body>
<h2>회원 등록 처리 결과</h2>
<%
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String id = request.getParameter("id");

    if (id != null && content != null && title != null) {
        JDBConnect jdbc = new JDBConnect();

        try {
            String sql = "INSERT INTO board (title, content, id) VALUES (?, ?, ?)";
            jdbc.psmt = jdbc.con.prepareStatement(sql);

            jdbc.psmt.setString(1, title);
            jdbc.psmt.setString(2, content);
            jdbc.psmt.setString(3, id);

            int inResult = jdbc.psmt.executeUpdate();

            out.println("<p>" + inResult + "명의 회원이 성공적으로 등록되었습니다.</p>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>오류 발생: " + e.getMessage() + "</p>");
        } finally {
            jdbc.close();
        }
    } else {
        out.println("<p>입력 값이 부족합니다. 다시 시도해주세요.</p>");
    }
%>

</body>
</html>