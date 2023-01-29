<template>
    <div style="background-color: cornsilk">
        <div>
            <div style="text-align:right;">
                <div class="year-range-picker">
                    <el-select v-model="filename" filterable placeholder="请选择文件">
                        <el-option
                                v-for="item in filenameArr"
                                :key="item"
                                :value="item" style="margin-right: 10px;">
                        </el-option>
                    </el-select>
                    <el-date-picker
                            v-model="startYear"
                            type="year"
                            placeholder="选择开始年"
                            size="mini"
                            class="year-picker"
                            @change="changeStartYear"
                            format="yyyy 年"
                            value-format="yyyy"
                    >
                    </el-date-picker>
                    <span class="range-word"> 至 </span>
                    <el-date-picker
                            v-model="endYear"
                            type="year"
                            placeholder="选择结束年"
                            size="mini"
                            class="year-picker"
                            @change="changeEndYear"
                            format="yyyy 年"
                            value-format="yyyy"
                    >
                    </el-date-picker>
                    <el-button type="primary" class="ml-5" @click="getPieData()">搜索<i
                            class="el-icon-search" style="margin-left: 10px"></i></el-button>
                    <el-button type="primary" class="ml-5" @click="exportUser">下载<i
                            class="el-icon-bottom"></i></el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="delRes()"
                    >
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i>
                        </el-button>
                    </el-popconfirm>
                </div>
            </div>
        </div>
        <div id="getPieData" style="width: 100%;height: 660px">
        </div>
    </div>
</template>

<script>

    import * as echarts from 'echarts'

    export default {
        name: "graph3",
        data() {
            return {
                filename: '',
                startYear: '',
                endYear: '',
                filenameArr: [],
                yearArr: [],
                legendData: [],
                legendData1: [],
            }
        },
        mounted() {
            this.getPieData()
        },
        created() {
            this.getFilename();
        },
        methods: {
            //年份专辑流派图
            getPieData() {
                var myChart = echarts.init(document.getElementById('getPieData'));
                var option = {
                    title: {
                        text: '流派销量',
                        left: '100'
                    },
                    legend: [
                        {

                            top: 80,
                            // right: 10,
                            left: '80%',
                            orient: 'vertical',
                            data: this.legendData,  // 切割 legend.data 进行分列展示  第一列
                            textStyle: {
                                color: 'black',
                                rich: {
                                    name: {
                                        color: '#A9FDFF'
                                    },
                                    val: {
                                        color: '#ffffff'
                                    }
                                }
                            },
                        }, {
                            top: 80,
                            left: '71%',
                            bottom: 150,
                            type: 'scroll',                 // legend.data 分页展示
                            orient: 'vertical',
                            data: this.legendData1,  // 切割 legend.data 进行分列展示  第二列
                            textStyle: {
                                color: 'black',
                                rich: {
                                    name: {
                                        color: 'black'
                                    },
                                    val: {
                                        color: '#ffffff'
                                    }
                                }
                            }
                        }
                    ],
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: [
                        {
                            name: 'Nightingale Chart',
                            type: 'pie',
                            radius: [50, 300],
                            center: ['30%', '53%'],
                            roseType: 'area',
                            itemStyle: {
                                borderRadius: 8
                            },
                            data: []
                        }
                    ]
                };
                this.request.get("/echarts/getPieData", {
                    params: {
                        filename: this.filename,
                        startYear: this.startYear,
                        endYear: this.endYear
                    }
                }).then(res => {
                    if (res.code === '200') {
                        option.series[0].data = res.data;
                        for (var index = 0; index < res.data.length; index++) {
                            if (index < 19) {
                                this.legendData.push(res.data[index].name);
                            } else {
                                this.legendData1.push(res.data[index].name);

                            }
                        }
                        option && myChart.setOption(option);
                    }

                })
            },
            //获取所有文件名
            getFilename() {
                this.request.get("/echarts/allFilename").then(res => {
                    this.filenameArr = res.data;
                })
            },
            delRes() {
                this.request.delete("/echarts/delRes/" + this.filename).then(res => {
                    if (res.code === '200') {
                        this.$message.success("删除成功")
                        this.getPieData();
                        this.getFilename();
                    } else {
                        this.$message.error("删除失败")
                        this.getPieData();
                        this.getFilename();
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>