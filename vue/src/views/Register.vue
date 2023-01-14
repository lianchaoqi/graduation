<template>
    <div class="wrapper">
        <!--        <dic>-->
        <!--            <h1 style="margin-top:100px;margin-left:700px; ">数字音乐分析系统</h1>-->
        <!--        </dic>-->

            <el-form :model="user" :rules="rules" ref="userForm" class="registerClss">
                <div class="title1"><b>注 册</b></div>
                <el-form-item prop="username" style="text-align: center; width:max-content;">
                    <el-input size="mini" placeholder="请输入账号" style="width: 200px;" prefix-icon="el-icon-user"
                              v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password" style="text-align: center; width:max-content;">
                    <el-input size="mini" placeholder="请输入密码" style="width: 200px;" prefix-icon="el-icon-lock"
                              show-password
                              v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword" style="text-align: center; width:max-content;">
                    <el-input size="mini" placeholder="请确认密码" style="width: 200px;" prefix-icon="el-icon-lock"
                              show-password
                              v-model="user.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item prop="email" style="text-align: center; width:max-content;">
                    <el-input size="mini" placeholder="请输入邮箱" style="width: 200px;" prefix-icon="el-icon-message"
                              v-model="user.email"></el-input>
                </el-form-item>
                <el-form-item prop="address" style="text-align: center; width:max-content;">
                    <el-input size="mini" placeholder="请输入地址" style="width: 200px;" prefix-icon="el-icon-position"
                              v-model="user.address"></el-input>
                </el-form-item>
                <el-form-item style="text-align: center">
                    <el-button type="primary" size="small" autocomplete="off" @click="registerUser">注册</el-button>
                    <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
</template>

<script>
    export default {
        name: "Register",
        data() {
            // 邮箱验证
            var checkEmail = (rule, value, callback) => {
                const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (!value) {
                    return callback(new Error("邮箱不能为空"));
                }
                setTimeout(() => {
                    if (mailReg.test(value)) {
                        callback();
                    } else {
                        callback(new Error("请输入正确的邮箱格式"));
                    }
                }, 100);
            };
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
                    confirmPassword: [
                        {required: true, message: '请再次输入密码', trigger: 'blur'},
                        {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                    ],
                    email: [
                        {required: true, validator: checkEmail, trigger: "change"}
                    ],
                    address: [
                        {required: true, message: '请输入地址', trigger: 'blur'},
                        {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            registerUser() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        // 表单校验是否合法
                        if (this.user.password !== this.user.confirmPassword) {
                            this.$message.error("两次输入密码不一致！")
                            return false
                        }
                        this.request.post("/user/registerUser", this.user).then(res => {
                            console.log(res);
                            if (res.code === "200") {
                                localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
                                this.$router.push("/register")
                                this.$message.success("注册成功，点击返回登录！")
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

    .registerClss {
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
