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
    // 1. POST 방식의 한글 깨짐 방지 처리
    request.setCharacterEncoding("UTF-8");

    // 2. 폼에서 전송된 값 받기
    String id = request.getParameter("id");
    String pass = request.getParameter("pass");
    String name = request.getParameter("name");

   
    if (id != null && !id.isEmpty() && pass != null && !pass.isEmpty() && name != null && !name.isEmpty()) {
        JDBConnect jdbc = new JDBConnect();

        try {
            String sql = "INSERT INTO member (id, pass, name) VALUES (?, ?, ?)";
            jdbc.psmt = jdbc.con.prepareStatement(sql);

            jdbc.psmt.setString(1, id);
            jdbc.psmt.setString(2, pass);
            jdbc.psmt.setString(3, name);

            int inResult = jdbc.psmt.executeUpdate();

            out.println("<h3>[처리 결과]</h3>");
            out.println("<p>" + inResult + "명의 회원이 성공적으로 등록되었습니다.</p>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>[오류 발생]</h3>");
            out.println("<p>오류 메시지: " + e.getMessage() + "</p>");
        } finally {
            jdbc.close();
        }
    } else {
        out.println("<h3>[오류 발생]</h3>");
        out.println("<p>입력 값이 부족합니다. 모든 항목을 입력해주세요.</p>");
    }
%>

</body>
</html>