import Vue from 'vue'
import VueRouter from 'vue-router'

import store from "../store/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Manage',
        component: () => import('../views/Manage.vue'),
        redirect: "/home",
        children: [
            // {
            //     path: 'user', name: 'User', component: () => import('../views/User.vue'),
            //
            // }
            {path: 'home', name: '首页', component: () => import('../views/Home.vue')},
            {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
            {path: 'file', name: '文件管理', component: () => import('../views/File.vue')}
        ]
    },

    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/test',
        name: 'Test',
        component: () => import('../views/Test.vue')
    }

    // {
    //     path: '/about',
    //     name: 'Login',
    //     component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
    // }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
// 路由守卫
router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
    store.commit("setPath")  // 触发store的数据更新
    next()  // 放行路由
})
export default router
