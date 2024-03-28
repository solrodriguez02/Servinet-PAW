<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<h2>Hello <c:out value="${user.username}"/> !</h2>
</body>
</html>
