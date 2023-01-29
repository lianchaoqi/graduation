<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             router
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/yellow.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
            <b style="color: white" v-show="logoTextShow">后台管理系统</b>
        </div>
        <el-menu-item index="/home">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-submenu index="1">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="/user">
                <i class="el-icon-s-custom"></i>
                <span slot="title">用户管理</span>
            </el-menu-item>
        </el-submenu>

        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">文件管理</span>
            </template>
            <el-menu-item index="/file">
                <i class="el-icon-folder-opened"></i>
                <span slot="title">源文件管理</span>
            </el-menu-item>
            <el-menu-item index="/res">
                <i class="el-icon-folder-opened"></i>
                <span slot="title">结果集管理</span>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">可视化管理</span>
            </template>
            <el-menu-item index="/graph">
                <i class="el-icon-s-data"></i>
                <span slot="title">流派年份销量条形图</span>
            </el-menu-item>
            <el-menu-item index="/graph1">
                <i class="el-icon-s-data"></i>
                <span slot="title">流派年份折线图</span>
            </el-menu-item>
            <el-menu-item index="/graph2">
                <i class="el-icon-s-data"></i>
                <span slot="title">流派年份条形图</span>
            </el-menu-item>
            <el-menu-item index="/graph3">
                <i class="el-icon-s-data"></i>
                <span slot="title">流派销量饼图</span>
            </el-menu-item>
        </el-submenu>
    </el-menu>

</template>


<script>
    export default {
        name: "Aside",
        props: {
            isCollapse: Boolean,
            logoTextShow: Boolean
        }
    }
</script>

<style scoped>

</style>