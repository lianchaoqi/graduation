package com.jack.graduation.model

/**
 *
 * @param id            主键
 * @param real_name     玩家名
 * @param art_name      艺名
 * @param role          职务
 * @param year_of_birth 出生年份
 * @param email         邮箱
 * @param country       国家
 * @param city          城市
 * @param zip_code      邮政编码
 */
case class Artist(
                   id: Int,
                   real_name: String,
                   art_name: String,
                   role: String,
                   year_of_birth: String,
                   country: String,
                   city: String,
                   email: String,
                   zip_code: String
                 )

/**
 * 销量数据宽表
 *
 * @param id                   主键id
 * @param artist_id            艺术家id
 * @param real_name            真实名
 * @param art_name             艺名
 * @param role                 职业（鼓手 吉他手）
 * @param year_of_birth        出生年份
 * @param email                邮箱
 * @param country              国家
 * @param city                 城市
 * @param zip_code             邮编
 * @param album_id             专辑id
 * @param album_title          专辑名称
 * @param genre                专辑流派
 * @param operation_id         流派操作人d
 * @param operation_time       流派记录时间
 * @param album_year_of_pub    专辑发表年份
 * @param num_of_tracks        专辑歌曲数量
 * @param num_of_sales         专辑销售量
 * @param rolling_stone_critic 滚石排名
 * @param mtv_critic           mtv排名
 * @param music_maniac_critic  mm排名
 * @param dt                   时间分区
 * @param filaname             文件名
 */
//def test(x: Option[Long], y: Int)
case class DwsAlbum(
                     id: Int,
                     artist_id: Int,
                     real_name: String,
                     art_name: String,
                     role: String,
                     year_of_birth: String,
                     email: String,
                     country: String,
                     city: String,
                     zip_code: String,
                     album_id: String,
                     album_title: String,
                     genre: String,
                     operation_id: String,
                     operation_time: String,
                     album_year_of_pub: String,
                     num_of_tracks: Int,
                     num_of_sales: Long,
                     rolling_stone_critic: Double,
                     mtv_critic: Double,
                     music_maniac_critic: Double,
                     create_time: String,
                     dt: String,
                     filaname: String
                   )

/**
 * 专辑
 *
 * @param id
 * @param artist_id            艺术家
 * @param album_title          专辑标题
 * @param genre                流派
 * @param year_of_pub          公开年份
 * @param num_of_tracks        专辑歌曲数量
 * @param num_of_sales         专辑销售量
 * @param rolling_stone_critic 滚石杂志对专辑的等级排序
 * @param mtv_critic           MTV对专辑的等级排序
 * @param music_maniac_critic  music_maniac对专辑的等级排序
 */


case class Album(
                  id: Int,
                  artist_id: String,
                  album_title: String,
                  genre: String,
                  year_of_pub: String,
                  num_of_tracks: Int,
                  num_of_sales: Int,
                  rolling_stone_critic: Double,
                  mtv_critic: Double,
                  music_maniac_critic: Double
                )

case class Genre(
                  id: Int,
                  genre: String,
                  operationId: String,
                  recordTime: Long
                )


