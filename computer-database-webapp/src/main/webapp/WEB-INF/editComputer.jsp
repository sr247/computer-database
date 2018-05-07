<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard"> Application - Computer
			Database </a>
	</div>
	</header>
	<section id="main">
	<div class="container">
	<!-- Error/Success messages -->
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<div class="label label-default pull-right">id: ${selectedComputer.id}
				</div>
				<h1>Edit Computer</h1>

				<form:form action="editComputer" method="POST" modelAttribute="ComputerDTO">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" class="form-control" name="name" id="name" 
								value="<c:out value="${selectedComputer.name}"/>"
								required>
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" name="introduced" id="introduced" 
								value="<c:out value="${selectedComputer.introduced}"/>"
								date-validation="date">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" name="discontinued" id="discontinued"
								value="<c:out value="${selectedComputer.discontinued}"/>"
								data-validation="date">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> 
							<form:select class="form-control" id="companyId" name="companyId" path="company.id">
								<c:forEach var="c" items="${companies}">
									<c:choose>
										<c:when test="${c.id == selectedComputer.company.id}">
											<form:option value="${c.id}" selected="selected">${c.name}</form:option>
										</c:when>
										<c:when test="${c.id != selectedComputer.company.id}">
											<form:option value="${c.id}">${c.name}</form:option>
										</c:when>
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" class="btn btn-primary">
						or <a href="dashboard" class="btn btn-default">Cancel</a>
					</div>
				</form:form>						

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