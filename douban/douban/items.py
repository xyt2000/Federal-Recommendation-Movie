# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class DoubanItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    #电影标题
    title = scrapy.Field()
    #导演
    director = scrapy.Field()
    #编剧
    scriptwriter = scrapy.Field()
    #演员
    actors = scrapy.Field()
    #上映日期
    date = scrapy.Field()
    #评分
    rate = scrapy.Field()
    #国家/地区
    district = scrapy.Field()
    #语言
    language = scrapy.Field()
    #封面图片
    cover = scrapy.Field()
    #简介
    abs = scrapy.Field()
    #类型
    categories = scrapy.Field()
    #时长
    duration = scrapy.Field()
