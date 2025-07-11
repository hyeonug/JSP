<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 등록 폼</title>
</head>
<body>
    <h2>회원 등록</h2>
    <form action="ExeUpdateMember.jsp" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <td>ID</td>
                <td><input type="text" name="id" required></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="pass" required></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" required></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="등록">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
