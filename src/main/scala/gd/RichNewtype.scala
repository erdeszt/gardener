package gd

import zio.jdbc.*
import zio.prelude.Newtype
import zio.schema.Schema

trait RichNewtype[T](using Schema[T]) extends Newtype[T]:
  given schema: Schema[Type] =
    Schema[T].transform[Type](wrap, unwrap)
  given jdbcDecoder: JdbcDecoder[Type] = JdbcDecoder.fromSchema[Type]
  given jdbcEncoder: JdbcEncoder[Type] = JdbcEncoder.fromSchema[Type]
