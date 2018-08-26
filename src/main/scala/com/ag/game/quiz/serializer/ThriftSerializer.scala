package com.ag.game.quiz.serializer

// import com.ag.game.quiz.idl.thriftscala.RandomQuizRequest
// import org.apache.thrift.Protocol.TBinaryProtocol
import com.twitter.scrooge.{BinaryThriftStructSerializer, HasThriftStructCodec3, ThriftStruct, ThriftStructCodec3}
import org.apache.thrift.protocol.{TBinaryProtocol, TProtocol}
import org.apache.thrift.transport.TMemoryInputTransport


/**
  * usage:
  * import ThriftSerializer._
  * val r = RandomQuizRequest(Some(2), Some(5))
  * val bytes = r.toByteArray
  * val rr = RandomQuizRequest.fromByteArray(bytes)
  * assert(r == rr)
  */
object ThriftSerializer {

  private[this] val ProtocolFactory = new TBinaryProtocol.Factory

//  def deserialize[T <: ThriftStruct](t: ThriftStructCodec3[T])(array: Array[Byte]): T =
//    BinaryThriftStructSerializer(t.metaData.codec).fromBytes(array)
//
//  def serialize[T <: ThriftStruct](t: ThriftStructCodec3[T])(x: T): Array[Byte] =
//    BinaryThriftStructSerializer(t.metaData.codec).toBytes(x)

  implicit class ThriftSerializerImplicit[T <: ThriftStruct with HasThriftStructCodec3[T]](t: T) {
    def  toByteArray: Array[Byte] = BinaryThriftStructSerializer(t._codec).toBytes(t)
  }

  implicit class ThriftDeSerializerImplicit[T <: ThriftStruct](t: ThriftStructCodec3[T]) {
    def fromByteArray(bytes: Array[Byte]): T = {
      val buf = new TMemoryInputTransport(bytes)
      val p: TProtocol = ProtocolFactory.getProtocol(buf)
      buf.close()
      t.metaData.codec.decode(p)
    }
  }


  //  implicit class ThriftStructCodec3Serializer[T <: HasThriftStructCodec3[_]](t: T) {
  //    def toByteArray(f: (T, TProtocol) => Unit): Array[Byte] = {
  //      val buf = new TMemoryBuffer(32)
  //      val p = ProtocolFactory.getProtocol(buf)
  //      f(t, p)
  //      val r = buf.getArray
  //      buf.close()
  //      r
  //    }
  //
  //  }
  //
  //  implicit class TTT[T <: ThriftStruct](t: T) {
  //    def asByteArray(): Array[Byte] = {
  //      val buf = new TMemoryBuffer(32)
  //      val p = ProtocolFactory.getProtocol(buf)
  //      t.write(p)
  //      val r = buf.getArray
  //      buf.close()
  //      r
  //    }
  //  }
}

