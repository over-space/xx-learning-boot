rootProject.name = "xx-learning-boot"

include("xx-learning-idea-plugin-client")
include("xx-learning-idea-plugin-server")
include("xx-learning-leetcode")
include("xx-learning-hadoop")
include("xx-learning-spark")
include("xx-learning-common")
include("xx-learning-mq")
include("xx-learning-netty")
include("xx-learning-logger")
include("xx-learning-seckill")
include("xx-learning-zookeeper")
include("xx-learning-sharding")
include("xx-learning-scala")
include("xx-learning-flink")
include("xx-learning-elasticsearch")
include("xx-learning-clickhouse")
include("xx-learning-springboot")
include("xx-learning-seata")
include("xx-learning-seata:xx-learning-seata-order")
findProject(":xx-learning-seata:xx-learning-seata-order")?.name = "xx-learning-seata-order"
include("xx-learning-seata:xx-learning-seata-goods")
findProject(":xx-learning-seata:xx-learning-seata-goods")?.name = "xx-learning-seata-goods"
include("xx-learning-seata:xx-learning-seata-pay")
findProject(":xx-learning-seata:xx-learning-seata-pay")?.name = "xx-learning-seata-pay"
include("xx-learning-seata:xx-learning-seata-api")
findProject(":xx-learning-seata:xx-learning-seata-api")?.name = "xx-learning-seata-api"
include("xx-learning-webflux")
