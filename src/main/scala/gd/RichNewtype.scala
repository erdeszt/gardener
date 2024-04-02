package gd

import zio.jdbc.*
import zio.prelude.Newtype
import zio.schema.Schema

trait AddSchema[T: Schema]:
  self: Newtype[T] =>

  given schema: Schema[Type] =
    Schema[T].transform[Type](wrap, unwrap)

trait AddJdbcDecoder[T: JdbcDecoder]:
  self: Newtype[T] =>

  given jdbcDecoder: JdbcDecoder[Type] = JdbcDecoder[T].map(wrap)

trait AddJdbcEncoder[T: JdbcEncoder]:
  self: Newtype[T] =>

  given jdbcEncoder: JdbcEncoder[Type] = JdbcEncoder[T].contramap(unwrap)

trait AddJdbcCodec[T: JdbcDecoder: JdbcEncoder]
    extends AddJdbcDecoder[T]
    with AddJdbcEncoder[T]:
  self: Newtype[T] =>

trait NewtypeWithCodecs[T: Schema: JdbcDecoder: JdbcEncoder]
    extends Newtype[T]
    with AddSchema[T]
    with AddJdbcCodec[T]
