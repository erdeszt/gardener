package gd.services.seedcompany

import java.util.UUID

import zio.jdbc.*
import zio.prelude.Newtype
import zio.schema.{DeriveSchema, Schema}

import gd.*

case class SeedCompany(
    id: SeedCompany.Id,
    name: SeedCompany.Name,
)

object SeedCompany:
  object Id extends RichNewtype[UUID]
  type Id = Id.Type

  object Name extends RichNewtype[String]
  type Name = Name.Type

  given schema: Schema[SeedCompany] = DeriveSchema.gen[SeedCompany]
  given jdbcDecoder: JdbcDecoder[SeedCompany] = JdbcDecoder.fromSchema
  given jdbcEncoder: JdbcEncoder[SeedCompany] = JdbcEncoder.fromSchema
