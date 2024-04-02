package gd.services.seedcompany.internal

import java.util.UUID

import com.github.f4b6a3.ulid.Ulid
import zio.*
import zio.jdbc.*

import gd.services.seedcompany.model.SeedCompany

class SeedCompaniesRepoLive extends SeedCompaniesRepo:

  override def list: URIO[ZConnection, Chunk[SeedCompany]] =
    sql"select id, name from seed_company"
      .query[(UUID, String)]
      .as[SeedCompany]
      .selectAll
      .orDie

  override def insert(
      name: SeedCompany.Name,
  ): URIO[ZConnection, SeedCompany.Id] =
    val id = SeedCompany.Id(Ulid.fast())
    sql"insert into seed_company (id, name)"
      .values(SeedCompany(id, name))
      .insert
      .orDie
      .as(id)
