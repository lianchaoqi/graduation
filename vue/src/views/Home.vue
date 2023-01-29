<!--<script src="../utils/request.js"></script>-->
<template>
    <div>
        <div id="aaa" style="width: 100%;height: 470px;background-color: azure"></div>
        <el-row :gutter="10">
            <el-col :span="6">
                <el-card style="background-color: beige">
                    <div id="memory" style="width: 300px; height: 200px"
                         v-model="this.memoryOption.series[0].data[0].value"></div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="background-color: beige">
                    <div id="cpu" style="width: 300px; height: 200px"></div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="background-color: beige">
                    <div style="width: 300px; height: 200px">
                        <div style="color: #42b983; height:200px; text-align:center;line-height: 200px;font-size: 70px;margin-left: 53px">
                            {{ severStatus }}
                        </div>
                        <div style="color: rgba(16, 38, 177, 1);font-weight: bold;margin-left: 133px;margin-top: -20px">
                            服务器状态
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="background-color: beige">
                    <div style="width: 300px; height: 200px ">
                        <div style="color: #42b983; height:200px; text-align:center;line-height: 200px;font-size: 70px;margin-left: 53px">
                            {{ hadoopStatus }}
                        </div>
                        <div style="color: rgba(16, 38, 177, 1);font-weight: bold;margin-left: 133px;margin-top: -20px">
                            Hadoop集群状态
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>

    </div>
</template>

<script>

    import * as echarts from 'echarts'


    export default {
        name: "home",
        data() {
            return {
                severStatus: '',
                hadoopStatus: '',
                memoryChart: {},
                cpuChart: {},
                logoChrt: {},
                // 配置可视化图形
                memoryOption: {
                    title: {
                        text: "内存使用",
                        textStyle: {
                            color: "rgba(16, 38, 177, 1)",


                        },
                        left: "35%",
                        bottom: 0,
                    },
                    series: [
                        {
                            type: 'gauge',
                            radius: '100%',
                            axisLine: {
                                lineStyle: {
                                    width: 15,
                                    color: [
                                        [0.3, '#67e0e3'],
                                        [0.7, '#37a2da'],
                                        [1, '#fd666d']
                                    ]
                                }
                            },
                            pointer: {
                                itemStyle: {
                                    color: 'inherit'
                                }
                            },
                            axisTick: {
                                distance: -30,
                                length: 8,
                                lineStyle: {
                                    color: '#fff',
                                    width: 2
                                }
                            },
                            splitLine: {
                                distance: -30,
                                length: 20,
                                lineStyle: {
                                    color: '#fff',
                                    width: 4
                                }
                            },
                            axisLabel: {
                                color: 'inherit',
                                distance: 40,
                                fontSize: 10
                            },
                            detail: {
                                valueAnimation: true,
                                formatter: '{value}%',
                                color: 'inherit',
                                fontSize: 15

                            },
                            data: [
                                {
                                    value: 10,
                                    name: "占用"
                                }
                            ]
                        }
                    ]
                },
                cpuOption: {
                    title: {
                        text: "cpu使用",
                        textStyle: {
                            color: "rgba(16, 38, 177, 1)",

                        },
                        left: "35%",
                        bottom: 0,
                    },
                    series: [
                        {
                            type: 'gauge',
                            radius: '100%',
                            axisLine: {
                                lineStyle: {
                                    width: 15,
                                    color: [
                                        [0.3, '#67e0e3'],
                                        [0.7, '#37a2da'],
                                        [1, '#fd666d']
                                    ]
                                }
                            },
                            pointer: {
                                itemStyle: {
                                    color: 'inherit'
                                }
                            },
                            axisTick: {
                                distance: -30,
                                length: 8,
                                lineStyle: {
                                    color: '#fff',
                                    width: 2
                                }
                            },
                            splitLine: {
                                distance: -30,
                                length: 20,
                                lineStyle: {
                                    color: '#fff',
                                    width: 4
                                }
                            },
                            axisLabel: {
                                color: 'inherit',
                                distance: 40,
                                fontSize: 10
                            },
                            detail: {
                                valueAnimation: true,
                                formatter: '{value}%',
                                color: 'inherit',
                                fontSize: 15

                            },
                            data: [
                                {
                                    value: 0,
                                    name: "占用"
                                }
                            ]
                        }
                    ]
                }
            }
        },
        mounted() {
            this.timer();
            this.getPage();

            var chartDom = document.getElementById('aaa');
            var myChart = echarts.init(chartDom);
            var option = {
                graphic: {
                    elements: [
                        {
                            type: 'text',
                            left: 'center',
                            top: 'center',
                            style: {
                                text: '音乐唱片数据分析系统',
                                fontSize: 130,
                                fontWeight: 'bold',
                                lineDash: [0, 200],
                                lineDashOffset: 0,
                                fill: 'transparent',
                                stroke: '#000',
                                lineWidth: 1,
                                textAlign: 'center'
                            },
                            keyframeAnimation: {
                                duration: 4000,
                                loop: true,
                                keyframes: [
                                    {
                                        percent: 0.7,
                                        style: {
                                            fill: 'transparent',
                                            lineDashOffset: 200,
                                            lineDash: [200, 0]
                                        }
                                    },
                                    {
                                        // Stop for a while.
                                        percent: 0.8,
                                        style: {
                                            fill: 'transparent'
                                        }
                                    },
                                    {
                                        percent: 1,
                                        style: {
                                            fill: '#2ec7c9'
                                        }
                                    }
                                ]
                            }
                        }
                    ]
                }
            };

            option && myChart.setOption(option);

        },
        created() {
            this.getStatus();


        },
        beforeDestroy() {
            clearInterval(this.timer);


        },
        methods: {
            timer() {
                setInterval(this.getPage, 5000);
            },
            getPage() {
                this.memoryChart = echarts.init(document.getElementById('memory'));
                this.request.get("/status/memory").then(res => {
                    this.memoryOption.series[0].data[0].value = res.data;
                    this.memoryOption && this.memoryChart.setOption(this.memoryOption);
                }).catch(err => {
                    this.memoryOption.series[0].data[0].value = 0;
                    this.memoryOption && this.memoryChart.setOption(this.memoryOption);
                });
                this.cpuChart = echarts.init(document.getElementById('cpu'));
                this.request.get("/status/cpu").then(res => {
                    this.cpuOption.series[0].data[0].value = res.data;
                    this.cpuOption && this.cpuChart.setOption(this.cpuOption);
                }).catch(err => {
                    this.cpuOption.series[0].data[0].value = 0;
                    this.cpuOption && this.cpuChart.setOption(this.cpuOption);
                });
            },
            getLogo() {
                // this.logoChrt = echarts.init(document.getElementById("logo"));
                // this.logoOption && this.logoChrt.setOption(this.logoOption)
            },
            getStatus() {
                this.request.get("/status/memory").then(res => {
                    if (res !== null) {
                        this.severStatus = 'ACTIVE';
                    }
                }).catch(err => {
                    this.severStatus = 'ERROR'
                })
                this.request.get("/hadoopStatus").then(res => {
                    if (res.code === '200') {
                        this.hadoopStatus = 'ACTIVE'
                    } else {
                        this.hadoopStatus = 'ERROR'
                    }
                }).catch(err => {
                    this.hadoopStatus = 'ERROR'
                })
            }
        },

    }
</script>

<style scoped>

</style>