import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import request from "./utils/request";
import store from "./store/store";

Vue.config.productionTip = false
Vue.prototype.request = request
Vue.use(ElementUI, {size: "mini"});
new Vue({
    router,
    render: h => h(App)
}).$mount('#app')

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
