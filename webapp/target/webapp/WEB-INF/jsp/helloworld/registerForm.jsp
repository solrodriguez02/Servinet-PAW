<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<c:url value="/create" var="registerUrl"/>
<form action="${registerUrl}" method="post">
    <div>
        <label>
            Username:
            <input name="username" placeholder="username"/>
        </label>
    </div>
    <div>
        <input type="submit" value="Register!">
    </div>
</form>
</body>
</html>
