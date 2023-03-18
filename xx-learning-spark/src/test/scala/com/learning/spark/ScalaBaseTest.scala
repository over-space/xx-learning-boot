package com.learning.spark

import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

/**
 * @author over.li
 * @since 2023/3/17
 */
@RunWith(classOf[JUnitRunner])
class ScalaBaseTest extends AnyFunSuite with Serializable {

  def line(): Unit = {
    println("*************************************************************************************")
  }

  def print(msg:String): Unit = {
    println(s"************************************ $msg *******************************************")
  }

  testsFor({
    println("================================================================================================")
    println("-------------------------------------开始执行测试方法---------------------------------------------")
    println("")
  })
}
