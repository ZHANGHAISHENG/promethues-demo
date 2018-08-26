package com.ag.game.quiz.serializer

import com.twitter.scrooge._


/**
  * usage:
  * val r = RandomQuizRequest(Some(2), Some(5))
  * val array = ThriftSerializer.serialize(r)
  * val rr = ThriftSerializer.deserialize(RandomQuizRequest)(array)
  * assert(r == rr)
  */
object ThriftJsonSerializer {
  def deserialize[T <: ThriftStruct](t: ThriftStructCodec3[T])(s: String): T =
    TJSONProtocolThriftSerializer(t.metaData.codec).fromString(s)

  def serialize[T <: ThriftStruct with HasThriftStructCodec3[T]](x: T): String =
    TJSONProtocolThriftSerializer(x._codec).toString(x)
}