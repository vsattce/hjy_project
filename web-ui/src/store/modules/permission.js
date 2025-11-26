import { defineStore } from "pinia";
import { constantRoutes } from "@/router";
import { filterAsyncRoutes } from "@/utils/router-gen";
import { getRouters } from "@/api/menu";

export const usePermissionStore = defineStore("permission", {
  state: () => ({
    routes: [], // 完整路由
    addRoutes: [], // 动态添加的路由
  }),
  actions: {
    generateRoutes() {
      return new Promise((resolve) => {
        getRouters().then((res) => {
          const sdata = JSON.parse(JSON.stringify(res.data));
          //
          const asyncRoutes = filterAsyncRoutes(sdata);
          asyncRoutes.push({
            path: "/:pathMatch(.*)*",
            redirect: "/404",
            hidden: true,
          });

          this.addRoutes = asyncRoutes;
          this.routes = constantRoutes.concat(asyncRoutes);
          //
          resolve({addRoutes: this.addRoutes, routes: this.routes});
        });
      });
    },
  },
});

// export default { usePermissionStore };
