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
    String id = request.getParameter("id");
    String pass = request.getParameter("pass");
    String name = request.getParameter("name");

    if (id != null && pass != null && name != null) {
        JDBConnect jdbc = new JDBConnect();

        try {
            String sql = "INSERT INTO member (id, pass, name) VALUES (?, ?, ?)";
            jdbc.psmt = jdbc.con.prepareStatement(sql);

            jdbc.psmt.setString(1, id);
            jdbc.psmt.setString(2, pass);
            jdbc.psmt.setString(3, name);

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