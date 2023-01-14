<template>
    <div style="line-height: 60px; display: flex">
        <div style="flex: 1;">
            <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
            <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
                <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 160px; cursor: pointer">
            <span class="userFlag">{{ "hello，"+user.username+"！" }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
<!--                <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>-->
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <span style="text-decoration: none" @click="loginOut">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
            }
        },
        methods: {
            loginOut() {
                //退回登录页面
                this.$router.push("/login")
                //清除登录数据信息
                localStorage.removeItem("user")
                this.$message.success("退出成功")
            }
        },
        props: {
            collapseBtnClass: String,
            collapse: Boolean
        },
        computed: {
            currentPathName() {
                return this.$store.state.currentPathName;　　//需要监听的数据
            }
        },
        watch: {
            currentPathName(newVal, oldVal) {
                console.log(newVal)
            }
        }
    }
</script>

<style scoped>
.userFlag{
    font-size: 18px;
    font-weight: bold;
    color: cornflowerblue;
    font-family: KaiTi, serif;
}
</style>