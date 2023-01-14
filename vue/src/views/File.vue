<template>
    <div>
        <div class="searchForm">
            <el-input style="width: 200px" placeholder="请输入ID" v-model="id"
                      prefix-icon="el-icon-search"></el-input>
            <el-input class="ml-5" style="width: 200px" placeholder="请输入文件名" v-model="fileName"
                      prefix-icon="el-icon-search"></el-input>
            <el-input class="ml-5" style="width: 200px" placeholder="请输入uuid" v-model="uuid"
                      prefix-icon="el-icon-search"></el-input>
            <el-button class="ml-5" type="primary" @click="rigthId();getData()">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                  @selection-change="handleSelectionChange"
                  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="id" label="ID" width="40"></el-table-column>
            <el-table-column prop="fileName" label="文件名" width="100"></el-table-column>
            <el-table-column prop="fileType" label="文件类型" width="70"></el-table-column>
            <el-table-column :formatter="formatIsEtl" prop="isEtl" label="是否清洗" width="70"></el-table-column>
            <el-table-column prop="uploadTime" label="上传时间" width="90"></el-table-column>
            <!--            <el-table-column prop="updateTime" label="修改时间" width="90"></el-table-column>-->
            <el-table-column prop="etlTime" label="清洗时间" width="90"></el-table-column>
            <el-table-column prop="fileSize" label="大小(kb)" width="70"></el-table-column>
            <el-table-column prop="uuid" label="uuid" width="245"></el-table-column>
            <el-table-column prop="url" label="下载地址" width="435"></el-table-column>
            <el-table-column label="操作" width="215" align="center">
                <template slot-scope="scope">
                    <el-button style="width: 60px;margin-left: 1px;text-align: center" type="success"
                               @click="cleanFile(scope.row)">清洗
                        <i
                                class="el-icon-coin"></i>
                    </el-button>
                    <el-button type="primary" @click="downloadFile(scope.row)"
                               style="width: 60px;margin-left: 1px;text-align: center">下载 <i
                            class="el-icon-caret-bottom"></i></el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="delFile(scope.row.id)">
                        <el-button type="danger" slot="reference"
                                   style="width: 60px;margin-right: 1px;text-align: center">删除 <i
                                class="el-icon-remove-outline"></i>
                        </el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style=" margin: 10px 0">
            <el-upload action="http://localhost:9090/file/uploadToHdfs" :show-file-list="false"
                       :on-success="uploadToHdfsSuccess" style="display: inline-block;">
                <el-button type="primary" class="ml-5" style="width: 90px;" @click="uploadToHdfs">上传<i
                        class="el-icon-caret-top"></i>
                </el-button>
            </el-upload>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确定'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除这些数据吗？"
                    @confirm="delFileBatch"
            >
                <el-button type="danger" slot="reference" class="ml-5" style="width: 90px;">批量删除 <i
                        class="el-icon-remove-outline"></i>
                </el-button>
            </el-popconfirm>

        </div>

        <div class="pagination">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[9, 18, 27, 36]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">     <!--分页插件-->
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "file",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                fileName: '',
                pageSize: 9,
                dialogFormVisible: false,
                addfileForm: {},
                uuid: '',
                id: '',
                multipleSelection: [],
                headerBg: 'headerBg'
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
                this.id = ''
                this.fileName = ''
                this.uuid = ''
                this.getData()
            },
            getData() {
                this.request.get(
                    "/file/page", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            fileName: this.fileName,
                            uuid: this.uuid,
                            id: this.id
                        }
                    }
                ).then(res => {
                    console.log(res.data);
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
            //删除文件
            delFile(id) {
                this.request.delete("/file/deleteFile/" + id).then(res => {
                    if (res.code === "200") {
                        this.$message.success(res.data)
                        this.getData()
                    } else {
                        this.$message.error(res.msg)
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
            delFileBatch() {
                //将ids对象取出变成单个id，放到数组里面
                let ids = this.multipleSelection.map(ids => ids.id);
                //post到服务器
                this.request.post("/file/delFileBatch", ids).then(res => {
                    if (res.code === "200") {
                        this.$message.success(res.data)
                        this.getData()
                    } else {
                        this.$message.error(res.msg)
                        this.getData()
                    }
                })
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
            },
            //将后端的0&1映射为是和否
            formatIsEtl(row) {
                return row.isEtl === 1 ? "已清洗" : "未清洗";
            },
            //文件上传成功回传
            uploadToHdfsSuccess(res) {
                console.log(res);
                if (res.code === '200') {
                    this.getData()
                    this.$message.success("文件上传成功")
                } else if (res.code >= "200") {
                    this.getData()
                    this.$message.error(this.date())
                } else {
                    this.getData()
                    this.$message.success("文件上传成功")
                }
            },

            downloadFile(row) {
                //如果是清洗过的文件，则从hdfs下载
                window.open(row.url + "/" + row.isEtl + '/' + row.etlTime.substr(0, 10))
            },
            cleanFile(row) {
                console.log(row);
                if (row.isEtl === 1) {
                    this.$message.error("文件已经清洗！")
                } else {
                    this.$message.success("文件正在清洗中，请等待....")
                    this.request.get("/sparkCon/etlFile/" + row.id).then(res => {
                        console.log(res);
                        this.getData()
                        if (res.code === '200') {
                            this.getData()
                            this.$message.success("清洗成功！")
                        } else {
                            this.$message.error("清洗失败！")
                            this.$message.error(res.msg)
                        }
                    })
                }
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