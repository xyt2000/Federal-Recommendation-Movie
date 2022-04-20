# -*- coding: utf-8 -*-
import scrapy
import json
import re
import time
from douban.items import DoubanItem
from fake_useragent import UserAgent
import random

class MovieHotSpider(scrapy.Spider):
    #爬虫的名称，在命令行可以方便的运行爬虫
    name = "movie"
    allowed_domains = ["movie.douban.com"]
    #pro = ['139.224.37.83','115.223.7.110','221.122.91.75']
    # 拼接豆瓣电影URL
    BASE_URL = 'https://movie.douban.com/j/search_subjects?type=movie&tag=%s&sort=recommend&page_limit=%s&page_start=%s'
    MOVIE_TAG = '最新'
    PAGE_LIMIT = 20
    page_start = 0

    domains = BASE_URL % (MOVIE_TAG, PAGE_LIMIT, page_start)

    #伪装浏览器
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36"
        #,"Cookie":'_vwo_uuid_v2=D65EBF690D9454DE4C13354E37DC5B9AA|3bb7e6e65f20e31141b871b4fea88dc2; __yadk_uid=QBp8bLKHjCn5zS2J5r8xV7327R0wnqkU; douban-fav-remind=1; gr_user_id=0a41d8d1-fe39-4619-827a-17961cf31795; viewed="35013197_10769749_23008813_26282806_34912177_22139960_35003794_30249691_26616244_27035127"; push_noty_num=0; push_doumail_num=0; __utmv=30149280.21320; bid=gplG4aEN4Xc; ll="108288"; ap_v=0,6.0; __utma=30149280.819011260.1572087992.1604448803.1604453561.105; __utmc=30149280; __utmz=30149280.1604453561.105.65.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __gads=ID=eddb65558a1da756-223ab4f88bc400c8:T=1604453562:RT=1604453562:S=ALNI_MZGB_I69qmiL2tt3lm57JVX1i4r2w; __utmb=30149280.4.10.1604453561; dbcl2="213202515:Ip9mjwUAab4"; ck=wxUS; __utma=223695111.897479705.1572088003.1604448803.1604455298.71; __utmb=223695111.0.10.1604455298; __utmc=223695111; __utmz=223695111.1604455298.71.42.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1604455298%2C%22https%3A%2F%2Faccounts.douban.com%2F%22%5D; _pk_ses.100001.4cf6=*; _pk_id.100001.4cf6=e11874c5506d4ab1.1572088003.71.1604455342.1604450364.'
    }

    #总共爬取的页数
    pages = 10
    # 爬虫从此开始
    def start_requests(self):
        print('~~~~爬取列表: '+ self.domains)
        yield scrapy.Request(
            url = self.domains,
            headers=self.headers,
            callback=self.request_movies
        )

    # 分析列表页
    def request_movies(self, response):
        infos = response.text
        # 使用JSON模块解析响应结果
        infos = json.loads(infos)
        # 迭代影片信息列表
        for movie_info in infos['subjects']:
            print('~~~爬取电影: ' + movie_info['title'] + '/'+ movie_info['rate'])
            # 提取影片页面url，构造Request发送请求，并将item通过meta参数传递给影片页面解析函数
            yield scrapy.Request(
                url = str(movie_info['url']),
                headers = self.headers,
                callback = self.request_movie,
                dont_filter=True
            )
        #如果已经爬完pages或者当前标签下没有更多电影时退出
        if self.pages > 0 and len(infos['subjects']) == self.PAGE_LIMIT:
            self.pages -= 1
            self.page_start += self.PAGE_LIMIT
            url = self.BASE_URL % (self.MOVIE_TAG,self.PAGE_LIMIT,self.page_start)
            time.sleep(5)
            print('-----爬取列表: ' + url)
            yield scrapy.Request(
                url=url,
                headers=self.headers,
                callback=self.request_movies,
                dont_filter=True
            )

    # 分析详情页
    def request_movie(self, response):
        #组装数据
        movie_item = DoubanItem()
        title = response.css('div#content>h1>span:nth-child(1)::text').extract_first()
        t = re.findall('[\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b\u4e00-\u9fa5_0-9]', title)
        #获取非info区域数据
        movie_item['title'] = ''.join(t)
        movie_item['date'] = response.css('div#content>h1>span.year::text').extract_first()[1:-1]
        movie_item['rate'] = response.css('strong.rating_num::text').extract_first()
        #movie_item['commentCount'] = response.css('div.rating_sum>a.rating_people>span::text').extract_first()
        #movie_item['start'] = '/'.join(response.css('span.rating_per::text').extract())
        #movie_item['better'] = '/'.join(response.css('div.rating_betterthan>a::text').extract())
        movie_item['abs'] = response.css('#link-report>span::text').extract_first().strip()
        movie_item['cover'] = response.css('#mainpic>a>img::attr(src)').extract_first()

        # 获取整个信息字符串
        info = response.css('div.subject div#info').xpath('string(.)').extract_first()
        # 提取所以字段名
        fields = [s.strip().replace(':', '') for s in response.css('div#info span.pl::text').extract()]
        # 提取所有字段的值
        values = [re.sub('\s+', '', s.strip()) for s in re.split('\s*(?:%s):\s*' % '|'.join(fields), info)][1:]
        # 处理列名称
        for i in range(len(fields)):

            if '导演' == fields[i]:
                fields[i] = 'director'
            if '编剧' == fields[i]:
                fields[i] = 'scriptwriter'
            if '主演' == fields[i]:
                fields[i] = 'actors'
            if '类型' == fields[i]:
                fields[i] = 'categories'
            if '制片国家/地区' == fields[i]:
                fields[i] = 'district'
            if '语言' == fields[i]:
                fields[i] = 'language'
            if '片长' == fields[i]:
                fields[i] = 'duration'
        # 将所有信息填入item
        other_info = list(zip(fields,values))
        for field,value  in other_info:
            if field in ['IMDb链接','上映日期','官方网站','又名']:
                other_info.remove((field,value))
        final_info = dict(other_info[:-1])
        movie_item.update(final_info)

        # 处理缺失字段
        if not 'director' in movie_item.keys():
            movie_item['director'] = '/'
        if not 'scriptwriter' in movie_item.keys():
            movie_item['scriptwriter'] = '/'
        if not 'actors' in movie_item.keys():
            movie_item['actors'] = '/'
        if not 'categories' in movie_item.keys():
            movie_item['categories'] = '/'
        if not 'district' in movie_item.keys():
            movie_item['district'] = '/'
        if not 'language' in movie_item.keys():
            movie_item['language'] = '/'
        if not 'duration' in movie_item.keys():
            movie_item['duration'] = '/'
        print('~完成爬取电影: ' + movie_item['title'] + '/' + movie_item['rate'])
        #将数据加入到字典中
        yield movie_item
