<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <form action="/guestbook4/add" method="post">
    <table border="1" width="550">
      <tr>
        <td>이름</td>
        <td><input type="text" name="name" value=""></td>
        <td>비밀번호</td>
        <td><input type="password" name="password" value=""></td>
      </tr>
      <tr>
        <td colspan="4"><textarea rows="10" cols="73" name="content"></textarea></td>
      </tr>
      <tr>
        <td colspan="4"><button type="submit">확인</button></td>
      </tr>
    </table>
  </form>
  <br>
  <br>
  <br>

  <c:forEach items="${gList}" var="gList">
    <form action="" method="post">
      <table border="1" width="550">
        <tr>
          <td>${gList.no}</td>
          <td>${gList.name}</td>
          <td>${gList.reg_date}</td>
          <td><a href="/guestbook4/deleteForm/${gList.no}">삭제</a></td>
        </tr>
        <tr>
          <td colspan="4">${gList.content}</td>
        </tr>
      </table>
    </form>
    <br>
  </c:forEach>


</body>
</html>