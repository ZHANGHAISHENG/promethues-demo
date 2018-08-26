package com.ag.game.quiz.serializer

import java.io.{FileInputStream, FileOutputStream}

import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import org.apache.thrift.protocol.TJSONProtocol
import org.apache.thrift.transport.TIOStreamTransport


object ThriftJsonFileSerializer {
  def serialize(thriftStruct: ThriftStruct, path: String): Unit = {
    val fileOutputStream = new FileOutputStream(path)
    try {
      val thriftTransport = new TIOStreamTransport(fileOutputStream)
      val jsonProtocol = new TJSONProtocol(thriftTransport)
      thriftStruct.write(jsonProtocol)
    } finally {
      fileOutputStream.close()
    }
  }

  def deserialize[T <: ThriftStruct](thriftStructCodec: ThriftStructCodec[T], path: String): T = {
    val fileInputStream = new FileInputStream(path)
    try {
      val jsonProtocolFactory = new TJSONProtocol.Factory
      val jsonProtocol = jsonProtocolFactory.getProtocol(new TIOStreamTransport(fileInputStream))
      thriftStructCodec.decode(jsonProtocol)
    } finally {
      fileInputStream.close()
    }
  }
}