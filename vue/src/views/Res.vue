<template>
    <div>

        <div class="searchForm">
            <el-input style="width: 200px" placeholder="请输入ID" v-model="id"
                      prefix-icon="el-icon-search"></el-input>
            <el-input class="ml-5" style="width: 200px" placeholder="请输入文件名" v-model="filename"
                      prefix-icon="el-icon-search"></el-input>
            <el-input class="ml-5" style="width: 200px" placeholder="请输入文件日期" v-model="dt"
                      prefix-icon="el-icon-search"></el-input>
            <el-button class="ml-5" type="primary" @click="rigthId();getData()">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
            <el-button type="primary" class="ml-5" @click="exportres" >导出</el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                  @selection-change="handleSelectionChange"
                  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="60"></el-table-column>
            <el-table-column prop="genre" label="流派" width="120"></el-table-column>
            <el-table-column prop="albumYearOfPub" label="专辑发售年份" width="150"></el-table-column>
            <el-table-column prop="numOfTracks" label="专辑销售歌曲数" width="150"></el-table-column>
            <el-table-column prop="numOfSales" label="专辑销售歌曲量"></el-table-column>
            <el-table-column prop="dt" label="文件分析日期"></el-table-column>
            <el-table-column prop="filename" label="文件名"></el-table-column>
        </el-table>


        <div class="pagination">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[15, 30, 45, 60]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">     <!--分页插件-->
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Res",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                resname: '',
                pageSize: 15,
                dialogFormVisible: false,
                addresForm: {},
                id: '',
                dt: '',
                filename: '',
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
                this.id = ''
                this.filename = ''
                this.dt = ''
                this.getData()
            },
            getData() {
                this.request.get(
                    "/echarts/getAnalysisAllDataPage", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            filename: this.filename,
                            id: this.id,
                            dt: this.dt,
                        }
                    }
                ).then(res => {
                    console.log(res.data);
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
            //批量选择用户
            handleSelectionChange(val) {
                console.log(val)
                this.multipleSelection = val
            },
            //导出用户
            exportres() {
                window.open("http://localhost:9090/echarts/exportRes")
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