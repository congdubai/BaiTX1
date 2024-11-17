<%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!-- ======= Sidebar ======= -->
            <aside id="sidebar" class="sidebar">

                <ul class="sidebar-nav" id="sidebar-nav">

                    <li class="nav-item">
                        <a class="nav-link collapsed" href="index.html">
                            <i class="bi bi-grid"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <!-- End Dashboard Nav -->
                    <li class="nav-item ">
                        <a class="nav-link " data-bs-target="#tables-nav" data-bs-toggle="collapse" href="#">
                            <i class="bi bi-layout-text-window-reverse"></i><span>Quản lý</span><i
                                class="bi bi-chevron-down ms-auto"></i>
                        </a>

                        <ul id="tables-nav"
                            class="nav-content"
                            data-bs-parent="#sidebar-nav">
                            <li>
                                <a class="nav-link">
                                    <i class="bi bi-circle"></i><span>Quản lý người dùng</span>
                                </a>
                            </li>
                            <li>
                                <a href="product-manage.html">
                                    <i class="bi bi-circle"></i><span>Quản lý sản phẩm</span>
                                </a>
                            </li>
                            <li>
                                <a href="order-manage.html">
                                    <i class="bi bi-circle"></i><span>Quản lý đặt hàng</span>
                                </a>
                            </li>
                            <li>
                                <a href="brand-manage.html">
                                    <i class="bi bi-circle"></i><span>Quản lý Hãng</span>
                                </a>
                            </li>
                        </ul>
                    </li><!-- End Tables Nav -->

                    <li class="nav-heading">Trang</li>
                    <li class="nav-item">
                        <a class="nav-link collapsed" href="pages-contact.html">
                            <i class="bi bi-envelope"></i>
                            <span>Liên hệ</span>
                        </a>
                    </li><!-- End Contact Page Nav -->

                    <li class="nav-item">
                        <a class="nav-link collapsed" href="pages-register.html">
                            <i class="bi bi-card-list"></i>
                            <span>Đăng ký</span>
                        </a>
                    </li><!-- End Register Page Nav -->

                    <li class="nav-item">
                        <a class="nav-link collapsed" href="pages-login.html">
                            <i class="bi bi-box-arrow-in-right"></i>
                            <span>Đăng nhập</span>
                        </a>
                    </li><!-- End Login Page Nav -->
                </ul>

            </aside><!-- End Sidebar-->