<template>
    <div>
        <!--        <div style="margin-bottom: 30px">-->
        <!--            <el-breadcrumb separator="/">-->
        <!--                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
        <!--                <el-breadcrumb-item>用户管理</el-breadcrumb-item>-->
        <!--            </el-breadcrumb>-->
        <!--        </div>-->

        <div class="searchForm">
            <el-input style="width: 200px" placeholder="请输入ID" v-model="id"
                      prefix-icon="el-icon-search"></el-input>
            <el-input class="ml-5" style="width: 200px" placeholder="请输入用户名" v-model="username"
                      prefix-icon="el-icon-search"></el-input>
            <el-button class="ml-5" type="primary" @click="rigthId();getData()">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <el-dialog title="用户信息添加" :visible.sync="dialogFormVisible" width="30%" center>
            <el-form label-width="80px">
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="addUserForm.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" :label-width="formLabelWidth">
                    <el-input v-model="addUserForm.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :label-width="formLabelWidth">
                    <el-input v-model="addUserForm.email" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="地址" :label-width="formLabelWidth">
                    <el-input v-model="addUserForm.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveUser">确 定</el-button>
            </div>
        </el-dialog>


        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                  @selection-change="handleSelectionChange"
                  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="60"></el-table-column>
            <el-table-column prop="username" label="姓名" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="150"></el-table-column>
            <el-table-column prop="address" label="地址" width="150"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column prop="updateTime" label="修改时间"></el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="editUser(scope.row)">编辑 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="delUser(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i>
                        </el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style=" margin: 10px 0">
            <el-button @click="addUser" type="primary" class="ml-5" style="width: 90px; margin-left: 0">新增 <i
                    class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确定'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除这些数据吗？"
                    @confirm="delUserBatch"
            >
                <el-button type="danger" slot="reference" class="ml-5" style="width: 90px;">批量删除 <i
                        class="el-icon-remove-outline"></i>
                </el-button>
            </el-popconfirm>
            <el-button type="primary" class="ml-5" @click="exportUser" style="width: 90px;">导出 <i
                    class="el-icon-top"></i></el-button>
        </div>

        <div class="pagination">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">     <!--分页插件-->
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        name: "User",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                username: '',
                pageSize: 13,
                dialogFormVisible: false,
                addUserForm: {},
                id: '',
                multipleSelection: [],
                headerBg: 'headerBg',
            }
        },
        created() {
            this.getData()
        },
        methods: {
            rigthId() {
                if (isNaN(this.id)) {
                    this.$message({
                        type: "warning",
                        message: "请输入正确输入数字id！"
                    })
                    this.reset()
                }
            },
            reset() {
                this.id = '',
                    this.username = ''
                this.getData()
            },
            getData() {
                this.request.get(
                    "/user/page", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            username: this.username,
                            id: this.id
                        }
                    }
                ).then(res => {
                    console.log(res.data);
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
                //     .catch(err => {
                //     alert('请输入正确输入数字id！');
                //     this.reset();
                // })
            },
            //用户添加
            addUser() {
                this.dialogFormVisible = true
                this.addUserForm = {}
            },
            //用户保存
            saveUser() {
                this.request.post("/user/saveUser", this.addUserForm).then(res => {
                    if (res.data) {
                        this.$message.success("添加成功")
                        this.dialogFormVisible = false
                        this.getData()
                    } else {
                        this.$message.error("添加失败")
                        this.dialogFormVisible = false
                    }
                })
            },
            //修改用户
            editUser(row) {
                this.addUserForm = row
                this.dialogFormVisible = true
            },
            //删除用户
            delUser(id) {
                this.request.delete("/user/deleteUser/" + id).then(res => {
                    if (res.data) {
                        this.$message.success("删除成功")
                        this.getData()
                    } else {
                        this.$message.error("添加失败")
                        this.getData()
                    }
                })
            },
            //批量选择用户
            handleSelectionChange(val) {
                console.log(val)
                this.multipleSelection = val
            },
            //批量删除
            delUserBatch() {
                let ids = this.multipleSelection.map(ids => ids.id);

                this.request.post("/user/delUserBatch", ids).then(res => {
                    if (res.data) {
                        this.$message.success("批量删除成功")
                        this.getData()
                    } else {
                        this.$message.error("批量添加失败")
                        this.getData()
                    }
                })
            },

            //导出用户
            exportUser() {
                window.open("http://localhost:9090/user/exportUser")
            },

            //分页数据请求
            handleSizeChange(pageSize) {
                console.log(pageSize)
                this.pageSize = pageSize
                this.getData()

            },
            handleCurrentChange(pageNum) {
                console.log(pageNum)
                this.pageNum = pageNum
                this.getData()
            }
        }
    }
</script>

<style>
    .headerBg {
        background: #eee !important;
    }

    .searchForm {
        margin: 10px 0;
    }

    .pagination {
        padding: 10px 0;
        width: max-content;
        margin: 0 auto;
        position: fixed;
        bottom: 10px;
        left: 40%;
    }

</style>