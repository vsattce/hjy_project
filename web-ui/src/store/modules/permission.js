import { defineStore } from "pinia";
import { constantRoutes, dynamicRoutes } from "@/router";
import { filterAsyncRoutes } from "@/utils/router-gen";
import { getRouters } from "@/api/menu";

export const usePermissionStore = defineStore("permission", {
  state: () => ({
    routes: [], // 完整路由
    addRoutes: [], // 动态添加的路由
  }),
  actions: {
    async generateRoutes() {
      let res = await getRouters();
      const sdata = JSON.parse(JSON.stringify(res.data));
      //
      const asyncRoutes = filterAsyncRoutes(sdata);
      asyncRoutes.push({
        path: "/:pathMatch(.*)*",
        redirect: "/404",
        hidden: true,
      });

      this.addRoutes = dynamicRoutes.concat(asyncRoutes);
      this.routes = constantRoutes.concat(asyncRoutes);
      //
      return { addRoutes: this.addRoutes, routes: this.routes };
    },
  },
});

// export default { usePermissionStore };
