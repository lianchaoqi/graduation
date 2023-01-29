<template>
    <div >
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
                    <el-button type="primary" class="ml-5" @click="albumAllGnereYearTop5()">搜索<i
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
        <div id="albumAllGnereYearTop5" style="width: 100%;height: 660px">
        </div>
    </div>
</template>

<script>

    import * as echarts from 'echarts'

    export default {
        name: "graph2",
        data() {
            return {
                filename: '',
                startYear: '',
                endYear: '',
                filenameArr: [],
                yearArr: []
            }
        },
        mounted() {
            this.albumAllGnereYearTop5()
        },
        created() {
            this.getFilename();
        },
        methods: {
            //年份专辑流派图
            albumAllGnereYearTop5() {
                var myChart = echarts.init(document.getElementById('albumAllGnereYearTop5'));
                var option = {
                    title: {
                        text: '年份专辑流柱形图表'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        //菜单字体样式
                        textStyle: {
                            color: 'black'
                        },
                        //菜单位置
                        x: 'center',
                        //菜单数据
                        data: [],
                    },
                    grid: {
                        left: '1%',
                        right: '4.5%',
                        bottom: '1%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            name: '年份(年)',
                            nameTextStyle: {
                                color: 'black',
                                fontWeight: 'normal',
                                fontSize: '15'
                            },
                            axisLine: {
                                show: true,
                                //x轴线样式
                                lineStyle: {
                                    color: '#17273B',
                                    width: 1,
                                    type: 'solid',
                                }
                            },
                            show: true,
                            //x轴字体设置
                            axisLabel: {
                                show: true,
                                fontSize: 15,
                                color: 'solid'
                            },
                            data: []
                        }],

                    yAxis: [
                        {
                            type: 'value',
                            name: '销量(张)',
                            nameTextStyle: {
                                fontWeight: 'normal',
                                fontSize: '15',
                                color: 'black'
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(151, 151, 151, 1)'
                                }
                            },
                            axisLabel: {
                                show: true,
                                fontSize: 15,
                                color: 'solid'
                            },
                            axisTick: {
                                show: true,
                                color: 'rgba(151, 151, 151, 1)'
                            },
                            splitLine: {
                                show: false,
                                lineStyle: {
                                    color: 'rgba(226, 232, 236, 1)',
                                    type: 'dashed'
                                }
                            },
                            splitArea: {
                                show: true,
                                areaStyle: {
                                    color: ['#fff', 'rgba(245, 246, 250, 1)']
                                }
                            }
                        }
                    ],
                    series: [
                        {
                            name: '',
                            type: 'bar',
                            //柱子宽度
                            barWidth: 18,
                            data: []
                        },
                        {
                            name: '',
                            type: 'bar',
                            //柱子宽度
                            barWidth: 18,
                            data: []
                        },
                        {
                            name: '',
                            type: 'bar',
                            //柱子宽度
                            barWidth: 18,
                            data: []
                        },
                        {
                            name: '',
                            type: 'bar',
                            //柱子宽度
                            barWidth: 18,
                            //柱状图样式
                            data: []
                        },
                        {
                            name: '',
                            type: 'bar',
                            //柱子宽度
                            barWidth: 18,
                            data: []
                        },
                    ]
                };
                this.request.get("/echarts/albumAllGnereYearTop5", {
                    params: {
                        filename: this.filename,
                        startYear: this.startYear,
                        endYear: this.endYear
                    }
                }).then(res => {
                    let beans = res.data;
                    var map = new Map();
                    var yearSet = new Set();
                    //将数据封装到map里面去
                    for (var index = 0; index < beans.length; index++) {
                        //获取所有的年
                        yearSet.add(beans[index].albumYearOfPub);
                        //已有键 直接value添加值
                        if (map.has(beans[index].genre)) {
                            var valueArr = map.get(beans[index].genre);
                            valueArr.push(beans[index].numOfSales)
                            map.set(beans[index].genre, valueArr);
                        } else {
                            //没有键 新加一个数组
                            var arr = [];
                            arr.push(beans[index].numOfSales)
                            map.set(beans[index].genre, arr);
                        }
                    }
                    this.yearArr = Array.from(yearSet);
                    //赋值
                    option.xAxis[0].data = this.yearArr
                    option.legend.data = keys;
                    //键的数组
                    var keys = Array.from(map.keys());
                    for (var index = 0; index < keys.length; index++) {
                        option.series[index].name = keys[index];
                        option.series[index].data = map.get(keys[index]);
                    }
                    option && myChart.setOption(option);
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
                        this.albumAllGnereYearTop5();
                        this.getFilename();
                    } else {
                        this.$message.error("删除失败")
                        this.albumAllGnereYearTop5();
                        this.getFilename();
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>