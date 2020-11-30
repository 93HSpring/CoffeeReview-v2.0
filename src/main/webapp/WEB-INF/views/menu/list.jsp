<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<%@include file="../includes/header.jsp" %>

<body>

    <div id="wrapper">

		<!-- Navigation -->
        <%@include file="../includes/nav.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">메뉴</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <!-- 메뉴 목록 출력 -->
            <div class="row">
                <c:forEach items="${list}" var="menuInfo">
                    <div class="col-xs-6 col-sm-6 col-md-3">
                    	<a class='move' href='<c:out value="${menuInfo.mno}"/>'>
	                    	<div class="panel panel-default">
	                        	<div class="mname panel-heading text-center">
	                        		<c:out value="${menuInfo.menu}" />
	                        	</div>
	                        	<div class="panel-body p-0">
	                        		<img src='/menu/display?menuName=STARBUCKS/<c:out value="${menuInfo.menu}"/>' class="full-width">
	                        	</div>
	                        	<div class="panel-footer list-star text-center">
	                        		<i class="fa fa-star fa-fw m-0"></i>
	                        		<c:out value="${menuInfo.star_avg}" />
	                        		<b>[ <c:out value="${menuInfo.replyCnt}" /> ]</b>
	                        	</div>
	                        </div>
	                    	<!-- /.panel -->
	                    </a>
                	</div>
                	<!-- /.col-xs-3 -->
                </c:forEach>
            </div>
            <!-- 메뉴 목록 출력 끝 -->
            
            <!-- 페이징 처리 -->
            <div class="pull-right">
            	<ul class="pagination">
            		<c:if test="${pageMaker.prev}">
            			<li class="paginate_button previous">
            				<a href='<c:out value="${pageMaker.startPage - 1}"/>'>Previous</a>
            			</li>
            		</c:if>
            			
            		<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
            			<li class="<c:out value= "${pageMaker.cri.pageNum == num ? 'paginate_button active': 'paginate_button'}" />">
            				<a href='<c:out value="${num}"/>'>${num}</a>
            			</li>
            		</c:forEach>
            		
            		<c:if test="${pageMaker.next}">
            			<li class="paginate_button next">
            				<a href='<c:out value="${pageMaker.endPage + 1}"/>'>Next</a>
            			</li>
            		</c:if>            		
            	</ul>
            </div>
            <form id='actionForm' action="/menu/list" method='get'>
            	<input type='hidden' name='cafe' value='<c:out value="${pageMaker.cri.cafe}" />'>
            	<input type='hidden' name='keyword' value='<c:out value="${pageMaker.cri.keyword}" />'>
				<input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}" />'>
				<!-- <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}" />'> -->
			</form>
            <!-- 페이징 처리 끝 -->            
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- plugin-js -->
    <%@include file="../includes/plugin_js.jsp" %>
    
    <!-- import JS -->
   	<script type="text/javascript" src="/resources/js/common.js?ver=0.1.1"></script>
    
    <script>
    
    	$(document).ready(function() {
    		
    		var actionForm = $("#actionForm");
    		
    		// 페이지를 눌러 이동 시
    		$(".paginate_button a").on("click", function(e) {
    			
    			// 현재 페이지와 같은 페이지를 클릭할 경우
				if (${pageMaker.cri.pageNum} == $(this).attr("href") ) {
    				
    				console.log("같은 페이지");
    				return false;
    				
    			}

				e.preventDefault();

				console.log('click');

				actionForm.find("input[name=pageNum]").val($(this).attr("href"));
				
				actionForm.submit();
			});
    		
    		// 메뉴 정보로 이동 시
    		$(".move").on("click", function(e) {
    			
    			e.preventDefault();
    			actionForm.append("<input type='hidden' name='mno' value='" + $(this).attr("href") + "'>");   
    			actionForm.attr("action", "/menu/info");
    			actionForm.submit();
    			
    		});
    
    	});
    
    </script>

</body>

</html>
