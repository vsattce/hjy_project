import router from "@/router";
import { usePermissionStore } from "@/store/modules/permission";
import NProgress from "nprogress"; // progress bar
import "nprogress/nprogress.css";
import { isAuthenticated, removeToken } from "@/utils/auth";

const whiteList = ["/login", "/auth-redirect", "/bind", "/register", "/redirect"]; // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  // start progress bar
  NProgress.start();

  const hasToken = isAuthenticated();
  if (hasToken) {
    if (to.path === "/login") {
      // if is logged in, redirect to the home page
      next({ path: "/" });
      NProgress.done();
    } else {
      const permissionStore = usePermissionStore();
      if (permissionStore.routes.length === 0) {
        try {
          // generate accessible routes map based on roles
          const { addRoutes } = await permissionStore.generateRoutes();
          // dynamically add accessible routes
          addRoutes.forEach((route) => {
            // router.addRoute(route);
            // 核心修复：如果是 http/https 开头的链接，直接跳过，不要 addRoute
            if (!/^(https?:|mailto:|tel:)/.test(route.path)) {
              router.addRoute(route);
            }
          });
          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          next({ ...to, replace: true });
        } catch (error) {
          // remove token and go to login page to re-login
          removeToken();
          console.error(error || "Has Error");
          next(`/login?redirect=${to.path}`);
          NProgress.done();
        }
      } else {
        next();
      }
    }
  } else {
    if (whiteList.includes(to.path)) {
      next();
    } else {
      next(`/login?redirect=${to.path}`);
      NProgress.done();
    }
  }
});

router.afterEach(() => {
  // finish progress bar
  NProgress.done();
});
