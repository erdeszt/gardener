package gd.services.seedcompany.model

import java.util.UUID

import zio.jdbc.*
import zio.json.JsonCodec
import zio.prelude.Newtype
import zio.schema.Schema.Field
import zio.schema.{DeriveSchema, Schema}

import gd.*

// TODO: Use strong types
case class SeedCompany(
    id: UUID, //  SeedCompany.Id,
    name: String, // SeedCompany.Name,
)

object SeedCompany:
  object Id extends RichNewtype[UUID]
  type Id = Id.Type

  object Name extends RichNewtype[String]
  type Name = Name.Type

  // TODO: Figure out why .fromSchema doesn't work (bounty?)
  given schema: Schema[SeedCompany] = DeriveSchema.gen
  given jdbcDecoder: JdbcDecoder[SeedCompany] =
    JdbcDecoder[(UUID, String)].map(SeedCompany.apply) //  JdbcDecoder.fromSchema
  given jdbcEncoder: JdbcEncoder[SeedCompany] = JdbcEncoder[(UUID, String)]
    .contramap(company => (company.id, company.name)) // JdbcEncoder.fromSchema
  given jsonCodec: JsonCodec[SeedCompany] =
    zio.schema.codec.JsonCodec.jsonCodec(schema)
