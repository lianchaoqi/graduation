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
 * @param record_time          记录时间
 * @param dt                   日期分区
 * @param fileName             文件名
 */
case class DwdAlbum(
                     id: Int,
                     artist_id: String,
                     album_title: String,
                     genre: String,
                     year_of_pub: String,
                     num_of_tracks: Int,
                     num_of_sales: Int,
                     rolling_stone_critic: Double,
                     mtv_critic: Double,
                     music_maniac_critic: Double,
                     record_time: String,
                     dt: String,
                     fileName: String
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