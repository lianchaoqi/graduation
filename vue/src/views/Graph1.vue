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
                    <el-button type="primary" class="ml-5" @click="albumAllGnereYear()">搜索<i
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
        <div id="albumAllGnereYear" style="width: 100%;height: 660px">
        </div>
    </div>
</template>

<script>

    import * as echarts from 'echarts'

    //分组
    export const groupBy = (list, fn) => {
        const groups = {};
        list.forEach(function (o) {
            const group = JSON.stringify(fn(o));
            groups[group] = groups[group] || [];
            groups[group].push(o);
        });
        return groups;
    }

    export default {
        name: "graph1",
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
            this.albumAllGnereYear()
        },
        created() {
            this.getFilename();
        },
        methods: {
            //年份专辑流派图
            albumAllGnereYear() {
                var myChart = echarts.init(document.getElementById('albumAllGnereYear'));
                var option = {
                    title: {
                        text: '年份专辑流派'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: []
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
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        name:'年份(年)',
                        nameTextStyle:{
                            color: 'black',
                            fontWeight: 'normal',
                            fontSize: '15'
                        },
                        data: [],
                        show: true,

                        axisLine: {
                            show: true,
                            //x轴线样式
                            lineStyle: {
                                color: '#17273B',
                                width: 1,
                                type: 'solid',
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
                    },
                    yAxis: {
                        name:'销量(张)',
                        nameTextStyle:{
                            color: 'black',
                            fontWeight: 'normal',
                            fontSize: '15'
                        },
                        type: 'value',
                        show: true,
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
                    },
                    series: [
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                        {name: "", type: 'line', stack: 'Total', data: []},
                    ]
                };
                this.request.get("/echarts/albumAllGnereYear", {
                    params: {
                        filename: this.filename,
                        startYear: this.startYear,
                        endYear: this.endYear
                    }
                }).then(res => {
                    if (res.code === '200') {
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
                        option.xAxis.data = this.yearArr;
                        //键的数组
                        var keys = Array.from(map.keys());
                        console.log(keys);
                        option.legend.data = keys;
                        for (var index = 0; index < keys.length; index++) {
                            option.series[index].name = keys[index];
                            option.series[index].data = map.get(keys[index]);
                        }
                        option && myChart.setOption(option);
                    }
                })
            },
            //获取所有文件名
            getFilename() {
                this.request.get("/echarts/allFilename").then(res => {
                    this.filenameArr = res.data;
                    console.log(res.data);
                })
            },
            delRes() {
                this.request.delete("/echarts/delRes/" + this.filename).then(res => {
                    if (res.code === '200') {
                        this.$message.success("删除成功")
                        this.albumAllGnereYear();
                        this.getFilename();
                    } else {
                        this.$message.error("删除失败")
                        this.albumAllGnereYear();
                        this.getFilename();
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>