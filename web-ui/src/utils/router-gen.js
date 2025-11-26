import Layout from "@/layout/index.vue";
import ParentView from "@/components/ParentView/index.vue";
import InnerLink from "@/components/InnerLink/index.vue";
import { markRaw } from "vue";

export function filterAsyncRoutes(routes) {
  const res = [];
  routes.forEach((route) => {
    const tmp = { ...route };
    if (tmp.component) {
      if (tmp.component === "Layout") {
        tmp.component = markRaw(Layout);
      } else if (tmp.component === "ParentView") {
        tmp.component = markRaw(ParentView);
      } else if (tmp.component === "InnerLink") {
        tmp.component = markRaw(InnerLink);
      } else {
        const componentPath = tmp.component;
        tmp.component = loadView(componentPath);
      }
    }

    if (tmp.children) {
      tmp.children = filterAsyncRoutes(tmp.children);
    }
    res.push(tmp);
  });
  // console.log()
  return res;
}

export const loadView = (view) => {
  return () => import(`@/views/${view}`);
};
