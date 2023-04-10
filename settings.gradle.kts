rootProject.name = "xx-learning-boot"

// 公共项目模块
include("xx-learning-logger")
include("xx-learning-common")
include("xx-learning-springboot")

// IDE插件开发模块
// include("xx-learning-idea-plugin-client")
include("xx-learning-idea-plugin-server")

// 项目学习模块
include("xx-learning-hadoop")
include("xx-learning-elasticsearch")
include("xx-learning-clickhouse")
include("xx-learning-scala")
include("xx-learning-spark")
include("xx-learning-flink")
include("xx-learning-sharding")
include("xx-learning-mq")
include("xx-learning-netty")
include("xx-learning-zookeeper")
include("xx-learning-webflux")

// 算法刷题模块
include("xx-learning-leetcode")

// 秒杀系统学习模块
include("xx-learning-seckill")

// seata学习模块
include("xx-learning-seata")
include("xx-learning-seata:xx-learning-seata-common")
findProject(":xx-learning-seata:xx-learning-seata-common")?.name = "xx-learning-seata-common"
include("xx-learning-seata:xx-learning-seata-order")
findProject(":xx-learning-seata:xx-learning-seata-order")?.name = "xx-learning-seata-order"
include("xx-learning-seata:xx-learning-seata-goods")
findProject(":xx-learning-seata:xx-learning-seata-goods")?.name = "xx-learning-seata-goods"
include("xx-learning-seata:xx-learning-seata-pay")
findProject(":xx-learning-seata:xx-learning-seata-pay")?.name = "xx-learning-seata-pay"

