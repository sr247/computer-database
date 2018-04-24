<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> --%>
    
<%--     <c:url value="dashboard.jsp" var="dash">
	 	<c:param name="page"   value="${pagesComputer.current}" />
	 	<c:param name="stride"    value="${pagesComputer.stride}" />
	</c:url> --%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<!-- Le répertoire Racine est webapp -->
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
            <h1 id="homeTitle">
               ${pagesComputer.numberOfElements} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Delete</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
	            <tbody id="results">
	            	<c:forEach var="computer" items="${pagesComputer.content}">
    	                <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
	                        </td>
	                        <td>
	                            <a href="#" onclick="location.href='editComputer?id=${computer.id}'"> ${computer.name} </a>
	                        </td>
	                        <td> ${computer.introduced}   </td>
	                        <td> ${computer.discontinued} </td>
	                        <td> ${computer.companyName}  </td>
    	                </tr>
                    </c:forEach>
                </tbody>
                
            </table>
        </div>
    </section>
    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
					<a href="#" aria-label="Next" onclick="location.href='dashboard?page=1'">
                    	<span aria-hidden="true">First</span>
                	</a> 
                    <a href="#" aria-label="Previous" onclick="location.href='dashboard?page=${current-1}'">
                    	<span aria-hidden="true">&larr;</span>
					</a>
				</li>				
				<!-- Focus de Pages -->
				<c:forEach var="i" begin="${focus-2}" end="${focus+2}" step="1">
					<c:choose>
						<c:when test="${i == currentPage}">
						<li><a style="background-color:WhiteSmoke;" href="#" onclick="location.href='dashboard?page=${i}'">${i}</a></li>
						</c:when>						
						<c:when test="${i != currentPage}">
						<li><a href="#" onclick="location.href='dashboard?page=${i}'">${i}</a></li>
						</c:when>
					</c:choose>
				</c:forEach>
				<li>
                	<a href="#" aria-label="Next" onclick="location.href='dashboard?page=${currentPage+1}'">
                    	<span aria-hidden="true">&rarr;</span>
                	</a>     
                	<a href="#" aria-label="Next" onclick="location.href='dashboard?page=${pagesComputer.numberOfPages}'">
                    	<span aria-hidden="true">Last</span>
                	</a>            	
            	</li> 
        	</ul>
        	
            <div class="btn-group btn-group-sm pull-right" role="group" >
	            <!-- Gérer les url via tag lib -->
                <button type="button" class="btn btn-default" onclick="location.href='dashboard?stride=10'">10</button>
                <button type="button" class="btn btn-default" onclick="location.href='dashboard?stride=50'">50</button>
                <button type="button" class="btn btn-default" onclick="location.href='dashboard?stride=100'">100</button>
            </div>
        </div>
    </footer>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>

</body>
</html>