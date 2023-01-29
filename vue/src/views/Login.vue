<template>
    <div class="wrapper">
        <!--        <dic>-->
        <!--            <h1 style="margin-top:100px;margin-left:700px; ">数字音乐分析系统</h1>-->
        <!--        </dic>-->

            <el-form :model="user" :rules="rules" ref="userForm" class="loginForm">
                <div class="title1"><b>登录</b></div>
                <el-form-item prop="username" style="text-align: center; width:max-content;">
                    <el-input size="large" placeholder="账号" style="width: 250px;" prefix-icon="el-icon-user"
                              v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password" style="text-align: center; width:max-content;">
                    <el-input size="large" placeholder="密码" style="width: 250px;" prefix-icon="el-icon-lock"
                              show-password
                              v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item style="text-align: center">
                    <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
                    <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                user: {},
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            login() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {  // 表单校验是否合法
                        this.request.post("/user/userLogin", this.user).then(res => {
                            console.log(res);
                            if (res.code === "200") {
                                localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
                                this.$router.push("/")
                                this.$message.success("登录成功！")
                            } else {
                                this.$message.error(res.msg)
                            }
                        })
                    }
                });
            }
        }
    }
</script>

<style>
    .wrapper {
        height: 100vh;
        width: 100%;
        overflow: hidden;
        background: url(../assets/back5.jpg) no-repeat;
        background-size: cover;
    }

    .title1 {
        margin: 20px 0;
        text-align: center;
        font-size: 30px;
        color: darkcyan;
        font-family: KaiTi, serif;
    }

    .loginForm {
        margin: 100px 0 100px 1000px;
        background-color: rgba(255, 255, 255, 0.1);
        width: 400px;
        height: 400px;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;
    }

</style>
