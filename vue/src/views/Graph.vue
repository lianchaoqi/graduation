<template>
    <div>
        <div>
            <div style="text-align:right;">
                <div class="year-range-picker">
                    <el-select v-model="genre" filterable placeholder="请选择音乐流派">
                        <el-option
                                v-for="item in genreArr"
                                :key="item"
                                :value="item" style="margin-right: 10px;">
                        </el-option>
                    </el-select>
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
                    <el-button type="primary" class="ml-5" @click="albumGnereYear()">搜索<i
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
        <div id="albumGnereYear" style="width: 100%;height: 660px">
        </div>
    </div>
</template>

<script>

    import * as echarts from 'echarts'

    export default {
        name: "graph",
        data() {
            return {
                genre: '',
                filename: '',
                defaultGenre: 'Indie',
                startYear: '',
                endYear: '',
                genreArr: [],
                filenameArr: [],

            }
        },
        mounted() {
            this.albumGnereYear()
        },
        created() {
            // this.getDate();
            this.getGenre();
            this.getFilename();
        },
        methods: {
            //流派年份可视化表
            albumGnereYear() {
                var myChart = echarts.init(document.getElementById('albumGnereYear'));
                var option = {
                    title: {
                        text: '流派年份销量'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {},
                    grid: {
                        left: '1%',
                        right: '4.5%',
                        bottom: '1%',
                        containLabel: true,
                    },
                    xAxis: {
                        name:'销量(张)',
                        type: 'value',
                        boundaryGap: [0, 0.01],
                        show: true,
                        nameTextStyle:{
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
                        name:"年份(年)",
                        nameTextStyle:{
                            color: 'black',
                            fontWeight: 'normal',
                            fontSize: '15'
                        },
                        type: 'category',
                        show: true,
                        data: [],
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
                        {
                            name: '',
                            type: 'bar',
                            data: [],
                            itemStyle: {
                                emphasis: {
                                    barBorderRadius: 7
                                },
                                normal: {
                                    barBorderRadius: 7,
                                    color: new echarts.graphic.LinearGradient(
                                        0, 0, 1, 0,
                                        [
                                            {offset: 0, color: '#5ba3ee'},
                                            {offset: 1, color: '#e19ef1'}

                                        ]
                                    )
                                }
                            }
                        },

                    ]
                };
                this.request.get("/echarts/albumGnereYear", {
                    params: {
                        genre: this.genre,
                        filename: this.filename,
                        startYear: this.startYear,
                        endYear: this.endYear
                    }
                }).then(res => {
                    if (res.code === '200') {
                        var yearArr = [];
                        var salesArr = [];
                        let arr = res.data;
                        for (var index = 0; index < arr.length; index++) {
                            yearArr.push(Number(arr[index].albumYearOfPub));
                            salesArr.push(arr[index].numOfSales)
                        }
                        //赋值
                        option.yAxis.data = yearArr;
                        option.series[0].data = salesArr;

                        if (this.genre === '') {
                            option.series[0].name = this.defaultGenre;
                        } else {
                            option.series[0].name = this.genre;
                        }
                        option && myChart.setOption(option);
                    }
                })
            },

            //获取所有流派信息
            getGenre() {
                this.request.get("/echarts/allGenre").then(res => {
                    this.genreArr = res.data;
                    console.log(res.data);
                    // console.log(this.genreArr)
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
                        this.albumGnereYear();
                        this.getGenre();
                        this.getFilename();
                    } else {
                        this.$message.error("删除失败")
                        this.albumGnereYear();
                        this.getGenre();
                        this.getFilename();
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>