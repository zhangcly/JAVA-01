# 第七周作业

## 周三必做题题2
按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率 /weeko7project/dbinsert1000k项目下

* 第一种方式：直接一条条插入，耗时约729s（zhangc.dbinsert100k.DataBaseInsert1）
  ![](img/1.png)
* 第二种方式：将多条数据拼接成一条sql，一次插入多条数据（zhangc.dbinsert100k.DataBaseInsert2），经测试在只有一个字段的情况下，每次5000-20000条性能最佳
  * 500条数据拼接成一条sql，分2000次插入,耗时约7s
    ![](img/2.png)
  * 1000条数据拼接成一条sql，分1000次插入,耗时约5s
    ![](img/3.png)
  * 5000条数据拼接成一条sql，分200次插入,耗时约4s
    ![](img/4.png)
  * 20000条数据拼接成一条sql，分50次插入,耗时约4s
    ![](img/5.png)
  * 1000000条数据拼接成一条sql,报错，sql太长了
    ![](img/6.png)
    设置足够的max_allowed_packet大小后，耗时约5s
    ![](img/7.png)
* 第三种方式 批处理executeBatch，
  * 使用Statement 耗时约552s （zhangc.dbinsert100k.DataBaseInsert3）
    ![](img/8.png)
  * 使用PreparedStatement 耗时约535s（zhangc.dbinsert100k.DataBaseInsert4）
    ![](img/9.png)
* 第四种方式 2与3结合
  * 批处理，每5000条数据拼成1条sql，使用Statement 耗时约4s（zhangc.dbinsert100k.DataBaseInsert5）
    ![](img/10.png)
  * 批处理，每1000条数据拼成1条sql，使用PreparedStatement 耗时约4s（zhangc.dbinsert100k.DataBaseInsert6）
    ![](img/11.png)


# 周日必做题题2
读写分离 - 动态切换数据源版本 1.0  /weeko7project/rwseparate01项目下

# 周日必做题题3
读写分离 - 数据库框架版本 2.0 /weeko7project/rwseparate02项目下

