<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" 
                                		id="computerName" name="computerName" placeholder="" 
                                		value="<c:out value="${computer.name}"/>"
                                		data-validation="required"
                                		data-validation="alphanumeric" 
                                		data-validation-allowing="- _/+"
                                		required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" 
                                		id="introduced" name="introduced" placeholder="" 
                                		value="<c:out value="${computer.introduced}"/>"
                                		data-validation="date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" 
                                		id="discontinued" name="discontinued" placeholder="" 
                                		value="<c:out value="${computer.discontinued}"/>"
                                		data-validation="date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <c:forEach var="company" items="${companies}">
                                        <c:choose>
                                            <c:when test="${company.name == computer.companyName}">
                                                <option value="${company.id}" selected="selected">${company.name}</option>
                                            </c:when>
                                            <c:when test="${company.name != computer.companyName}">
                                                <option value="${company.id}">${company.name}</option>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
<script src="static/js/jquery.min.js"></script>
<script src="static/js/jquery.validate.min.js"></script>
<script>
  $.validate({
    lang: 'fr'
  });
</script>
</body>
</html>