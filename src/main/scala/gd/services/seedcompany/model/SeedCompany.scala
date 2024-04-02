package gd.services.seedcompany.model

import java.util.UUID

import com.github.f4b6a3.ulid.Ulid
import zio.Chunk
import zio.jdbc.*
import zio.json.JsonCodec
import zio.prelude.Newtype
import zio.schema.Schema.Field
import zio.schema.{DeriveSchema, Schema, StandardType}

import gd.*
import gd.interop.ulid.given

case class SeedCompany(
    id: SeedCompany.Id,
    name: SeedCompany.Name,
)

object SeedCompany:
  object Id extends NewtypeWithCodecs[Ulid]
  type Id = Id.Type

  object Name extends NewtypeWithCodecs[String]
  type Name = Name.Type

  given schema: Schema[SeedCompany] = DeriveSchema.gen
  given jdbcDecoder: JdbcDecoder[SeedCompany] =
    JdbcDecoder[(Id, Name)].map(SeedCompany.apply)
  given jdbcEncoder: JdbcEncoder[SeedCompany] =
    JdbcEncoder[(Id, Name)].contramap(company => (company.id, company.name))
  given jsonCodec: JsonCodec[SeedCompany] =
    zio.schema.codec.JsonCodec.jsonCodec(schema)
