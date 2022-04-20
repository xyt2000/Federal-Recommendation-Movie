# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from scrapy.exceptions import DropItem
from scrapy.http import Request
from scrapy.pipelines.images import ImagesPipeline
import pymysql
import random

class DoubanPipeline:
    def process_item(self, item, spider):
        return item

#根据取得的图片url重新请求，下载图片到本地
class DownloadImagePipeline(ImagesPipeline):

    default_headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36",
        #"Cookie":'_vwo_uuid_v2=D65EBF690D9454DE4C13354E37DC5B9AA|3bb7e6e65f20e31141b871b4fea88dc2; __yadk_uid=QBp8bLKHjCn5zS2J5r8xV7327R0wnqkU; douban-fav-remind=1; gr_user_id=0a41d8d1-fe39-4619-827a-17961cf31795; viewed="35013197_10769749_23008813_26282806_34912177_22139960_35003794_30249691_26616244_27035127"; push_noty_num=0; push_doumail_num=0; __utmv=30149280.21320; bid=gplG4aEN4Xc; ll="108288"; ap_v=0,6.0; __utma=30149280.819011260.1572087992.1604448803.1604453561.105; __utmc=30149280; __utmz=30149280.1604453561.105.65.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __gads=ID=eddb65558a1da756-223ab4f88bc400c8:T=1604453562:RT=1604453562:S=ALNI_MZGB_I69qmiL2tt3lm57JVX1i4r2w; __utmb=30149280.4.10.1604453561; dbcl2="213202515:Ip9mjwUAab4"; ck=wxUS; __utma=223695111.897479705.1572088003.1604448803.1604455298.71; __utmb=223695111.0.10.1604455298; __utmc=223695111; __utmz=223695111.1604455298.71.42.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1604455298%2C%22https%3A%2F%2Faccounts.douban.com%2F%22%5D; _pk_ses.100001.4cf6=*; _pk_id.100001.4cf6=e11874c5506d4ab1.1572088003.71.1604455342.1604450364.'
    }

    def get_media_requests(self, item, info):
        #print('到这里来了...')
        image_url = item['cover']

        yield Request(
            image_url,
            headers=self.default_headers)

    #get_media_requests函数返回后执行
    def item_completed(self, results, item, info):
        image_paths = [x['path'] for ok, x in results if ok]
        if not image_paths:
            raise DropItem("Item contains no images")
        #返回的图片地址是full+文件名的格式，由于我是边爬边下载，所以每次只有一张图片，但是返回的是
        #数组，函数设计为多张图片，我将‘full’替换成了自己后台接口的地址，方便数据库中的存储
        image_paths = str(image_paths[0]).replace('full','static/photos/full')
        item['cover'] = image_paths
        return item


# 将电影信息存入到数据库中
class DBPipeline(object):
    def __init__(self):
        # connection database
        # 后面三个依次是数据库连接名、数据库密码、数据库名称
        self.connect = pymysql.connect(host='127.0.0.1', user='root', password='070500',
                                       db='db_fd_movie',charset='utf8',port=3306)
        # get cursor
        self.cursor_1 = self.connect.cursor()
        self.cursor_2 = self.connect.cursor()
        self.type_to_id = {
            '剧情': 1,'喜剧':2, '动作':3,
            '爱情': 4, '科幻':5, '动画':6,
            '悬疑': 7, '惊悚' : 8, '恐怖' : 9,
            '犯罪': 10, '同性':11, '音乐':12,
            '歌舞':13, '传记':14,'历史':15,
            '战争':16, '西部':17, '奇幻':18,
            '冒险':19, '灾难':20,'武侠':21, '情色':22
        }
        print("连接数据库成功")

    def process_item(self, item, spider):
        if item['title'] == '':
            return
        # sql语句h
        insert_movie_sql = """
        insert ignore into `movies`(post_url,title, director, scriptwriter, actors, district,rate,date,language,duration,type,abs) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """
        insert_mc_sql = """
        insert into `movie_category` (mid,cid) values (%s,%s)
        """
        # 执行插入数据到数据库操作
        self.cursor_1.execute(insert_movie_sql, (item['cover'], item['title'], item['director'], item['scriptwriter'],
                                         item['actors'],item['district'],item['rate'],
                                         item['date'],item['language'],item['duration'],item['categories'],item['abs']))
        mid = self.cursor_1.lastrowid
        #处理标签

        cids = []
        categories = item['categories'].split('/')
        for c in categories:
            if c not in self.type_to_id.keys():continue
            cids.append(self.type_to_id.get(c))
        #插入关联表
        print(cids)
        for cid in cids:
            self.cursor_2.execute(insert_mc_sql,(mid,cid))
        # 提交，不进行提交无法保存到数据库"""
        self.connect.commit()

    def close_spider(self, spider):
        # 关闭游标和连接
        self.cursor_1.close()
        self.cursor_2.close()
        self.connect.close()