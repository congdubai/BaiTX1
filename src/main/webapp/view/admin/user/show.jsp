<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Tables / Data - NiceAdmin Bootstrap Template</title>
<meta content="" name="description">
<meta content="" name="keywords">

<!-- Favicons -->
<link href="assets/img/favicon.png" rel="icon">
<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Option 1: Include in HTML -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link
	href="${pageContext.request.contextPath}/resources/css/boxicons.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/css/quill.snow.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/quill.bubble.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css">
<link
	href="${pageContext.request.contextPath}/resources/css/style-data.css"
	rel="stylesheet">
<!-- Template Main CSS File -->
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">
</head>

<body>
	<jsp:include page="../layout/header.jsp" />
	<jsp:include page="../layout/sidebar.jsp" />

	<main id="main" class="main">
		<div class="pagetitle">
			<h1>Quản lý người dùng</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
					<li class="breadcrumb-item"><a href="index.html">Quản lý</a></li>
					<li class="breadcrumb-item active">Quản lý người dùng</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<div class="d-flex justify-content-between">
								<h5 class="card-title">Danh sách người dùng</h5>

								<button class="btn btn-primary m-3 mb-2" data-bs-toggle="modal"
									data-bs-target="#addModal">Thêm người dùng</button>
							</div>

							<!-- Table with stripped rows -->
							<table class="table">
								<thead>
									<tr>
										<th class="col-1">ID</th>
										<th class="col-1">Ảnh </th>
										<th class="col-1">Email</th>
										<th class="col-2">Họ tên</th>
										<th class="col-1">Vai trò</th>
										<th class="col-2 ps-5">chức năng</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="user" items="${Lists}">
										<tr>
											<td>${user.user_id}</td>
											<td><img style="width: 50px; height: 50px;" src="${pageContext.request.contextPath}/resources/images/${user.user_avatar}"></td>
											<td>${user.user_email}</td>
											<td>${user.user_fullname}</td>
											<td>${user.user_role_id}</td>
											<td><a class="btn btn-success"
												href="/BaiTX1/loadUser?sId=${user.user_id}&user-action=detailform">Chi tiết</a>
												<a class="btn btn-warning"
												href="/BaiTX1/loadUser?sId=${user.user_id}&user-action=editform">Cập nhật</a>
												<a class="btn btn-danger"
												href="/BaiTX1/loadUser?sId=${user.user_id}&user-action=deleteform">Xóa</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- End Table with stripped rows -->

							<!-- Start Page panning -->
							<nav aria-label="Page navigation example">
								<ul class="pagination justify-content-center">
									<li class="page-item"><a
										class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
										href="?page=${currentPage - 1}" aria-label="Previous"> <span
											aria-hidden="true">&laquo;</span>
									</a></li>
									<c:forEach begin="0" end="${totalPage-1}" varStatus="loop">
										<li class="page-item"><a
											class="${(loop.index+1) eq currentPage ? 'active page-link' : 'page-link'}"
											href="?page=${loop.index+1}">${loop.index+1}</a></li>
									</c:forEach>
									<li class="page-item"><a
										class="${totalPage eq currentPage ? 'disabled page-link' : 'page-link'}"
										href="?page=${currentPage + 1}" aria-label="Next"> <span
											aria-hidden="true">&raquo;</span>
									</a></li>
								</ul>
							</nav>
							<!-- End Page panning -->
						</div>
					</div>

				</div>
			</div>
		</section>


		<!-- check delete id -->
		<c:if test="${not empty userIdToDelete}">
			<script>
				// Sử dụng JavaScript để mở modal tự động khi có userIdToDelete
				window.onload = function() {
					var deleteModal = new bootstrap.Modal(document
							.getElementById('deleteModal'));
					deleteModal.show();
				};
			</script>
		</c:if>

		<!--Start Form Xóa-->
		<form method="Post" action="/BaiTX1/loadUser?user-action=delete"
			class="modal" id="deleteModal">
			<div class="modal-dialog ">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Xác nhận xóa</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<!-- Modal body -->
					<div class="modal-body row">Bạn chắc chắn xóa người dùng này?
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" class="btn btn-danger">Đồng ý</button>
						<button type="button" class="btn btn-danger"
							data-bs-dismiss="modal">Thoát</button>
					</div>
				</div>
			</div>
		</form>
		<!--End Form Xóa-->

		<!-- Start Form Thêm -->
		<form method="Post" action="/BaiTX1/loadUser?user-action=add"
			class="modal" id="addModal">
			<div class="modal-dialog modal-lg ">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Thêm người dùng</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body row">
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Email:</label> <input type="email"
								class="form-control" name="user_email" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Mật khẩu:</label> <input
								type="password" class="form-control" name="user_pass" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Họ và Tên:</label> <input type="text"
								class="form-control" name="user_fullname" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Ảnh đại diện:</label> <input type="text"
								class="form-control" name="user_avatar" />
						</div>
						<div class="mb-3 col-12">
							<label class="form-label">Địa chỉ:</label> <input type="text"
								class="form-control" name="user_address" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Vai trò:</label> <select
								class="form-select" name="user_role_id">
								<option value="ADMIN">ADMIN</option>
								<option value="USER">USER</option>
							</select>
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Giới tính:</label> <select
								class="form-select" name="user_gender">
								<option value="Nam">Nam</option>
								<option value="Nữ">Nữ</option>
							</select>
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Điện thoại:</label> <input type="text"
								class="form-control" name="user_phone" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Ngày Sinh:</label> <input type="text"
								class="form-control" name="user_birthday" />
						</div>
						<div class="col-12 mt-3 text-end">
							<button type="submit" class="btn btn-primary">Đồng ý</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- End Form Thêm -->

		<!-- check delete id -->
		<c:if test="${not empty userIdEdit}">
			<script>
				// Sử dụng JavaScript để mở modal tự động khi có userIdToDelete
				window.onload = function() {
					var editModal = new bootstrap.Modal(document
							.getElementById('editModal'));
					editModal.show();
				};
			</script>
		</c:if>
		<!-- Start Form Sửa -->
		<form method="Post" action="/BaiTX1/loadUser?user-action=edit"
			class="modal" id="editModal">
			<div class="modal-dialog modal-lg ">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Sửa người dùng</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body row">
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">ID:</label> <input type="text"
								class="form-control" name="user_id" value="${user_id}" disabled />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Email:</label> <input type="email"
								class="form-control" name="user_email" value="${user_email}"
								disabled />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Họ và Tên:</label> <input type="text"
								class="form-control" name="user_fullname"
								value="${user_fullname}" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Ảnh đại diện:</label> <input type="text"
								class="form-control" name="user_avatar" value="${user_avatar}" />
						</div>
						<div class="mb-3 col-12">
							<label class="form-label">Địa chỉ:</label> <input type="text"
								class="form-control" name="user_address" value="${user_address}" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Vai trò:</label> <select
								class="form-select" name="user_role_id">
								<option value="ADMIN"
									${user_role_id == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
								<option value="USER" ${user_role_id == 'USER' ? 'selected' : ''}>USER</option>
							</select>
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Giới tính:</label> <select
								class="form-select" name="user_gender">
								<option value="Nam" ${user_gender == 'Nam' ? 'selected' : ''}>Nam</option>
								<option value="Nữ" ${user_gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
							</select>
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Điện thoại:</label> <input type="text"
								class="form-control" name="user_phone" value="${user_phone}" />
						</div>
						<div class="mb-3 col-12 col-md-6">
							<label class="form-label">Ngày Sinh:</label> <input type="text"
								class="form-control" name="user_birthday"
								value="${user_birthday}" />
						</div>
						<div class="col-12 mt-3 text-end">
							<button type="submit" class="btn btn-warning">Đồng ý</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- End Form sửa -->


		<!-- check detail id -->
		<c:if test="${not empty userIdDetail}">
			<script>
				// Sử dụng JavaScript để mở modal tự động khi có userIdToDelete
				window.onload = function() {
					var detailModal = new bootstrap.Modal(document
							.getElementById('detailModal'));
					detailModal.show();
				};
			</script>
		</c:if>
		<!-- Start form detail -->
		<div class="modal" id="detailModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Chi tiết nguời dùng</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="card">
							<ul class="list-group list-group-flush">
								<li class="list-group-item"><img style="width: 160px; height: 170px;" src="${pageContext.request.contextPath}/resources/images/${user_avatar}"></li>
								<li class="list-group-item">ID: ${user_id}</li>
								<li class="list-group-item">Email: ${user_email}</li>
								<li class="list-group-item">Mật khẩu: ${user_pass}</li>
								<li class="list-group-item">Họ và tên:
									${user_fullname}</li>
								<li class="list-group-item">Giới tính: ${user_gender}</li>
								<li class="list-group-item">Địa chỉ: ${user_address}</li>
								<li class="list-group-item">Điện thoại: ${user_phone}</li>
								<li class="list-group-item">Vai trò: ${user_role_id}</li>
								<li class="list-group-item">Ngày tạo: ${user_create_at}</li>
							</ul>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-success"
							data-bs-dismiss="modal">Trở về</button>
					</div>

				</div>
			</div>
		</div>

		<!-- End form detail -->
	</main>
	<!-- End #main -->

	<jsp:include page="../layout/footer.jsp" />

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script
		src="${pageContext.request.contextPath}/resources/js/apexcharts.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/chart.umd.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/echarts.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/quill.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/simple-datatables.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/tinymce.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>

</html>