<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>사용자가 작성한 board</title>
</head>
<body>
    <h2>사용자가 작성한 board</h2>
    <form action="ExeUpdateBoard.jsp" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <td>num</td>
                <td><input type="text" name="num" required></td>
            </tr>
            <tr>
                <td>title</td>
                <td><input type="text" name="content" required></td>
            </tr>
            <tr>
                <td>id</td>
                <td><input type="text" name="id" required></td>
            </tr>
            <tr>
                <td>postdate</td>
                <td><input type="text" name="postdate" required></td>
            </tr>
            <tr>
                <td>visitcount</td>
                <td><input type="text" name="visitcount" required></td>
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
