package com.ag.game.quiz

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaType
import akka.http.scaladsl.model.MediaTypes._

import io.circe.{Decoder, Encoder, HCursor, Json}
//import play.twirl.api.{Html, Txt}


package object api {

  object ApiStatus {

    sealed abstract class ApiStatus(val value: Int)

    final case object UnKnowError extends ApiStatus(-1)

    final case object Ok extends ApiStatus(0)

    final case object Forbidden extends ApiStatus(4)

    final case object InternalServerError extends ApiStatus(5)

    final case object TokenInvalid extends ApiStatus(7)

    final case object TokenExpired extends ApiStatus(9)

    final case object ParameterError extends ApiStatus(8)

    final case object UserInfoError extends ApiStatus(10)

    final case object WxTokenExpiredError extends ApiStatus(11)

    final case object CheatError extends ApiStatus(12)

    final case object UserNotExistsError extends ApiStatus(13)

    final case object UserHasLoginByOtherWayError extends ApiStatus(14)

    final case object EnergyError extends ApiStatus(21)

    def fromValue(value: Int): ApiStatus = value match {
      case UnKnowError.value => UnKnowError
      case Ok.value => Ok
      case Forbidden.value => Forbidden
      case InternalServerError.value => InternalServerError
      case TokenInvalid.value => TokenInvalid
      case TokenExpired.value => TokenExpired
      case ParameterError.value => ParameterError
      case UserInfoError.value => UserInfoError
      case WxTokenExpiredError.value => WxTokenExpiredError
      case CheatError.value => CheatError
      case UserNotExistsError.value => UserNotExistsError
      case UserHasLoginByOtherWayError.value => UserHasLoginByOtherWayError
      case EnergyError.value => EnergyError
      case _ => InternalServerError
    }

  }

  def apiStatusFromValue(value: Int) = ApiStatus.fromValue(value)

  import ApiStatus.ApiStatus

  sealed trait ApiData {
    def status: ApiStatus
  }

  final case class Ok(status: ApiStatus) extends ApiData

  object Ok {
    def apply(): Ok = Ok(ApiStatus.Ok)
  }

  final case class Good[T](data: T, status: ApiStatus) extends ApiData

  object Good {
    def apply[T](data: T): Good[T] = Good(data, ApiStatus.Ok)
  }

  final case class ApiError(status: ApiStatus, message: Option[String]) extends ApiData

  object ApiError {

    def apply(status: ApiStatus, message: String): ApiError = ApiError(status, Some(message))

    def apply(status: ApiStatus): ApiError = ApiError(status, None)

    def tokenExpired = ApiError(status = ApiStatus.TokenExpired)

    def tokenInvalid = ApiError(status = ApiStatus.TokenInvalid)

  }

  implicit def apiStatusEncoder[A <: ApiStatus.ApiStatus]: Encoder[A] = (x: A) => Json.fromInt(x.value)

  implicit def apiStatusDecoder[A <: ApiStatus.ApiStatus]: Decoder[A] = Decoder.decodeInt.map(ApiStatus.fromValue).map(_.asInstanceOf[A])

  /** Serialize Twirl `Html` to `text/html`. */
  //implicit val twirlHtmlMarshaller: ToEntityMarshaller[Html] = twirlMarshaller[Html](`text/html`)

  /** Serialize Twirl `Txt` to `text/plain`. */
 // implicit val twirlTxtMarshaller: ToEntityMarshaller[Txt] = twirlMarshaller[Txt](`text/plain`)

  /** Serialize Twirl formats to `String`. */
  protected def twirlMarshaller[A <: AnyRef](contentType: MediaType): ToEntityMarshaller[A] =
    Marshaller.StringMarshaller.wrap(contentType)(_.toString)

}
