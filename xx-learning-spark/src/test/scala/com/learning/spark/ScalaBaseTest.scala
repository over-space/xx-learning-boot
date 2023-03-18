package com.learning.spark

import org.junit.{AfterClass, BeforeClass}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

/**
 * @author over.li
 * @since 2023/3/17
 */
@RunWith(classOf[JUnitRunner])
trait ScalaBaseTest extends AnyFunSuite with Serializable {

  def line(): Unit = {
    println("*************************************************************************************")
  }

  def print(msg:String): Unit = {
    println(s"************************************ $msg *******************************************")
  }

  @BeforeClass
  def before(){
    println("================================================================================================")
    println("-------------------------------------开始执行测试方法---------------------------------------------")
    println("")
  }

  @AfterClass
  def after(): Unit = {
    println("")
    println("-------------------------------------测试方法执行完成---------------------------------------------")
    println("================================================================================================")
  }
}
