# Fedral-Recommendation-Movie=
基于联邦学习的电影推荐系统,采用联邦学习框架FATE1.3.1的横向联邦推荐算法。

# 部署
这里只介绍FATE1.3.1的部署方案：

1、备好一台配置足够的服务器，安装CentOS 7系统。

2、安装python3.6,更改usr/bin链接。

3、安装virtualenv 和 virtualenvwrapper。

4、安装jdk1.8并配置环境变量。

5、安装mysql5.6并设置用户和密码。

6、安装Docker19.08以及Docker-Compose1.24.0

7、检查本地8080、9360、9380端口是否被占用

8、wget获取FATE1.3.1的安装包，解压。官方给定最新安装目录无法下载到1.3.1版本，正确地址为：
https://webank-ai-1251170195.cos.ap-guangzhou.myqcloud.com /docker_standalone-fate-1.3.1.tar.gz。

9、运行install_standalone_docker.sh在Docker中安装FATE框架。

10、运行docker exec命令执行FATE容器里的测试脚本验证部署是否成功。

至此，FATE联邦学习框架部署完成。

具体步骤可以参照FATE的官方文档

# 使用
本联邦学习算法有三个参与方，分别是guest，host以及aribiter,guest和host都负责训练本地模型，arbiter负责传输梯度和合并模型，以及帮助guest和host更新模型。使用流程如下：

具体可以参照https://gitee.com/mirrors/FATE/tree/v1.5.0/examples/dsl/v1
和https://gitee.com/mirrors/FATE/tree/v1.3.1/examples/federatedrec-examples使用

1、启动FATE容器。

2、新建guest节点的数据上传配置文件，在文件中标注上传文件所在位置，定义其表名和名词空间，执行命令通过本数据上传配置文件加载节点数据。

3、新建host节点的数据上传配置文件，在文件中标注上传文件所在位置，定义其名词空间，执行命令通过本数据上传配置文件加载节点数据，配置文件和2中的类似。

4、新建算法的配置文件，在文件中先定义各个节点的partyID（在FATE中party用来模拟联邦学习过程中的不同参与方），再定义各个参与方点的数据信息，即将数据的表名名词空间绑定到该参与方上。

5、在算法的配置文件中定义使用什么算法、算法使用什么参数等信息。

6、定义算法执行流程，即算法的执行模块和每个执行模块的输入输出。

7、运行执行算法命令，该命令参数即为算法的配置文件和流程配置文件。

8、在fateboard界面查看算法执行过程。

9、运行下载算法输出的命令下载算法输出文件。


